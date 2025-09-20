package com.ximalaya.openapi.admin.controller.purchase;

import com.ximalaya.chameleon.plugin.annotation.ApiDoc;
import com.ximalaya.mainstay.rpc.thrift.TException;
import com.ximalaya.openapi.admin.model.purchase.*;
import com.ximalaya.openapi.admin.service.purchase.PurchaseProjectService;
import com.ximalaya.openapi.admin.utils.checker.PurchaseProjectChecker;
import com.ximalaya.openapi.admin.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 
 * @author: JordanQiu
 * @time: 2023/3/28
 */
@RestController
@RequestMapping("/purchase/project")
public class PurchaseProjectCommandController {

    @Autowired
    private PurchaseProjectChecker checker;

    @Autowired
    private PurchaseProjectService projectService;

//    @Autowired
//    private PurchaseOrderOrderService purchaseOrderService;

    /**
     * 新增采购项目
     *
     * @param purchaseProjectDetailRequest 采购项目信息
     * @return
     */
    @PostMapping(value = "/add_purchase_project")
    public Response<ProjectResult> addPurchaseProject(@RequestBody PurchaseProjectDetailRequest purchaseProjectDetailRequest) throws Exception {
        ProjectResult result = projectService.addPurchaseProject(purchaseProjectDetailRequest);
        return Response.success(result);
    }

    /**
     * 编辑采购项目
     *
     * @return
     */
    @PostMapping(value = "/update_purchase_project")
    @ResponseBody
    public Response<ProjectResult> updatePurchaseProject(@RequestBody UpdatePurchaseProjectRequest updatePurchaseProjectRequest) throws TException {
        ProjectResult result = projectService.updatePurchaseProject(updatePurchaseProjectRequest);
        return Response.success(result);
    }

    /**
     * 增购份数
     *
     * @return
     */
    @PostMapping(value = "/increase_project_quota")
    public Response<ProjectResult> increaseProjectQuota(@RequestBody IncreaseProjectQuotaRequest increaseProjectQuotaRequest) throws TException {
        ProjectResult result = projectService.increaseProjectQuota(increaseProjectQuotaRequest);
        return Response.success(result);
    }

    /**
     * 退购份数
     *
     * @return
     */
    @PostMapping(value = "/reduce_project_quota")
    public Response<ProjectResult> reduceProjectQuota(@RequestBody ReduceProjectQuotaDTO reduceProjectQuotaDTO) {
        return Response.success("");
    }

    /**
     * 补单
     *
     * @return
     */
    @PostMapping(value = "/supply_business_order")
    public Response<ProjectResult> supplyBusinessOrder(@RequestBody SupplementBusinessOrderRequest supplementBusinessOrderRequest) throws TException {
        ProjectResult result = projectService.supplyBusinessOrder(supplementBusinessOrderRequest);
        return Response.success(result);
    }

    /**
     * 批量充值
     *
     * @param batchRechargeRequest
     * @return
     */
    @PostMapping(value = "/batch_recharge")
    public Response<ProjectResult> batchRecharge(@RequestBody BatchRechargeRequest batchRechargeRequest) throws TException {
        String rechargeBatchNo  = projectService.batchRecharge(batchRechargeRequest);
        return Response.success(rechargeBatchNo);
    }


    /**
     * 参数校验
     *
     * @param purchaseProjectDetailRequest
     * @return
     */
    public Response<ProjectResult> validate(PurchaseProjectDetailRequest purchaseProjectDetailRequest) {
        try {
            checker.checkProjectName(purchaseProjectDetailRequest.getProjectName());
        } catch (Exception e) {
            return Response.failure(-1, e.getMessage());
        }
        return null;
    }

    /**
     * 参数校验
     *
     * @param request
     * @return
     */
    public Response<ProjectResult> validate(UpdatePurchaseProjectRequest request) {
        try {
            checker.checkProjectName(request.getPurchaseProjectDetailRequest().getProjectName());
        } catch (Exception e) {
            return Response.failure(-1, e.getMessage());
        }
        return null;
    }
}
