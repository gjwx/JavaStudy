package com.ximalaya.openapi.admin.aspect.impl;

import com.ximalaya.omp.openapi.payment.config.FootballDistributeRetryConfig;
import com.ximalaya.omp.openapi.payment.entity.CompliantType;
import com.ximalaya.omp.openapi.payment.entity.PayCommonQueryParam;
import com.ximalaya.omp.openapi.payment.util.IPWhitelistCheckUtil;
import com.ximalaya.openapi.admin.aspect.annotation.Compliant;
import com.ximalaya.openplatform.appservice.thrift.iface.App;
import com.ximalaya.openplatform.signature.service.AppServiceClient;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.ximalaya.openapi.log.AutoLog.autoLog;

@Slf4j
@Component
@Aspect
public class CompliantAspect{

    @Autowired
    private AppServiceClient              appServiceClient;

    @Autowired
    private FootballDistributeRetryConfig footballDistributeRetryConfig;

    //    @Around("@annotation(com.ximalaya.omp.openapi.payment.aspect.annotation.Compliant) && args(compliant)")
    @Around("@annotation(compliant)")
    public Object checkIpWhiteList(ProceedingJoinPoint pjp, Compliant compliant) throws Throwable{
        CompliantType compliantType = compliant.type();
        PayCommonQueryParam payCommonQueryParam = (PayCommonQueryParam) pjp.getArgs()[0];
        String realIP = payCommonQueryParam.getIp();
        String methodName = pjp.getSignature().getName();
        String className = pjp.getSignature().getDeclaringType().getSimpleName();
        if (className.equals("DistributionController") && methodName.equals("distribute")
                        && footballDistributeRetryConfig.getForwardToV2DistributeAppSet().contains(payCommonQueryParam.getAppKey())){
            return pjp.proceed();
        }
        App app = appServiceClient.getAppByAppKey(payCommonQueryParam.getAppKey());
        String whiteList = app.getIpWhitelistPay();
        switch (compliantType) {
            case CLIENT_ONLY:
                payCommonQueryParam.setCompliantType(CompliantType.CLIENT_ONLY.getValue());
                break;
            case SERVER_ONLY:
                if (!IPWhitelistCheckUtil.ifRealIPExistInWhiteList(realIP, whiteList)){
                    log.warn(
                                    autoLog(
                                                    "realIP [{}] not in whitelist, appKey: {}, payCommonQueryParam:{}",
                                                    realIP,
                                                    payCommonQueryParam.getAppKey(),
                                                    payCommonQueryParam));
                    throw new OpenAPIRuntimeException(403, "real ip [" + realIP + "] are not included in ip white list");
                }
                payCommonQueryParam.setCompliantType(CompliantType.SERVER_ONLY.getValue());
                payCommonQueryParam.setRealIpIsInWhiteList(true);
                break;
            // 暂时只用到这个情况
            case SERVER_CLIENT:
                // 在 AccessToken 为空的情况下 检查
                if (payCommonQueryParam.getAccessToken() == null){
                    if (IPWhitelistCheckUtil.ifRealIPExistInWhiteList(realIP, whiteList)){
                        payCommonQueryParam.setCompliantType(CompliantType.SERVER_ONLY.getValue());
                        payCommonQueryParam.setRealIpIsInWhiteList(true);
                    }else{

                        String IS_SERVER_ONLY_FOR_PLACEORDER = "is_server_only_for_placeorder";

                        // 合作方是否只是服务端对接下单确单接口
                        int isServerOnlyForPlaceorder = Integer
                                        .parseInt(app.getExtFieldsMap().get(IS_SERVER_ONLY_FOR_PLACEORDER).getFieldValue());
                        // 表示合作方只是通过服务端对接下单接口，直接报白名单异常提示
                        if (isServerOnlyForPlaceorder > 0){
                            log.warn(
                                            autoLog(
                                                            "realIP [{}] not in whitelist, appKey: {}, isServerOnlyForPlaceorder:{},payCommonQueryParam:{}",
                                                            realIP,
                                                            payCommonQueryParam.getAppKey(),
                                                            isServerOnlyForPlaceorder,
                                                            payCommonQueryParam));
                            throw new OpenAPIRuntimeException(403, "real ip [" + realIP + "] are not included in ip white list");
                        }else{
                            log.info(
                                            autoLog(
                                                            "realIP [{}] not in whitelist but isServerOnlyForPlaceorder:[{}] not > 0, appKey:{}, payCommonQueryParam:{}",
                                                            realIP,
                                                            isServerOnlyForPlaceorder,
                                                            payCommonQueryParam.getAppKey(),
                                                            payCommonQueryParam));
                            payCommonQueryParam.setCompliantType(CompliantType.CLIENT_ONLY.getValue());
                        }
                    }
                }
                break;
            default:
                break;
        }
        return pjp.proceed();
    }

}
