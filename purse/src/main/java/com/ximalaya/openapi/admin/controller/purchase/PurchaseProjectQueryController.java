package com.ximalaya.openapi.admin.controller.purchase;

import com.ximalaya.chameleon.plugin.annotation.ApiDoc;
import com.ximalaya.mainstay.rpc.thrift.TException;
import com.ximalaya.omp.distributor.api.model.PurchaseProjectInfoPage;
import com.ximalaya.omp.distributor.api.model.SupplementBusinessOrderPage;
import com.ximalaya.openapi.admin.converter.purchase.PurchaseProjectMapper;
import com.ximalaya.openapi.admin.exception.TException;
import com.ximalaya.openapi.admin.model.purchase.*;
import com.ximalaya.openapi.admin.service.purchase.PurchaseProjectQueryService;
import com.ximalaya.openapi.admin.vo.PageResult;
import com.ximalaya.openapi.admin.vo.Response;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**

 */
@RestController
@RequestMapping("/purchase/query")
public class PurchaseProjectQueryController {
    @Autowired
    private PurchaseProjectQueryService projectService;

    /**
     * 查询采购项目
     *
     * @param projectName     采购项目名称
     * @param appKey          应用
     * @param purchaseType    1-标准采购限制份额，2-标准采购不限份额，3-非预付采购
     * @param projectId       项目ID
     * @param projectStatus   1-审批中；2-审批拒绝；3-审批通过待生效；4-审批通过生效中；5-已过期；
     * @param fulfillmentType 1-有效期，商品上取有效时间，2-时间段
     * @param currentPage
     * @param pageSize
     * @return
     */

    @GetMapping(value = "/query_purchase_project")
    public Response<PageResult<PurchaseProjectInfo>> queryPurchaseProject(@RequestParam(required = false) String projectName,
                                                                          @RequestParam(required = false) String appKey,
                                                                          @RequestParam(required = false) String purchaseType,
                                                                          @RequestParam(required = false) String projectId,
                                                                          @RequestParam(required = false) Integer projectStatus,
                                                                          @RequestParam(required = false) Integer fulfillmentType,
                                                                          @RequestParam(defaultValue = "1") Integer currentPage,
                                                                          @RequestParam(defaultValue = "10") Integer pageSize) throws TException {


        PurchaseProjectInfoPage purchaseProjectInfoPage = projectService.queryPurchaseProject(projectName, appKey, purchaseType, projectId, projectStatus, fulfillmentType, currentPage, pageSize);
        PurchaseProjectMapper mapper = Mappers.getMapper(PurchaseProjectMapper.class);
        PageResult<PurchaseProjectInfo> result = mapper.convert(purchaseProjectInfoPage);
        return Response.success(result);
    }


    /**
     * 查询补单列表
     *
     * @param projectId 项目ID
     * @return
     */
    @GetMapping(value = "/query_supplement_business_order")
    public Response<PageResult<SupplementBusinessOrder>> querySupplementBusinessOrder(@RequestParam String projectId,
                                                                                      @RequestParam(defaultValue = "1") Integer currentPage,
                                                                                      @RequestParam(defaultValue = "100") Integer pageSize) throws TException {
        SupplementBusinessOrderPage supplementBusinessOrderPage = projectService.querySupplementBusinessOrder(projectId, currentPage, pageSize);
        PurchaseProjectMapper mapper = Mappers.getMapper(PurchaseProjectMapper.class);
        PageResult<SupplementBusinessOrder> result = mapper.convert(supplementBusinessOrderPage);
        return Response.success(result);
    }

    /**
     * 是否允许编辑项目：只有审批拒绝才允许编辑
     * 1-审批中；2-审批拒绝；3-审批通过待生效；4-审批通过生效中；5-已过期；     *
     *
     * @param projectId 项目id
     * @return
     */
    @GetMapping(value = "/allow_to_update_project")
    public boolean allowToUpdateProject(@RequestParam String projectId) {
        return false;
    }

    /**
     * 查询项目消耗详情
     *
     * @param projectId   项目ID
     * @param consumeType 1-自主领取；2-批量充值
     * @param currentPage
     * @param pageSize
     * @return
     */
    @GetMapping(value = "/query_project_consume_info")
    public Response<PageResult<PurchaseProjectConsumeInfo>> queryProjectConsumeInfo(@RequestParam String projectId,
                                                                                    @RequestParam(required = false) Integer consumeType,
                                                                                    @RequestParam(defaultValue = "1") Integer currentPage,
                                                                                    @RequestParam(defaultValue = "100") Integer pageSize) {
        return null;
    }

    /**
     * 查询项目变更日志
     *
     * @param projectId 项目ID
     * @return
     */
    @GetMapping(value = "/query_project_change_log")
    public Response<PageResult<ProjectChangeLog>> queryProjectChangeLog(@RequestParam String projectId,
                                                                        @RequestParam(defaultValue = "1") Integer currentPage,
                                                                        @RequestParam(defaultValue = "100") Integer pageSize) {
        return null;
    }

    /**
     * 查询审批结果（含驳回原因）
     *
     * @param projectId 项目ID
     */
    @GetMapping(value = "/query_approval_result")
    public Response<ProjectApprovalResult> queryApprovalResult(@RequestParam String projectId) {
        return null;
    }

    /**
     * 查询消耗单汇总结果（消耗单就是对应"充值"）
     *
     * @param projectId 项目ID
     */
    @GetMapping(value = "/query_consumption_order_result")
    public Response<PageResult<ProjectConsumptionOrderResult>> queryConsumptionOrderResult(@RequestParam String projectId,
                                                                                           @RequestParam String rechargeBatchNo,
                                                                                           @RequestParam(defaultValue = "1") Integer currentPage,
                                                                                           @RequestParam(defaultValue = "100") Integer pageSize) {
        return null;
    }

}
