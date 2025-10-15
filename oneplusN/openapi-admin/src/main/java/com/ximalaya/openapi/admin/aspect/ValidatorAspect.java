package com.ximalaya.openapi.admin.aspect;

import com.ximalaya.openapi.admin.model.ArgumentErrorResponse;
import com.ximalaya.openapi.admin.model.DeveloperAuditResult;
import com.ximalaya.openapi.admin.utils.IdUtils;
import com.ximalaya.openapi.admin.utils.UserUtils;
import com.ximalaya.openapi.audit.inf.model.AuditResult;
import com.ximalaya.openapi.web.core.model.AuditStatus;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

/**
 * @author arnold
 * @date 2018/6/29
 */
@Aspect
@Order(0)
public class ValidatorAspect{

    /**
     * // 处理@Valid注解的校验结果
     */
    @Around("@annotation(com.ximalaya.openapi.admin.annotation.CheckBindingResult)&& args(..,bindingResult)")
    public Object handle(ProceedingJoinPoint joinPoint,BindingResult bindingResult) throws Throwable{
        if (bindingResult.hasErrors()){
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors){
                String message = fieldError.getDefaultMessage();
                return new ArgumentErrorResponse(message);
            }
        }
        return joinPoint.proceed();
    }

    //---------------------------------------------------------------

    /**
     * // 校验AuditResult合法性 && 填充AuditResult中的auditor字段
     */
    @Around("@annotation(com.ximalaya.openapi.admin.annotation.AuditResultValidator)&& args(auditId,auditResult)")
    public Object auditResultValidator(ProceedingJoinPoint joinPoint,Long auditId,AuditResult auditResult) throws Throwable{
        if (!IdUtils.isValid(auditId)){
            return ArgumentErrorResponse.GENERAL_ARGUMENT_ERROR_RESPONSE;
        }
        auditResult.setAuditor(UserUtils.getRealName()); // CAS账号
        // 审核未通过时，需要填写未通过原因
        if (auditResult.getAuditState() == AuditStatus.NO_PASS.getStatus()){
            if (org.springframework.util.StringUtils.isEmpty(auditResult.getAuditFailReason()))
                return new ArgumentErrorResponse("审核失败原因必填");
        }
        return joinPoint.proceed();
    }

    //---------------------------------------------------------------

    @Around("@annotation(com.ximalaya.openapi.admin.annotation.AuditResultValidator)&& args(auditId,developerAuditResult)")
    public Object developerAuditResultValidator(ProceedingJoinPoint joinPoint,Long auditId,DeveloperAuditResult developerAuditResult)
                    throws Throwable{
        if (!IdUtils.isValid(auditId)){
            return ArgumentErrorResponse.GENERAL_ARGUMENT_ERROR_RESPONSE;
        }
        developerAuditResult.setAuditor(UserUtils.getRealName()); // CAS账号

        //---------------------------------------------------------------

        // 审核未通过时，需要填写未通过原因
        if (developerAuditResult.getAuditState() == AuditStatus.NO_PASS.getStatus()){
            if (org.springframework.util.StringUtils.isEmpty(developerAuditResult.getAuditFailReason())){
                return new ArgumentErrorResponse("审核失败原因必填");
            }
        }
        return joinPoint.proceed();
    }

}
