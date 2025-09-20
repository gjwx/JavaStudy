package com.ximalaya.openapi.admin.entity;

/**
 * @author yunfeng.lu
 * @create 2019-06-19.
 */
public enum ClientOsType {
    IOS(1),
    Android(2),
    /**
     * 针对原有业务上不同的类型枚举名字不一样，所以这样子定义 其实 web和pc 是一个概念
     */
    Web(3),
    PC(3),
    /**
     * Linux或者服务端API对接模式
     */
    SERVER_OR_LINUX(4),
    API(4),
    ECOS(5),
    QNIX(6);

    private int osType;
    private static final ClientOsType[] CLIENT_OS_TYPES = ClientOsType.values();
    private static final int MAX_CLIENT_OS_TYPE_VAL = CLIENT_OS_TYPES.length;

    private ClientOsType(int osType) {
        this.osType = osType;
    }

    public int getOsType() {
        return osType;
    }

    public void setOsType(int osType) {
        this.osType = osType;
    }

    public static ClientOsType getClientOsType(int osType) {
        if (osType <= 0 || osType > MAX_CLIENT_OS_TYPE_VAL) {
            return null;
        }

        return CLIENT_OS_TYPES[osType - 1];
    }
}
