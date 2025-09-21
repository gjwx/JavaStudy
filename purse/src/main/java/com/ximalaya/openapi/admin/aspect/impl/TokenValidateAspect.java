package com.ximalaya.openapi.admin.aspect.impl;

import com.ximalaya.oauth2.service.auth.inf.thrift.generate.CheckResultInfo;
import com.ximalaya.omp.facade.distribution.api.enums.AccessAccountTypeEnum;
import com.ximalaya.omp.openapi.payment.aspect.annotation.TokenValidate;
import com.ximalaya.omp.openapi.payment.client.AccountClient;
import com.ximalaya.omp.openapi.payment.client.AuthClient;
import com.ximalaya.omp.openapi.payment.client.OpenApiVuserClient;
import com.ximalaya.omp.openapi.payment.config.FootballDistributeRetryConfig;
import com.ximalaya.omp.openapi.payment.entity.*;
import com.ximalaya.omp.openapi.payment.ex.OpenAPIRuntimeException;
import com.ximalaya.openapi.common.constant.TokenLevelType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.ximalaya.openapi.log.AutoLog.autoLog;
import static com.ximalaya.openapi.web.common.constant.APIError.ACCESS_TOKEN_INVALID_OR_EXPIRED;

/**
 * 
 * @deprecated 要使用v2
 */
@Deprecated
@Slf4j
@Component
@Aspect
public class TokenValidateAspect{

    @Autowired
    private OpenApiVuserClient            openApiVuserClient;

    @Autowired
    private AuthClient                    authClient;

    @Autowired
    private AccountClient                 accountClient;

    @Autowired
    private FootballDistributeRetryConfig footballDistributeRetryConfig;

    //---------------------------------------------------------------

    //    @Around("@annotation(com.ximalaya.omp.openapi.payment.aspect.annotation.TokenValidate) && args(tokenValidate)")
    @Around("@annotation(tokenValidate)")
    public Object checkToken(ProceedingJoinPoint pjp,TokenValidate tokenValidate) throws Throwable{
        PayCommonQueryParam payCommonQueryParam = (PayCommonQueryParam) pjp.getArgs()[0];
        String thirdUidParam = payCommonQueryParam.getThirdUid();
        String accessTokenParam = payCommonQueryParam.getAccessToken();
        String deviceIdParam = payCommonQueryParam.getDeviceID();
        String thirdTokenParam = payCommonQueryParam.getThirdToken();
        int thirdUidType = payCommonQueryParam.getThirdUidType();
        String mobile = payCommonQueryParam.getMobile();
        String methodName = pjp.getSignature().getName();
        String className = pjp.getSignature().getDeclaringType().getSimpleName();
        if (className.equals("DistributionController") && methodName.equals("distribute")
                        && footballDistributeRetryConfig.getForwardToV2DistributeAppSet().contains(payCommonQueryParam.getAppKey())){
            return pjp.proceed();
        }

        /*
         * 对于客户端对接,需要校验thirdUid thirdToken以及deviceId参数
         */
        if (CompliantType.CLIENT_ONLY.getValue() == payCommonQueryParam.getCompliantType()){
            if (!isDeviceIdParamNotNull(payCommonQueryParam.getDeviceID())){
                log.warn(autoLog("device_id is empty or null"));
                throw new OpenAPIRuntimeException(400, "device_id is empty or null");
            }
            if (StringUtils.isNotBlank(thirdUidParam) && !isThirdTokenNotNullIfThirdUidExist(thirdTokenParam)){
                log.warn(autoLog("third_uid and third_token did not existed in pairs"));
                throw new OpenAPIRuntimeException(400, "third_uid and third_token did not existed in pairs");
            }
        }

        //---------------------------------------------------------------
        // Refine error log
        validateByThirdUidType(thirdUidParam, mobile, thirdUidType);

        /*
         * 对于服务端和客户端对接都需要校验的部分,包括thirdUid accessToken等参数
         */
        if (isAccessTokenAndThirdUidBothNotNull(accessTokenParam, thirdUidParam)){
            log.warn(autoLog("access_token and third_uid can not both be passed"));
            throw new OpenAPIRuntimeException(400, "access_token and third_uid can not both be passed"); // access_token and third_uid can not both be
        }else if (isAccessTokenOrThirdUidBothNull(accessTokenParam, thirdUidParam, mobile, payCommonQueryParam.getUid())){
            log.warn(autoLog("mobile and third_uid uid can not both be null"));
            throw new OpenAPIRuntimeException(400, "mobile and third_uid uid can not both be null"); // access_token and third_uid can not both be null
        }else if (StringUtils.isNotBlank(accessTokenParam) && !deviceIdExistAlongWithAccessToken(deviceIdParam)){
            log.warn(autoLog("device_id can not be null if access_token existed"));
            throw new OpenAPIRuntimeException(400, "device_id can not be null if access_token existed");
        }

        //---------------------------------------------------------------
        /*
         * 校验accessToken
         */
        String appKey = payCommonQueryParam.getAppKey();
        String accessToken = payCommonQueryParam.getAccessToken();
        String thirdUid = payCommonQueryParam.getThirdUid();
        long uid = payCommonQueryParam.getUid();
        if (uid > 0){
            payCommonQueryParam.setOrderUid(uid);
            // 校验 uid 是否存在
            if (!accountClient.getBasicUserInfo(uid)){
                throw new OpenAPIRuntimeException(400, "uid:" + uid + "不存在(未注册)");
            }
            return pjp.proceed();
        }

        //---------------------------------------------------------------
        // todo accessToken也是空的在客户端接入场景下需要用到
        CheckResultInfo checkResultInfo = null;
        if (accessToken != null){
            log.debug(autoLog("authenticate, access_token: {}", accessToken));
            String deviceId = payCommonQueryParam.getDeviceID();
            checkResultInfo = authClient.checkTokenInfoWithUid(accessToken, appKey, deviceId, uid);
            if (checkResultInfo == null || !checkResultInfo.isCheckResult()){
                throw new OpenAPIRuntimeException(ACCESS_TOKEN_INVALID_OR_EXPIRED);
            }
            if (StringUtils.isNotBlank(checkResultInfo.getThirdUid())){
                //third_uid换access_token的方式: 记录third_uid
                payCommonQueryParam.setThirdUid(checkResultInfo.getThirdUid());
                thirdUid = checkResultInfo.getThirdUid();
                // 如果是tokenExchage模式（即third_uid换access_token），在AppStateCheckFilter根据入参判定是不准确的，
                // 因为可能传的是uid+access_token，但是access_token中是third_uid，所以这里在解析accessToken的时候如果发现是third_uid，
                // 需要对openapi_internal_domain进行重新赋值
                payCommonQueryParam.setOpenapiInternaldomain(
                                StringUtils.isNotEmpty(thirdUid) ? DomainType.THIRD_PARTY.getValue() : DomainType.XIMALAYA.getValue());
            }
            if (checkResultInfo.getTokenLevel() != null){
                TokenLevelType tokenLevelType = TokenLevelType
                                .getTokenLevelType(Integer.parseInt(String.valueOf(checkResultInfo.getTokenLevel().getValue())));
                payCommonQueryParam.setTokenLevelType(tokenLevelType);
            }
            log.debug(autoLog("authenticate access_token pass! uid=" + checkResultInfo.getUid()));
        }

        //---------------------------------------------------------------

        // 如果third_uid_type=1即third_uid传的是第三方账号时需要经过Vuser服务映射;
        // 如果TokenValidate注解上的值为ENABLE_GO_THROUGHT则也需要经过Vuser服务映射
        if (ThirdUidType.THIRD_PARTY_ACCOUNT.getValue() == thirdUidType
                        || GoThroughVuserServiceType.ENABLE_GO_THROUGHT.equals(tokenValidate.goThroughType())){
            long orderUid = openApiVuserClient
                            .getVuidV2(appKey, getDomain(mobile, thirdUid, thirdUidType), getUserId(thirdUid, mobile, checkResultInfo));
            // 表示使用未登录的accessToken
            if (checkResultInfo != null && checkResultInfo.getUid() == 0 && StringUtils.isEmpty(checkResultInfo.getThirdUid())){
                orderUid = 0;
            }
            payCommonQueryParam.setOrderUid(orderUid);
        }
        payCommonQueryParam.setUid(checkResultInfo != null ? checkResultInfo.getUid() : uid);
        return pjp.proceed();
    }

    //---------------------------------------------------------------

    private int getDomain(String mobile,String thirdUid,int thirdUidType){
        int domain;
        if (StringUtils.isNotBlank(thirdUid)){
            domain = DomainType.THIRD_PARTY.getValue();
            // 使用手机号的情况，比如西安城市合伙人业务
            if (thirdUidType == 2){
                domain = DomainType.XIMALAYA.getValue();
            }
        }else if (StringUtils.isNotBlank(mobile)){
            domain = DomainType.XIMALAYA.getValue();
        }else{
            domain = DomainType.XIMALAYA.getValue();
        }
        return domain;
    }

    //---------------------------------------------------------------

    private String getUserId(String thirdUid,String mobile,CheckResultInfo checkResultInfo){
        String userId;
        if (StringUtils.isNotBlank(thirdUid)){
            userId = thirdUid;
        }else if (StringUtils.isNotBlank(mobile)){
            userId = mobile;
        }else{
            if (checkResultInfo != null){
                userId = String.valueOf(checkResultInfo.getUid());
            }else{
                log.warn(autoLog("access_token,mobile ,third_uid , uid 都为空"));
                throw new OpenAPIRuntimeException(400, "access_token,mobile ,third_uid , uid 都为空");
            }
        }
        return userId;
    }

    //---------------------------------------------------------------
    /**
     * deviceId是否为空
     */
    private boolean isDeviceIdParamNotNull(String deviceID){
        return StringUtils.isNotBlank(deviceID);
    }

    /**
     * thirdUid和thirdToken必须成对出现
     */
    private boolean isThirdTokenNotNullIfThirdUidExist(String thirdTokenParam){
        return StringUtils.isNotBlank(thirdTokenParam);
    }

    /**
     * accessToken和thirdUid是否同时为空
     */
    private boolean isAccessTokenOrThirdUidBothNull(String accessTokenParam,String thirdUid,String mobile,long uid){
        return StringUtils.isBlank(accessTokenParam) && StringUtils.isBlank(thirdUid) && StringUtils.isBlank(mobile) && uid == 0; // 对于直充接口不会传accessToken和thirdUid，而是mobile，相当于thirdUid
    }

    //---------------------------------------------------------------

    /**
     * validate thirdUid and mobile by thirdUidType <br>
     * - alert enhancement: According thirdUidType do refine error alert.
     */
    private void validateByThirdUidType(String thirdUid,String mobile,int thirdUidType){
        switch (AccessAccountTypeEnum.getTypeEnum(thirdUidType)) {
            case THIRD_UID:
                if (StringUtils.isEmpty(thirdUid)){
                    log.warn(autoLog("third_uid can not be null"));
                    throw new OpenAPIRuntimeException(400, "third_uid can not both be null");
                }
            case MOBILE:
                if (StringUtils.isEmpty(mobile)){
                    log.warn(autoLog("mobile can not be null"));
                    throw new OpenAPIRuntimeException(400, "mobile can not both be null");
                }
        }

    }

    /**
     * accessTokenParam和thirdUid是否同时不为空
     */
    private boolean isAccessTokenAndThirdUidBothNotNull(String accessTokenParam,String thirdUidParam){
        return StringUtils.isNotBlank(accessTokenParam) && StringUtils.isNotBlank(thirdUidParam);
    }

    /**
     * device_id是否和access_token同时存在
     */
    private boolean deviceIdExistAlongWithAccessToken(String deviceIdParam){
        return StringUtils.isNotBlank(deviceIdParam);
    }
}
