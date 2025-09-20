package com.ximalaya.openapi.admin.ex;

import lombok.Data;

/**
 **/
@Data
public class ParamCheckException extends RuntimeException {

    private String msg;

    public ParamCheckException(String msg) {
        this.msg = msg;
    }

    public ParamCheckException(String message, String msg) {
        super(message);
        this.msg = msg;
    }

    public ParamCheckException(String message, Throwable cause, String msg) {
        super(message, cause);
        this.msg = msg;
    }

    public ParamCheckException(Throwable cause, String msg) {
        super(cause);
        this.msg = msg;
    }
}
