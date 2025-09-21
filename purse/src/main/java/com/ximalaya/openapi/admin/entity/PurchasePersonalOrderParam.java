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
public class PurchasePersonalOrderParam{

    @JsonProperty("third_order_no")
    private String         thirdOrderNo;

    @JsonProperty("item_type")
    private Integer        itemType;

    @JsonProperty("access_account_type")
    private Integer        accessAccountType;

    @JsonProperty("access_account_id")
    private String         accessAccountId;

    @JsonProperty("business_third_order_no")
    private String         businessThirdOrderNo;

    @JsonProperty("trans_time")
    private String         transTime;

    private List<ItemInfo> items;

}
