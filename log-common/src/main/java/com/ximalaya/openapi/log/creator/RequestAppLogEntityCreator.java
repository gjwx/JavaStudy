package com.ximalaya.openapi.log.creator;

import com.feilong.servlet.http.RequestUtil;
import com.feilong.spring.web.util.WebSpringUtil;
import com.ximalaya.openapi.log.RequestLogUtil;
import com.ximalaya.openapi.log.model.RequestAppLogEntity;

import javax.servlet.http.HttpServletRequest;

/**
 * 〈〉
 *
 * @author gongjiawei
 * @date 2025/9/22 上午11:28
 */
public class RequestAppLogEntityCreator {
    public RequestAppLogEntityCreator() {
    }

    public static RequestAppLogEntity create() {
        HttpServletRequest request = WebSpringUtil.getRequest();
        RequestAppLogEntity requestAppLogEntity = new RequestAppLogEntity();
        requestAppLogEntity.setApi(null == request ? "" : RequestUtil.getRequestURL(request));
        requestAppLogEntity.setAppKey(RequestLogUtil.getAppKey(request));
        requestAppLogEntity.setQueryString(null == request ? "" : RequestUtil.parseParamsToQueryString(request));
        return requestAppLogEntity;
    }
}
