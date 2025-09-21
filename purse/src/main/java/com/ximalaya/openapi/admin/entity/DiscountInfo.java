package com.ximalaya.openapi.admin.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * The Class DiscountInfo.
 */
public class DiscountInfo{

    /** The discount rate. */
    @JsonProperty("discount_rate")
    private String discountRate;

    /** The rebate rate id. */
    @JsonProperty("rebate_rate_id")
    private String rebateRateId;

    //---------------------------------------------------------------
    /**
     * To string.
     *
     * @return the string
     */
    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString(){
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }

    /**
     * Gets the discount rate.
     *
     * @return the discount rate
     */
    public String getDiscountRate(){
        return discountRate;
    }

    /**
     * Sets the discount rate.
     *
     * @param discountRate
     *            the new discount rate
     */
    public void setDiscountRate(String discountRate){
        this.discountRate = discountRate;
    }

    /**
     * Gets the rebate rate id.
     *
     * @return the rebate rate id
     */
    public String getRebateRateId(){
        return rebateRateId;
    }

    /**
     * Sets the rebate rate id.
     *
     * @param rebateRateId
     *            the new rebate rate id
     */
    public void setRebateRateId(String rebateRateId){
        this.rebateRateId = rebateRateId;
    }
}
