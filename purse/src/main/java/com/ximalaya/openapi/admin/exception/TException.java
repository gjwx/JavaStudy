package com.ximalaya.openapi.admin.exception;

/**
 * 〈异常类〉
 *
 * @author gongjiawei
 * @date 2025/9/11 下午5:13
 */
public class TException extends Exception {
    private static final long serialVersionUID = 1L;

    public TException() {
    }

    public TException(String message) {
        super(message);
    }

    public TException(Throwable cause) {
        super(cause);
    }

    public TException(String message, Throwable cause) {
        super(message, cause);
    }
}
