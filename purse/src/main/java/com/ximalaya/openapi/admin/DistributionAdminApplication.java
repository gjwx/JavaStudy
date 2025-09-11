package com.ximalaya.openapi.admin;

//import com.ximalaya.auth.starter.config.spring.context.annotation.EnableAuth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Set;

@Slf4j
//@EnableAuth
//@ImportResource(locations = {
//        "classpath:mainstay3/*.xml"})
@EnableWebMvc
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class DistributionAdminApplication {

//    public static void main(String[] args) {
//        final ConfigurableApplicationContext run = SpringApplication.run(DistributionAdminApplication.class, args);
//        log.info("容器启动成功！");
//    }

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(DistributionAdminApplication.class);

        // 获取默认的监听器列表
        Set<ApplicationListener<?>> listeners = application.getListeners();

        // 移除不需要的监听器
        listeners.removeIf(listener -> listener.getClass().getName().equals("com.ximalaya.eros.listenner.ErosSpringApplicationRunListener"));

        // 设置新的监听器列表
        application.setListeners(listeners);

        application.run(args);

        log.info("容器启动成功！");
    }

}
