package com.ximalaya.openapi.admin.ex;

/**
 */
public class RateLimitException extends RuntimeException {

    /**
     * 只返回http错误码
     */
    private int httpCode;

    public RateLimitException(int httpCode, APIError message) {
        super(message.getErrorCode() + " " + message.getErrorDesc());
        this.httpCode = httpCode;
    }

    public int getCode() {
        return this.httpCode;
    }
}
