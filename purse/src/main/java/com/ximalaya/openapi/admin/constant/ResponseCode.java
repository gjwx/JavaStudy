package com.ximalaya.openapi.admin.constant;

/**
 * @author hylexus
 * createdAt 2018/5/29
 **/
public enum ResponseCode {
    /**
     * 成功
     */
    SUCCESS(0, "success"),
    FAIL(-1, "failed"),
    /**
     * 服务端统一错误码
     */
    SERVER_ERR(500, "内部异常"),
    BUSINESS_ERR(-1, ""),
    PARAM_ERR(-2, ""),
    SERVER_BACKEND_SERVICE_ERR(501, ""),
    AUTH_REQUIRED(401, "auth required"),
    ACCESS_DENIED(403, ""),
    DEVELOPER_ROLE_REQUIRED(40300, "developer role required"),
//    DEVELOPER_ROLE_REQUIRED(-40300, "developer role required"),
    VERIFY_CODE_REQUIRED(40301, "请先提交验证码"),
    ECHO_MSG_TO_CLIENT(666, ""),// 客户端见到该异常码，直接提示msg给用户即可
    RESOURCE_NOT_FOUND(40400, "您访问的资源不存在"),
    DEVELOPER_NAME_IS_NOT_EXIST(405, "开发者名称不存在")
    ;
    private int code;
    private String msg;

    ResponseCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
