package com.ximalaya.openapi.admin.aspect.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author chevn
 * @date 2019-01-08 10:25
 * 适用于controller中的 控制器方法 中带有 PayCommonQueryParam 类型入参的方法，
 * 在解析该参数时，是否自动查询并注入指定参数，例：promotionUid。annotation 中默认是 false 不自动注入
 * 代码中一般默认自动注入。根据业务需要和代码逻辑进行合理配置
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface PayCommonQueryParamInjectedFieldConfig {

    // promotionUid 注入开启与否（默认值false-不开启自，开启时会自动根据入参请求对应接口获取数据并配置到对象中）
    boolean promotionUidInjectSwitch() default false;
}
