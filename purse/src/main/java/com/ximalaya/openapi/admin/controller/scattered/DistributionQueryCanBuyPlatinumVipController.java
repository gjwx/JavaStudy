package com.ximalaya.openapi.admin.controller.scattered;

import com.feilong.json.JsonUtil;
import com.ximalaya.openapi.admin.alllenum.AccessAccountTypeEnum;
import com.ximalaya.openapi.admin.aspect.annotation.CompliantV2;
import com.ximalaya.openapi.admin.aspect.annotation.LimitAopV2;
import com.ximalaya.openapi.admin.aspect.annotation.TokenValidateV2;
import com.ximalaya.openapi.admin.client.AccountClient;
import com.ximalaya.openapi.admin.controller.handler.DistributePlatinumVipMemberChecker;
import com.ximalaya.openapi.admin.entity.CompliantType;
import com.ximalaya.openapi.admin.entity.PayCommonQueryParam;
import com.ximalaya.openapi.log.LogContextExecutor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import signature.SignatureAopV2;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static com.feilong.core.bean.ConvertUtil.toMap;
import static com.feilong.core.date.DateUtil.formatDurationUseBeginTimeMillis;
import static com.feilong.net.http.HttpLogHelper.autoLog;
import static com.ximalaya.openapi.log.AutoLog.autoLog;

@RestController
@Slf4j
public class DistributionQueryCanBuyPlatinumVipController {

    @Autowired
    private DistributePlatinumVipMemberChecker distributePlatinumVipMemberChecker;

    @Autowired
    private AccountClient accountClient;
    //---------------------------------------------------------------

    @ResponseBody
    @RequestMapping(value = "/query/can_buy_platinum_vip")
    @LimitAopV2
    //    @PlaceOrderParamsCheckV2(deviceIdNotBlankCheck = true)
    //    @DistributeLog
    @CompliantV2(type = CompliantType.SERVER_CLIENT)
    //    @EnablePayCheckV2
    @TokenValidateV2
    @SignatureAopV2
    public Object queryCanBuyPlatinumVip(
                    PayCommonQueryParam payCommonQueryParam,
                    @RequestParam(value = "access_account_type") Integer accessAccountType,
                    //  请勿删除 这个参数 aop中需要这个参数
                    HttpServletRequest request){

        return LogContextExecutor.call("", () -> {
            long startTime = System.currentTimeMillis();
            log.info(autoLog("willQueryCanBuyPlatinumVip"));

            long uid = getUid(payCommonQueryParam, accessAccountType);

            Pair<Boolean, String> canBuyPlatinumVipPair = distributePlatinumVipMemberChecker.getCanBuyPlatinumVipPair(uid);
            Map<String, Object> result = toMap(
                            "status",
                            canBuyPlatinumVipPair.getLeft(), //

                            "desc",
                            canBuyPlatinumVipPair.getRight());

            log.info(
                            autoLog(
                                            "endQueryCanBuyPlatinumVip useTime: [{}] result:{}",
                                            formatDurationUseBeginTimeMillis(startTime),
                                            JsonUtil.toString(result)));
            return result;

        });
    }

    private long getUid(PayCommonQueryParam payCommonQueryParam,Integer accessAccountType){
        AccessAccountTypeEnum accountType = AccessAccountTypeEnum.getTypeEnum(accessAccountType);
        if (AccessAccountTypeEnum.MOBILE.equals(accountType)){
            return accountClient.queryUserInfoByMobile(payCommonQueryParam.getMobile());
        }
        return payCommonQueryParam.getUid();
    }

}