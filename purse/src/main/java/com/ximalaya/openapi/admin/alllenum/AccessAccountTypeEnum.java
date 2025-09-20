package com.ximalaya.openapi.admin.alllenum;

public enum AccessAccountTypeEnum {

    ACCESS_TOKEN(1,"access token接入"),

    THIRD_UID(2, "第三方账户接入"),

    MOBILE(3,"手机号接入"),

    UID(4,"喜马uid接入"),

    CORP_PERSONAL_UID(5,"轻学堂企业个人账号接入"),

    CORP_UID(6,"轻学堂企业级账号接入"),

    B_CORP_UID(7,"B端合作企业账号接入"),

    UNKNOWN(10, "未知");

    private Integer type;
    private String name;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    AccessAccountTypeEnum(Integer type, String name) {
        this.type = type;
        this.name = name;
    }

    public static AccessAccountTypeEnum getTypeEnum(Integer status) {
        AccessAccountTypeEnum result = null;
        switch(status) {
            case 1:
                result = ACCESS_TOKEN;
                break;
            case 2:
                result = THIRD_UID;
                break;
            case 3:
                result = MOBILE;
                break;
            case 4:
                result = UID;
                break;
            case 5:
                result = CORP_PERSONAL_UID;
                break;
            case 6:
                result = CORP_UID;
                break;
            case 7:
                result = B_CORP_UID;
                break;
            default:
                result = UNKNOWN;
                break;
        }
        return result;
    }

}
