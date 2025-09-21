package com.ximalaya.openapi.admin.entity;

/**
 * @author yunfeng.lu
 * @create 2019-06-18.
 */
public enum CompliantType{

    /**
     * 不支持的对接服务类型
     */
    NOT_SUPPORTED(0),

    SERVER_ONLY(1),

    CLIENT_ONLY(2),

    SERVER_CLIENT(3);

    //---------------------------------------------------------------

    private int value;

    CompliantType(int value){
        this.value = value;
    }

    public int getValue(){
        return value;
    }

    //---------------------------------------------------------------

    public static CompliantType getCompliantType(int compliantType){
        switch (compliantType) {
            case 1:
                return SERVER_ONLY;
            case 2:
                return CLIENT_ONLY;
            case 3:
                return SERVER_CLIENT;
            default:
                return NOT_SUPPORTED;
        }
    }
}
