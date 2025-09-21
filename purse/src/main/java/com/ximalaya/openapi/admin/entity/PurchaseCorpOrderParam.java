package com.ximalaya.openapi.admin.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author JordanQiu
 * @create 2022-10-11
 */

@ToString
@Data
public class PurchaseCorpOrderParam {
    @JsonProperty("third_order_no")
    private String thirdOrderNo;
    @JsonProperty("item_type")
    private Integer itemType;
    @JsonProperty("access_account_type")
    private  Integer accessAccountType;
    @JsonProperty("access_account_id")
    private String accessAccountId;
    @JsonProperty("pay_price")
    private String payPrice;
    @JsonProperty("trans_time")
    private String transTime;
    private List<ItemInfo> items;
    @JsonProperty("discount_info")
    private DiscountInfo discountInfo;
    @JsonProperty("app_key")
    private String appKey;
    /**
     * 设备唯一标识设备唯一标识
     */
    @JsonProperty("device_id")
    private String deviceID;
    /**
     * 客户端操作系统类型
     */
    @JsonProperty("client_os_type")
    private int clientOsType;
    @JsonProperty("device_agent")
    private String deviceAgent;
    /**
     * 设备的IP地址
     */
    @JsonProperty("device_ip")
    private String deviceIP;

}
