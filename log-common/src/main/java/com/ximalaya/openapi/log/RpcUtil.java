package com.ximalaya.openapi.log;

import com.feilong.core.Validator;
import com.feilong.core.lang.StringUtil;
import com.ximalaya.mainstay.common.RpcContext;

import java.net.InetSocketAddress;
import java.util.Map;

/**
 * 〈〉
 *
 * @author gongjiawei
 * @date 2025/9/22 下午12:07
 */
public class RpcUtil {
    public RpcUtil() {
    }

    /** @deprecated */
    @Deprecated
    public static String createRpcMsg(String messagePattern, Object... params) {
        String resolverMainMessage = StringUtil.formatPattern(messagePattern, params);
        return StringUtil.formatPattern("{}{}{}{}", new Object[]{AutoLog.getCounterLogString(), AutoLog.getLogkeyString(), getRpcCallerString(), resolverMainMessage});
    }

    public static String getRpcCallerString() {
        RpcContext rpcContext = RpcContext.getContext();
        if (null == rpcContext) {
            return "";
        } else {
            String callApplicaion = getCallApplicaion(rpcContext);
            String remoteAddress = getRemoteAddress(rpcContext);
            return Validator.isAllNullOrEmpty(new Object[]{callApplicaion, remoteAddress}) ? "" : "CallerApp:[" + callApplicaion + " : " + remoteAddress + "],";
        }
    }

    private static String getCallApplicaion(RpcContext rpcContext) {
        if (null == rpcContext) {
            return "";
        } else {
            Map<String, String> attachment = rpcContext.getAttachment();
            return Validator.isNullOrEmpty(attachment) ? "" : (String)attachment.get("M3-APP");
        }
    }

    private static String getRemoteAddress(RpcContext rpcContext) {
        if (null == rpcContext) {
            return null;
        } else {
            InetSocketAddress remoteAddress = rpcContext.getRemoteAddress();
            return null == remoteAddress ? "" : "" + remoteAddress;
        }
    }
}
