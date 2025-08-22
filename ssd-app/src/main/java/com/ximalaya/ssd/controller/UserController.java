package com.ximalaya.ssd.controller;

import com.ximalaya.ssd.client.UserServiceClient;
import com.ximalaya.ssd.thrift.User;
import com.ximalaya.ssd.thrift.UserNotFoundException;
import com.ximalaya.ssd.thrift.UserService;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户控制器 - 作为Thrift客户端调用ssd-service
 * 这是一个纯粹的客户端应用，通过Thrift RPC调用后端服务
 */
@RestController
@RequestMapping("/api/users")
public class UserController {
    
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    
    @Value("${thrift.client.host:localhost}")
    private String thriftHost;
    
    @Value("${thrift.client.port:9090}")
    private int thriftPort;
    
    /**
     * 测试连接
     */
    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        try {
            UserService.Client client = UserServiceClient.createClient(thriftHost, thriftPort);
            String result = client.ping();
            logger.info("Ping成功: {}", result);
            return ResponseEntity.ok(result);
        } catch (TException e) {
            logger.error("Ping失败", e);
            return ResponseEntity.status(500).body("服务调用失败: " + e.getMessage());
        }
    }
    
    /**
     * 获取所有用户
     */
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        try {
            UserService.Client client = UserServiceClient.createClient(thriftHost, thriftPort);
            List<User> users = client.getAllUsers();
            logger.info("获取用户列表成功，用户数: {}", users.size());
            return ResponseEntity.ok(users);
        } catch (TException e) {
            logger.error("获取用户列表失败", e);
            return ResponseEntity.status(500).body(null);
        }
    }
    
    /**
     * 根据ID获取用户
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        try {
            UserService.Client client = UserServiceClient.createClient(thriftHost, thriftPort);
            User user = client.getUserById(id);
            logger.info("获取用户成功: {}", user.getName());
            return ResponseEntity.ok(user);
        } catch (UserNotFoundException e) {
            logger.warn("用户不存在: {}", id);
            return ResponseEntity.notFound().build();
        } catch (TException e) {
            logger.error("获取用户失败", e);
            return ResponseEntity.status(500).body(null);
        }
    }
    
    /**
     * 创建用户
     */
    @PostMapping
    public ResponseEntity<Long> createUser(@RequestBody User user) {
        try {
            UserService.Client client = UserServiceClient.createClient(thriftHost, thriftPort);
            long userId = client.createUser(user);
            logger.info("创建用户成功, userId: {}, name: {}", userId, user.getName());
            return ResponseEntity.ok(userId);
        } catch (TException e) {
            logger.error("创建用户失败", e);
            return ResponseEntity.status(500).body(null);
        }
    }
    
    /**
     * 更新用户
     */
    @PutMapping("/{id}")
    public ResponseEntity<Boolean> updateUser(@PathVariable Long id, @RequestBody User user) {
        try {
            UserService.Client client = UserServiceClient.createClient(thriftHost, thriftPort);
            user.setId(id); // 设置ID
            boolean result = client.updateUser(user);
            logger.info("更新用户成功, userId: {}, name: {}", id, user.getName());
            return ResponseEntity.ok(result);
        } catch (UserNotFoundException e) {
            logger.warn("要更新的用户不存在: {}", id);
            return ResponseEntity.notFound().build();
        } catch (TException e) {
            logger.error("更新用户失败", e);
            return ResponseEntity.status(500).body(null);
        }
    }
    
    /**
     * 删除用户
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable Long id) {
        try {
            UserService.Client client = UserServiceClient.createClient(thriftHost, thriftPort);
            boolean result = client.deleteUser(id);
            logger.info("删除用户成功, userId: {}", id);
            return ResponseEntity.ok(result);
        } catch (UserNotFoundException e) {
            logger.warn("要删除的用户不存在: {}", id);
            return ResponseEntity.notFound().build();
        } catch (TException e) {
            logger.error("删除用户失败", e);
            return ResponseEntity.status(500).body(null);
        }
    }
} 