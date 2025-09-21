package signature.kit;

/**
 * The Class HttpResponseStatusCode.
 */
public class HttpResponseStatusCode{

    /** 请求成功. */
    public static final int HTTP_STATUS_OK                            = 200;

    /** 创建成功. */
    public static final int HTTP_STATUS_CREATED                       = 201;

    /** 更新成功. */
    public static final int HTTP_STATUS_ACCEPTED                      = 202;

    /** 请求永久重定向. */
    public static final int HTTP_STATUS_PERMANENT_REDIRECT            = 301;

    /** 请求临时重定向. */
    public static final int HTTP_STATUS_TEMPORAL_REDIRECT             = 302;

    /** 未更改. */
    public static final int HTTP_SATUS_NOT_MODIFIED                   = 304;

    /** 请求的地址不存在或者包含非法或者不支持的参数. */
    public static final int HTTP_STATUS_BAD_REQUEST                   = 400;

    /** 用户未进行身份认证. */
    public static final int HTTP_STATUS_UNAUTHORIZED                  = 401;

    /** 被禁止访问，因为用户被认定为没有访问特定资源的权限. */
    public static final int HTTP_STATUS_FORBIDDEN                     = 403;

    /** 请求的资源不存在. */
    public static final int HTTP_STATUS_NOT_FOUND                     = 404;

    /** HTTP谓词错误. */
    public static final int HTTP_METHOD_NOT_ALLOWED                   = 405;

    /** 服务器理解请求，但其中包含非法参数. */
    public static final int HTTP_STATUS_UNPROCESSABLE_ENTITY          = 422;

    /** 请求频率超配. */
    public static final int HTTP_STATUS_TOO_MANY_REQUESTS             = 429;

    /** 由于法律或者政策原因禁止内容被访问. */
    public static final int HTTP_STATUS_UNAVAILABLE_FOR_LEGAL_REASONS = 451;

    /** 服务器错误. */
    public static final int HTTP_STATUS_SERVER_ERROR                  = 500;

    /** 服务不可用. */
    public static final int HTTP_STATUS_SERVICE_UNAVAILABLE           = 503;
}
