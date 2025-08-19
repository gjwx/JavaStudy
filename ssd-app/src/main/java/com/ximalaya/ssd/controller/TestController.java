package com.ximalaya.ssd.controller;

import com.ximalaya.ssd.aspect.annotation.LimitAopV2;
import com.ximalaya.ssd.aspect.annotation.SECOND;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

/**
 * 〈测试功能〉
 *
 * @author gongjiawei
 * @date 2025/7/11 下午12:05
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/hello")
    @LimitAopV2
    @SECOND
    public String hello(@RequestParam("name") String name){


        return "hello"+name;
    }

}
