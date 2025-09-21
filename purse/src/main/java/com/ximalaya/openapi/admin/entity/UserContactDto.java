package com.ximalaya.openapi.admin.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 实物商品等用户收货信息
 * 
 * @Author JordanQiu
 * @Date 2022/6/1 14:20
 **/
@Data
public class UserContactDto{

    @JsonProperty("contact_name")
    private String  contactName;

    @JsonProperty("contact_phone")
    private String  contactPhone;

    @JsonProperty("area_code")
    private String  areaCode;

    @JsonProperty("address_line")
    private String  addressLine;

    @JsonProperty("default_address")
    private Boolean defaultAddress;

    @JsonProperty("usr_contact_id")
    private String  usrContactId;
}
