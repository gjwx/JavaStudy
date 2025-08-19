package com.ximalaya.ssd.aspect.impl;

import com.ximalaya.ssd.aspect.annotation.LimitAopV2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 〈〉
 *
 * @author gongjiawei
 * @date 2025/8/19 下午2:28
 */
@Aspect
@Order(1)
@Component
public class LimitAopV2Impl {
    @Around("@annotation(limitAopV2)")
    public Object around(ProceedingJoinPoint joinPoint, LimitAopV2 limitAopV2) throws Throwable {
        String name = (String) joinPoint.getArgs()[0];

            System.out.println("网页输入的名字是"+name);
        String result= (String) joinPoint.proceed()+"被我改了";

        return result;
    }

}
