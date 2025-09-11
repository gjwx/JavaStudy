package com.ximalaya.openapi.admin.vo;

import com.ximalaya.openapi.admin.constant.ResponseCode;

import java.io.Serializable;

/**
 * @author hylexus
 * createdAt 2018/5/29
 **/
public class Response<T> implements Serializable {

    private static final long serialVersionUID = -665767755302634795L;

    /**
     * 消息体
     */
    private T data;
    /**
     * 状态码，0：正常,其他参见
     */
    private Integer code;

    private String message;
    /**
     * 扩展参数
     */
    private Object ext;

    public static <E extends Serializable> Response<E> response(boolean result){
        if (result){
            return success();
        } else {
            return failure(ResponseCode.FAIL);
        }
    }

    public static <E extends Serializable> Response<E> success() {
        return success("成功");
    }

    public static <E> Response<E> success(E body) {
        final Response<E> resp = new Response<>();
        resp.data = body;
        resp.code = ResponseCode.SUCCESS.getCode();
        resp.message = "成功";
        return resp;
    }

    public static <E> Response<E> success(String message) {
        final Response<E> resp = new Response<>();
        resp.message = message;
        resp.code = ResponseCode.SUCCESS.getCode();
        return resp;
    }

    public static <E> Response<E> success(E body,String message) {
        final Response<E> resp = new Response<>();
        resp.data = body;
        resp.code = ResponseCode.SUCCESS.getCode();
        resp.message = message;
        return resp;
    }

    public static <E> Response<E> failure(ResponseCode code) {
        return failure(code, null);
    }

    public static <E> Response<E> failure(int code, String msg) {
        final Response<E> response = new Response<>();
        response.code = code;
        response.message = msg;
        return response;
    }

    public static <E> Response<E> failure(ResponseCode code, String msg) {
        return failure(code.getCode(), msg);
    }


    public static <E> Response<E> failure(E body,String message) {
        final Response<E> resp = new Response<>();
        resp.data = body;
        resp.code = ResponseCode.FAIL.getCode();
        resp.message = message;
        return resp;
    }


    public Integer getCode() {
        return code;
    }

    public Response<T> setCode(Integer code) {
        this.code = code;
        return this;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getExt() {
        return ext;
    }

    public Response<T> setExt(Object ext) {
        this.ext = ext;
        return this;
    }

}
