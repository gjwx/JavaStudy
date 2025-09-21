package com.ximalaya.openapi.admin.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 
 * @Author dandan.zhu
 * @Date 2020-07-29 19:44
 **/

public class ActivityProductStockMsgDTO{

    private Long    activityId;

    private String  appKey;

    private String  productId;

    private Long    realStock;

    private Integer activityType;

    /**
     * @return the activityId
     */
    public Long getActivityId(){
        return activityId;
    }

    /**
     * @param activityId
     *            the activityId to set
     */
    public void setActivityId(Long activityId){
        this.activityId = activityId;
    }

    /**
     * @return the appKey
     */
    public String getAppKey(){
        return appKey;
    }

    /**
     * @param appKey
     *            the appKey to set
     */
    public void setAppKey(String appKey){
        this.appKey = appKey;
    }

    /**
     * @return the productId
     */
    public String getProductId(){
        return productId;
    }

    /**
     * @param productId
     *            the productId to set
     */
    public void setProductId(String productId){
        this.productId = productId;
    }

    /**
     * @return the realStock
     */
    public Long getRealStock(){
        return realStock;
    }

    /**
     * @param realStock
     *            the realStock to set
     */
    public void setRealStock(Long realStock){
        this.realStock = realStock;
    }

    /**
     * @return the activityType
     */
    public Integer getActivityType(){
        return activityType;
    }

    /**
     * @param activityType
     *            the activityType to set
     */
    public void setActivityType(Integer activityType){
        this.activityType = activityType;
    }

    //---------------------------------------------------------------
    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString(){
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }

}
