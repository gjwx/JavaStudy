package com.ximalaya.openapi.log;

import com.feilong.core.Validator;
import com.feilong.core.lang.StringUtil;
import com.ximalaya.openapi.log.creator.ContextLogCreator;
import com.ximalaya.openapi.log.model.RequestAppLogEntity;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 〈〉
 *
 * @author gongjiawei
 * @date 2025/9/22 上午11:33
 */
public class AutoLog {
    public AutoLog() {
    }

    public static String autoLog(String messagePattern, Object... params) {
        String mainMessage = StringUtil.formatPattern(messagePattern, params);
        return StringUtil.formatPattern("{}{}{}{}", new Object[]{getCounterLogString(), getLogkeyString(), ContextLogCreator.getContextLog(), mainMessage});
    }

    public static String autoLog(RequestAppLogEntity requestAppLogEntity, String messagePattern, Object... params) {
        if (null == requestAppLogEntity) {
            return autoLog(messagePattern, params);
        } else {
            String message = StringUtil.formatPattern(messagePattern, params);
            return StringUtil.formatPattern("appId:[{}],appkey:[{}],api:[{}],queryString:[{}],{}", new Object[]{requestAppLogEntity.getAppId(), requestAppLogEntity.getAppKey(), requestAppLogEntity.getApi(), requestAppLogEntity.getQueryString(), message});
        }
    }

    static String getCounterLogString() {
        AtomicInteger counter = LogCounterThreadLocal.getCounter();
        return null != counter && counter.get() >= 0 ? "Step:[" + counter.getAndIncrement() + "]," : "";
    }

    static String getLogkeyString() {
        String logkey = LogKeyThreadLocal.getLogkey();
        return Validator.isNullOrEmpty(logkey) ? "" : "logkey:[" + logkey + "],";
    }
}
