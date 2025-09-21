package com.ximalaya.commons.model.entity;

/**
 * 〈〉
 *
 * @author gongjiawei
 * @date 2025/9/18 下午4:05
 */
public class UserInfo {
    private Integer accessAccountType;
    private String uid;
    private String thirdUid;
    private String accessToken;
    private String orderUid;
    private String mobile;

    public UserInfo() {
    }

    public UserInfo(Integer accessAccountType, String uid, String thirdUid, String accessToken, String orderUid, String mobile) {
        this.accessAccountType = accessAccountType;
        this.uid = uid;
        this.thirdUid = thirdUid;
        this.accessToken = accessToken;
        this.orderUid = orderUid;
        this.mobile = mobile;
    }

    public void setAccessAccountType(Integer accessAccountType) {
        this.accessAccountType = accessAccountType;
    }

    public Integer getAccessAccountType() {
        return this.accessAccountType;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUid() {
        return this.uid;
    }

    public void setThirdUid(String thirdUid) {
        this.thirdUid = thirdUid;
    }

    public String getThirdUid() {
        return this.thirdUid;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return this.accessToken;
    }

    public void setOrderUid(String orderUid) {
        this.orderUid = orderUid;
    }

    public String getOrderUid() {
        return this.orderUid;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMobile() {
        return this.mobile;
    }

    @Override
    public String toString() {
        return "UserInfo(" +"accessAccountType=" + this.accessAccountType + ", " + "uid=" + this.uid + ", " + "thirdUid=" + this.thirdUid + ", " + "accessToken=" + this.accessToken + ", " + "orderUid=" + this.orderUid + ", " + "mobile=" + this.mobile + ")";
    }

    @Override
    public int hashCode() {
        int hash = 1;
        hash = 31 * hash + (this.accessAccountType == null ? 0 : this.accessAccountType.hashCode());
        hash = 31 * hash + (this.uid == null ? 0 : this.uid.hashCode());
        hash = 31 * hash + (this.thirdUid == null ? 0 : this.thirdUid.hashCode());
        hash = 31 * hash + (this.accessToken == null ? 0 : this.accessToken.hashCode());
        hash = 31 * hash + (this.orderUid == null ? 0 : this.orderUid.hashCode());
        hash = 31 * hash + (this.mobile == null ? 0 : this.mobile.hashCode());
        return hash;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof UserInfo)) {
            return false;
        }
        UserInfo that = (UserInfo) other;
        if (this.accessAccountType == null) {
            if (that.accessAccountType != null)
                return false;
        } else if (!this.accessAccountType.equals(that.accessAccountType)) {
            return false;
        }
        if (this.uid == null) {
            if (that.uid != null)
                return false;
        } else if (!this.uid.equals(that.uid)) {
            return false;
        }
        if (this.thirdUid == null) {
            if (that.thirdUid != null)
                return false;
        } else if (!this.thirdUid.equals(that.thirdUid)) {
            return false;
        }
        if (this.accessToken == null) {
            if (that.accessToken != null)
                return false;
        } else if (!this.accessToken.equals(that.accessToken)) {
            return false;
        }
        if (this.orderUid == null) {
            if (that.orderUid != null) {
                return false;
            }
        } else if (!this.orderUid.equals(that.orderUid)) {
            return false;
        }
        if (this.mobile == null) {
            if (that.mobile != null)
                return false;
        } else if (!this.mobile.equals(that.mobile)) {
            return false;
        }
        return true;
    }
}
