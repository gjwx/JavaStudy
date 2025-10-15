package com.ximalaya.openapi.admin.aspect;

import com.ximalaya.auth.common.web.ActionResult;
import com.ximalaya.openapi.admin.model.OpenapiAdminResponse;
import com.ximalaya.openapi.admin.vo.Response;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;

/**
 * @author arnold
 * @date 2018/6/29
 */
@Aspect
@Order(1)
public class OpenapiAdminResponseAspect{

    @Around("execution(* com.ximalaya.openapi.admin.controller..*(..))")
    public Object handle(ProceedingJoinPoint joinPoint) throws Throwable{
        Object response = joinPoint.proceed();
        if (response instanceof Response){
            return response;
        }
        if (response instanceof OpenapiAdminResponse){
            return response;
        }
        if (response instanceof ActionResult){
            return response;
        }
        if (("index").equals(response)){
            return response;
        }
        return OpenapiAdminResponse.wrap(response);
    }

}
