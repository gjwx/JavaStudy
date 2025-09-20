package com.ximalaya.commons.model.entity;

/**
 * 〈〉
 *
 * @author gongjiawei
 * @date 2025/9/18 下午4:08
 */
public class UserContactInfo {

    private String contactName;
    private String contactPhone;
    private String areaCode;
    private String addressLine;
    private Boolean defaultAddress;
    private String usrContactId;

    public UserContactInfo() {
    }

    public UserContactInfo(String contactName, String contactPhone, String areaCode, String addressLine, Boolean defaultAddress, String usrContactId) {
        this.contactName = contactName;
        this.contactPhone = contactPhone;
        this.areaCode = areaCode;
        this.addressLine = addressLine;
        this.defaultAddress = defaultAddress;
        this.usrContactId = usrContactId;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactName() {
        return this.contactName;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getContactPhone() {
        return this.contactPhone;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getAreaCode() {
        return this.areaCode;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    public String getAddressLine() {
        return this.addressLine;
    }

    public void setDefaultAddress(Boolean defaultAddress) {
        this.defaultAddress = defaultAddress;
    }

    public Boolean getDefaultAddress() {
        return this.defaultAddress;
    }

    public void setUsrContactId(String usrContactId) {
        this.usrContactId = usrContactId;
    }

    public String getUsrContactId() {
        return this.usrContactId;
    }

    @Override
    public String toString() {
        return "UserContactInfo(" +"contactName=" + this.contactName + ", " + "contactPhone=" + this.contactPhone + ", " + "areaCode=" + this.areaCode + ", " + "addressLine=" + this.addressLine + ", " + "defaultAddress=" + this.defaultAddress + ", " + "usrContactId=" + this.usrContactId + ")";
    }

    @Override
    public int hashCode() {
        int hash = 1;
        hash = 31 * hash + (this.contactName == null ? 0 : this.contactName.hashCode());
        hash = 31 * hash + (this.contactPhone == null ? 0 : this.contactPhone.hashCode());
        hash = 31 * hash + (this.areaCode == null ? 0 : this.areaCode.hashCode());
        hash = 31 * hash + (this.addressLine == null ? 0 : this.addressLine.hashCode());
        hash = 31 * hash + (this.defaultAddress == null ? 0 : this.defaultAddress.hashCode());
        hash = 31 * hash + (this.usrContactId == null ? 0 : this.usrContactId.hashCode());
        return hash;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof UserContactInfo)) {
            return false;
        }
        UserContactInfo that = (UserContactInfo) other;
        if (this.contactName == null) {
            if (that.contactName != null) {
                return false;
            }
        } else if (!this.contactName.equals(that.contactName)) {
            return false;
        }
        if (this.contactPhone == null) {
            if (that.contactPhone != null) {
                return false;
            }
        } else if (!this.contactPhone.equals(that.contactPhone)) {
            return false;
        }
        if (this.areaCode == null) {
            if (that.areaCode != null)
                return false;
        } else if (!this.areaCode.equals(that.areaCode)) {
            return false;
        }
        if (this.addressLine == null) {
            if (that.addressLine != null)
                return false;
        } else if (!this.addressLine.equals(that.addressLine)) {
            return false;
        }
        if (this.defaultAddress == null) {
            if (that.defaultAddress != null) {
                return false;
            }
        } else if (!this.defaultAddress.equals(that.defaultAddress)) {
            return false;
        }
        if (this.usrContactId == null) {
            if (that.usrContactId != null)
                return false;
        } else if (!this.usrContactId.equals(that.usrContactId)) {
            return false;
        }
        return true;
    }
}
