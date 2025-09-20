package com.ximalaya.openapi.admin.ex;

/**

 */
public class OpenAPIThirdPartyServiceException extends RuntimeException{

    private int    code;

    private String serviceName;

    private String param;

    public OpenAPIThirdPartyServiceException(String serviceName, Throwable t){
        super(serviceName, t);
    }

    //---------------------------------------------------------------

    public String getServiceName(){
        return serviceName;
    }

    public void setServiceName(String serviceName){
        this.serviceName = serviceName;
    }

}
