package com.ximalaya.ssd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * SSD服务启动类
 * 专门处理业务逻辑的独立服务
 */
@SpringBootApplication
public class SSDServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SSDServiceApplication.class, args);
    }
} 