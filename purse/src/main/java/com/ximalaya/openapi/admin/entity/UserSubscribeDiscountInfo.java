package com.ximalaya.openapi.admin.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @Author zhuangyuan.liu
 * @Date 2024/9/12 19:33
 * 
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
@JsonPropertyOrder({ "deduct_point", "origin_price", "current_price" })
public class UserSubscribeDiscountInfo{

    /**
     * 优惠归属的扣费次数，从1开始
     */
    @XmlElement(name = "deduct_point")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer deductPoint;

    /**
     * 原价，以分为单位
     */
    @XmlElement(name = "origin_price")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer originPrice;

    /**
     * 优惠后的真实价，以分为单位
     */
    @XmlElement(name = "current_price")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer currentPrice;

    //---------------------------------------------------------------

    @JsonProperty("deduct_point")
    public Integer getDeductPoint(){
        return deductPoint;
    }

    public void setDeductPoint(Integer deductPoint){
        this.deductPoint = deductPoint;
    }

    @JsonProperty("origin_price")
    public Integer getOriginPrice(){
        return originPrice;
    }

    public void setOriginPrice(Integer originPrice){
        this.originPrice = originPrice;
    }

    @JsonProperty("current_price")
    public Integer getCurrentPrice(){
        return currentPrice;
    }

    public void setCurrentPrice(Integer currentPrice){
        this.currentPrice = currentPrice;
    }
}
