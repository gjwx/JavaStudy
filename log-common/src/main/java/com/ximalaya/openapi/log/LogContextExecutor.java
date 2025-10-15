package com.ximalaya.openapi.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Supplier;

/**
 * 〈〉
 *
 * @author gongjiawei
 * @date 2025/9/22 上午11:35
 */
public class LogContextExecutor {
    private static final Logger log = LoggerFactory.getLogger(LogContextExecutor.class);

    public LogContextExecutor() {
    }

    public static <T> T call(String logKey, Supplier<T> supplier) throws Throwable {
        return call(logKey, supplier, (ExceptionCallback)null);
    }

    public static <T> T call(String logKey, Supplier<T> supplier, ExceptionCallback exceptionCallback) throws Throwable {
        Object var9;
        try {
            LogCounterThreadLocal.beginCounter();
            LogKeyThreadLocal.setLogkey(logKey);
            var9 = supplier.get();
        } catch (Throwable var7) {
            Throwable e = var7;
            log.warn(AutoLog.autoLog("", new Object[0]), e);
            if (null != exceptionCallback) {
                exceptionCallback.callback(e);
            }

            throw e;
        } finally {
            LogCounterThreadLocal.removeCounter();
            LogKeyThreadLocal.removeLogkey();
        }

        return (T) var9;
    }
}
