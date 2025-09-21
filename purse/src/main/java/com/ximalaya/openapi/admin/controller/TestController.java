package com.ximalaya.openapi.admin.controller;

import com.ximalaya.openapi.admin.vo.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

/**
 * 〈测试功能〉
 *
 * @author gongjiawei
 * @date 2025/7/11 下午12:05
 */
@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/hello")
    public Response hello(@RequestParam("name") String name){
        log.info("收到 hello 请求, name={}", name);
        return Response.success("hello " + name);
    }

}
