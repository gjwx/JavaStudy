package signature.entity;

import java.io.Serializable;

/**
 * post 发送请求必须继承这个基类
 *
 */
public class BasePostParam implements Serializable {

    /**
     * 喜马拉雅开放平台应用公钥
     */
    public String app_key;
    /**
     * 固定值4
     */
    public String client_os_type;
    /**
     * 一个随机字符串，随机性越大越好，每个请求都需要重新生成
     */
    public String nonce;
    /**
     * 当前Unix毫秒数时间戳，每个请求都需要重新生成，与当前时间间隔不要超过一小时
     */
    public Long timestamp;

    public String sig;

    public String server_api_version;

}
