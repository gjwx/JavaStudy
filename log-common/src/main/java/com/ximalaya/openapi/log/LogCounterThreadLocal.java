package com.ximalaya.openapi.log;

import org.springframework.core.NamedThreadLocal;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 〈〉
 *
 * @author gongjiawei
 * @date 2025/9/22 下午12:04
 */
public class LogCounterThreadLocal {
    private static final ThreadLocal<AtomicInteger> COUNTER_THREAD_LOCAL = new NamedThreadLocal<AtomicInteger>("LogCounterThreadLocal") {
        protected AtomicInteger initialValue() {
            return null;
        }
    };

    public LogCounterThreadLocal() {
    }

    public static void beginCounter() {
        setCounter(new AtomicInteger(0));
    }

    public static void setCounter(AtomicInteger counter) {
        COUNTER_THREAD_LOCAL.set(counter);
    }

    public static AtomicInteger getCounter() {
        return (AtomicInteger)COUNTER_THREAD_LOCAL.get();
    }

    public static void removeCounter() {
        COUNTER_THREAD_LOCAL.remove();
    }

}
