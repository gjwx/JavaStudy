package com.ximalaya.openapi.admin.ex;

import java.util.Arrays;

/**
 * 〈错误码〉
 *
 * @author gongjiawei
 * @date 2025/9/18 下午3:19
 */
public enum APIError {
    /**
     * 通用错误码
     */
    REQUEST_PARAM_CHECK_FAILED(100, "ximalaya.common.request_param_check_failed", "some request param type is incorrect, please check the doc"/*参数校验失败，详细错误描述由调用方自己提供*/),
    SIGNATURE_CHECK_FAILED(101, "ximalaya.common.signature_check_failed", "signature check failed"),
    PERMISSION_VALIDATE_FAILED(102, "ximalaya.common.permission_validate_failed", ""/*权限验证失败，详细错误描述由调用方自己提供*/),
    APP_VALIDATE_FAILED(103, "ximalaya.common.app_validate_failed", "app_key is invalid, or app is inactive, or client_os_type/pack_id mismatch"),
    REQUEST_OUT_OF_LIMIT(104, "ximalaya.common.request_out_of_limit", "request is out of limit"), /*调用频率超限*/
    HTTP_METHOD_INVALID(105, "ximalaya.common.http_method_invalid", "this api only support HTTP {http_method} method"),
    HTTP_REFERER_INVALID(106, "ximalaya.common.http_referer_invalid", "your http-referer-head is illegal, please contact us to check your domain-name."),
    BRUSH_PLAY_DATA(107, "ximalaya.common.risk_of_brush_play_data", "You are brushing play data, please contact us if have more question."), /*有刷播放数据的嫌疑*/
    SERVER_API_VERSION_REQUIRED(108, "ximalaya.common.server_api_version_required", "server api version required"),
    REDIRECT_URL_NOT_FOUND(109, "ximalaya.common.redirect_url_not_found", "Please make sure the redirect url is correct"),
    REQUEST_ANTI_BRUSH_LIMIT(110, "ximalaya.common.risk_of_brush_limit", "Your device_id or third_uid are identified request too many times, please try again later"),
    NOT_ALLOWED_TO_BE_DISTRIBUTABLE(111, "ximalaya.common.not_allowed_to_be_distributable", "Please make sure your app is distributable"),//app没有入驻

    /**
     * OAuth2相关错误码
     */
    REDIRECT_URI_INVALID(200, "ximalaya.oauth2.redirect_uri_invalid", "redirect_uri is missing or unmatch with what you registered when creating the app"),
    RESPONSE_TYPE_INVALID(201, "ximalaya.oauth2.response_type_invalid", "response_type is invalid or not supported, only 'code' is allowed"),
    SCOPE_GRANT_DENIED(202, "ximalaya.oauth2.scope_grant_denied", "scope grant denied because scope is invalid or out of bound"),
    AUTHORIZATION_CODE_INVALID(203, "ximalaya.oauth2.authorization_code_invalid", "authorization_code is incorrect or has expired"),
    AUTHORIZATION_GRANT_DENIED(204, "ximalaya.oauth2.authorization_grant_denied", "user or authorization server may have revoked authorization grant"),
    GRANT_TYPE_INVALID(205, "ximalaya.oauth2.grant_type_invalid", "grant_type is invalid or not supported, only authorizatioin_code, client_credentials, refresh_token are allowed"),
    ACCESS_TOKEN_INVALID_OR_EXPIRED(206, "ximalaya.oauth2.access_token_invalid_or_expired", "access_token is invalid or expired"),
    USER_LOGIN_FAILED(207, "ximalaya.oauth2.user_login_failed", "用户名或密码错误，请重新输入！"),
    OAUTH2_AUTHENTICATE_FAILED(208, "ximalaya.authenticate.oauth2_authenticate_failed", "oauth2 authenticate failed when try to acquire a new access_token."),   /*申请oauth2 access_token时候，服务端身份认证失败*/
    OAUTH2_DIVICE_ID_INVALID(209, "ximalaya.authenticate.oauth2_device_id_invalid", "oauth2 device_id is invalid."),
    OAUTH2_APP_VALIDATE_FAILED(210, "ximalaya.common.app_validate_failed", "client_id is invalid, or app is inactive."),
    OAUTH2_UID_INVALID(211, "ximalaya.common.uid_is_missing_or_invalid", "uid is missing or invalid."),
    REFFRESH_TOKEN_INVALID_OR_EXPIRED(212, "ximalaya.oauth2.refresh_token_invalid_or_expired", "refresh_token is invalid or expired"),
    OAUTH2_THIRD_UID_INVALID(213, "ximalaya.oauth2.third_uid_invalid", "third_uid is invalid"),
    OAUTH2_THIRD_TOKEN_INVALID(214, "ximalaya.oauth2.third_token_invalid", "third_token is invalid"),
    OAUTH2_THIRD_TOKEN_VALIDATE_URI_INVALID(215, "ximalaya.oauth2.third_token_validate_uri_invalid", "third_token_validate_uri is invalid"),
    OAUTH2_THIRD_TOKEN_VALIDATE_FAILED(216, "ximalaya.oauth2.third_token_validate_failed", "third_token validate failed, maybe your third_token is expired!"),
    OAUTH2_QRCODE_EXPIRED(217, "ximalaya.oauth2.qrcode_expired", "your qrcode is expired, please genarate again!"),
    OAUTH2_MOBILE_UNREGISTERED(218, "ximalaya.oauth2.mobile_unregistered", "mobile has not register ximalaya-account"),
    OAUTH2_QRCODE_LOGIN_DISABLE(219, "ximalaya.oauth2.qrcode_login_disable", "your app don't have permission to login by qrcode, Please contact our technical support staff."),
    THIRD_TOKEN_VALIDATE_URL_ERR(220, "ximalaya.oauth2.third_token_validate_url_error", "some error occur while calling third_token_validate_url, Please ensure that the third_token_validate_url is correctly implemented according to our api_document."),
    OAUTH2_SMS_FREQUENCY_LIMIT_PER_DAY(221, "ximalaya.oauth2.sms_frequency_limit_per_day", "今日获取验证码次数过多，请明日再试"),
    OAUTH2_SMS_FREQUENCY_LIMIT_PER_MINUTE(222, "ximalaya.oauth2.sms_frequency_limit_per_minute", "要过一分钟才能重新发送噢"),
    APPKEY_INCONSISTENT_WITH_ACCESS_TOKEN(223, "ximalaya.common.app_key is inconsistent with access_token", "app_key is inconsistent with the app_key used when applying for access_token"),
    DEVICE_ID_INCONSISTENT_WITH_ACCESS_TOKEN(224, "ximalaya.common.device_id is inconsistent with access_token", "device_id is inconsistent with the device_id used when applying for access_token"),
    OAUTH2_INVOKE_WITH_SAME_NONCE_TIMESTAMP_PARAMS(225, "ximalaya.duplicate invoke with same nonce and timestamp", "duplicate invoke with same nonce and timestamp is not allowed for oauth2-authentication"),   /*申请oauth2 access_token时候，重复使用nonce timestamp参数*/

    /**
     * 身份认证、用户状态相关错误码
     */
    OTP_INVALID_OR_EXPIRED(300, "ximalaya.authenticate.otp_invalid_or_expired", "otp is invalid or expired"),
    SERVER_AUTHENTICATE_FAILED(301, "ximalaya.authenticate.server_authenticate_failed", "server authenticate failed"),   /*服务端身份认证失败*/

    /**
     * 第三方业务异常相关错误码
     */
    RADIO_NOT_FOUNOD(400, "ximalaya.live.radio_not_found", "radio not found"),
    SCHEDULE_NOT_FOUND(401, "ximalaya.live.schedule_not_found", "schedule not found"),
    PROGRAM_NOT_FOUND(402, "ximalaya.live.program_not_found", "program not found"),
    LIVE_ANNOUNCER_NOT_FOUND(403, "ximalaya.live.announcer_not_found", "live announcer not found"),
    SDKORAPP_VERSION_NOT_FOUND(405, "ximalaya.versionquery.sdk_or_app_version_not_found", "sdk_or_appVersion_not_found"),
    SEARCH_KEY_CONTAINS_SENSITIVE_WORD(406, "ximalaya.search.search_key_contains_sensitive_word", "your search key contains sensitive word"),
    CUSTOMIZED_ICON_ACCESS_VIOLATED(407, "ximalaya.customized.icon_access_violated", "unauthorized icon interface access"),
    CUSTOMIZED_TRACK_ACCESS_VIOLATED(408, "ximalaya.customized.track_access_violated", "unauthorized track interface access"),
    TIMESTAMP_NOT_IN_LEGAL_RANGE(409, "ximalaya.timestamp.not_in_legal_range", "timestamp not in legal range，Please confirm the validity of this field"),
    QUERY_ACTIVITY_RECORD_ERROR(410, "ximalaya.activity.query_activity_record_error", "query activity record error"),


    /**
     * 服务器错误
     */
    INTERNAL_SERVICE_ERROR(500, "ximalaya.system.internal_service_error", "some server error occurs in internal service"),   // 内部服务调用错误
    THIRDPARTY_SERVICE_ERROR(501, "ximalaya.system.thirdparty_service_error", "some error occurs in third party service"),   // 第三方服务调用错误
    UNKNOWN_SERVER_ERROR(502, "ximalaya.system.unknown_server_error", "unknown server error occurs"),                    // 未知服务器错误

    /**
     * 付费相关错误
     */
    OFF_SHELVE_PAY_CONTENT_ERROR(600, "ximalaya.pay.pay_content_off_shelve", "pay content is off shelve"),	// 待购买内容已下架
    PAID_TRACKS_FROM_DIFFERENT_ALBUM_ERROR(601, "ximalaya.pay.paid_tracks_from_different album", "paid tracks are from different album"),	// 待购买声音来自不同专辑
    INCORRECT_PRICE_ERROR(602, "ximalaya.pay.price_incorrect", "price is incorret"),	// 单价校验失败
    EXIST_UNPAID_ORDER_ERROR(603, "ximalaya.pay.already_exist_unpaid_order", "there already exist unpaid order"),	// 已存在未支付订单包含重叠付费音频
    //	UNSUPPORTED_PAY_TYPE_ERROR(604, "ximalaya.pay.pay_type_not_supported", "pay type is not supported"),	// 购买类型不支持(用于处理619 620和621以外的异常情况)
    ORDER_HAS_BEEN_CANCELLED_ERROR(605, "ximalaya.pay.order_can_not_cancelled_again", "order can not be cancelled again"),	// 订单已取消不能再取消
    NOT_EXIST_ORDER_ERROR(606, "ximalaya.pay.order_not_exist", "the specified order doesn't exist"),	// 订单号不存在
    COMPLETED_ORER_ERROR(607, "ximalaya.pay.completed_order_not_allow_changed", "order is completed, so its status is not allowed to change"),	// 订单状态已终结（已成功或已取消），状态不可再更改
    FREE_TRACK_CAN_NOT_GET_PLAY_INFO_ERROR(608, "ximalaya.pay.free_track_not_allow_get_play_info", "free track can not get play info"),	// 获取免费声音的播放和下载地址
    DISABLE_PAY_ERROR(609, "ximalaya.pay.app_disable_pay", "app don't have enable pay permission"), // 应用没有付费音频接入资格
    REAL_IP_NOT_INCLUDE_IN_WHITE_LIST_ERROR(610, "ximalaya.pay.real_ip_not_include_in_white_list", "real ip are not included in ip white list"),    // 应用IP不在白名单中
    OFF_SHELVE_AND_NOT_BELONG_REJECT_PAYED_VISIBLE_TRACK_ERROR(611, "ximalaya.pay.track_is_not_paid_track", "track is off shelve and not visible for user can not get play info"),	// 已下架且不是用户已购买的声音不能获取播放下载信息
    PAID_TRACK_NOT_SUPPORT_PREVIEW_LISTEN_ERROR(612, "ximalaya.pay.paid_track_not_support_preview_listen", "paid track not support preview listen"),	// 该付费声音不支持试听
    MISMATCH_ORDER_INFO_AND_USER_ERROR(613, "ximalaya.pay_mismatch_order_info_and_user", "order did not belong to user"),	// 当前查询的订单不属于当前用户
    ALBUM_IS_NOT_EXISTED_ERROR(614, "ximalaya.pay_album_is_not_existed", "album is not existed"),	// 要查询价格信息的专辑不存在
    ORDER_HAS_CONFIRMED_ERROR(615, "ximalaya.pay_order_has_confirmed", "order has confirmed, can not place order"),	// 已经确单的订单不能再下单
    EXIST_PAID_ORDER_ERROR(616, "ximalaya.pay_exist_paid_order", "there already exist other paid order"),	// 已有其他支付订单
    INVALID_PAGE_ERROR(617, "ximalaya.redeem.request_page_is_invalid_page", "the request page is invalid page"),	// 请求的页面是非法页面
    COMMODITY_HAS_BEEN_BOUGHT_ON_XIMALAYA_ERROR(618, "ximalaya.pay_commodity_has_been_bought_on_ximalaya", "commodity has been bought on ximalaya app"),	// 用户已经在喜马拉雅APP上购买过该商品
    UNSUPPORTED_BUY_PREVIEW_LISTEN_RESOURCE_ERROR(619, "ximalaya.pay.pay_content_is_free_preview_listen_resouce", "pay content is free to preview listen and no need to buy"),	// 声音资源是免费试听的，无需购买
    PLACE_SINGLE_TRACK_AS_WHOLE_ALBUM_ERROR(620, "ximalaya.pay.place_single_track_type_as_whole_album_type", "pay content is single track type, can't buy as whole album type"),	// 购买的资源是单集购买类型，不能当做整张类型购买
    //	PLACE_WHOLE_ALBUM_AS_SINGLE_TRACK_ERROR(621, "ximalaya.pay.place_whole_album_type_as_single_track_type", "pay content is whole album type, can't buy as single track type"),	// 购买的资源是整张购买类型，不能当做整张类型购买
    COMMODITY_IS_NOT_EXIST_ERROR(621, "ximalaya.pay.the_commodity_to_be_purchased_is_not_exist", "the commodity to be purchased is not exist"),	// 购买的商品不存在

    CHANNEL_NAME_IS_EMPTY_ERROR(622, "xiamalaya.redeem.channel_name_is_empty", "channel name of app is empty"),	// 应用的channel name为空
    MISMATCH_BATCH_ID_AND_CHANNEL_ERROR(623, "ximalaya.redeem.mismatch_batch_id_and_channel", "batch id did not belong to channel side, no permission to get redeem codes of batch id"),	// 需要查询的批次Id不在该渠道下，没有权限获取批次对应的兑换码信息

    MOBILE_UNBOUND_XIMA_ACCOUNT_ERROR(624, "ximalaya.pay.phone_number_is_not_bound_to_ximalaya_account", "phone number is not bound to ximalaya account"),	// 手机号未绑定喜马拉雅账户
    INSUFFICIENT_BALANCE_OF_ACCOUNT_ERROR(625, "ximalaya.pay.insufficient_balance_of_account", "Insufficient balance of account, please refill it"),	// 推广账户余额不足，无法下单

    INVALID_RECHARGE_AMOUNT(626, "ximalaya.pay.invalid_recharge_amount", "recharge amount is invalid to recharge"),	// 非法的充值金额
    REPEAT_THIRD_PARTY_ORDER_NO(627, "ximalaya.pay.repeated_third_party_order_number", "third party order number is repeated"),	// 订单号重复
    INVALID_AGENT_INFO(628, "ximalaya.pay.agent_id_is_invalid_or_is_not_exist", "agent id is invalid or is not exist"),	// 商家信息无效
    USER_DID_NOT_LOGIN_ERROR(629, "ximalaya.pay.user_did_not_log_in", "user did not login, please login first"),	// 用户未登录
    USER_ALREADY_ALLOCATE_COUPON(630, "ximalaya.pay.user_has_already_allocated_coupon", "user has already allocated coupon before"),	// 用户已经领过该活动优惠券
    FREE_ALBUM_HAS_NO_PRICE_ERROR(631, "ximalaya.pay.free_album_has_no_price", "free album has no price"), 	//	免费专辑没有价格
    COUPON_ID_IS_INVALID(632, "ximalaya.pay.coupon_id_is_invalid", "coupon_id is invalid"),	// 该活动优惠券已失效
    COUPON_ID_HAS_BEEN_USED(633, "ximalaya.pay.user_has_used_coupon_id", "user has used this coupon_id"),	// 用户已经使用过此优惠券
    COUPON_ID_IS_OUT_OF_VALID_TIME(634, "ximalaya.pay.coupon_id_is_out_of_valid_time", "coupon_id is out of valid time, it is not allowed to be used now"),	// 该活动优惠券不在可用时间范围内
    COUPON_ID_IS_OUT_OF_USING_THRESHOLD(635, "ximalaya.pay.coupon_id_is_out_of_using_threshold", "coupon_id is out of using threshold"),	// 未达到该活动优惠券的使用门槛
    COUPON_ID_EXCEED_ORDER_AMOUNT(636, "ximalaya.pay.coupon_id_exceed_order_amount", "coupon_id exceed order amount"),	// 活动优惠券超过订单金额
    USER_HAS_NOT_ALLOCATE_COUPON_ID(637, "ximalaya.pay.user_has_not_allocate_coupon_id", "user has not allocate coupon_id"),	// 用户没有领取活动优惠券
    EXPERIENCE_MEMBERSHIP_QUOTA_HAS_BEEN_USED_UP_ERROR(638, "ximalaya.pay.experience_membership_quota_has_been_used_up", "experience membership quota has been used up"),	// 体验会员额度已经用完
    MEMBERSHIP_EXCLUSIVE_ALBUM_CAN_NOT_BUY_ERROR(639, "ximalaya.pay.membership_exclusive_album_does_not_support_buying", "membership exclusive album does not support buying"),	// 会员专享专辑不支持售卖
    COUPON_NOT_FOUND(640,"ximalaya.pay.coupon_not_found","coupon not found"),//优惠券不存在
    CREATE_VIRSUAL_USER_ERROR(641,"ximalaya.pay.create_virsual_user_error","create virsual user error"),//创建虚拟用户失败
    THIRD_PART_ORDER_NO_IS_NOT_EXIST(642,"ximalaya.pay.third_party_order_no_is_not_exist","third party order no is not exist"),

    /***
     * 声音加密解密相关
     * **/
    CLIENT_PUB_KEY_INVALID(701,"ximalaya.codec.track.client_pub_key_invalid","client public key is invalid"),
    SECRET_NOT_EXISTS(702,"ximalaya.codec.track.aes.secret_not_exists","secret for encryption is not exists or expired"),

    /**
     * 新分销相关
     */
    FIND_BY_XIMA_ORDER_ERROR(801,"ximalaya.new.distribution_find_by_xima_order_error","find by ximaOrderNo cause exception"), // 查询商业化订单明细异常
    DATA_CHECK_CLEARING_RATE_NOT_SET(802,"ximalaya.new.distribution_data_check_clearing_rate_not_set", "data check clearing rate not set"), //未设置分成比例
    UNSUPPORTED_DISTRIBUTE_MODE(803,"ximalaya.new.distribution_unsupport_distribution_mode","unsupport distribution mode"), // 不支持的分销付款模式
    COPYRIGHT_ERROR(804,"ximalaya.new.distribution_copyright_invoke_error","copyright invoke error"),// 调用开放平台版权异常
    ILLEGAL_CLIENT_OS_TYPE(805,"ximalaya.new.distribution_illegal_client_os_type","illegal client os type"),
    UNKNOWN_DISTRIBUTE_ITEM_TYPE(806,"ximalaya.new.distribution_unknown_distribute_item_type","unknown distribute item type"),
    USER_HAS_NOT_ALLOCATE_COUPON_ID_ERROR(807,"ximalaya.new.distribution_user_has_not_allocate_coupon_id_error","user has not allocate coupon id error"), //用户未领券
    CLEARING_RATE_SERVICE_ERROR(808,"ximalaya.new.distribution_clearing_rate_service_error","clearing rate service error"),
    NOT_FOUND_CLEARING_RATE(809,"ximalaya.new.distribution_not_found_clearing_rate","not found clearing rate"),
    NOT_FOUND_PAY_CHANNEL_RATE(810,"ximalaya.new.distribution_not_found_pay_channel_rate","not found pay channel rate"),
    ORDER_REPEAT_SUBMIT_CODE(811,"ximalaya.new.distribution_order_repeat_submit","order repeat submit"), // 订单重复提交
    USER_HAS_PRIVILEGE_CODE(812,"ximalaya.new.distribution_user_has_privilege","user has privilege"), // 用户已经有权益
    PRODUCT_NOT_SUPPORT_REPEAT_PURCHASE(813,"ximalaya.new.distribution_product_not_support_repeat_purchase","product not support repeat purchase") , // 产品不支持重复购买

    UNSUPPORTED_DISTRIBUTOR_SETTLEMENT_TYPE_ERROR(814, "ximalaya.order_domain_error", "the current distributor settlement type is not supported"),   // 不支持该分销商的结算类型
    INVALID_PRODUCT_TYPE(815, "Invalid product_type", "the current product type is not supported"),// 不支持该商品类型
    NOT_SUPPORT_OF_THE_ACCESS_ACCOUNT_TYPE(816, "ximalaya.not_support_of_the_access_account_type", "the current product type is not supported for this access account type"),// 不支持该商品的用户类型
    PRODUCT_NOT_ALLOWED_ON_CLIENT_OS_TYPE(817,"ximalaya.current_product_is_not_supported_on_this_client_os_type","the current product is not supported on this client os type "), //商品不允许在该客户端类型售卖
    PAY_PRICE_SHOULD_NOT_BE_NULL(818, "ximalaya.pay_price_should_not_be_null", "pay_price_should_not_be_null"),
    NOT_SUPPORT_OF_THE_CLIENT_TYPE(819, "ximalaya.not_support_of_the_clietn_type", "the current product type is not supported for this client type"),
    ENABLED_DISTRIBUTION_PRODUCT(820, "ximalaya.enabled_distribution_product", "enabled_distribution_product"),// 该商品不可分销
    ITEM_ALREADY_SIGNED(821,"ximalaya.item_already_signed", "item_already_signed"),// 对应商品已签约

    /**
     * 退款相关错误
     */
    ORDER_REFUND_NOT_DISTRIBUTION_ORDER(822,  "not_distribution_order", "current order is not distribution order"),// 当前订单不是分销订单
    ORDER_REFUND_NOT_BELONG_TO_CURRENT_DISTRIBUTOR(823,  "not_belong_to_current_distributor", "current order is not belong to current distributor"),	// 当前退款订单不属于该分销商
    ORDER_REFUND_HAD_BEEN_REFUNDED(824,  "order_had_been_refunded", "current order had been refunded"),// 当前退款订单已退款
    ORDER_REFUND_ONLY_SUPPORT_THEY_PAY(825, "only_they_pay_distributor_supported", "only they-pay distributor supported"),// 仅支持他方收款合作方进行退款
    ORDER_REFUND_APPKEY_NOT_IN_WHITE_LIST(826, "appkey_not_in_refundable_appkey_white_list", "only appkeys those in white list could refund order"),// 应用不在可退款应用白名单内
    MEMBER_SHIP_EXPIRY_TIME_EXCEDED_LIMIT(827,"distribution_member_product_exceded_limit","can place order cause member expiry time exceded limit") , // 会员时长超限制不能继续购买

    ACTIVITY_PRODUCT_STOCK_SOLD_OUT_ERROR(12431, "ximalaya.new.distribution_activity_product_stock_sold_out_error", "activity product stock sold out error"), //活动库存不足，已售罄
    ORDER_REFUND_HAD_ERROR(40102432, "order_refund_error", "order_refund_error"),


    /***
     * 免费获取所有付费声音相关
     * **/
    APPKEY_HAS_NOT_PRIVILEGE_TO_PAID_TRACK(900,"ximalaya.app_key_could_not_get_paid_track_with_free","appKey could not get paid track with free"), // 该应用不能免费获取付费声音
    DEVICE_ID_HAS_NOT_PRIVILEGE_TO_PAID_TRACK(901,"ximalaya.device_id_could_not_get_paid_track_with_free","deviceId could not get paid track with free"), // 该设备不能免费获取付费声音
    WHITE_LIST_PRIVILEGE_EXPIRED_TO_PAID_TRACK(902,"ximalaya.white_list_privilege_expired_to_paid_track","privilege_expired_to_paid_track"), // 免费获取付费声音权限过期
    DECRY_PAID_TRACK_ERROR(903,"ximalaya.decry_paid_track_error","decry_paid_track_error"), // 解密付费声音出错
    IP_HAS_NOT_PRIVILEGE_TO_PAID_TRACK(904,"ximalaya.ip_could_not_get_paid_track_with_free","ip could not get paid track with free"), // 该ip不能免费获取付费声音
    TRACK_QUERY_PRIVILEGE_OUT_OF_LIMIT(905,"ximalaya.track_query_privilege_out_of_limit","track query privilege out of limit"); // 免费获取付费声音权限超过次数


    private int errorNO;        // 错误编号
    private String errorCode;   // 错误码
    private String errorDesc;   // 错误描述

    APIError(int errorNO, String errorCode, String errorDesc) {
        this.errorNO = errorNO;
        this.errorCode = errorCode;
        this.errorDesc = errorDesc;
    }

    public static APIError getApiErrorByErrorNo(int errorNo) {
        return Arrays.stream(values()).filter(apiError -> apiError.getErrorNO() == errorNo).findFirst().orElse(INTERNAL_SERVICE_ERROR);
    }

    public int getErrorNO() {
        return errorNO;
    }

    public void setErrorNO(int errorNO) {
        this.errorNO = errorNO;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorDesc() {
        return errorDesc;
    }

    public void setErrorDesc(String errorDesc) {
        this.errorDesc = errorDesc;
    }

}
