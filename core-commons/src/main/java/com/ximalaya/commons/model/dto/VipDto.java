package com.ximalaya.commons.model.dto;

/**
 * 〈〉
 *
 * @author gongjiawei
 * @date 2025/9/18 下午4:41
 */
public class VipDto {
    private Short categoryId;
    private String spuId;
    private Long userId;
    private Long expireDate;
    private Byte level;
    private boolean valid = false;

    public VipDto() {
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Short getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(Short categoryId) {
        this.categoryId = categoryId;
    }

    public String getSpuId() {
        return this.spuId;
    }

    public void setSpuId(String spuId) {
        this.spuId = spuId;
    }

    public Long getExpireDate() {
        return this.expireDate;
    }

    public void setExpireDate(Long expireDate) {
        this.expireDate = expireDate;
    }

    public Byte getLevel() {
        return this.level;
    }

    public void setLevel(Byte level) {
        this.level = level;
    }

    public boolean isValid() {
        return this.valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
@Override
    public String toString() {
        return "VipDto{categoryId=" + this.categoryId + ", spuId='" + this.spuId + '\'' + ", userId=" + this.userId + ", expireDate=" + this.expireDate + ", level=" + this.level + ", valid=" + this.valid + '}';
    }
}
