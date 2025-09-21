package com.ximalaya.openapi.admin.aspect.impl;

import com.ximalaya.omp.openapi.payment.entity.PayCommonQueryParam;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.servlet.http.HttpServletRequest;

/**
 * The Class DistributeRequest.
 */
public class DistributeRequest{

    /** The pay common query param. */
    private PayCommonQueryParam payCommonQueryParam;

    /** The request. */
    private HttpServletRequest  request;

    //---------------------------------------------------------------

    /**
     * Gets the pay common query param.
     *
     * @return the pay common query param
     */
    public PayCommonQueryParam getPayCommonQueryParam(){
        return payCommonQueryParam;
    }

    /**
     * Sets the pay common query param.
     *
     * @param payCommonQueryParam
     *            the new pay common query param
     */
    public void setPayCommonQueryParam(PayCommonQueryParam payCommonQueryParam){
        this.payCommonQueryParam = payCommonQueryParam;
    }

    /**
     * Gets the request.
     *
     * @return the request
     */
    public HttpServletRequest getRequest(){
        return request;
    }

    /**
     * Sets the request.
     *
     * @param request
     *            the new request
     */
    public void setRequest(HttpServletRequest request){
        this.request = request;
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
