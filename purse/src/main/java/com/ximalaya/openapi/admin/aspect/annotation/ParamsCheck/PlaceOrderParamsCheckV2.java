package com.ximalaya.openapi.admin.aspect.annotation.ParamsCheck;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author chevn
 * @date 2019-01-08 10:25
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface PlaceOrderParamsCheckV2 {
    // 注解中有此属性的 会对请求参数中的deviceId 进行校验
    // 规则之内的要求deviceId 参数必传非空，规则之外的不做要求
    boolean deviceIdNotBlankCheck() default false;
}
