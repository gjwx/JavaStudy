package com.ximalaya.openapi.admin.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlElement;

/**
 * @创建人 weiwei.li
 * @创建时间 2019/7/30
 * 
 */
@JsonPropertyOrder({"code","xima_order_no"})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRefundResult {

    @XmlElement(name = "code")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private int code;

    @XmlElement(name = "xima_order_no")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String ximaOrderNo;
}
