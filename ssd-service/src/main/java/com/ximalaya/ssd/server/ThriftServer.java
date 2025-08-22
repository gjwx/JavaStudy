package com.ximalaya.ssd.server;

import com.ximalaya.ssd.service.UserServiceImpl;
import com.ximalaya.ssd.thrift.UserService;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Thrift服务器
 * 负责启动和管理Thrift服务
 */
@Component
public class ThriftServer implements CommandLineRunner {
    
    private static final Logger logger = LoggerFactory.getLogger(ThriftServer.class);
    
    @Value("${thrift.server.port:9090}")
    private int port;
    
    @Value("${thrift.server.min-threads:5}")
    private int minThreads;
    
    @Value("${thrift.server.max-threads:20}")
    private int maxThreads;
    
    @Autowired
    private UserServiceImpl userService;
    
    private TServer server;
    
    @Override
    public void run(String... args) throws Exception {
        // 在单独线程中启动Thrift服务器，避免阻塞Spring Boot应用
        Thread serverThread = new Thread(this::startThriftServer, "ThriftServer");
        serverThread.setDaemon(false); // 设置为非守护线程，确保服务器不会随主线程结束
        serverThread.start();
        
        // 添加关闭钩子，优雅关闭服务器
        Runtime.getRuntime().addShutdownHook(new Thread(this::stopThriftServer));
    }
    
    private void startThriftServer() {
        try {
            logger.info("正在启动Thrift服务器，端口: {}", port);
            
            // 创建服务处理器
            UserService.Processor<UserService.Iface> processor = 
                new UserService.Processor<>(userService);
            
            // 创建传输层
            TServerSocket serverTransport = new TServerSocket(port);
            
            // 配置服务器参数
            TThreadPoolServer.Args serverArgs = new TThreadPoolServer.Args(serverTransport)
                .processor(processor)
                .protocolFactory(new TBinaryProtocol.Factory())
                .minWorkerThreads(minThreads)
                .maxWorkerThreads(maxThreads);
            
            // 创建服务器
            server = new TThreadPoolServer(serverArgs);
            
            logger.info("Thrift服务器启动成功！");
            logger.info("监听端口: {}", port);
            logger.info("工作线程池: {}-{}", minThreads, maxThreads);
            logger.info("可以使用客户端连接到: localhost:{}", port);
            
            // 启动服务器（这个调用会阻塞）
            server.serve();
            
        } catch (TTransportException e) {
            logger.error("Thrift服务器启动失败", e);
            throw new RuntimeException("Thrift服务器启动失败", e);
        } catch (Exception e) {
            logger.error("Thrift服务器运行异常", e);
            throw new RuntimeException("Thrift服务器运行异常", e);
        }
    }
    
    /**
     * 停止Thrift服务器
     */
    private void stopThriftServer() {
        if (server != null && server.isServing()) {
            logger.info("正在关闭Thrift服务器...");
            server.stop();
            logger.info("Thrift服务器已关闭");
        }
    }
    
    /**
     * 检查服务器是否正在运行
     */
    public boolean isServing() {
        return server != null && server.isServing();
    }
} 