package com.ximalaya.openapi.log;

import com.feilong.core.bean.ConvertUtil;
import com.feilong.core.lang.ObjectUtil;
import com.feilong.core.lang.StringUtil;
import com.feilong.servlet.http.RequestUtil;
import com.feilong.spring.web.util.WebSpringUtil;
import com.ximalaya.openapi.log.model.RequestAppLogEntity;
import org.apache.commons.lang3.ArrayUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 〈〉
 *
 * @author gongjiawei
 * @date 2025/9/22 下午12:06
 */
public class RequestLogUtil {
    public RequestLogUtil() {
    }

    public static boolean isTestRequest() {
        HttpServletRequest request = WebSpringUtil.getRequest();
        if (null == request) {
            return false;
        } else if (ArrayUtils.contains(ConvertUtil.toArray(new String[]{"6b4374b72c434a2154c04040899c2da4", "0bc2e17c6cba7083f3ef4ac6e824eb82"}), getAppKey(request))) {
            return true;
        } else {
            return StringUtil.trimAndEqualsIgnoreCase("forTestDevice", RequestUtil.getParameter(request, "device_id")) ? true : StringUtil.trimAndEqualsIgnoreCase("openapiLogTest", RequestUtil.getParameter(request, "openapiLogTest"));
        }
    }

    /** @deprecated */
    @Deprecated
    public static String createMsgAutoRequest(String messagePattern, Object... params) {
        HttpServletRequest request = WebSpringUtil.getRequest();
        String message = StringUtil.formatPattern(messagePattern, params);
        return StringUtil.formatPattern("{} api:[{}],queryString:[{}],{}", new Object[]{getSimpleLogPrefix(request), null == request ? "" : RequestUtil.getRequestURL(request), null == request ? "" : RequestUtil.parseParamsToQueryString(request), message});
    }

    /** @deprecated */
    @Deprecated
    public static String createMsgAutoRequest(RequestAppLogEntity requestAppLogEntity, String messagePattern, Object... params) {
        if (null == requestAppLogEntity) {
            return createMsgAutoRequest(messagePattern, params);
        } else {
            String message = StringUtil.formatPattern(messagePattern, params);
            return StringUtil.formatPattern("appId:[{}],appkey:[{}],api:[{}],queryString:[{}],{}", new Object[]{requestAppLogEntity.getAppId(), requestAppLogEntity.getAppKey(), requestAppLogEntity.getApi(), requestAppLogEntity.getQueryString(), message});
        }
    }

    /** @deprecated */
    @Deprecated
    public static String createMsg(AtomicInteger i, String messagePattern, Object... params) {
        return createMsgAutoRequest("Step:" + i.getAndIncrement() + "," + messagePattern, params);
    }

    private static String getSimpleLogPrefix(HttpServletRequest request) {
        AtomicInteger counter = LogCounterThreadLocal.getCounter();
        return null != counter && counter.get() >= 0 ? "Step:[" + counter.getAndIncrement() + "]," + "" : "";
    }

    public static String getAppKey(RequestAppLogEntity requestAppLogEntity) {
        if (null != requestAppLogEntity) {
            return requestAppLogEntity.getAppKey();
        } else {
            HttpServletRequest request = WebSpringUtil.getRequest();
            return getAppKey(request);
        }
    }

    public static String getAppKey(HttpServletRequest request) {
        try {
            String appKey = RequestUtil.getParameter(request, "app_key");
            return (String) ObjectUtil.defaultIfNullOrEmpty(appKey, "CanNotGetAppKey");
        } catch (Throwable var2) {
            return null;
        }
    }
}
