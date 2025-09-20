package com.ximalaya.commons.model.entity;

/**
 * 〈〉
 *
 * @author gongjiawei
 * @date 2025/9/18 下午4:04
 */
public class AppInfo {
    private String appKey;
    private Long promotionUid;

    public AppInfo() {
    }

    public AppInfo(String appKey, Long promotionUid) {
        this.appKey = appKey;
        this.promotionUid = promotionUid;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppKey() {
        return this.appKey;
    }

    public void setPromotionUid(Long promotionUid) {
        this.promotionUid = promotionUid;
    }

    public Long getPromotionUid() {
        return this.promotionUid;
    }

    @Override
    public String toString() {
        return "AppInfo(" +"appKey=" + this.appKey + ", " + "promotionUid=" + this.promotionUid + ")";
    }

    @Override
    public int hashCode() {
        int hash = 1;
        hash = 31 * hash + (this.appKey == null ? 0 : this.appKey.hashCode());
        hash = 31 * hash + (this.promotionUid == null ? 0 : this.promotionUid.hashCode());
        return hash;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof AppInfo)) {
            return false;
        }
        AppInfo that = (AppInfo) other;
        if (this.appKey == null) {
            if (that.appKey != null)
                return false;
        } else if (!this.appKey.equals(that.appKey)) {
            return false;
        }
        if (this.promotionUid == null) {
            if (that.promotionUid != null)
                return false;
        } else if (!this.promotionUid.equals(that.promotionUid)) {
            return false;
        }
        return true;
    }
}
