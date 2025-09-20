package com.ximalaya.openapi.admin.aspect.annotation;

import com.ximalaya.openapi.admin.entity.CompliantType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 兼容
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface CompliantV2 {

    /**
     * 服务对接类型
     */
    CompliantType type();
}
