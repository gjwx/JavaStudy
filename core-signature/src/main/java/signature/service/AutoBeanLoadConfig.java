package signature.service;

import com.ximalaya.mainstay.init.ApplicationConfig;
import com.ximalaya.mainstay.init.ProtocolConfig;
import com.ximalaya.mainstay.init.RegistryConfig;
import com.ximalaya.mainstay.init.spring.ClientConfigBean;
import com.ximalaya.openplatform.appservice.thrift.iface.AppService;
import org.osgi.framework.ServiceException;
import org.springframework.context.annotation.Bean;

/**
 */
public class AutoBeanLoadConfig{

    @Bean("appService")
    public AppService.Iface appService(ApplicationConfig applicationConfig,RegistryConfig registryConfig,ProtocolConfig protocolConfig)
                    throws Exception{
        if (applicationConfig == null || registryConfig == null || protocolConfig == null){
            throw new ServiceException("请确认mainstay3配置是否存在");
        }

        ClientConfigBean<AppService.Iface> configBean = new ClientConfigBean<>();
        configBean.setId("openapi-app-service");
        configBean.setGroup("openapi-app-service");
        configBean.setIfaceClass(AppService.Iface.class);
        configBean.setMultiplex(true);
        configBean.setTimeout(8000);
        configBean.setApplication(applicationConfig);
        configBean.setProtocol(protocolConfig);
        configBean.setRegistry(registryConfig);
        configBean.afterPropertiesSet();

        return configBean.getObject();
    }

}
