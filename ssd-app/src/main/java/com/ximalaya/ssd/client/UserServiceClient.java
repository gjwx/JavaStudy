package com.ximalaya.ssd.client;

import com.ximalaya.ssd.thrift.UserService;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 简单的用户服务客户端
 */
public class UserServiceClient {
    
    private static final Logger logger = LoggerFactory.getLogger(UserServiceClient.class);
    
    /**
     * 创建用户服务客户端
     */
    public static UserService.Client createClient(String host, int port) throws TTransportException {
        logger.debug("创建Thrift客户端连接: {}:{}", host, port);
        
        TTransport transport = new TSocket(host, port);
        TProtocol protocol = new TBinaryProtocol(transport);
        
        transport.open();
        
        return new UserService.Client(protocol);
    }
} 