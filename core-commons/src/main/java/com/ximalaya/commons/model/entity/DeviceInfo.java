package com.ximalaya.commons.model.entity;

/**
 * 〈〉
 *
 * @author gongjiawei
 * @date 2025/9/18 下午4:06
 */
public class DeviceInfo {
    private Integer clientOsType;
    private Integer deviceTypeId;
    private String deviceId;

    public DeviceInfo() {
    }

    public DeviceInfo(Integer clientOsType, Integer deviceTypeId, String deviceId) {
        this.clientOsType = clientOsType;
        this.deviceTypeId = deviceTypeId;
        this.deviceId = deviceId;
    }

    public void setClientOsType(Integer clientOsType) {
        this.clientOsType = clientOsType;
    }

    public Integer getClientOsType() {
        return this.clientOsType;
    }

    public void setDeviceTypeId(Integer deviceTypeId) {
        this.deviceTypeId = deviceTypeId;
    }

    public Integer getDeviceTypeId() {
        return this.deviceTypeId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    @Override
    public String toString() {
        return "DeviceInfo(" +"clientOsType=" + this.clientOsType + ", " + "deviceTypeId=" + this.deviceTypeId + ", " + "deviceId=" + this.deviceId + ")";
    }

    @Override
    public int hashCode() {
        int hash = 1;
        hash = 31 * hash + (this.clientOsType == null ? 0 : this.clientOsType.hashCode());
        hash = 31 * hash + (this.deviceTypeId == null ? 0 : this.deviceTypeId.hashCode());
        hash = 31 * hash + (this.deviceId == null ? 0 : this.deviceId.hashCode());
        return hash;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof DeviceInfo)) {
            return false;
        }
        DeviceInfo that = (DeviceInfo) other;
        if (this.clientOsType == null) {
            if (that.clientOsType != null) {
                return false;
            }
        } else if (!this.clientOsType.equals(that.clientOsType)) {
            return false;
        }
        if (this.deviceTypeId == null) {
            if (that.deviceTypeId != null) {
                return false;
            }
        } else if (!this.deviceTypeId.equals(that.deviceTypeId)) {
            return false;
        }
        if (this.deviceId == null) {
            if (that.deviceId != null)
                return false;
        } else if (!this.deviceId.equals(that.deviceId)) {
            return false;
        }
        return true;
    }
}
