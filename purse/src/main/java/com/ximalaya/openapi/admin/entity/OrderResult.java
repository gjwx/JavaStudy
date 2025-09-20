package com.ximalaya.openapi.admin.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.ximalaya.omp.facade.distribution.api.thrift.iface.TOperationResult;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * The Class OrderResult.
 *
 * @author luyunfeng
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
@JsonPropertyOrder({
                     "xima_order_no",
                     "xima_order_status",
                     "clearing_rate",
                     "order_amount",
                     "order_preferential_price",
                     "xima_order_created_at",
                     "xima_order_updated_at" })
public class OrderResult{

    /** 喜马拉雅付费音频订单号. */
    @XmlElement(name = "xima_order_no")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String  ximaOrderNo;

    /**
     * 喜马拉雅订单状态
     * <p>
     * 2-下单成功，3-订单取消，异步支付后可以通过 订单查询接口获取订单最终状态
     * </p>
     * .
     */
    @XmlElement(name = "xima_order_status")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer ximaOrderStatus;

    /** 喜马拉雅订单状态. */
    @XmlElement(name = "clearing_rate")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String  clearingRate;

    /** 订单金额. */
    @XmlElement(name = "order_amount")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String  orderAmount;

    /** 优惠后的订单金额. */
    @XmlElement(name = "order_preferential_price")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String  orderPreferentialPrice;

    /** 喜马拉雅订单创建时间. */
    @XmlElement(name = "xima_order_created_at")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long    ximaOrderCreatedAt;

    /** 喜马拉雅订单更新时间. */
    @XmlElement(name = "xima_order_updated_at")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long    ximaOrderUpdatedAt;

    /** 喜马拉雅用户是拉新or召回. */
    @XmlElement(name = "is_xmly_old_vip")
    private Boolean isXmlyOldVip;

    /**
     * Gets the xmly old vip.
     *
     * @return the xmly old vip
     */
    @JsonProperty("is_xmly_old_vip")
    public Boolean getXmlyOldVip(){
        return isXmlyOldVip;
    }

    /**
     * Sets the xmly old vip.
     *
     * @param xmlyOldVip
     *            the new xmly old vip
     */
    public void setXmlyOldVip(Boolean xmlyOldVip){
        isXmlyOldVip = xmlyOldVip;
    }

    /**
     * Gets the 喜马拉雅付费音频订单号.
     *
     * @return the 喜马拉雅付费音频订单号
     */
    @JsonProperty("xima_order_no")
    public String getXimaOrderNo(){
        return ximaOrderNo;
    }

    /**
     * Sets the 喜马拉雅付费音频订单号.
     *
     * @param ximaOrderNo
     *            the new 喜马拉雅付费音频订单号
     */
    public void setXimaOrderNo(String ximaOrderNo){
        this.ximaOrderNo = ximaOrderNo;
    }

    /**
     * Gets the 喜马拉雅订单状态
     * <p>
     * 2-下单成功，3-订单取消，异步支付后可以通过 订单查询接口获取订单最终状态
     * </p>
     * .
     *
     * @return the 喜马拉雅订单状态
     *         <p>
     *         2-下单成功，3-订单取消，异步支付后可以通过 订单查询接口获取订单最终状态
     *         </p>
     */
    @JsonProperty("xima_order_status")
    public Integer getXimaOrderStatus(){
        return ximaOrderStatus;
    }

    /**
     * Sets the 喜马拉雅订单状态
     * <p>
     * 2-下单成功，3-订单取消，异步支付后可以通过 订单查询接口获取订单最终状态
     * </p>
     * .
     *
     * @param ximaOrderStatus
     *            the new 喜马拉雅订单状态
     *            <p>
     *            2-下单成功，3-订单取消，异步支付后可以通过 订单查询接口获取订单最终状态
     *            </p>
     */
    public void setXimaOrderStatus(Integer ximaOrderStatus){
        this.ximaOrderStatus = ximaOrderStatus;
    }

    /**
     * Gets the 喜马拉雅订单创建时间.
     *
     * @return the 喜马拉雅订单创建时间
     */
    @JsonProperty("xima_order_created_at")
    public Long getXimaOrderCreatedAt(){
        return ximaOrderCreatedAt;
    }

    /**
     * Sets the 喜马拉雅订单创建时间.
     *
     * @param ximaOrderCreatedAt
     *            the new 喜马拉雅订单创建时间
     */
    public void setXimaOrderCreatedAt(Long ximaOrderCreatedAt){
        this.ximaOrderCreatedAt = ximaOrderCreatedAt;
    }

    /**
     * Gets the 喜马拉雅订单更新时间.
     *
     * @return the 喜马拉雅订单更新时间
     */
    @JsonProperty("xima_order_updated_at")
    public Long getXimaOrderUpdatedAt(){
        return ximaOrderUpdatedAt;
    }

    /**
     * Sets the 喜马拉雅订单更新时间.
     *
     * @param ximaOrderUpdatedAt
     *            the new 喜马拉雅订单更新时间
     */
    public void setXimaOrderUpdatedAt(Long ximaOrderUpdatedAt){
        this.ximaOrderUpdatedAt = ximaOrderUpdatedAt;
    }

    /**
     * Gets the 喜马拉雅订单状态.
     *
     * @return the 喜马拉雅订单状态
     */
    @JsonProperty("clearing_rate")
    public String getClearingRate(){
        return clearingRate;
    }

    /**
     * Sets the 喜马拉雅订单状态.
     *
     * @param clearingRate
     *            the new 喜马拉雅订单状态
     */
    public void setClearingRate(String clearingRate){
        this.clearingRate = clearingRate;
    }

    /**
     * Gets the 订单金额.
     *
     * @return the 订单金额
     */
    @JsonProperty("order_amount")
    public String getOrderAmount(){
        return orderAmount;
    }

    /**
     * Sets the 订单金额.
     *
     * @param orderAmount
     *            the new 订单金额
     */
    public void setOrderAmount(String orderAmount){
        this.orderAmount = orderAmount;
    }

    /**
     * Gets the 优惠后的订单金额.
     *
     * @return the 优惠后的订单金额
     */
    @JsonProperty("order_preferential_price")
    public String getOrderPreferentialPrice(){
        return orderPreferentialPrice;
    }

    /**
     * Sets the 优惠后的订单金额.
     *
     * @param orderPreferentialPrice
     *            the new 优惠后的订单金额
     */
    public void setOrderPreferentialPrice(String orderPreferentialPrice){
        this.orderPreferentialPrice = orderPreferentialPrice;
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

    /**
     * Instantiates a new order result.
     */
    public OrderResult(){
    }

    /**
     * Instantiates a new order result.
     *
     * @param operationResult
     *            the operation result
     */
    public OrderResult(TOperationResult operationResult){
        this.setXimaOrderNo(operationResult.getData().getUnifiedOrderNo());
        this.setXimaOrderStatus(operationResult.getData().getOrderStatus());
        BigDecimal clearingRate = new BigDecimal(operationResult.getData().getClearingRate());
        this.setClearingRate(
                        clearingRate.divide(new BigDecimal(1000)).setScale(4, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString());
        long now = System.currentTimeMillis();
        if (operationResult.getData().isSetOrderCreatedAt()){
            this.setXimaOrderCreatedAt(operationResult.getData().getOrderCreatedAt());
        }else{
            this.setXimaOrderCreatedAt(now);
        }
        if (operationResult.getData().isSetOrderUpdatedAt()){
            this.setXimaOrderUpdatedAt(operationResult.getData().getOrderUpdatedAt());
        }else{
            this.setXimaOrderUpdatedAt(now);
        }
    }

}
