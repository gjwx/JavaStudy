package com.ximalaya.openapi.admin.controller.handler;

import com.ximalaya.commons.enumall.DistributeItemType;
import com.ximalaya.commons.model.dto.DistributeDto;
import com.ximalaya.commons.model.dto.FindProductItemByItemIdResponseDto;
import com.ximalaya.commons.model.dto.VipDto;
import com.ximalaya.openapi.admin.client.ProductClient;
import com.ximalaya.openapi.admin.client.VipServiceClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.feilong.core.bean.ConvertUtil.toLong;
import static com.feilong.core.lang.StringUtil.formatPattern;
import static com.ximalaya.openapi.log.AutoLog.autoLog;

@Slf4j
@Component
public class DistributePlatinumVipMemberChecker{

    @Autowired
    private VipServiceClient vipServiceClient;

    @Autowired
    private ProductClient productClient;

    //---------------------------------------------------------------

    /**
     * 校验白金会员单购条件，组合商品不受限制
     *
     * @param distributeDto
     *            支付参数
     * @return 校验结果，如果校验通过返回null，否则返回错误响应
     */
    public boolean checkPatinaVipPurchaseCondition(DistributeDto distributeDto){
        if (DistributeItemType.PLATINUM_VIP_MEMBER.getValue() != distributeDto.getDistributeItemType()){
            return true;
        }
        // 判断组合商品
        FindProductItemByItemIdResponseDto productItemInfo = productClient
                        .findProductItemByItemId(Long.valueOf(distributeDto.getPayContent()));
        if (productItemInfo != null && productItemInfo.getComposite()){
            return true;
        }

        //---------------------------------------------------------------
        // 获取用户ID
        String uidStr = distributeDto.getUserInfo().getUid();
        Long uid = toLong(uidStr);

        Pair<Boolean, String> canBuyPlatinumVip = getCanBuyPlatinumVipPair(uid);
        return canBuyPlatinumVip.getLeft();
    }

    public Pair<Boolean, String> getCanBuyPlatinumVipPair(Long uid){
        if (uid <= 0){
            log.warn(autoLog("uid:{} <=0", uid));
            return Pair.of(false, "no uid");
        }

        //---------------------------------------------------------------
        // 获取会员信息
        VipDto vipDto = vipServiceClient.getVipDto(uid);
        if (vipDto == null){
            log.warn(autoLog("uid:{} vipDtoIsNull", uid));
            return Pair.of(false, "vipDto is null");
        }

        if (!vipDto.isValid()){
            log.warn(autoLog("uid:{} 未通过会员身份校验", uid));
            return Pair.of(false, formatPattern("uid:[{}] vipDto is not Valid", uid));
        }

        //---------------------------------------------------------------
        // 计算会员过期时间是否大于31天
        long expireTime = vipDto.getExpireDate();
        long daysDiff = (expireTime - System.currentTimeMillis()) / (24 * 60 * 60 * 1000);

        if (daysDiff <= 31){
            log.warn(autoLog("uid:{} 未通过会员有效期大于31天校验", uid));
            return Pair.of(false, formatPattern("uid:[{}] expireDate can not buy", uid));
        }

        return Pair.of(true, "success");
    }
}
