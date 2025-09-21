package com.ximalaya.openapi.admin.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Map;

/**
 * @author yunfeng.lu
 * @create 2019-06-18.
 */
public class ShortMessageDTO{

    /**
     * 模板ID
     */
    private String              templateId;

    /**
     * 手机号
     */
    private String              mobile;

    /**
     * 模板变量Map
     */
    private Map<String, String> templateVariableMap;

    //---------------------------------------------------------------

    public String getTemplateId(){
        return templateId;
    }

    public void setTemplateId(String templateId){
        this.templateId = templateId;
    }

    public String getMobile(){
        return mobile;
    }

    public void setMobile(String mobile){
        this.mobile = mobile;
    }

    public Map<String, String> getTemplateVariableMap(){
        return templateVariableMap;
    }

    public void setTemplateVariableMap(Map<String, String> templateVariableMap){
        this.templateVariableMap = templateVariableMap;
    }

    //---------------------------------------------------------------
    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString(){
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
