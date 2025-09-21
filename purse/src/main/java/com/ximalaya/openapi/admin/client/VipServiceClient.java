package com.ximalaya.openapi.admin.client;

import com.ximalaya.commons.model.dto.VipDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.ximalaya.openapi.log.AutoLog.autoLog;

/**
 * 〈〉
 *
 * @author gongjiawei
 * @date 2025/9/18 下午4:44
 */
@Slf4j
@Service
public class VipServiceClient {

    //
    //    /**
    //     * 0是查有没有权限，1是查有没有购买
    //     * 之后他们要迁移，就不能用作1是否购买这个业务逻辑了
    //     */
    //    private static final int VIP_PERMISSION_TYPE = 0;

    @Autowired
    private PlaceTradeOrderServiceClient            placeTradeOrderServiceClient;

    //    @Autowired
    //    private TradeCommandService tradeCommandService;
    @Autowired
    private VipUserQueryClient                      vipUserQueryClient;

    @Autowired
    private SubscriptionPayQueryServiceThriftClient subscriptionPayQueryServiceThriftClient;

    //    private static final ObjectMapper               OBJECT_MAPPER   = new ObjectMapper();

    //---------------------------------------------------------------

    /**
     * 校验是否是会员
     *
     * @param userId
     *            喜马id
     * @return boolean
     */
    public boolean checkIsVip(long userId){
        VipDto vipDto = getVipDto(userId);
        // 查询是会员 并且会员过期时长大于0
        return vipDto != null && vipDto.isValid();
    }

    /**
     * 获取用户会员信息包括失效时间
     *
     * @param userId
     *            喜马id
     */
    public VipDto getVipDto(long userId){
        String serviceAndMethod = "vipUserQueryClient.getFullUser:会员权益查询服务";
        try{

            //             * 会员产品 categoryId
            //            private static final Integer                    VIP_CATEGORY_ID = 105;
            //            * 会员产品 resourceId
            //           private static final String                     VIP_RESOURCE_ID = "100000";
            VipParam param = new VipParam((short) 105, "100000", userId);

            log.info(autoLog("{}, Request.VipParam: {}", serviceAndMethod, param));

            VipDto result = vipUserQueryClient.getFullUser(param);
            log.info(autoLog("{}, param:[{}] Response.VipDto: {}", serviceAndMethod, param, result));
            return result;
        }catch (Exception e){
            log.error(autoLog("{} Response.VipDto: {}", serviceAndMethod), e);
            throw new com.ximalaya.omp.openapi.payment.ex.OpenAPIThirdPartyServiceException(serviceAndMethod, e);
        }
    }

    /**
     * 校验是否领取过首月特惠会员
     *
     * @param userId
     *            喜马id
     * @param productItemId
     *            商品id
     */
    public boolean checkIsReceivedPromotionVip(long userId,Long productItemId){
        String serviceAndMethod = "SubscriptionPayQueryServiceThriftClient.findUserIntroductoryByItemId:促销会员领取查询服务";
        try{
            log.info(autoLog("REQUEST: Method: {}, Request.userId: {} productItemId: {}", serviceAndMethod, userId, productItemId));

            //private static final int                        XIMA_DOMIAN     = 1;
            UserIntroductoryDto userIntroductoryDto = subscriptionPayQueryServiceThriftClient
                    .findUserIntroductoryByItemId(1, userId, productItemId);
            log.info(autoLog("RESPONSE: METHOD: {}, Response.UserIntroductoryDto: {}", serviceAndMethod, userIntroductoryDto));

            // userIntroductoryDto 为null表示没有享受过优惠，可以享受试用 (实际测试发现这个不为空但是里面的数据为空)
            return userIntroductoryDto != null && userIntroductoryDto.getUserId() != null;
        }catch (Exception e){
            log.error(autoLog("METHOD: {} Request.userId: {} productItemId: {}", serviceAndMethod, userId, productItemId), e);
            throw new com.ximalaya.omp.openapi.payment.ex.OpenAPIThirdPartyServiceException(serviceAndMethod, e);
        }
    }

    /**
     * 校验是否领取过首月特惠会员 走分成服务
     *
     * @param userId
     *            喜马id
     * @param productItemId
     *            商品id
     */
    public boolean checkIsBoughtPromotionVip(long userId,Long productItemId){
        //  public static final String                      SUCCESS = "0";

        TradeOrderRequest tradeOrderRequest = new TradeOrderRequest();
        tradeOrderRequest.setUid(String.valueOf(userId));
        tradeOrderRequest.setItemId(String.valueOf(productItemId));
        tradeOrderRequest.setResCode("0");

        String serviceAndMethod = "IDistributorService.getTradeOrder:订单日志查询服务";
        try{
            log.info(autoLog("REQUEST: Method: {}, Request.TradeOrderRequest: {}", serviceAndMethod, tradeOrderRequest));
            TradeOrderResponse tradeOrder = placeTradeOrderServiceClient.getTradeOrder(tradeOrderRequest);
            log.info(autoLog("RESPONSE: METHOD: {}, Response.TradeOrderResponse: {}", serviceAndMethod, tradeOrder));

            // 有订单并且 状态为成功
            return tradeOrder != null && "0".equals(tradeOrder.getResCode());
        }catch (Exception e){
            log.error(autoLog("METHOD: {} Request.clearingOrderQuery: {}", serviceAndMethod, tradeOrderRequest), e);
            throw new com.ximalaya.omp.openapi.payment.ex.OpenAPIThirdPartyServiceException(serviceAndMethod, e);
        }

    }

}
