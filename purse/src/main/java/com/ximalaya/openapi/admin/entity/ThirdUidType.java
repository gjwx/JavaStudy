package com.ximalaya.openapi.admin.entity;

/**
 * @author yunfeng.lu
 * @create 2019-06-20.
 */
public enum ThirdUidType{

    NOT_SUPPORTED(0)/* 不支持的平台类型 */,

    THIRD_PARTY_ACCOUNT(1),

    MOBILE_NO_ACCOUNT(2);

    //---------------------------------------------------------------

    private int value;

    private ThirdUidType(int value){
        this.value = value;
    }

    public int getValue(){
        return value;
    }

    public static ThirdUidType getThirdUidType(int thirdUidType){
        ThirdUidType result = null;
        switch (thirdUidType) {
            case 1:
                result = THIRD_PARTY_ACCOUNT;
                break;
            case 2:
                result = MOBILE_NO_ACCOUNT;
                break;
            default:
                result = NOT_SUPPORTED;
                break;
        }
        return result;
    }
}
