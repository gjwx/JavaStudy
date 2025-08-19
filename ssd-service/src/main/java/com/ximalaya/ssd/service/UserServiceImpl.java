package com.ximalaya.ssd.service;

import com.ximalaya.ssd.thrift.User;
import com.ximalaya.ssd.thrift.UserNotFoundException;
import com.ximalaya.ssd.thrift.UserService;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 用户服务实现类
 * 处理用户相关的业务逻辑
 */
@Service
public class UserServiceImpl implements UserService.Iface {
    
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    
    // 模拟数据库存储 - 实际项目中应该使用数据库
    private final Map<Long, User> userDatabase = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);
    
    public UserServiceImpl() {
        // 初始化一些测试数据
        initTestData();
    }
    
    private void initTestData() {
        User user1 = new User(1L, "张三", "zhangsan@ximalaya.com", 25);
        User user2 = new User(2L, "李四", "lisi@ximalaya.com", 30);
        User user3 = new User(3L, "王五", "wangwu@ximalaya.com", 28);
        
        userDatabase.put(1L, user1);
        userDatabase.put(2L, user2);
        userDatabase.put(3L, user3);
        idGenerator.set(4L);
        
        logger.info("用户服务初始化完成，当前用户数: {}", userDatabase.size());
    }

    @Override
    public User getUserById(long userId) throws UserNotFoundException, TException {
        logger.debug("获取用户信息, userId: {}", userId);
        
        User user = userDatabase.get(userId);
        if (user == null) {
            String message = "用户不存在, userId: " + userId;
            logger.warn(message);
            throw new UserNotFoundException(message);
        }
        
        logger.debug("成功获取用户信息: {}", user.getName());
        return user;
    }

    @Override
    public long createUser(User user) throws TException {
        logger.info("创建新用户: {}", user.getName());
        
        // 生成新的用户ID
        long newUserId = idGenerator.getAndIncrement();
        user.setId(newUserId);
        
        // 保存用户
        userDatabase.put(newUserId, user);
        
        logger.info("成功创建用户, userId: {}, name: {}", newUserId, user.getName());
        return newUserId;
    }

    @Override
    public boolean updateUser(User user) throws UserNotFoundException, TException {
        logger.info("更新用户信息, userId: {}", user.getId());
        
        if (!userDatabase.containsKey(user.getId())) {
            String message = "用户不存在, userId: " + user.getId();
            logger.warn(message);
            throw new UserNotFoundException(message);
        }
        
        userDatabase.put(user.getId(), user);
        logger.info("成功更新用户信息, userId: {}, name: {}", user.getId(), user.getName());
        return true;
    }

    @Override
    public boolean deleteUser(long userId) throws UserNotFoundException, TException {
        logger.info("删除用户, userId: {}", userId);
        
        User removedUser = userDatabase.remove(userId);
        if (removedUser == null) {
            String message = "用户不存在, userId: " + userId;
            logger.warn(message);
            throw new UserNotFoundException(message);
        }
        
        logger.info("成功删除用户, userId: {}, name: {}", userId, removedUser.getName());
        return true;
    }

    @Override
    public List<User> getAllUsers() throws TException {
        logger.debug("获取所有用户列表");
        
        List<User> users = new ArrayList<>(userDatabase.values());
        // 按ID排序
        users.sort(Comparator.comparing(User::getId));
        
        logger.debug("成功获取用户列表, 用户数: {}", users.size());
        return users;
    }

    @Override
    public String ping() throws TException {
        logger.debug("收到ping请求");
        return "pong - SSD服务正常运行, 当前时间: " + new Date() + 
               ", 当前用户数: " + userDatabase.size();
    }
} 