package com.ximalaya.openapi.admin.client;

import com.google.common.collect.Maps;
import com.ximalaya.commons.model.dto.FindProductItemByItemIdResponseDto;
import com.ximalaya.commons.model.dto.ProductItemDto;
import com.ximalaya.openapi.admin.aspect.annotation.AopLog;
import com.ximalaya.openapi.admin.ex.OpenAPIThirdPartyServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.feilong.core.Validator.isNullOrEmpty;
import static com.ximalaya.openapi.log.AutoLog.autoLog;
import static java.util.Collections.emptyMap;

/**
 * 〈〉
 *
 * @author gongjiawei
 * @date 2025/9/18 下午4:46
 */
@Slf4j
@Service
public class ProductClient extends AbstractBusinessClient{
    @Autowired
    private ProductItemQueryService productItemQueryService;

    //---------------------------------------------------------------

    @AopLog
    public Map<Long, ProductItemDto> findByItemIds(List<Long> itemIds){
        if (isNullOrEmpty(itemIds)){
            return emptyMap();
        }

        //---------------------------------------------------------------
        Map<Long, ProductItemDto> result = Maps.newHashMap();
        try{
            List<ProductItemDto> dtoList = productItemQueryService.findByItemIds(itemIds);
            result.putAll(dtoList.stream().collect(Collectors.toMap(ProductItemDto::getItemId, o -> o, (k1, k2) -> k1)));
        }catch (Exception e){
            log.error(autoLog("ThriftProductItemQueryService.findByItemIds itemIds:{}", itemIds), e);
        }
        return result;
    }

    //---------------------------------------------------------------

    @AopLog
    public ProductItemDto findByItemId(Long itemId){
        String serviceAndMethod = "ThriftProductItemQueryService.findByItemId:商品查询服务";
        try{
            //            log.info(autoLog("REQUEST: Method:{}, itemId:{}", serviceAndMethod, itemId));

            ProductItemDto productItemDto = productItemQueryService.findByItemId(itemId);
            log.info(autoLog("RESPONSE: METHOD:{}, itemId:[{}] Response:{}", serviceAndMethod, itemId, productItemDto));
            return productItemDto;
        }catch (Exception e){
            log.error(autoLog("METHOD:{} itemId:{}", serviceAndMethod, itemId), e);
            throw new OpenAPIThirdPartyServiceException(serviceAndMethod, e);
        }
    }

    //---------------------------------------------------------------

    public FindProductItemByItemIdResponseDto findProductItemByItemId(Long itemId){
        FindProductItemByItemIdRequestDto requestDto = new FindProductItemByItemIdRequestDto();
        requestDto.setItemId(itemId);

        return BusinessUtil.execute(
                commonClientService,
                businessAccessPlatformConfig.getAppKey(),
                businessAccessPlatformConfig.getAppSecret(),
                requestDto);
    }

    public QueryBySpuIdResponseDto queryBySpuId(String spuId){
        QueryBySpuIdRequestDto requestDto = new QueryBySpuIdRequestDto();
        requestDto.setSpuId(spuId);

        return BusinessUtil.execute(
                commonClientService,
                businessAccessPlatformConfig.getAppKey(),
                businessAccessPlatformConfig.getAppSecret(),
                requestDto);
    }
}
