package com.ximalaya.openapi.admin.aspect.annotation;

import java.lang.annotation.*;

/**
 * @Author JordanQiu
 * @Date 2022/3/31 21:07
 **/
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ResponseBodyAdvice {
}
