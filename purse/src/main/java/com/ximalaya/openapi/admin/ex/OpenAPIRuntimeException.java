package com.ximalaya.openapi.admin.ex;


/**

 */
public class OpenAPIRuntimeException extends RuntimeException {
    private static final long serialVersionUID = -3355938693322098987L;

    /**
     * 定义的业务异常，在全局异常处理里面统一返回200
     */
    private int code;

    public OpenAPIRuntimeException(APIError apiError) {
        super(apiError.getErrorCode() + " " + apiError.getErrorDesc());
        code = apiError.getErrorNO();
    }

    public OpenAPIRuntimeException(APIError apiError, String msg) {
        super(apiError.getErrorCode() + " " + msg);
        code = apiError.getErrorNO();
    }

    public OpenAPIRuntimeException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
