package com.ximalaya.openapi.admin.aspect.impl;

import com.ximalaya.omp.openapi.payment.aspect.annotation.LimitAop;
import com.ximalaya.omp.openapi.payment.entity.PayCommonQueryParam;
import com.ximalaya.omp.openapi.payment.ex.RateLimitException;
import com.ximalaya.omp.openapi.payment.util.SpringContextHolder;
import com.ximalaya.openapi.limiter.rate.inf.thrift.iface.RateLimiterServiceOpt;
import com.ximalaya.openapi.limiter.rate.inf.thrift.iface.TRateLimitAccessResult;
import com.ximalaya.openapi.web.common.constant.APIError;
import com.ximalaya.openplatform.appservice.thrift.iface.App;
import com.ximalaya.openplatform.signature.kit.HttpResponseStatusCode;
import com.ximalaya.openplatform.signature.service.AppServiceClient;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import static com.ximalaya.openapi.limiter.rate.inf.thrift.iface.TRateLimitAccessResult.GLOBAL_RATE_LIMIT_EXCEED;
import static com.ximalaya.openapi.limiter.rate.inf.thrift.iface.TRateLimitAccessResult.SUCCESS;
import static com.ximalaya.openapi.log.AutoLog.autoLog;

/**
 * @author yunfeng.lu
 * @create 2019-06-25.
 */
@Slf4j
@Component
@Aspect
@Order(1)
public class LimitAspect{

    @Autowired
    @Qualifier("rateLimiterService")
    private RateLimiterServiceOpt.Iface rateLimiterService;

    //    @Around("@annotation(com.ximalaya.omp.openapi.payment.aspect.annotation.LimitAop) && args(limitAop)")
    @Around("@annotation(limitAop)")
    public Object limitAspect(ProceedingJoinPoint pjp,LimitAop limitAop) throws Throwable{
        AppServiceClient appServiceClient = SpringContextHolder.getBean(AppServiceClient.class);
        PayCommonQueryParam payCommonQueryParam = (PayCommonQueryParam) pjp.getArgs()[0];
        App app = appServiceClient.getAppByAppKey(payCommonQueryParam.getAppKey());
        TRateLimitAccessResult limitAccessResult;
        try{
            limitAccessResult = rateLimiterService.tryAcquire(app.getId());
        }catch (Exception e){
            log.error(autoLog("some exception occurs when invoke rate limiter, app id: {}  ", app.getId()), e);
            // 如果频控服务不可用，则直接放行
            return pjp.proceed();
        }
        if (limitAccessResult == SUCCESS){
            log.debug(autoLog("正常放行,result {} ", limitAccessResult));
            return pjp.proceed();
        }else if (limitAccessResult == GLOBAL_RATE_LIMIT_EXCEED){
            // 全局错误
            throw new RateLimitException(HttpResponseStatusCode.HTTP_STATUS_SERVER_ERROR, APIError.INTERNAL_SERVICE_ERROR);
        }else{
            throw new RateLimitException(HttpResponseStatusCode.HTTP_STATUS_TOO_MANY_REQUESTS, APIError.REQUEST_OUT_OF_LIMIT);
        }
    }

}
