package com.ximalaya.openapi.admin.entity;

/**
 * @author yunfeng.lu
 * @create 2019-06-18.
 */
public enum GoThroughVuserServiceType {
    /**
     * 不支持的接入类型
     */
    NOT_SUPPORTED(-1),
    DISABLE_GO_THROUGHT(0),
    ENABLE_GO_THROUGHT(1);

    private int value;

    GoThroughVuserServiceType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static GoThroughVuserServiceType getGoThroughVuserServiceType(int goThroughVuserServiceType) {
        GoThroughVuserServiceType result = null;
        switch (goThroughVuserServiceType) {
            case 0:
                result = DISABLE_GO_THROUGHT;
                break;
            case 1:
                result = ENABLE_GO_THROUGHT;
                break;
            default:
                result = NOT_SUPPORTED;
                break;
        }
        return result;
    }
}
