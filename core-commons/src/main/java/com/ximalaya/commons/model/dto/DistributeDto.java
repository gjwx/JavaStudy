package com.ximalaya.commons.model.dto;

import com.ximalaya.commons.model.entity.AppInfo;
import com.ximalaya.commons.model.entity.DeviceInfo;
import com.ximalaya.commons.model.entity.UserContactInfo;
import com.ximalaya.commons.model.entity.UserInfo;

/**
 * 〈〉
 *
 * @author gongjiawei
 * @date 2025/9/18 下午4:01
 */
public class DistributeDto {

    private AppInfo appinfo;
    private UserInfo userInfo;
    private DeviceInfo deviceInfo;
    private Integer openapiInternaldomain;
    private String payContent;
    /**支付渠道*/
    private Integer payChannel;
    /**#优惠券ID*/
    private Long couponId;
    private Integer distributeItemType;
    private String expectPrice;
    private String payPrice;
    private String thirdPartOrderNo;
    /**合作方和喜马对账时间，时间格式为yyyy-MM-dd hh:mm:ss*/
    private String transTime;
    /** 是否是预下单订单*/
    private Boolean advanceTrans;
    /** 是否是延迟鉴权的订单（之前有预下单记录的订单）*/
    private Boolean delayTrans;
    /** 下单请求参数*/
    private String distributionRequestParam;
    /** 限量秒杀库存*/
    private Long sku;
    /** 分销下单客户端额外信息*/
    private String clientExtraInfo;
    /** 实物商品收货地址*/
    private UserContactInfo userContactDto;
    /**商品数量*/
    private Long itemQuantity;
    /**签约单号*/
    private String signOrderNo;
    /**since 20250226 谭凤新增需求 https://teambition.ximalaya.com/task/67bed56902b9441aa94c0310*/
    private String utmSource;

    public DistributeDto() {
    }

    public DistributeDto(AppInfo appinfo, UserInfo userInfo, DeviceInfo deviceInfo, Integer openapiInternaldomain, String payContent, Integer payChannel, Long couponId, Integer distributeItemType, String expectPrice, String payPrice, String thirdPartOrderNo, String transTime, Boolean advanceTrans, Boolean delayTrans, String distributionRequestParam, Long sku, String clientExtraInfo, UserContactInfo userContactDto, Long itemQuantity, String signOrderNo, String utmSource) {
        this.appinfo = appinfo;
        this.userInfo = userInfo;
        this.deviceInfo = deviceInfo;
        this.openapiInternaldomain = openapiInternaldomain;
        this.payContent = payContent;
        this.payChannel = payChannel;
        this.couponId = couponId;
        this.distributeItemType = distributeItemType;
        this.expectPrice = expectPrice;
        this.payPrice = payPrice;
        this.thirdPartOrderNo = thirdPartOrderNo;
        this.transTime = transTime;
        this.advanceTrans = advanceTrans;
        this.delayTrans = delayTrans;
        this.distributionRequestParam = distributionRequestParam;
        this.sku = sku;
        this.clientExtraInfo = clientExtraInfo;
        this.userContactDto = userContactDto;
        this.itemQuantity = itemQuantity;
        this.signOrderNo = signOrderNo;
        this.utmSource = utmSource;
    }

    public void setAppinfo(AppInfo appinfo) {
        this.appinfo = appinfo;
    }

    public AppInfo getAppinfo() {
        return this.appinfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public UserInfo getUserInfo() {
        return this.userInfo;
    }

    public void setDeviceInfo(DeviceInfo deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public DeviceInfo getDeviceInfo() {
        return this.deviceInfo;
    }

    public void setOpenapiInternaldomain(Integer openapiInternaldomain) {
        this.openapiInternaldomain = openapiInternaldomain;
    }

    public Integer getOpenapiInternaldomain() {
        return this.openapiInternaldomain;
    }

    public void setPayContent(String payContent) {
        this.payContent = payContent;
    }

    public String getPayContent() {
        return this.payContent;
    }

    public void setPayChannel(Integer payChannel) {
        this.payChannel = payChannel;
    }

    public Integer getPayChannel() {
        return this.payChannel;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    public Long getCouponId() {
        return this.couponId;
    }

    public void setDistributeItemType(Integer distributeItemType) {
        this.distributeItemType = distributeItemType;
    }

    public Integer getDistributeItemType() {
        return this.distributeItemType;
    }

    public void setExpectPrice(String expectPrice) {
        this.expectPrice = expectPrice;
    }

    public String getExpectPrice() {
        return this.expectPrice;
    }

    public void setPayPrice(String payPrice) {
        this.payPrice = payPrice;
    }

    public String getPayPrice() {
        return this.payPrice;
    }

    public void setThirdPartOrderNo(String thirdPartOrderNo) {
        this.thirdPartOrderNo = thirdPartOrderNo;
    }

    public String getThirdPartOrderNo() {
        return this.thirdPartOrderNo;
    }

    public void setTransTime(String transTime) {
        this.transTime = transTime;
    }

    public String getTransTime() {
        return this.transTime;
    }

    public void setAdvanceTrans(Boolean advanceTrans) {
        this.advanceTrans = advanceTrans;
    }

    public Boolean getAdvanceTrans() {
        return this.advanceTrans;
    }

    public void setDelayTrans(Boolean delayTrans) {
        this.delayTrans = delayTrans;
    }

    public Boolean getDelayTrans() {
        return this.delayTrans;
    }

    public void setDistributionRequestParam(String distributionRequestParam) {
        this.distributionRequestParam = distributionRequestParam;
    }

    public String getDistributionRequestParam() {
        return this.distributionRequestParam;
    }

    public void setSku(Long sku) {
        this.sku = sku;
    }

    public Long getSku() {
        return this.sku;
    }

    public void setClientExtraInfo(String clientExtraInfo) {
        this.clientExtraInfo = clientExtraInfo;
    }

    public String getClientExtraInfo() {
        return this.clientExtraInfo;
    }

    public void setUserContactDto(UserContactInfo userContactDto) {
        this.userContactDto = userContactDto;
    }

    public UserContactInfo getUserContactDto() {
        return this.userContactDto;
    }

    public void setItemQuantity(Long itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public Long getItemQuantity() {
        return this.itemQuantity;
    }

    public void setSignOrderNo(String signOrderNo) {
        this.signOrderNo = signOrderNo;
    }

    public String getSignOrderNo() {
        return this.signOrderNo;
    }

    public void setUtmSource(String utmSource) {
        this.utmSource = utmSource;
    }

    public String getUtmSource() {
        return this.utmSource;
    }

    @Override
    public String toString() {
        return "DistributeDto(" +"appinfo=" + this.appinfo + ", " + "userInfo=" + this.userInfo + ", " + "deviceInfo=" + this.deviceInfo + ", " + "openapiInternaldomain=" + this.openapiInternaldomain + ", " + "payContent=" + this.payContent + ", " + "payChannel=" + this.payChannel + ", " + "couponId=" + this.couponId + ", " + "distributeItemType=" + this.distributeItemType + ", " + "expectPrice=" + this.expectPrice + ", " + "payPrice=" + this.payPrice + ", " + "thirdPartOrderNo=" + this.thirdPartOrderNo + ", " + "transTime=" + this.transTime + ", " + "advanceTrans=" + this.advanceTrans + ", " + "delayTrans=" + this.delayTrans + ", " + "distributionRequestParam=" + this.distributionRequestParam + ", " + "sku=" + this.sku + ", " + "clientExtraInfo=" + this.clientExtraInfo + ", " + "userContactDto=" + this.userContactDto + ", " + "itemQuantity=" + this.itemQuantity + ", " + "signOrderNo=" + this.signOrderNo + ", " + "utmSource=" + this.utmSource + ")";
    }

    @Override
    public int hashCode() {
        int hash = 1;
        hash = 31 * hash + (this.appinfo == null ? 0 : this.appinfo.hashCode());
        hash = 31 * hash + (this.userInfo == null ? 0 : this.userInfo.hashCode());
        hash = 31 * hash + (this.deviceInfo == null ? 0 : this.deviceInfo.hashCode());
        hash = 31 * hash + (this.openapiInternaldomain == null ? 0 : this.openapiInternaldomain.hashCode());
        hash = 31 * hash + (this.payContent == null ? 0 : this.payContent.hashCode());
        hash = 31 * hash + (this.payChannel == null ? 0 : this.payChannel.hashCode());
        hash = 31 * hash + (this.couponId == null ? 0 : this.couponId.hashCode());
        hash = 31 * hash + (this.distributeItemType == null ? 0 : this.distributeItemType.hashCode());
        hash = 31 * hash + (this.expectPrice == null ? 0 : this.expectPrice.hashCode());
        hash = 31 * hash + (this.payPrice == null ? 0 : this.payPrice.hashCode());
        hash = 31 * hash + (this.thirdPartOrderNo == null ? 0 : this.thirdPartOrderNo.hashCode());
        hash = 31 * hash + (this.transTime == null ? 0 : this.transTime.hashCode());
        hash = 31 * hash + (this.advanceTrans == null ? 0 : this.advanceTrans.hashCode());
        hash = 31 * hash + (this.delayTrans == null ? 0 : this.delayTrans.hashCode());
        hash = 31 * hash + (this.distributionRequestParam == null ? 0 : this.distributionRequestParam.hashCode());
        hash = 31 * hash + (this.sku == null ? 0 : this.sku.hashCode());
        hash = 31 * hash + (this.clientExtraInfo == null ? 0 : this.clientExtraInfo.hashCode());
        hash = 31 * hash + (this.userContactDto == null ? 0 : this.userContactDto.hashCode());
        hash = 31 * hash + (this.itemQuantity == null ? 0 : this.itemQuantity.hashCode());
        hash = 31 * hash + (this.signOrderNo == null ? 0 : this.signOrderNo.hashCode());
        hash = 31 * hash + (this.utmSource == null ? 0 : this.utmSource.hashCode());
        return hash;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof DistributeDto)) {
            return false;
        }
        DistributeDto that = (DistributeDto) other;
        if (this.appinfo == null) {
            if (that.appinfo != null) {
                return false;
            }
        } else if (!this.appinfo.equals(that.appinfo)) {
            return false;
        }
        if (this.userInfo == null) {
            if (that.userInfo != null) {
                return false;
            }
        } else if (!this.userInfo.equals(that.userInfo)) {
            return false;
        }
        if (this.deviceInfo == null) {
            if (that.deviceInfo != null) {
                return false;
            }
        } else if (!this.deviceInfo.equals(that.deviceInfo)) {
            return false;
        }
        if (this.openapiInternaldomain == null) {
            if (that.openapiInternaldomain != null) {
                return false;
            }
        } else if (!this.openapiInternaldomain.equals(that.openapiInternaldomain)) {
            return false;
        }
        if (this.payContent == null) {
            if (that.payContent != null) {
                return false;
            }
        } else if (!this.payContent.equals(that.payContent)) {
            return false;
        }
        if (this.payChannel == null) {
            if (that.payChannel != null) {
                return false;
            }
        } else if (!this.payChannel.equals(that.payChannel)) {
            return false;
        }
        if (this.couponId == null) {
            if (that.couponId != null) {
                return false;
            }
        } else if (!this.couponId.equals(that.couponId)) {
            return false;
        }
        if (this.distributeItemType == null) {
            if (that.distributeItemType != null) {
                return false;
            }
        } else if (!this.distributeItemType.equals(that.distributeItemType)) {
            return false;
        }
        if (this.expectPrice == null) {
            if (that.expectPrice != null) {
                return false;
            }
        } else if (!this.expectPrice.equals(that.expectPrice)) {
            return false;
        }
        if (this.payPrice == null) {
            if (that.payPrice != null) {
                return false;
            }
        } else if (!this.payPrice.equals(that.payPrice)) {
            return false;
        }
        if (this.thirdPartOrderNo == null) {
            if (that.thirdPartOrderNo != null) {
                return false;
            }
        } else if (!this.thirdPartOrderNo.equals(that.thirdPartOrderNo)) {
            return false;
        }
        if (this.transTime == null) {
            if (that.transTime != null) {
                return false;
            }
        } else if (!this.transTime.equals(that.transTime)) {
            return false;
        }
        if (this.advanceTrans == null) {
            if (that.advanceTrans != null) {
                return false;
            }
        } else if (!this.advanceTrans.equals(that.advanceTrans)) {
            return false;
        }
        if (this.delayTrans == null) {
            if (that.delayTrans != null) {
                return false;
            }
        } else if (!this.delayTrans.equals(that.delayTrans)) {
            return false;
        }
        if (this.distributionRequestParam == null) {
            if (that.distributionRequestParam != null) {
                return false;
            }
        } else if (!this.distributionRequestParam.equals(that.distributionRequestParam)) {
            return false;
        }
        if (this.sku == null) {
            if (that.sku != null) {
                return false;
            }
        } else if (!this.sku.equals(that.sku)) {
            return false;
        }
        if (this.clientExtraInfo == null) {
            if (that.clientExtraInfo != null) {
                return false;
            }
        } else if (!this.clientExtraInfo.equals(that.clientExtraInfo)) {
            return false;
        }
        if (this.userContactDto == null) {
            if (that.userContactDto != null) {
                return false;
            }
        } else if (!this.userContactDto.equals(that.userContactDto)) {
            return false;
        }
        if (this.itemQuantity == null) {
            if (that.itemQuantity != null) {
                return false;
            }
        } else if (!this.itemQuantity.equals(that.itemQuantity)) {
            return false;
        }
        if (this.signOrderNo == null) {
            if (that.signOrderNo != null) {
                return false;
            }
        } else if (!this.signOrderNo.equals(that.signOrderNo)) {
            return false;
        }
        if (this.utmSource == null) {
            if (that.utmSource != null) {
                return false;
            }
        } else if (!this.utmSource.equals(that.utmSource)) {
            return false;
        }
        return true;
    }
}
