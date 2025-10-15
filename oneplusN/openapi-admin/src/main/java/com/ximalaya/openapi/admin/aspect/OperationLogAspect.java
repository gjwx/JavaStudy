package com.ximalaya.openapi.admin.aspect;

import com.google.gson.Gson;
import com.openapi.ms.openapimsloggingsystem.thrift.client.OpLogServiceClient;
import com.ximalaya.openapi.admin.config.shiro.support.UserAgent;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static com.ximalaya.openapi.admin.utils.BackendLogUtil.autoBackendLog;

@Slf4j
@Component
@Aspect
//@Order(1)
public class OperationLogAspect{

    @Autowired
    private OpLogServiceClient opLogServiceClient;

    /**
     * 定义一个切点
     */
    //    @Pointcut(value = "@annotation(com.ximalaya.openplatform.adminsite.aspect.OperationLog)")
    //    @Pointcut("execution(public * com.ximalaya.openplatform.adminsite.front.controller.AppsController*.*(..))")
    @Pointcut("@annotation(com.ximalaya.openapi.admin.aspect.OperationLog)")
    public void operationLogPointCut(){
    }

    //切面 配置通知
    @Around("operationLogPointCut()")
    public Object saveSysLog(ProceedingJoinPoint joinPoint) throws Throwable{
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        //获取操作
        OperationLog operationLog = method.getAnnotation(OperationLog.class);

        //请求的参数
        Object[] args = joinPoint.getArgs();
        //将参数所在的数组转换成json
        String params = convertToParamString(args);
        Object result = null;

        //        String username = (String) SecurityUtils.getSubject().getPrincipal();
        String username = UserAgent.getCurrentUserInfo() == null ? UserAgent.getCurrentUserName()
                        : UserAgent.getCurrentUserInfo().getRealName();
        try{
            result = joinPoint.proceed();
        }catch (Throwable throwable){
            log.error(autoBackendLog("OperationLogAspect saveSysLog Throwable"), throwable);
            throw throwable;
        }finally{
            log.info(
                     autoBackendLog(
                                    "OperationLogAspect saveSysLog opName:{}, before:{}, params:{}, username:{}",
                                    operationLog.opName(),
                                    "",
                                    params,
                                    username));
            try{
                opLogServiceClient.sendOpLogMap2String(operationLog.opName(), "", params, username);
            }catch (Exception e){
                log.error(autoBackendLog("sendOpLogMap2String Exception"), e);
            }
        }
        return result;
    }

    private String convertToParamString(Object[] args){

        //多个参数用map存
        Map argumentsMap = new HashMap();
        if (args != null || args.length >= 1){
            //            ArrayList arguments = new ArrayList();
            for (int i = 0; i < args.length; i++){
                if (args[i] instanceof ServletRequest || args[i] instanceof ServletResponse || args[i] instanceof MultipartFile
                                || args[i] instanceof BindingResult || args[i] instanceof RedirectAttributes){
                    continue;
                }
                //                arguments.add(args[i]);
                argumentsMap.put("args" + i, new Gson().toJson(args[i]));
            }
        }
        return new Gson().toJson(argumentsMap);
    }
}