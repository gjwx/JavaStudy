package com.ximalaya.openapi.log;

import com.ximalaya.openapi.log.model.MqInfo;
import org.springframework.core.NamedThreadLocal;

/**
 * 〈〉
 *
 * @author gongjiawei
 * @date 2025/9/22 下午12:06
 */
public class MqInfoThreadLocal {
    private static final ThreadLocal<MqInfo> MQINFO_THREAD_LOCAL = new NamedThreadLocal<MqInfo>("MqInfoThreadLocal") {
        protected MqInfo initialValue() {
            return null;
        }
    };

    public MqInfoThreadLocal() {
    }

    public static void setMqInfo(MqInfo mqInfo) {
        MQINFO_THREAD_LOCAL.set(mqInfo);
    }

    public static MqInfo getMqInfo() {
        return (MqInfo)MQINFO_THREAD_LOCAL.get();
    }

    public static void removeMqInfo() {
        MQINFO_THREAD_LOCAL.remove();
    }

}
