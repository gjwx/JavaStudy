package com.ximalaya.openapi.admin.entity;

import com.ximalaya.omp.facade.distribution.api.enums.AccessAccountTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商品请求信息
 * 
 * @author : Danny Liu(幸程)
 * @date : Created in 14:01 of 2020/6/19
 *       * @version : 1.0
 * @version : 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QueryParam{

    private long                  uid;

    private int                   appId;

    private int                   domain;

    private long                  payContent;

    private AccessAccountTypeEnum accountType;

    private PayCommonQueryParam   payCommonQueryParam;
}
