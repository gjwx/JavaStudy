package com.ximalaya.openapi.log;

import org.springframework.core.NamedThreadLocal;

/**
 * 〈〉
 *
 * @author gongjiawei
 * @date 2025/9/22 下午12:05
 */
public class LogKeyThreadLocal {
    private static final ThreadLocal<String> LOGKEY_THREAD_LOCAL = new NamedThreadLocal<String>("logKeyThreadLocal") {
        protected String initialValue() {
            return null;
        }
    };

    public LogKeyThreadLocal() {
    }

    public static void setLogkey(String counter) {
        LOGKEY_THREAD_LOCAL.set(counter);
    }

    public static String getLogkey() {
        return (String)LOGKEY_THREAD_LOCAL.get();
    }

    public static void removeLogkey() {
        LOGKEY_THREAD_LOCAL.remove();
    }

}
