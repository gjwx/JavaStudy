package com.ximalaya.openapi.admin.aspect;

import com.ximalaya.openapi.admin.utils.UserUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import static com.ximalaya.openapi.admin.utils.BackendLogUtil.autoBackendLog;

/**

 */
@Slf4j
@Aspect
public class LogAspect{

    @Before("execution(* com.ximalaya.openapi.admin.controller..create*(..))")
    public void logCreate(){
        log.info(autoBackendLog("用户 {}({}) 正在进行create操作", UserUtils.getRealName(), UserUtils.getUserId()));
    }

    @Before("execution(* com.ximalaya.openapi.admin.controller..update*(..))")
    public void logUpdate(){
        log.info(autoBackendLog("用户 {}({}) 正在进行update操作", UserUtils.getRealName(), UserUtils.getUserId()));
    }

    @Before("execution(* com.ximalaya.openapi.admin.controller..delete*(..))")
    public void logDelete(){
        log.info(autoBackendLog("用户 {}({}) 正在进行delete操作", UserUtils.getRealName(), UserUtils.getUserId()));
    }

}
