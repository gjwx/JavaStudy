package com.ximalaya.openapi.admin.aspect.impl.handler;

import com.ximalaya.omp.openapi.payment.entity.PayCommonQueryParam;
import com.ximalaya.omp.openapi.payment.rocketmq.message.DistributeLogMessage;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Service
public class DistributeLogMessageCreator{

    public static DistributeLogMessage createDistributeLogMessage(HttpServletRequest request,PayCommonQueryParam payCommonQueryParam){

        Map<String, String> payCommonQueryParamsMap = new HashMap<>();

        // app_key : value
        payCommonQueryParamsMap.put("app_key", request.getParameter("app_key"));
        // device_id: value
        payCommonQueryParamsMap.put("device_id", request.getParameter("device_id"));
        // client_os_type: value
        payCommonQueryParamsMap.put("client_os_type", request.getParameter("client_os_type"));
        // nonce: value
        payCommonQueryParamsMap.put("nonce", request.getParameter("nonce"));
        // timestamp: value
        payCommonQueryParamsMap.put("timestamp", request.getParameter("timestamp"));
        // server_api_version: value
        payCommonQueryParamsMap.put("server_api_version", request.getParameter("server_api_version"));
        // device_id_type: value
        payCommonQueryParamsMap.put("device_id_type", request.getParameter("device_id_type"));
        // sig: value
        payCommonQueryParamsMap.put("sig", request.getParameter("sig"));
        // third_token: value
        payCommonQueryParamsMap.put("third_token", request.getParameter("third_token"));
        // third_uid_type: value
        payCommonQueryParamsMap.put("third_uid_type", request.getParameter("third_uid_type"));
        // parsed from request
        payCommonQueryParamsMap.put("ip", payCommonQueryParam.getIp());

        //---------------------------------------------------------------
        Map<String, String> distributeRequestParamsMap = new HashMap<>();
        // third_uid : value
        distributeRequestParamsMap.put("third_uid", request.getParameter("third_uid"));
        // distribute_item_type: value
        distributeRequestParamsMap.put("distribute_item_type", request.getParameter("distribute_item_type"));
        // pay_content: value
        distributeRequestParamsMap.put("pay_content", request.getParameter("pay_content"));
        // access_account_type: value
        distributeRequestParamsMap.put("access_account_type", request.getParameter("access_account_type"));
        // access_token: value
        distributeRequestParamsMap.put("access_token", request.getParameter("access_token"));
        // mobile: value
        distributeRequestParamsMap.put("mobile", request.getParameter("mobile"));
        // uid: value
        distributeRequestParamsMap.put("uid", request.getParameter("uid"));
        // pay_channel: value
        distributeRequestParamsMap.put("pay_channel", request.getParameter("pay_channel"));
        // pay_price: value
        distributeRequestParamsMap.put("pay_price", request.getParameter("pay_price"));
        // third_order_no: value
        distributeRequestParamsMap.put("third_order_no", request.getParameter("third_order_no"));
        // expect_price: value
        distributeRequestParamsMap.put("expect_price", request.getParameter("expect_price"));
        // device_ip: value
        distributeRequestParamsMap.put("device_ip", request.getParameter("device_ip"));
        // device_user_agent: value
        distributeRequestParamsMap.put("device_user_agent", request.getParameter("device_user_agent"));
        // coupon_id: value
        distributeRequestParamsMap.put("coupon_id", request.getParameter("coupon_id"));
        // trans_time: value
        distributeRequestParamsMap.put("trans_time", request.getParameter("trans_time"));
        // advance_trans: value
        distributeRequestParamsMap.put("advance_trans", request.getParameter("advance_trans"));
        // item_quantity: value
        distributeRequestParamsMap.put("item_quantity", request.getParameter("item_quantity"));

        //---------------------------------------------------------------
        Map<String, String> distributeRequestHeadersMap = new HashMap<>();

        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()){
            String key = (String) headerNames.nextElement();
            Enumeration value = request.getHeaders(key);
            while (value.hasMoreElements()){
                distributeRequestHeadersMap.put(key, (String) value.nextElement());
            }
        }

        //---------------------------------------------------------------
        DistributeLogMessage distributeLogMessage = new DistributeLogMessage();
        distributeLogMessage.setAppKey(payCommonQueryParam.getAppKey());
        distributeLogMessage.setRetryStatus(0);
        distributeLogMessage.setRetryTimes(0);
        distributeLogMessage.setThirdOrderNo(payCommonQueryParam.getThirdOrderNo());

        distributeLogMessage.setPayCommonQueryParams(payCommonQueryParamsMap);
        distributeLogMessage.setDistributeRequestParams(distributeRequestParamsMap);
        distributeLogMessage.setDistributeRequestHeaders(distributeRequestHeadersMap);
        return distributeLogMessage;
    }

}
