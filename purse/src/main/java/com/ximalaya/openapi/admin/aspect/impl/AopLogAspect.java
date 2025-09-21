package com.ximalaya.openapi.admin.aspect.impl;

import com.ximalaya.openapi.admin.aspect.annotation.AopLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;

import static com.feilong.core.date.DateUtil.formatDurationUseBeginTimeMillis;
import static com.ximalaya.openapi.log.AutoLog.autoLog;

/**
 * @author yunfeng.lu
 * @create 2019-06-25.
 */
@Slf4j
@Component
@Aspect
public class AopLogAspect{

    //    @Around("@annotation(com.ximalaya.omp.openapi.payment.aspect.annotation.AopLog) && args(logAop)")
    @Around("@annotation(logAop)")
    public Object printLog(ProceedingJoinPoint pjp, AopLog logAop) throws Throwable{
        AopLog.ParamPrintOption printOption = logAop.outParamPrint();
        long currentTime = System.currentTimeMillis();
        String inParam = Arrays.toString(pjp.getArgs());
        String className = pjp.getTarget().getClass().getName();
        String methodName = pjp.getSignature().getName();

        //---------------------------------------------------------------

        try{
            Object result = pjp.proceed();

            // 未配置情况 系统默认不打印 集合类型，其余都打印
            if (AopLog.ParamPrintOption.UNCONFIG.equals(printOption)){
                if (result instanceof Collection){
                    printOption = AopLog.ParamPrintOption.IGNORE;
                }else if (result != null && result.getClass().isArray()){
                    printOption = AopLog.ParamPrintOption.IGNORE;
                }else{
                    printOption = AopLog.ParamPrintOption.PRINT;
                }
            }

            //---------------------------------------------------------------
            log.info(
                            autoLog(
                                            "{}.{}:  入参:{}  出参:{}  耗时:{}毫秒",
                                            className,
                                            methodName,
                                            inParam,

                                            printOption.equals(AopLog.ParamPrintOption.PRINT) ? result : "参数未配置打印",
                                            formatDurationUseBeginTimeMillis(currentTime)));

            return result;
        }catch (Throwable throwable){
            log.warn(autoLog("{}.{}: 执行报错,入参: {}", className, methodName, inParam), throwable);
            throw throwable;
        }
    }

}
