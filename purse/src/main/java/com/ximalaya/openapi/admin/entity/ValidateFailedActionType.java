package com.ximalaya.openapi.admin.entity;

/**
 * @author yunfeng.lu
 * @create 2019-06-18.
 */
public enum ValidateFailedActionType{

    /**
     * 不支持的身份校验类型
     */
    NOT_SUPPORTED(0),
    /**
     * 校验失败抛出异常
     */
    THROW_OUT_EXCEPTION(1),
    /**
     * 校验失败与否仍然继续执行
     */
    ALWAYS_CONTINUE(2);

    //---------------------------------------------------------------

    private int validateFailedActionType;

    ValidateFailedActionType(int priceType){
        this.validateFailedActionType = priceType;
    }

    public int getValidateFailedActionType(){
        return validateFailedActionType;
    }

    public static ValidateFailedActionType getValidateFailedActionType(int validateFailedActionType){
        ValidateFailedActionType result = null;
        switch (validateFailedActionType) {
            case 1:
                result = ValidateFailedActionType.THROW_OUT_EXCEPTION;
                break;
            case 2:
                result = ValidateFailedActionType.ALWAYS_CONTINUE;
                break;
            default:
                result = ValidateFailedActionType.NOT_SUPPORTED;
                break;
        }
        return result;
    }
}
