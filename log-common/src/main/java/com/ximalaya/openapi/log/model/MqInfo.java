package com.ximalaya.openapi.log.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 〈〉
 *
 * @author gongjiawei
 * @date 2025/9/22 上午11:29
 */
public class MqInfo {
    private String mqType;
    private String mqQueueOrConsumer;
    private String mqContent;
    private String receiverApplication;

    public MqInfo() {
    }

    public String getMqType() {
        return this.mqType;
    }

    public void setMqType(String mqType) {
        this.mqType = mqType;
    }

    public String getMqQueueOrConsumer() {
        return this.mqQueueOrConsumer;
    }

    public void setMqQueueOrConsumer(String mqQueueOrConsumer) {
        this.mqQueueOrConsumer = mqQueueOrConsumer;
    }

    public String getMqContent() {
        return this.mqContent;
    }

    public void setMqContent(String mqContent) {
        this.mqContent = mqContent;
    }

    public String getReceiverApplication() {
        return this.receiverApplication;
    }

    public void setReceiverApplication(String receiverApplication) {
        this.receiverApplication = receiverApplication;
    }
@Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }

}
