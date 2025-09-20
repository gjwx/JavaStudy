package com.ximalaya.openapi.admin.aspect.impl;

import com.ximalaya.omp.openapi.payment.config.FootballDistributeRetryConfig;
import com.ximalaya.omp.openapi.payment.entity.EnablePayType;
import com.ximalaya.omp.openapi.payment.entity.PayCommonQueryParam;
import com.ximalaya.omp.openapi.payment.ex.OpenAPIRuntimeException;
import com.ximalaya.openplatform.appservice.thrift.iface.App;
import com.ximalaya.openplatform.signature.service.AppServiceClient;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.ximalaya.openapi.log.AutoLog.autoLog;

/**
 * 检查是否有支付权限
 */
@Slf4j
@Component
@Aspect
public class EnablePayCheckAspect{

    @Autowired
    private AppServiceClient              appServiceClient;

    @Autowired
    private FootballDistributeRetryConfig footballDistributeRetryConfig;

    @Around("@annotation(com.ximalaya.omp.openapi.payment.aspect.annotation.EnablePayCheck)")
    public Object checkEnablePay(ProceedingJoinPoint pjp) throws Throwable{
        PayCommonQueryParam payCommonQueryParam = (PayCommonQueryParam) pjp.getArgs()[0];
        String appKey = payCommonQueryParam.getAppKey();
        String methodName = pjp.getSignature().getName();
        String className = pjp.getSignature().getDeclaringType().getSimpleName();
        if (className.equals("DistributionController") && methodName.equals("distribute")
                        && footballDistributeRetryConfig.getForwardToV2DistributeAppSet().contains(payCommonQueryParam.getAppKey())){
            return pjp.proceed();
        }
        App app = appServiceClient.getAppByAppKey(appKey);
        if (!EnablePayType.ENABLE_PAY.equals(EnablePayType.getEnablePayType(app.getEnablePay()))){
            log.warn(autoLog("app don't have enable pay permission"));
            throw new OpenAPIRuntimeException(403, "app[appKey: " + appKey + "] don't have enable pay permission");
        }
        return pjp.proceed();
    }
}
