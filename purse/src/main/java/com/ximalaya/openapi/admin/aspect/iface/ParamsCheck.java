package com.ximalaya.openapi.admin.aspect.iface;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * @program: PACKAGE_NAME:com.ximalaya.omp.openapi.payment.aspect.iface
 * 业务参数校验接口
 * @author: dan.qiu
 * @create: 2020-05-12 09:57
 **/

public interface ParamsCheck {

    Object paramValidate(ProceedingJoinPoint pjp) throws Throwable;
}
