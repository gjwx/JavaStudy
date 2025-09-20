package com.ximalaya.openapi.admin.aspect.impl;

import com.ximalaya.commons.enumall.DistributeItemType;
import com.ximalaya.omp.facade.distribution.api.enums.AccessAccountTypeEnum;
import com.ximalaya.omp.openapi.payment.aspect.iface.ParamsCheck;
import com.ximalaya.omp.openapi.payment.config.FootballDistributeRetryConfig;
import com.ximalaya.omp.openapi.payment.constant.XiBeanProductEnum;
import com.ximalaya.omp.openapi.payment.entity.PayCommonQueryParam;
import com.ximalaya.openapi.admin.ex.APIError;
import com.ximalaya.openapi.admin.ex.OpenAPIRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static com.ximalaya.openapi.log.AutoLog.autoLog;

/**

 */
@Component
@Aspect
@Slf4j
public class PlaceOrderParamsCheckAspect implements ParamsCheck{

    @Autowired
    private FootballDistributeRetryConfig footballDistributeRetryConfig;

    /**
     * 分销业务参数校验
     */
    @Override
    @Around("@annotation(com.ximalaya.omp.openapi.payment.aspect.annotation.ParamsCheck.PlaceOrderParamsCheck)")
    public Object paramValidate(ProceedingJoinPoint pjp) throws Throwable{
        Object[] args = pjp.getArgs();
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        String[] parameterNames = methodSignature.getParameterNames();
        // 获取参数下标
        int payCommonQueryParamIndex = ArrayUtils.indexOf(parameterNames, "payCommonQueryParam");
        int distributeItemTypeIndex = ArrayUtils.indexOf(parameterNames, "itemType");
        int payContentIndex = ArrayUtils.indexOf(parameterNames, "payContent");
        int accessAccountTypeIndex = ArrayUtils.indexOf(parameterNames, "accessAccountType");
        int payPriceIndex = ArrayUtils.indexOf(parameterNames, "payPrice");

        //---------------------------------------------------------------

        String payContent = (String) args[payContentIndex];
        String payPrice = (String) args[payPriceIndex];
        Integer distributeItemType = (Integer) args[distributeItemTypeIndex];
        Integer accessAccountType = (Integer) args[accessAccountTypeIndex];
        PayCommonQueryParam payCommonQueryParam = (PayCommonQueryParam) args[payCommonQueryParamIndex];
        String methodName = pjp.getSignature().getName();
        String className = pjp.getSignature().getDeclaringType().getSimpleName();
        if (className.equals("DistributionController") && methodName.equals("distribute")
                        && footballDistributeRetryConfig.getForwardToV2DistributeAppSet().contains(payCommonQueryParam.getAppKey())){
            return pjp.proceed();
        }

        riskControlParamsCheck(
                        payCommonQueryParam.isNeedRiskControlParamsExist(),
                        payCommonQueryParam.getDeviceAgent(),
                        payCommonQueryParam.getDeviceIP());

        distributionParamsCheck(distributeItemType, accessAccountType, payCommonQueryParam.getClientOsType(), payContent, payPrice);

        return pjp.proceed();
    }

    /**
     * 根据开关 needRiskControlParamsExist 校验是否需要风控参数必须出现
     */
    private void riskControlParamsCheck(boolean needRiskControlParamsExist,String deviceAgent,String deviceIp){
        if (needRiskControlParamsExist){
            if (StringUtils.isBlank(deviceAgent) || StringUtils.isBlank(deviceIp)){
                log.warn(autoLog("开启了风控开关， deviceAgent or deviceIp should not be blank"));
                throw new OpenAPIRuntimeException(400, "deviceAgent or deviceIp should not be blank");
            }
        }
    }

    /**
     * 校验商品信息、客户端类型信息等
     */
    private void distributionParamsCheck(int distributeItemType,int accessAccountType,int clientOsType,String payContent,String payPrice){

        if (DistributeItemType.XI_BEAN.getValue() == distributeItemType || DistributeItemType.XI_COIN.getValue() == distributeItemType
                        || DistributeItemType.MEMBER.getValue() == distributeItemType){
            if (AccessAccountTypeEnum.THIRD_UID.getType().equals(accessAccountType)){
                throw new OpenAPIRuntimeException(APIError.NOT_SUPPORT_OF_THE_ACCESS_ACCOUNT_TYPE);
            }
        }
        if (DistributeItemType.XI_BEAN.getValue() == distributeItemType || DistributeItemType.XI_COIN.getValue() == distributeItemType){
            if (StringUtils.isEmpty(payPrice)){
                throw new OpenAPIRuntimeException(APIError.PAY_PRICE_SHOULD_NOT_BE_NULL);
            }
        }
        if (DistributeItemType.XI_BEAN.getValue() == distributeItemType){
            XiBeanProductEnum xiBeanProductEnum = XiBeanProductEnum.valueOf(Long.valueOf(payContent));
            if (Objects.isNull(xiBeanProductEnum)){
                throw new OpenAPIRuntimeException(APIError.ENABLED_DISTRIBUTION_PRODUCT);
            }else if (clientOsType != xiBeanProductEnum.getClientOsType().getOsType()){
                throw new OpenAPIRuntimeException(APIError.NOT_SUPPORT_OF_THE_CLIENT_TYPE);
            }
        }
    }
}
