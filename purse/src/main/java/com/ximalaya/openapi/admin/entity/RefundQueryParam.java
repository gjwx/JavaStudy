package com.ximalaya.openapi.admin.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author MFrank
 * @date 2019-12-31 16:09
 **/
@Data
public class RefundQueryParam{

    @NotBlank(message = "xima_order_no could not be null")
    @JSONField(name = "xima_order_no")
    private String  ximaOrderNo;

    @JSONField(name = "combined_product")
    private Integer combinedProduct;

    // 公共参数
    @NotBlank(message = "app_key could not be null")
    @JSONField(name = "app_key")
    private String  appKey;

    @NotBlank(message = "client_os_type could not be null")
    @JSONField(name = "client_os_type")
    private String  clientOsType;

    @NotBlank(message = "nonce could not be null")
    private String  nonce;

    @NotBlank(message = "timestamp could not be null")
    private String  timestamp;

    @NotBlank(message = "device_id could not be null")
    @JSONField(name = "device_id")
    private String  deviceId;

    @NotBlank(message = "server_api_version could not be null")
    @JSONField(name = "server_api_version")
    private String  serverApiVersion;

    @NotBlank(message = "sig could not be null")
    private String  sig;
}
