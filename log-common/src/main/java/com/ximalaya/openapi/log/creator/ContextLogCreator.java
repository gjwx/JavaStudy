package com.ximalaya.openapi.log.creator;

import com.feilong.core.Validator;
import com.feilong.core.lang.ClassUtil;
import com.feilong.core.lang.StringUtil;
import com.feilong.servlet.http.RequestUtil;
import com.feilong.spring.web.util.WebSpringUtil;

import com.ximalaya.openapi.log.RpcUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * 〈〉
 *
 * @author gongjiawei
 * @date 2025/9/22 上午11:24
 */

public class ContextLogCreator {
    private static final Logger log = LoggerFactory.getLogger(ContextLogCreator.class);
    private static Class<?> MAINSTAY_RPC_CONTEXT_CLASS = getClass("com.ximalaya.mainstay.common.RpcContext");
    private static Class<?> HTTP_SERVLET_REQUEST_CLASS = getClass("javax.servlet.http.HttpServletRequest");

    public ContextLogCreator() {
    }

    public static String getContextLog() {
        try {
            if (null != MAINSTAY_RPC_CONTEXT_CLASS) {
                String rpcCallerString = RpcUtil.getRpcCallerString();
                if (Validator.isNotNullOrEmpty(rpcCallerString)) {
                    return rpcCallerString;
                }
            }

            if (null != HTTP_SERVLET_REQUEST_CLASS) {
                HttpServletRequest request = WebSpringUtil.getRequest();
                if (null != request) {
                    return getRequestInfo(request);
                }
            }

            return "";
        } catch (Throwable var1) {
            Throwable e = var1;
            return "exception:" + e.getMessage();
        }
    }

    private static String getRequestInfo(HttpServletRequest request) {
        return StringUtil.formatPattern("api:[{}],paramString:[{}]", new Object[]{null == request ? "" : RequestUtil.getRequestURL(request), RequestUtil.parseParamsToQueryString(request)});
    }

    private static Class<?> getClass(String className) {
        try {
            Class<?> mainstayRpcContextClass = ClassUtil.getClass(className);
            log.info("findAndLoad:[{}]", className);
            return mainstayRpcContextClass;
        } catch (Exception var2) {
            Exception e = var2;
            log.warn("can't load:[{}]", className, e);
            return null;
        }
    }
}
