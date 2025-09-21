package signature.entity;

import lombok.Data;


@Data
public class CommonResponse {

    private int code;

    private String msg;

    private Object date;

    public CommonResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }



}
