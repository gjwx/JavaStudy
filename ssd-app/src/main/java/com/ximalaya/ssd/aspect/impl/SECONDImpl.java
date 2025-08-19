package com.ximalaya.ssd.aspect.impl;

import com.ximalaya.ssd.aspect.annotation.LimitAopV2;
import com.ximalaya.ssd.aspect.annotation.SECOND;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 〈〉
 *
 * @author gongjiawei
 * @date 2025/8/19 下午3:26
 */
@Aspect
@Component
@Order(2)
public class SECONDImpl {

    @Before("@annotation(second)")
    public void before(JoinPoint joinPoint, SECOND second) throws Throwable {
        String name = (String) joinPoint.getArgs()[0];
        System.out.println("我是before通知+网页输入的名字是"+name);
    }
}
