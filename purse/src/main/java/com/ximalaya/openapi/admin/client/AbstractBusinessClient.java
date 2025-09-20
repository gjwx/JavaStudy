package com.ximalaya.openapi.admin.client;

import com.ximalaya.business.gateway.open.sdk.java.common.service.CommonClientService;
import com.ximalaya.omp.openapi.payment.config.BusinessAccessPlatformConfig;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractBusinessClient {

    @Autowired
    protected CommonClientService          commonClientService;

    @Autowired
    protected BusinessAccessPlatformConfig businessAccessPlatformConfig;

}
