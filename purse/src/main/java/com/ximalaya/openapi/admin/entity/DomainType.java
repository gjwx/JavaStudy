package com.ximalaya.openapi.admin.entity;

/**
 * @author yunfeng.lu
 * @create 2019-06-18.
 */
public enum DomainType {
    /**
     * 不支持的平台类型
     */
    NOT_SUPPORTED(0),
    /**
     * ximalaya
     */
    XIMALAYA(1),
    /**
     * 第三方
     */
    THIRD_PARTY(2);

    private int value;

    private DomainType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static DomainType getDomainType(int domainType) {
        DomainType result = null;
        switch (domainType) {
            case 1:
                result = XIMALAYA;
                break;
            case 2:
                result = THIRD_PARTY;
                break;
            default:
                result = NOT_SUPPORTED;
                break;
        }
        return result;
    }
}
