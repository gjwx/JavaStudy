package com.ximalaya.openapi.admin.client;

import com.alibaba.fastjson.JSON;
import com.ximalaya.mainstay.common.RpcContext;
import com.ximalaya.omp.openapi.payment.entity.*;
import com.ximalaya.omp.openapi.payment.ex.OpenAPIRuntimeException;
import com.ximalaya.omp.reward.common.beans.BindInfo;
import com.ximalaya.omp.reward.common.beans.enums.BindReason;
import com.ximalaya.omp.reward.common.beans.enums.FromBusiness;
import com.ximalaya.omp.reward.common.beans.enums.RewardType;
import com.ximalaya.openapi.admin.aspect.annotation.AopLog;
import com.ximalaya.openapi.admin.ex.OpenAPIThirdPartyServiceException;
import com.ximalaya.openapi.service.vuser.inf.iface.thrift.OpenApiNewUserDTO;
import com.ximalaya.openapi.service.vuser.inf.iface.thrift.OpenApiVuserService;
import com.ximalaya.openapi.service.vuser.inf.iface.thrift.OperateType;
import com.ximalaya.openapi.service.vuser.inf.iface.thrift.UidSourceType;
import com.ximalaya.openplatform.signature.service.AppServiceClient;
import com.ximalaya.service.profile.thrift.BasicUserInfo;
import com.ximalaya.service.profile.thrift.RemoteUserInfoQueryService;
import com.ximalaya.userservice.facade.model.PlatformId;
import com.ximalaya.userservice.facade.model.RegisterReq;
import com.ximalaya.userservice.facade.model.RegisterResult;
import com.ximalaya.userservice.facade.model.UserInfo;
import com.ximalaya.userservice.facade.service.impl.ThriftRemoteSyncRegisterServiceFacade;
import com.ximalaya.userservice.facade.service.impl.ThriftRemoteSyncUserServiceAuthenticationFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static com.ximalaya.openapi.log.AutoLog.autoLog;

/**
 * @author yunfeng.lu
 * @create 2019-06-18.
 */
@Slf4j
@Service
public class AccountClient {

    @Autowired
    private ThriftRemoteSyncUserServiceAuthenticationFacade userServiceAuthenticationFacade;

    @Autowired
    private ThriftRemoteSyncRegisterServiceFacade           registerServiceFacade;

    @Autowired
    private RemoteUserInfoQueryService.Iface                userInfoQuery;

    @Autowired
    private AppServiceClient                                appServiceClient;

    @Autowired
    @Qualifier("openApiVuserService")
    private OpenApiVuserService.Iface                       vuserService;

    @Value("${user.register.sms.template.id}")
    private String                                          userRegisterTemplateId;

    @Autowired
    @Qualifier("userRegisterSmsNotifyTemplateV2")
    private RabbitTemplate                                  userRegisterSmsNotifyTemplateV2;

    @Autowired
    @Qualifier("rewardbindNotifyTemplate")
    private RabbitTemplate                                  rewardbindNotifyTemplate;

    @Value("${user.register.signature.app.id}")
    private String                                          userRegisterAppId;

    @Value("${user.register.signature.app.security.key}")
    private String                                          userRegisterAppSecurityKey;

    private final ThreadPoolExecutor                        threadPool = new ThreadPoolExecutor(
                    2,
                    4,
                    60L,
                    TimeUnit.SECONDS,
                    new LinkedBlockingQueue<>(),
                    new CustomizableThreadFactory("omp-payment-open-api-account-pool-"));

    //---------------------------------------------------------------

    /**
     * 对于未绑定的手机号注册为喜马拉雅用户,并返回uid；对于已经注册的手机号，查询uid并返回
     * 基本参数 mobile，userRegisterSmsNotification，appKey，developerId 分别为 手机号，短信通知开启与否的标识（0-不开启短信通知 1-开启短信通知），appKey，开发者ID
     * 新增增加风控所需参数 ua,clientIp,deviceId 分别为用户客户端代理，用户客户端ip，用户客户端设备ID
     *
     * @return 注：依赖此方法的接口应该要求对接方 提供 ua, clientIp, clientDeviceId 等风控参数
     */
    @AopLog
    public long registerIfMPhoneNotBoundedWithRiskControl(PayCommonQueryParam payCommonQueryParam){

        RiskControlModel riskControlModel = new RiskControlModel(payCommonQueryParam);

        // 辅助拉新相关逻辑
        OperateType opType = OperateType.LOGIN;
        UidSourceType srcType = UidSourceType.RECHARGE;
        String appKey = payCommonQueryParam.getAppKey();

        // 查询并校验mobile对应uid存在性
        String mobile = payCommonQueryParam.getMobile();

        Long uid = queryUserInfoByMobile(mobile);
        if (uid.compareTo(0L) <= 0){
            Map<String, Object> clientInfoMap = new HashMap<>();
            clientInfoMap.put("ip", riskControlModel.getDeviceIp());
            clientInfoMap.put("deviceId", Objects.nonNull(riskControlModel.getDeviceId()) ? riskControlModel.getDeviceId() : "");
            clientInfoMap.put("userAgent", riskControlModel.getDeviceAgent());
            clientInfoMap.put("clientPort", "");
            RpcContext.getContext().getAttachment().put("clientInfo", JSON.toJSONString(clientInfoMap));
            RegisterReq registerReq = new RegisterReq();
            registerReq.setPlatformId(PlatformId.OPEN);
            registerReq.setPlatformThirdpartyIdentity(appKey);
            registerReq.setMobile(mobile).doSignature(userRegisterAppId, userRegisterAppSecurityKey);
            RegisterResult registerResult = registerServiceFacade.register(registerReq);
            uid = registerResult.getUid();
            // 手机注册失败
            if (Objects.isNull(uid) || (Objects.nonNull(uid) && uid.compareTo(0L) <= 0)){
                log.warn(
                                autoLog(
                                                "注册失败, req:{},resp:{},code:{},msg:{}",
                                                mobile,
                                                registerResult,
                                                registerResult.getErrorCode(),
                                                registerResult.getErrorMsg()));
                throw new OpenAPIRuntimeException(400, "user-service " + String.format("mobile:%s register fail!", mobile));
            }

            log.info(autoLog("注册新UID mobile:[{}] appKey:[{}] registerResult:[{}]", mobile, appKey, registerResult));
            // 注册成功时，异步发送消息，用于通知用户注册成为喜马拉雅用户以及初始密码
            // 0-不开启短信通知 1-开启短信通知
            if (payCommonQueryParam.isEnableUserRegisterSmsNotification()){
                ShortMessageDTO shortMessageDTO = new ShortMessageDTO();
                shortMessageDTO.setTemplateId(userRegisterTemplateId);
                shortMessageDTO.setMobile(mobile);
                log.info(autoLog("send user register message, mobile: {}", mobile));
                userRegisterSmsNotifyTemplateV2.convertAndSend(shortMessageDTO);
            }
            // 如果是新用户, 则调用绑定服务 errorCode = 20004为老用户
            if (registerResult.getSuccess() && registerResult.getErrorCode() != 20004){
                newUserBind(uid, mobile, appKey, payCommonQueryParam.getDeveloperId());
            }
            opType = OperateType.REGISTER;
        }

        // uid信息异步落盘，不影响主流程
        if (null != uid){
            threadPool.submit(
                            new UidRegisterOrLogin(
                                            new OpenApiNewUserDTO.Builder().setAppKey(appKey).setUid(uid).setSrcType(srcType)
                                                            .setOpType(opType).build()));
        }
        return uid;
    }

    @AopLog
    public long registerIfMPhoneNotBounded(PayCommonQueryParam payCommonQueryParam){

        String appKey = payCommonQueryParam.getAppKey();
        // 查询并校验mobile对应uid存在性
        String mobile = payCommonQueryParam.getMobile();

        Long uid = queryUserInfoByMobile(mobile);
        if (uid.compareTo(0L) <= 0){
            //            Map<String, Object> clientInfoMap = new HashMap<>();
            RegisterReq registerReq = new RegisterReq();
            registerReq.setPlatformId(PlatformId.OPEN);
            registerReq.setPlatformThirdpartyIdentity(appKey);
            registerReq.setMobile(mobile).doSignature(userRegisterAppId, userRegisterAppSecurityKey);
            RegisterResult registerResult = registerServiceFacade.register(registerReq);
            uid = registerResult.getUid();
            // 手机注册失败
            if (Objects.isNull(uid) || (Objects.nonNull(uid) && uid.compareTo(0L) <= 0)){
                log.warn(
                                autoLog(
                                                "注册失败, req:{},resp:{},code:{},msg:{}",
                                                mobile,
                                                registerResult,
                                                registerResult.getErrorCode(),
                                                registerResult.getErrorMsg()));
                throw new OpenAPIRuntimeException(400, "user-service " + String.format("mobile:%s register fail!", mobile));
            }

            log.info(autoLog("注册新UID mobile:[{}]  registerResult:[{}]", mobile, registerResult));
        }
        return uid;
    }

    private class UidRegisterOrLogin implements Runnable{

        private OpenApiNewUserDTO userDTO;

        public UidRegisterOrLogin(OpenApiNewUserDTO userDTO){
            this.userDTO = userDTO;
        }

        @Override
        public void run(){
            try{
                log.info(autoLog("start vuserService::ximaUserRegisterOrLogin({})", userDTO));
                vuserService.ximaUserRegisterOrLogin(userDTO);
            }catch (Throwable e){
                log.warn(autoLog("vuserService::ximaUserRegisterOrLogin(userDTO:{}) failed", userDTO));
            }
        }
    }

    @AopLog
    private void newUserBind(long consumerUid,String consumerMobile,String appKey,Long developerId){
        THREAD_POOL_EXECUTOR_HELPER.submit(() -> {
            BindInfo bindInfo = new BindInfo();
            bindInfo.setUid(consumerUid);
            bindInfo.setRewardType(RewardType.REWARD_ACQUISITION);
            LocalDateTime now = LocalDateTime.now();
            Long bindTime = localDateTimeToDate(now).getTime();
            bindInfo.setBindTime(bindTime);
            bindInfo.setInvalidTime(localDateTimeToDate(now.plusYears(1)).getTime());
            bindInfo.setBindReason(BindReason.NEW_USER);
            bindInfo.setFromBusiness(FromBusiness.API_DIRECT_RECHARGE);
            bindInfo.setAppKey(appKey);
            bindInfo.setFromApp(appServiceClient.getAppByAppKey(appKey).getName());
            bindInfo.setDeveloperId(developerId);
            bindInfo.setMobile(consumerMobile);
            log.info(autoLog("newUserBind message send uid:{}, developerId:{}, mobile:{}", consumerUid, developerId, consumerMobile));
            rewardbindNotifyTemplate.convertAndSend(bindInfo);
        });
    }

    /**
     * localDateTime 类型转 Date 类型
     *
     * @param localDateTime
     *            指定日期
     */
    private static Date localDateTimeToDate(LocalDateTime localDateTime){
        localDateTime = localDateTime == null ? LocalDateTime.now() : localDateTime;
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        return Date.from(instant);
    }

    @AopLog
    public Boolean getBasicUserInfo(Long uid){
        try{
            BasicUserInfo basicUserInfo = userInfoQuery.getBasicUserInfo(uid);
            //            return Objects.nonNull(basicUserInfo) && !Objects.equals(basicUserInfo.getUid(), 0L);
            return Objects.nonNull(basicUserInfo) && basicUserInfo.isSuccess() && basicUserInfo.isSetUid()
                            && !Objects.equals(basicUserInfo.getUid(), 0L);
        }catch (Throwable e){
            log.error(autoLog("userInfoQuery.getBasicUserInfo:{}", uid), e);
            throw new OpenAPIThirdPartyServiceException("profile-proxy", e);
        }
    }

    /**
     * @param mobile
     *            手机号
     * @return 注册结果
     */
    @AopLog
    public Long queryUserInfoByMobile(String mobile){
        try{
            UserInfo userInfo = userServiceAuthenticationFacade.queryUserInfoByMobile(mobile);
            if (null == userInfo){
                log.warn(autoLog("mobile:[{}] noUserInfo return0", mobile));
                return 0L;
            }

            //---------------------------------------------------------------

            Long uid = userInfo.getUid();
            if (null == uid){
                log.warn(autoLog("mobile:[{}] uidIs0 return0 userInfo:[{}]", mobile, userInfo));
                return 0L;
            }

            //---------------------------------------------------------------
            // 查询并校验mobile对应uid存在性
            log.info(autoLog("mobile:[{}] uid:[{}] userInfo:[{}]", mobile, uid, userInfo));
            return uid;
        }catch (Exception e){
            log.error(autoLog("userServiceAuthenticationFacade.queryUserInfoByMobile mobile:{}", mobile), e);
            throw new OpenAPIThirdPartyServiceException("userServiceAuthenticationFacade", e);
        }
    }

    public void processUidAndDomain(QueryParam queryParam){
        long uid;
        int domain;

        switch (queryParam.getAccountType()) {
            case THIRD_UID:
                uid = queryParam.getPayCommonQueryParam().getOrderUid();
                domain = DomainType.THIRD_PARTY.getValue();
                break;
            case MOBILE:
                String mobile = queryParam.getPayCommonQueryParam().getMobile();
                uid = queryUserInfoByMobile(mobile).longValue();
                if (uid > 0){
                    //已注册情况下，直接查uid返回
                    domain = DomainType.XIMALAYA.getValue();
                }else{
                    uid = -1L;
                    domain = -1;
                }
                break;
            case UID:
            case ACCESS_TOKEN:
                uid = queryParam.getPayCommonQueryParam().getUid();
                domain = DomainType.XIMALAYA.getValue();
                break;
            default:
                uid = -2L;
                domain = -2;
        }

        queryParam.setUid(uid);
        queryParam.setDomain(domain);
    }

}
