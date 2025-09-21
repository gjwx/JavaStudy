package com.ximalaya.openapi.admin.entity;

import com.alibaba.fastjson.JSON;

import com.ximalaya.openapi.admin.ex.OpenAPIRuntimeException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import static com.ximalaya.openapi.log.AutoLog.autoLog;

/**
 */
@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RiskControlModel{

    /**
     * 用户设备IP
     */
    private String deviceIp;

    /**
     * 用户客户端代理标识
     */
    private String deviceAgent;

    /**
     * 客户端设备ID
     */
    private String deviceId;

    //---------------------------------------------------------------

    public RiskControlModel(PayCommonQueryParam payCommonQueryParam){
        this.deviceIp = payCommonQueryParam.getDeviceIP();
        this.deviceAgent = payCommonQueryParam.getDeviceAgent();
        this.deviceId = payCommonQueryParam.getDeviceID();

        if (StringUtils.isBlank(this.deviceIp) || StringUtils.isBlank(this.deviceAgent) || StringUtils.isBlank(this.deviceId)){

            log.warn(autoLog("payCommonQueryParamData: {}", JSON.toJSONString(payCommonQueryParam)));
            throw new OpenAPIRuntimeException(400, "当三方采用手机号方式接入需要传入 device_ip,device_user_agent,device_id");
        }

    }
}
