package com.ximalaya.openapi.admin.aspect.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.feilong.json.JsonUtil;
import com.google.common.base.Joiner;
import com.ximalaya.danube.core.model.DanubeMessage;
import com.ximalaya.mainstay.common.MainstayTimeoutException;
import com.ximalaya.mainstay.common.MainstayTransportException;
import com.ximalaya.omp.distributor.api.model.DistributeRequestWithRetryLogDto;
import com.ximalaya.omp.distributor.api.service.IRemoteSyncDistributeRequestLogService;
import com.ximalaya.omp.facade.distribution.api.thrift.iface.TOperationResult;
import com.ximalaya.omp.facade.distribution.api.thrift.iface.TOrderData;
import com.ximalaya.omp.openapi.payment.aspect.annotation.DistributeLog;
import com.ximalaya.omp.openapi.payment.aspect.impl.handler.DistributeLogMessageCreator;
import com.ximalaya.omp.openapi.payment.config.FootballDistributeRetryConfig;
import com.ximalaya.omp.openapi.payment.entity.OrderResult;
import com.ximalaya.omp.openapi.payment.entity.PayCommonQueryParam;
import com.ximalaya.omp.openapi.payment.ex.OpenAPIRuntimeException;
import com.ximalaya.omp.openapi.payment.ex.OpenAPIThirdPartyServiceException;
import com.ximalaya.omp.openapi.payment.rocketmq.config.DistributeDelayedRetryRocketMqProducerConfig;
import com.ximalaya.omp.openapi.payment.rocketmq.message.DistributeLogMessage;
import com.ximalaya.omp.openapi.payment.util.JwtUtils;
import com.ximalaya.omp.openapi.payment.util.RedisLockUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.UndeclaredThrowableException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static com.feilong.core.date.DateUtil.formatDurationUseBeginTimeMillis;
import static com.ximalaya.openapi.log.AutoLog.autoLog;

/**
 * @author chevn
 * @create 2021-12-13.
 */
@Slf4j
@Component
@Aspect
@Order(2)
public class DistributeLogAspect{

    @Resource(name = "redisTemplate0")
    private RedisTemplate                                redisTemplate;

    @Autowired
    private RedisLockUtil                                redisLockUtil;

    @Autowired
    private DistributeDelayedRetryRocketMqProducerConfig distributeDelayedRetryRocketMqProducerConfig;

    @Autowired
    private FootballDistributeRetryConfig                footballDistributeRetryConfig;

    @Autowired
    private IRemoteSyncDistributeRequestLogService       remoteSyncDistributeRequestLogService;

    //---------------------------------------------------------------

    @Around("@annotation(distributeLog)")
    public Object distributeLog(ProceedingJoinPoint pjp,DistributeLog distributeLog) throws Throwable{

        long currentTime = System.currentTimeMillis();

        DistributeRequest distributeRequest = createDistributeRequest(pjp.getArgs());
        HttpServletRequest request = distributeRequest.getRequest();
        PayCommonQueryParam payCommonQueryParam = distributeRequest.getPayCommonQueryParam();
        String queryParamJson = JsonUtil.toString(payCommonQueryParam);

        String appKey = payCommonQueryParam.getAppKey();

        String thirdOrderNo = getThirdOrderNo(distributeRequest);

        //---------------------------------------------------------------
        String className = pjp.getTarget().getClass().getName();
        String methodName = pjp.getSignature().getName();

        //---------------------------------------------------------------
        DistributeLogMessage distributeLogMessage = null;

        //---------------------------------------------------------------
        Object result;
        try{
            distributeLogMessage = redisLockUtil.executeWithLock(
                            Joiner.on("-").join("DistributeLogKey", appKey, thirdOrderNo),
                            () -> checkAndSendLogMessage(request, payCommonQueryParam));

            //---------------------------------------------------------------

            //具体执行
            result = pjp.proceed();

            //---------------------------------------------------------------
            log.info(
                            autoLog(
                                            "{}方法{} 执行结束, distributeLogMessage:[{}] 入参:[{}] 结果:[{}]",
                                            className,
                                            methodName,
                                            JsonUtil.toString(distributeLogMessage),
                                            queryParamJson,
                                            result));

            //---------------------------------------------------------------
            JSONObject jsonResult = (JSONObject) JSON.toJSON(result);

            DistributeLogMessage finalDistributeLogMessage = distributeLogMessage;
            redisLockUtil.processWithLock(
                            Joiner.on("-").join("DistributeRetryOrLogKey", appKey, thirdOrderNo),
                            () -> checkAndSendRetryOrLogMessage(finalDistributeLogMessage, jsonResult));

            //---------------------------------------------------------------
            Object checkAndRewriteResult = checkAndRewriteResult(request, payCommonQueryParam, result, null);
            log.info(
                            autoLog(
                                            "{}.{} 最终结束, 入参:[{}] 结果:[{}], 转换结果:[{}] 耗时:{}",
                                            className,
                                            methodName,

                                            queryParamJson,
                                            jsonResult,
                                            JsonUtil.toString(checkAndRewriteResult),

                                            formatDurationUseBeginTimeMillis(currentTime)));
            return checkAndRewriteResult;
        }catch (OpenAPIRuntimeException e){
            // 这部分异常主要是传参异常
            log.warn(autoLog("OpenAPIRuntimeException 这部分异常主要是传参异常 不重试了"), e);
            throw e;

        }catch (MainstayTimeoutException | MainstayTransportException | OpenAPIThirdPartyServiceException | UndeclaredThrowableException e){
            // mainstay 超时应该根据应用配置情况 来判断是否发起重试
            log.warn(autoLog(" mainstay 超时应该根据应用配置情况 来判断是否发起重试 exception"), e);
            // 检查返回值 指定错误码的时候可以 进行重试逻辑
            JSONObject json = new JSONObject();
            // 手动为jsonResult赋值，以进行重试逻辑
            json.put("error_desc", "调用omp-distribute-facade-service 超时或其他三方服务异常" + e.getMessage());
            json.put("error_code", "501");
            json.put("error_no", "501");

            DistributeLogMessage finalDistributeLogMessage = distributeLogMessage;
            redisLockUtil.processWithLock(
                            Joiner.on("-").join("DistributeLogEndKey", appKey, request.getParameter("third_order_no")),
                            () -> checkAndSendRetryOrLogMessage(finalDistributeLogMessage, json));

            Object checkAndRewriteResult = checkAndRewriteResult(request, payCommonQueryParam, json, e);
            log.info(autoLog("checkAndRewriteResult:[{}]", JsonUtil.toString(checkAndRewriteResult)));
            return checkAndRewriteResult;
        }catch (Exception e){
            // 这部分异常主要是由 网关层服务调用的异常情况触发，错误码目前比较混乱，本次迭代暂不关注这部分异常情况的处理。
            log.warn(autoLog("其他异常 exception"), e);

            // 检查返回值 指定错误码的时候可以 进行重试逻辑
            JSONObject json = new JSONObject();
            // 手动为jsonResult赋值，以进行重试逻辑
            json.put("error_desc", "其他三方服务异常" + e.getMessage());
            json.put("error_code", "501");
            json.put("error_no", "501");

            DistributeLogMessage finalDistributeLogMessage = distributeLogMessage;
            redisLockUtil.processWithLock(
                            Joiner.on("-").join("DistributeLogExKey", appKey, request.getParameter("third_order_no")),
                            () -> checkAndSendRetryOrLogMessage(finalDistributeLogMessage, json));
            Object checkAndRewriteResult = checkAndRewriteResult(request, payCommonQueryParam, json, e);

            log.info(autoLog("checkAndRewriteResult:[{}]", JsonUtil.toString(checkAndRewriteResult)));
            return checkAndRewriteResult;

        }catch (Throwable throwable){
            log.warn(autoLog("其他错误不会重试 {}.{} 执行报错,入参:{}", className, methodName, queryParamJson), throwable);
            // 将来规范化网关层错误码后可以捕获处理重试逻辑
            throw throwable;
        }

    }

    //---------------------------------------------------------------

    private DistributeRequest createDistributeRequest(Object[] args){
        DistributeRequest distributeRequest = new DistributeRequest();
        if (args[0] instanceof PayCommonQueryParam){
            distributeRequest.setPayCommonQueryParam((PayCommonQueryParam) args[0]);
        }
        if (args[args.length - 1] instanceof HttpServletRequest){
            distributeRequest.setRequest((HttpServletRequest) args[args.length - 1]);
        }

        //---------------------------------------------------------------

        if (Objects.isNull(distributeRequest.getPayCommonQueryParam()) || Objects.isNull(distributeRequest.getRequest())){
            return Optional.of(args).map(innerArgs -> {
                DistributeRequest innerDistributeRequest = new DistributeRequest();
                for (Object obj : innerArgs){
                    if (obj instanceof PayCommonQueryParam){
                        innerDistributeRequest.setPayCommonQueryParam((PayCommonQueryParam) obj);
                    }
                    if (obj instanceof HttpServletRequest){
                        innerDistributeRequest.setRequest((HttpServletRequest) obj);
                    }
                }
                return innerDistributeRequest;
            }).filter(req -> !Objects.isNull(req.getPayCommonQueryParam()) && !Objects.isNull(req.getRequest())).orElse(null);
        }
        return distributeRequest;

    }

    //---------------------------------------------------------------

    private String getThirdOrderNo(DistributeRequest distributeRequest){
        HttpServletRequest request = distributeRequest.getRequest();
        String thirdOrderNo = request.getParameter("third_order_no");

        return !StringUtils.isBlank(thirdOrderNo) ? thirdOrderNo : distributeRequest.getPayCommonQueryParam().getThirdOrderNo();
    }

    //---------------------------------------------------------------

    private void checkAndSendRetryOrLogMessage(DistributeLogMessage distributeLogMessage,JSONObject jsonResult){
        if (Objects.isNull(distributeLogMessage)){ // 说明是分销服务自身发起的重试
            log.info(autoLog("no need do anything"));
            return;
        }

        //---------------------------------------------------------------
        String appKey = distributeLogMessage.getAppKey();
        String thirdOrderNo = distributeLogMessage.getThirdOrderNo();

        String key = appKey + ":" + thirdOrderNo;

        boolean isNeedRetry = isNeedRetry(jsonResult);
        if (!isNeedRetry){
            distributeLogMessage.setRetryStatus(2);
            Optional.ofNullable(jsonResult).map(jsonObject -> jsonObject.getString("ximaOrderNo"))
                            .ifPresent(unifiedOrderNO -> distributeLogMessage.setUnifiedOrderNo(unifiedOrderNO));

            sendDistributeDelayedRetryMq(distributeLogMessage, key);
            log.info(
                            autoLog(
                                            "errorCode不在重试列表中,不需要重试; 但发送RetryStatus2完成的消息,以便更新logDB appkey:{}, thirdOrderNo:{},响应结果:{}  消息msg:[{}] tag:[{}], key:[{}]",
                                            appKey,
                                            thirdOrderNo,

                                            jsonResult,

                                            JSON.toJSONString(distributeLogMessage),
                                            "",
                                            key));
            return;
        }

        //---------------------------------------------------------------
        Integer compositeRetryTimesByAppKey = footballDistributeRetryConfig.getCompositeRetryTimesByAppKey(appKey);
        if (compositeRetryTimesByAppKey > 0){
            log.warn(autoLog("errorCode重试待定 requestAppkey:{}, thirdOrderNo:{},响应结果:{}", appKey, thirdOrderNo, jsonResult));

            DistributeRequestWithRetryLogDto isCustomerRetry = (DistributeRequestWithRetryLogDto) redisTemplate.opsForValue()
                            .get("firstRetryMsgSendKeyPrefix:" + key);
            //            DistributeRequestWithRetryLogDto isCustomerRetry = null;
            if (Objects.isNull(isCustomerRetry) || StringUtils.isBlank(isCustomerRetry.getThirdOrderNo())){
                try{
                    isCustomerRetry = remoteSyncDistributeRequestLogService.querySimpleDistributeRequestLog(appKey, thirdOrderNo);
                }catch (Exception e){
                    log.warn(autoLog("查询重试状态出错appkey：{}, thirdOrderNo:{}, exception:{}", appKey, thirdOrderNo), e);
                    isCustomerRetry = new DistributeRequestWithRetryLogDto();
                    isCustomerRetry.setAppKey(appKey);
                    isCustomerRetry.setThirdOrderNo(thirdOrderNo);
                    isCustomerRetry.setRetryTimes(1);
                }
            }

            //---------------------------------------------------------------
            if (Optional.ofNullable(isCustomerRetry).map(DistributeRequestWithRetryLogDto::getRetryStatus)
                            .map(retryStatus -> !Integer.valueOf(0).equals(retryStatus)).orElse(false)){
                // 仅记录日志，不再发送重试消息
                log.info(
                                autoLog(
                                                "customerRetry request:{} isCustomerRetry:[{}], notDoAutoRetryAgain 仅记录日志，不再发送重试消息",
                                                JsonUtil.toString(isCustomerRetry),
                                                distributeLogMessage));
            }else{
                // 不是客户的重试请求，正常发送首次重试消息 send msg
                distributeLogMessage.setRetryStatus(1);
                distributeLogMessage.setRetryTimes(1);

                sendDelayTimeLevelMQ(distributeLogMessage, appKey, key);

                redisTemplate.opsForValue().set("firstRetryMsgSendKeyPrefix:" + key, isCustomerRetry, 3, TimeUnit.HOURS);

                log.info(autoLog("发送MQ消息 msg:[{}] tag:[{}], key:[{}]", JSON.toJSONString(distributeLogMessage), "", key));
            }
            return;
        }

        if (compositeRetryTimesByAppKey == 0){
            distributeLogMessage.setRetryStatus(3);
            distributeLogMessage.setFinalFailReason(
                            "retryTimes config is zero" + jsonResult.getString("error_code") + "-" + jsonResult.getString("error_desc"));
            sendDistributeDelayedRetryMq(distributeLogMessage, key);
            log.info(
                            autoLog(
                                            "compositeRetryTimesByAppKeyIsZero setRetryStatus3 发送MQ消息 msg:[{}] tag:[{}], key:[{}]",
                                            JSON.toJSONString(distributeLogMessage),
                                            "",
                                            key));

            return;
        }

        //---------------------------------------------------------------
        //TODO 理论上不会到这里 
        distributeLogMessage.setRetryStatus(2);
        Optional.ofNullable(jsonResult).map(jsonObject -> jsonObject.getString("ximaOrderNo"))
                        .ifPresent(unifiedOrderNO -> distributeLogMessage.setUnifiedOrderNo(unifiedOrderNO));
        sendDistributeDelayedRetryMq(distributeLogMessage, key);
        log.info(
                        autoLog(
                                        "发送MQ消息 compositeRetryTimesByAppKey:[{}] msg:[{}] tag:[{}], key:[{}]",
                                        compositeRetryTimesByAppKey,
                                        JSON.toJSONString(distributeLogMessage),
                                        "",
                                        key));
        return;
    }

    private void sendDelayTimeLevelMQ(DistributeLogMessage distributeLogMessage,String appKey,String key){
        DanubeMessage msg = DanubeMessage
                        .createRocketMessage("", key, JSON.toJSONString(distributeLogMessage).getBytes(StandardCharsets.UTF_8));
        // 设置延迟等级
        msg.setDelayTimeLevel(1 + footballDistributeRetryConfig.getCompositeDelayTimeLevelShiftByAppKey(appKey));
        distributeDelayedRetryRocketMqProducerConfig.topicOpenPlatformDistributeDelayedRetryProducer.send(msg);
    }

    private void sendDistributeDelayedRetryMq(DistributeLogMessage distributeLogMessage,String key){
        DanubeMessage msg = DanubeMessage
                        .createRocketMessage("", key, JSON.toJSONString(distributeLogMessage).getBytes(StandardCharsets.UTF_8));
        distributeDelayedRetryRocketMqProducerConfig.topicOpenPlatformDistributeDelayedRetryProducer.send(msg);
    }

    //---------------------------------------------------------------

    /**
     * 如果不是重试请求 在重试错误码的情况下返回 成功结果，成功结果只包含 status = 2 和时间信息，没有其他信息
     * 如果是异常情况，重试请求时仍然按照抛出异常的逻辑处理，非重试请求时按照 重写逻辑处理不抛异常
     */
    private Object checkAndRewriteResult(HttpServletRequest request,PayCommonQueryParam payCommonQueryParam,Object result,Exception e)
                    throws Exception{
        JSONObject jsonResult = (JSONObject) JSON.toJSON(result);
        JwtUtils.VerifyResult isRetryRequest = isRetryRequest(request);

        String thirdOrderNo = payCommonQueryParam.getThirdOrderNo();
        String appKey = Optional.ofNullable(payCommonQueryParam).map(PayCommonQueryParam::getAppKey).orElse("object is null");
        if (isRetryRequest.isVerifySucc()){
            // 说明是服务端发起的重试请求 不需要在做额外的消息发送和记录,需要设置 payCommonQueryParam 中的ip字段为 token中的ip 即原客户端ip
            if (Objects.nonNull(e)){
                log.warn(autoLog("returnThrowE result:[{}] thirdOrderNo:[{}]", JsonUtil.toString(result), thirdOrderNo), e);
                throw e;
            }
            log.info(
                            autoLog(
                                            "retry distribute result:{}, appkey:{}, thirdOrderNo:{} ",
                                            JsonUtil.toString(result),
                                            appKey,
                                            Optional.ofNullable(thirdOrderNo)));
            return result;
        }
        //---------------------------------------------------------------
        if (isNeedRetry(jsonResult)){
            OrderResult orderResult = new OrderResult(
                            new TOperationResult.Builder().setData(new TOrderData.Builder().setClearingRate("0").build()).build());
            orderResult.setXimaOrderStatus(2);//喜马拉雅订单状态：2-下单成功，3-订单取消
            orderResult.setClearingRate("");
            orderResult.setXimaOrderNo("");

            log.warn(
                            autoLog(
                                            "errorCode:[{}] 需要重试 thirdOrderNo:[{}] 原始结果result:[{}] 先返回orderResult:[{}]",
                                            jsonResult.getString("error_code"),
                                            thirdOrderNo,
                                            JsonUtil.toString(result),
                                            JsonUtil.toString(orderResult)));
            return orderResult;
        }

        //---------------------------------------------------------------
        if (Objects.nonNull(e)){
            log.warn(autoLog("有异常 returnThrowE result:[{}] thirdOrderNo:[{}]", JsonUtil.toString(result), thirdOrderNo), e);
            throw e;
        }

        //---------------------------------------------------------------
        log.info(autoLog("不需要重试,且没有异常, appkey:{}, thirdOrderNo:{} 返回result:[{}]", appKey, thirdOrderNo, JsonUtil.toString(result)));
        return result;
    }

    //---------------------------------------------------------------

    /**
     * 检查是否是重试请求，重试请求直接返回空值，且不会发送任何消息。
     * 正常的客户端请求，会发送请求记录消息，消息处理器会根据消息内容记录请求记录到数据库
     */
    private DistributeLogMessage checkAndSendLogMessage(HttpServletRequest request,PayCommonQueryParam payCommonQueryParam){
        JwtUtils.VerifyResult isRetryRequest = isRetryRequest(request);

        // 说明是服务端发起的重试请求 不需要在做额外的消息发送和记录,需要设置 payCommonQueryParam 中的ip字段为 token中的ip 即原客户端ip
        if (isRetryRequest.isVerifySucc()){
            payCommonQueryParam.setIp(isRetryRequest.getJwt().getClaim("ip").asString());
            String thirdOrderNo = request.getParameter("third_order_no");
            String appKey = Optional.ofNullable(payCommonQueryParam).map(PayCommonQueryParam::getAppKey).orElse("object is null");
            log.info(
                            autoLog(
                                            "isVerifySucc retryDistribute appkey:{},thirdOrderNo:{} returnNull",
                                            appKey,
                                            Optional.ofNullable(thirdOrderNo)));
            return null;
        }else{
            // 说明是客户发起的请求，应当做消息发送和记录到数据库
            DistributeLogMessage distributeLogMessage = DistributeLogMessageCreator
                            .createDistributeLogMessage(request, payCommonQueryParam);
            String key = distributeLogMessage.getAppKey() + ":" + distributeLogMessage.getThirdOrderNo();
            sendDistributeDelayedRetryMq(distributeLogMessage, key);

            log.info(autoLog("发送MQ消息 msg:[{}] tag:[{}], key:[{}]", JSON.toJSONString(distributeLogMessage), "", key));
            return distributeLogMessage;
        }
    }

    //---------------------------------------------------------------

    /**
     * 判断header中是否有指定的token 如果有token则是重试请求，不需要重复写记录到可能需要重试的日志数据表
     * 如果没有指定 token 则表示是客户请求，每次都需要记录请求记录
     */
    private JwtUtils.VerifyResult isRetryRequest(HttpServletRequest request){
        return Optional.ofNullable(request.getHeader("DDelayed-Retry-Token"))//
                        .filter(tk -> StringUtils.isNotBlank(tk))//
                        .map(tk -> JwtUtils.verifyAndGetRetryToken(tk))//
                        .orElse(new JwtUtils.VerifyResult(false, null));

    }

    /**
     * 判断结果是否需要重试
     * 
     * @since 2025-08-15
     */
    private boolean isNeedRetry(JSONObject jsonResult){
        ////12010,12015,12270,12425,12450,14000,14001,15001,501,500,12004
        Set<String> needRetryCodeSet = footballDistributeRetryConfig.getNeedRetryCodeSet();
        String errorCode = jsonResult.getString("error_code");

        //---------------------------------------------------------------
        return needRetryCodeSet.contains(errorCode);
    }

}
