package com.ximalaya.openapi.admin.entity;

import com.alibaba.fastjson.JSON;
import com.feilong.json.JsonUtil;
import com.feilong.servlet.http.RequestUtil;
import com.ximalaya.omp.facade.distribution.api.enums.AccessAccountTypeEnum;
import com.ximalaya.omp.openapi.payment.ex.OpenAPIRuntimeException;
import com.ximalaya.omp.openapi.payment.util.IPWhitelistCheckUtil;
import com.ximalaya.omp.openapi.payment.util.WebUtil;
import com.ximalaya.openapi.common.constant.TokenLevelType;
import com.ximalaya.openplatform.appservice.thrift.iface.App;
import com.ximalaya.openplatform.appservice.thrift.iface.AppExtField;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.UUID;

import static com.ximalaya.openapi.log.AutoLog.autoLog;
import static com.ximalaya.openapi.web.common.constant.APIError.REAL_IP_NOT_INCLUDE_IN_WHITE_LIST_ERROR;

/**
 * @author yunfeng.lu
 * @create 2019-06-18.
 */
@Getter
@Setter
@Slf4j
public class PayCommonQueryParam{

    public PayCommonQueryParam(HttpServletRequest request, App app){

        this.appKey = request.getParameter("app_key");
        this.deviceID = request.getParameter("device_id");
        this.clientOsType = getRealClientOsType(request, app);
        this.deviceTypeId = clientOsTypeMapToDeviceTypeId(this.clientOsType);
        this.thirdOrderNo = StringUtils.isBlank(request.getParameter("third_order_no")) ? UUID.randomUUID().toString()
                        : request.getParameter("third_order_no");

        Object accessTokenLevelValue = request.getAttribute("access_token_level");
        if (accessTokenLevelValue != null){
            this.tokenLevelType = TokenLevelType.getTokenLevelType(Integer.parseInt(String.valueOf(accessTokenLevelValue)));
        }
        this.openapiInternaldomain = StringUtils.isNotBlank(request.getParameter("third_uid")) ? DomainType.THIRD_PARTY.getValue()
                        : DomainType.XIMALAYA.getValue();

        this.ip = WebUtil.getRealIP(request);
        if (StringUtils.isBlank(this.ip)){
            this.ip = String.valueOf(request.getAttribute("ip"));
        }

        //---------------------------------------------------------------

        // 这个地方实际上永远不会执行if块后面的逻辑 先增加log记录下
        if (isServerAuthentication(request)){
            if (!IPWhitelistCheckUtil.ifRealIPExistInWhiteList(this.ip, app.getIpWhitelistPay())){
                log.warn(autoLog("realIP [{}] not in whitelist, payCommonQueryParam:{}", this.ip, app.getIpWhitelistPay()));
                throw new OpenAPIRuntimeException(REAL_IP_NOT_INCLUDE_IN_WHITE_LIST_ERROR);
            }
        }

        //---------------------------------------------------------------

        // 风控相关的参数
        this.deviceAgent = request.getParameter("device_user_agent");
        this.deviceIP = request.getParameter("device_ip");
        if (StringUtils.isNotBlank(request.getParameter("uid"))){
            this.uid = Long.parseLong(request.getParameter("uid"));
        }
        this.accessToken = request.getParameter("access_token");
        thirdUid = request.getParameter("third_uid");
        thirdToken = request.getParameter("third_token");

        //---------------------------------------------------------------
        // 如果third_uid_type没传，设置默认值为1-第三方账号
        String thirdUidTypeValue = request.getParameter("third_uid_type");
        if (StringUtils.isBlank(thirdUidTypeValue)){
            thirdUidType = 1;
        }else{
            thirdUidType = Integer.parseInt(String.valueOf(thirdUidTypeValue));
        }

        //---------------------------------------------------------------
        accessAccountId = request.getParameter("access_account_id");

        String accessAccountTypeValue = request.getParameter("access_account_type");
        accessAccountType = StringUtils.isNotBlank(accessAccountTypeValue) ? Integer.parseInt(accessAccountTypeValue) : 0;

        //---------------------------------------------------------------
        mobile = request.getParameter("mobile");
        if (StringUtils.isBlank(mobile)){
            mobile = accessAccountType == AccessAccountTypeEnum.MOBILE.getType() ? accessAccountId : mobile;
        }

        //分页参数
        String parameter = request.getParameter("page");
        page = StringUtils.isNotBlank(parameter) ? Integer.parseInt(parameter) : 1;
        String parameter2 = request.getParameter("count");
        count = StringUtils.isNotBlank(parameter2) ? Integer.parseInt(parameter2) : 20;

        //---------------------------------------------------------------

        // vip短信模板
        Map<String, AppExtField> extFieldsMap = app.getExtFieldsMap();

        this.customVipSmsTemplate = extFieldsMap.get("custom_vip_sms_template").getFieldValue();
        this.developerId = app.getDeveloperId();

        //---------------------------------------------------------------

        // 请求方式 1-服务端 2-客户端，具体取值参见CompliantType枚举类
        String fieldValue = extFieldsMap.get("need_risk_control_params_exist").getFieldValue();
        if (StringUtils.isNotBlank(fieldValue)){
            this.needRiskControlParamsExist = Integer.parseInt(fieldValue) > 0;
        }

        //---------------------------------------------------------------

        String fieldValue2 = extFieldsMap.get("user_register_sms_notification").getFieldValue();
        if (StringUtils.isNotBlank(fieldValue2)){
            this.enableUserRegisterSmsNotification = Integer.parseInt(fieldValue2) > 0;
        }

        //---------------------------------------------------------------

        // 开启直充業務短信通知服务
        String fieldValue3 = extFieldsMap.get("enable_sms_notification").getFieldValue();
        if (StringUtils.isNotBlank(fieldValue3)){
            this.enableSmsNotification = Integer.parseInt(fieldValue3) > 0;
        }

    }

    //---------------------------------------------------------------

    /**
     * 如果App中的扩展字段fixed_client_os_type_for_pay为0表示没有预设客户端类型
     * 如果为1表示预设iOS， 2表示Android，这时候请求参数传的clientOsType会被覆盖
     */
    private int getRealClientOsType(HttpServletRequest request,App app){

        int clientOsType = Integer.parseInt(request.getParameter("client_os_type"));
        int fixedClientOsTypeForPay = Integer.parseInt(app.getExtFieldsMap().get("fixed_client_os_type_for_pay").getFieldValue());
        switch (fixedClientOsTypeForPay) {
            case 0:
                break;
            case 1:
                clientOsType = ClientOsType.IOS.getOsType();
                break;
            case 2:
                clientOsType = ClientOsType.Android.getOsType();
                break;
            default:
                break;
        }
        return clientOsType;
    }

    //---------------------------------------------------------------

    private boolean isServerAuthentication(HttpServletRequest request){
        Object isServerAuthentication = request.getAttribute("is_server_authentication");

        String fullLog = JsonUtil.toString(RequestUtil.getRequestInfoFullMapForLog(request));
        if (isServerAuthentication == null){
            log.info(
                            autoLog(
                                            "default logic 1 IS_SERVER_AUTHENTICATION has never been set,isServerAuthenticationIsFalse requestInfo:[{}]",
                                            fullLog));
            return false;
        }

        if (isServerAuthentication instanceof Boolean){
            log.info(
                            autoLog(
                                            "default logic 2  IS_SERVER_AUTHENTICATION has been set before isServerAuthenticationIsTrue,requestInfo:[{}]",
                                            fullLog));
            return (boolean) isServerAuthentication;
        }
        log.info(autoLog("isServerAuthentication {}  returnFalse,requestInfo:[{}]", JSON.toJSONString(isServerAuthentication), fullLog));
        return false;
    }

    //---------------------------------------------------------------

    private int clientOsTypeMapToDeviceTypeId(int clientOsType){
        switch (clientOsType) {
            case 1:
                return 200;
            case 2:
                return 100;
            default:
                throw new OpenAPIRuntimeException(400, "client_os_type should be 1 or 2");
        }
    }

    //---------------------------------------------------------------

    /**
     * 应用唯一标识
     */
    private String         appKey;

    /**
     * 设备唯一标识设备唯一标识
     */
    private String         deviceID;

    /**
     * 客户端操作系统类型
     */
    private int            clientOsType;

    /**
     * otp与access_token二选一
     */
    private String         accessToken;

    /**
     * 当前登录用户：uid必须大于等于0，uid为0时表示无用户概念（uid为0时不需要传递)
     */
    private long           uid;

    /**
     * 合作方第三方用户唯一标识
     */
    private String         thirdUid;

    /**
     * 合作方第三方账号类型 1-第三方账号，2-与喜马拉雅账号绑定的手机号
     */
    private int            thirdUidType;

    /**
     * token等级
     */
    private TokenLevelType tokenLevelType;

    /**
     * 合作方第三方token值
     */
    private String         thirdToken;

    /**
     * 喜马拉雅订单用户ID所属账号域（domain：1-喜马拉雅账号，2-第三方合作方账号）
     */
    private int            openapiInternaldomain;

    /**
     * 将uid或third_uid映射后得到的
     */
    private long           orderUid;

    /**
     * 用户手机号
     */
    private String         mobile;

    /**
     * 设备的用户代理（UserAgent）
     */
    private String         deviceAgent;

    /**
     * 设备的IP地址
     */
    private String         deviceIP;

    /**
     * 付费合作方推广用户ID
     * 目前api侧不会再设置该参数，如有用到则service层再依赖服务获取，后端获取该值一般会获取到默认值 0
     */
    private long           promotionUid;

    /**
     * 请求IP地址
     */
    private String         ip;

    /**
     * ip地址是否在白名单中
     */
    private boolean        realIpIsInWhiteList;

    /**
     * 服务对接类型
     */
    private int            compliantType;

    /**
     * ToB组客户端操作系统类型 android-100 ios-200
     */
    private int            deviceTypeId;

    /**
     * 注册短信提醒
     */
    private boolean        enableUserRegisterSmsNotification;

    /**
     * 是否开启短信通知(针对直充业务，直充会员、直充节目)
     */
    private boolean        enableSmsNotification;

    /**
     * 开发者ID
     */
    private long           developerId;

    /**
     * 是否需要校验风控参数是否存在
     */
    private boolean        needRiskControlParamsExist;

    /**
     * vip短信模板
     */
    private String         customVipSmsTemplate;

    /**
     * 分页查询参数
     */
    private Integer        page  = 1;

    private Integer        count = 20;

    private String         order;

    private boolean        desc;

    private String         thirdOrderNo;

    private String         accessAccountId;

    private int            accessAccountType;

    //---------------------------------------------------------------

    public Integer getPage(){
        return page;
    }

    public void setPage(Integer page){
        this.page = page;
    }

    public Integer getCount(){
        return count;
    }

    public void setCount(Integer count){
        this.count = count;
    }

    public String getOrder(){
        return order;
    }

    public void setOrder(String order){
        this.order = order;
    }

    public boolean isDesc(){
        return desc;
    }

    public void setDesc(boolean desc){
        this.desc = desc;
    }

    public void setPromotionUid(long promotionUid){
        this.promotionUid = promotionUid;
    }

    public void setThirdUid(String thirdUid){
        this.thirdUid = thirdUid;

    }

    public void setOpenapiInternaldomain(int i){
        this.openapiInternaldomain = i;

    }

    public void setTokenLevelType(TokenLevelType tokenLevelType){
        this.tokenLevelType = tokenLevelType;

    }

    public void setOrderUid(long orderUid){

        this.orderUid = orderUid;
    }

    public void setUid(long uid){
        this.uid = uid;

    }

    public void setCompliantType(int compliantType){
        this.compliantType = compliantType;
    }

    public void setRealIpIsInWhiteList(boolean realIpIsInWhiteList){
        this.realIpIsInWhiteList = realIpIsInWhiteList;

    }

    //---------------------------------------------------------------
    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString(){
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
