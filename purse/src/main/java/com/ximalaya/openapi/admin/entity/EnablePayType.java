package com.ximalaya.openapi.admin.entity;

/**
 * @author yunfeng.lu
 * @create 2019-06-18.
 */
public enum EnablePayType {
    /**
     * 不支持的接入类型
     */
    NOT_SUPPORTED(-1),
    DISABLE_PAY(0),
    ENABLE_PAY(1);

    private int value;

    EnablePayType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static EnablePayType getEnablePayType(int enablePayType) {
        EnablePayType result;
        switch (enablePayType) {
            case 0:
                result = DISABLE_PAY;
                break;
            case 1:
                result = ENABLE_PAY;
                break;
            default:
                result = NOT_SUPPORTED;
                break;
        }
        return result;
    }
}
