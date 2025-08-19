package com.ximalaya.ssd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 〈启动类〉
 *
 * @author gongjiawei
 * @date 2025/7/10 下午2:00
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration .class})
public class SSDApplication {
    public static void main(String[] args) {
        SpringApplication.run(SSDApplication.class,args);
    }
}
