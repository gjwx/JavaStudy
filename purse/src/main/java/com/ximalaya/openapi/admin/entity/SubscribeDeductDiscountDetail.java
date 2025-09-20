package com.ximalaya.openapi.admin.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * @author luyunfeng
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
@JsonPropertyOrder({ "item_id", "item_name", "user_subscribe_discount_info_list" })
public class SubscribeDeductDiscountDetail{

    /**
     * 商品 id
     */
    @XmlElement(name = "item_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long                            itemId;

    /**
     * 商品名称
     */
    @XmlElement(name = "item_name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String                          itemName;

    /**
     * 原价，以分为单位
     */
    @XmlElement(name = "origin_price")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer                         originPrice;

    /**
     * 优惠信息数组
     */
    @XmlElement(name = "user_subscribe_discount_info_list")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<UserSubscribeDiscountInfo> userSubscribeDiscountInfoList;

    //---------------------------------------------------------------

    @JsonProperty("item_id")
    public Long getItemId(){
        return itemId;
    }

    public void setItemId(Long itemId){
        this.itemId = itemId;
    }

    @JsonProperty("item_name")
    public String getItemName(){
        return itemName;
    }

    public void setItemName(String itemName){
        this.itemName = itemName;
    }

    @JsonProperty("origin_price")
    public Integer getOriginPrice(){
        return originPrice;
    }

    public void setOriginPrice(Integer originPrice){
        this.originPrice = originPrice;
    }

    @JsonProperty("user_subscribe_discount_info_list")
    public List<UserSubscribeDiscountInfo> getUserSubscribeDiscountInfoList(){
        return userSubscribeDiscountInfoList;
    }

    public void setUserSubscribeDiscountInfoList(List<UserSubscribeDiscountInfo> userSubscribeDiscountInfoList){
        this.userSubscribeDiscountInfoList = userSubscribeDiscountInfoList;
    }
}
