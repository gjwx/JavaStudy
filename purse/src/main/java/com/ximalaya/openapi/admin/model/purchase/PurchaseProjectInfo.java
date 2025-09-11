/**

 */
package com.ximalaya.openapi.admin.model.purchase;

import com.google.common.base.Objects;

import java.util.List;

public class PurchaseProjectInfo {

    /**
     * 采购项目名称
     */
    private String projectName;
    /**
     * 应用
     */
    private String appKey;
    /**
     * 1-标准采购限制份额，2-标准采购不限份额，3-非预付采购
     */
    private Integer purchaseType;
    /**
     * 采购商品ID
     */
    private String projectProductId;
    /**
     * 采购份额
     */
    private String projectQuota;
    /**
     * 采购金额
     */
    private String projectActualAmount;
    /**
     * 采购单折扣，参数格式：“80.00”代表8折
     */
    private String discountRate;
    /**
     * 1-一次性打款，2-多次回款，3-合作方结算后补单
     */
    private Integer payType;
    /**
     * 1-有效期，商品上取有效时间，2-时间段
     */
    private Integer fulfillmentType;
    /**
     * 项目开始时间，yyyy_MM_dd HH:mm:ss
     */
    private Long startAt;
    /**
     * 项目结束时间，yyyy_MM_dd HH:mm:ss
     */
    private Long endAt;
    /**
     * 汇款发票附件列表
     */
    private List<AttachInfo> remittanceVouchers;
    /**
     * 合同附件列表
     */
    private List<AttachInfo> contractAttachments;
    /**
     * 项目变更日志列表
     */
    private List<ProjectChangeLog> projectChangeLogList;
    /**
     * 采购项目id
     */
    private String projectId;
    /**
     * 采购商品名称
     */
    private String projectProductName;
    /**
     * 采购已消耗份额
     */
    private String projectConsumeQuota;
    /**
     * 1-审批中；2-审批拒绝；3-审批通过待生效；4-审批通过生效中；5-已过期；
     */
    private Integer projectStatus;
    /**
     * 项目订单号列表，增购等场景会有多个订单返回
     */
    private List<String> projectOrderNoList;
    /**
     * 最近操作人
     */
    private String operatorName;
    /**
     * 项目创建时间
     */
    private Long createdAt;
    /**
     * 最近操作时间
     */
    private Long updatedAt;

    /**
     * 商品类型：轻学堂企业会员-1,专辑-2,ximi团会员-3,喜马会员-4，内容包-5
     */
    private Integer itemType;
    /**
     * 应用名称
     */
    private String appName;
    /**
     * 项目审批流水号列表
     */
    private List<String> processSerialNoList;

    public PurchaseProjectInfo() {
    }

    public PurchaseProjectInfo(String projectName, String appKey, Integer purchaseType, String projectProductId, String projectQuota, String projectActualAmount, String discountRate, Integer payType, Integer fulfillmentType, Long startAt, Long endAt, List<AttachInfo> remittanceVouchers, List<AttachInfo> contractAttachments, List<ProjectChangeLog> projectChangeLogList, String projectId, String projectProductName, String projectConsumeQuota, Integer projectStatus, List<String> projectOrderNoList, String operatorName, Long createdAt, Long updatedAt, Integer itemType, String appName, List<String> processSerialNoList) {
        this.projectName = projectName;
        this.appKey = appKey;
        this.purchaseType = purchaseType;
        this.projectProductId = projectProductId;
        this.projectQuota = projectQuota;
        this.projectActualAmount = projectActualAmount;
        this.discountRate = discountRate;
        this.payType = payType;
        this.fulfillmentType = fulfillmentType;
        this.startAt = startAt;
        this.endAt = endAt;
        this.remittanceVouchers = remittanceVouchers;
        this.contractAttachments = contractAttachments;
        this.projectChangeLogList = projectChangeLogList;
        this.projectId = projectId;
        this.projectProductName = projectProductName;
        this.projectConsumeQuota = projectConsumeQuota;
        this.projectStatus = projectStatus;
        this.projectOrderNoList = projectOrderNoList;
        this.operatorName = operatorName;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.itemType = itemType;
        this.appName = appName;
        this.processSerialNoList = processSerialNoList;
    }
    public Integer getItemType() {
        return itemType;
    }

    public void setItemType(Integer itemType) {
        this.itemType = itemType;
    }

    public Integer getPurchaseType() {
        return purchaseType;
    }

    public void setPurchaseType(Integer purchaseType) {
        this.purchaseType = purchaseType;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getProjectProductId() {
        return projectProductId;
    }

    public void setProjectProductId(String projectProductId) {
        this.projectProductId = projectProductId;
    }

    public String getProjectQuota() {
        return projectQuota;
    }

    public void setProjectQuota(String projectQuota) {
        this.projectQuota = projectQuota;
    }

    public String getProjectActualAmount() {
        return projectActualAmount;
    }

    public void setProjectActualAmount(String projectActualAmount) {
        this.projectActualAmount = projectActualAmount;
    }

    public String getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(String discountRate) {
        this.discountRate = discountRate;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Integer getFulfillmentType() {
        return fulfillmentType;
    }

    public void setFulfillmentType(Integer fulfillmentType) {
        this.fulfillmentType = fulfillmentType;
    }

    public Long getStartAt() {
        return startAt;
    }

    public void setStartAt(Long startAt) {
        this.startAt = startAt;
    }

    public Long getEndAt() {
        return endAt;
    }

    public void setEndAt(Long endAt) {
        this.endAt = endAt;
    }

    public List<AttachInfo> getRemittanceVouchers() {
        return remittanceVouchers;
    }

    public void setRemittanceVouchers(List<AttachInfo> remittanceVouchers) {
        this.remittanceVouchers = remittanceVouchers;
    }

    public List<AttachInfo> getContractAttachments() {
        return contractAttachments;
    }

    public void setContractAttachments(List<AttachInfo> contractAttachments) {
        this.contractAttachments = contractAttachments;
    }

    public List<ProjectChangeLog> getProjectChangeLogList() {
        return projectChangeLogList;
    }

    public void setProjectChangeLogList(List<ProjectChangeLog> projectChangeLogList) {
        this.projectChangeLogList = projectChangeLogList;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectProductName() {
        return projectProductName;
    }

    public void setProjectProductName(String projectProductName) {
        this.projectProductName = projectProductName;
    }

    public String getProjectConsumeQuota() {
        return projectConsumeQuota;
    }

    public void setProjectConsumeQuota(String projectConsumeQuota) {
        this.projectConsumeQuota = projectConsumeQuota;
    }

    public Integer getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(Integer projectStatus) {
        this.projectStatus = projectStatus;
    }

    public List<String> getProjectOrderNoList() {
        return projectOrderNoList;
    }

    public void setProjectOrderNoList(List<String> projectOrderNoList) {
        this.projectOrderNoList = projectOrderNoList;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Long updatedAt) {
        this.updatedAt = updatedAt;
    }


    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }
    public List<String> getProcessSerialNoList() {
        return processSerialNoList;
    }

    public void setProcessSerialNoList(List<String> processSerialNoList) {
        this.processSerialNoList = processSerialNoList;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PurchaseProjectInfo that = (PurchaseProjectInfo) o;
        return Objects.equal(projectName, that.projectName) && Objects.equal(appKey, that.appKey) && Objects.equal(purchaseType, that.purchaseType) && Objects.equal(projectProductId, that.projectProductId) && Objects.equal(projectQuota, that.projectQuota) && Objects.equal(projectActualAmount, that.projectActualAmount) && Objects.equal(discountRate, that.discountRate) && Objects.equal(payType, that.payType) && Objects.equal(fulfillmentType, that.fulfillmentType) && Objects.equal(startAt, that.startAt) && Objects.equal(endAt, that.endAt) && Objects.equal(remittanceVouchers, that.remittanceVouchers) && Objects.equal(contractAttachments, that.contractAttachments) && Objects.equal(projectChangeLogList, that.projectChangeLogList) && Objects.equal(projectId, that.projectId) && Objects.equal(projectProductName, that.projectProductName) && Objects.equal(projectConsumeQuota, that.projectConsumeQuota) && Objects.equal(projectStatus, that.projectStatus) && Objects.equal(projectOrderNoList, that.projectOrderNoList) && Objects.equal(operatorName, that.operatorName) && Objects.equal(createdAt, that.createdAt) && Objects.equal(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(projectName, appKey, purchaseType, projectProductId, projectQuota, projectActualAmount, discountRate, payType, fulfillmentType, startAt, endAt, remittanceVouchers, contractAttachments, projectChangeLogList, projectId, projectProductName, projectConsumeQuota, projectStatus, projectOrderNoList, operatorName, createdAt, updatedAt);
    }

    @Override
    public String toString() {
        return "PurchaseProjectInfo{" +
                "projectName='" + projectName + '\'' +
                ", appKey='" + appKey + '\'' +
                ", purchaseType=" + purchaseType +
                ", projectProductId='" + projectProductId + '\'' +
                ", projectQuota='" + projectQuota + '\'' +
                ", projectActualAmount='" + projectActualAmount + '\'' +
                ", discountRate='" + discountRate + '\'' +
                ", payType=" + payType +
                ", fulfillmentType=" + fulfillmentType +
                ", startAt=" + startAt +
                ", endAt=" + endAt +
                ", remittanceVouchers=" + remittanceVouchers +
                ", contractAttachments=" + contractAttachments +
                ", projectChangeLogList=" + projectChangeLogList +
                ", projectId='" + projectId + '\'' +
                ", projectProductName='" + projectProductName + '\'' +
                ", projectConsumeQuota='" + projectConsumeQuota + '\'' +
                ", projectStatus=" + projectStatus +
                ", projectOrderNoList=" + projectOrderNoList +
                ", operatorName='" + operatorName + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}