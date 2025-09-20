package com.ximalaya.openapi.admin.aspect.annotation;

import com.ximalaya.openapi.admin.entity.GoThroughVuserServiceType;
import com.ximalaya.openapi.admin.entity.ValidateFailedActionType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by roy.zhu on 2017/4/11.
 * 
 * @deprecated 要使用v2
 */
@Deprecated
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface TokenValidate {

    ValidateFailedActionType validateType() default ValidateFailedActionType.THROW_OUT_EXCEPTION;

    GoThroughVuserServiceType goThroughType() default GoThroughVuserServiceType.ENABLE_GO_THROUGHT;

}
