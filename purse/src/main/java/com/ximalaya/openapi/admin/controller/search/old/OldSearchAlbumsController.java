package com.ximalaya.openapi.admin.controller.search.old;

import com.feilong.core.lang.ObjectUtil;
import com.feilong.core.lang.StringUtil;
import com.ximalaya.openapi.app.gateway.annotation.GetClassifyCopyrightConfig;
import com.ximalaya.openapi.app.gateway.annotation.SearchResultConfig;
import com.ximalaya.openapi.app.gateway.aspect.FreeAudioApiPermissionCheckAspect;
import com.ximalaya.openapi.app.gateway.component.CategoryUtil;
import com.ximalaya.openapi.app.gateway.component.CoverUtil;
import com.ximalaya.openapi.app.gateway.football.SearchAlbumRerankConfig;
import com.ximalaya.openapi.app.gateway.football.SearchV2Config;
import com.ximalaya.openapi.app.gateway.service.CategoryBizService;
import com.ximalaya.openapi.app.gateway.service.search.SearchBizService;
import com.ximalaya.openapi.app.gateway.web.controller.search.v2.V2SearchAlbumsHandler;
import com.ximalaya.openapi.client.permission.annotation.Permission;
import com.ximalaya.openapi.common.exception.OpenAPIRequestParamValidateException;
import com.ximalaya.openapi.common.util.PagingRequestParam;
import com.ximalaya.openapi.web.common.constant.SearchCalcDimensionType;
import com.ximalaya.openapi.web.common.dto.album.AlbumList;
import com.ximalaya.openapi.web.common.query.CommonQueryParam;
import com.ximalaya.openapi.web.common.query.SearchQueryParam;
import com.ximalaya.openapi.web.common.util.ThreadLocals;
import com.ximalaya.openapi.web.common.validator.SearchQueryParamValidator;
import com.ximalaya.openapi.web.common.validator.ValidateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.feilong.core.bean.ConvertUtil.toLong;
import static com.ximalaya.openapi.log.AutoLog.autoLog;

/**
 * @deprecated 请使用 V2SearchAlbumsController 版权管控更优雅
 */
@Deprecated
@Slf4j
@Controller
public class OldSearchAlbumsController {

    @Autowired
    private SearchQueryParamValidator searchParamValidator;

    @Autowired
    private SearchBizService          newSearchBizService;

    @Autowired
    private SearchAlbumRerankConfig   searchAlbumRerankConfig;

    @Autowired
    private CategoryBizService        categoryBizService1;

    @Autowired
    private V2SearchAlbumsHandler     v2SearchAlbumsHandler;

    @Autowired
    private SearchV2Config            searchV2Config;

    @Value("${search.rerank.config.name}")
    private String                    configName;

    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.setValidator(searchParamValidator);
    }

    @Autowired
    private FreeAudioApiPermissionCheckAspect freeAudioApiPermissionCheckAspect;

    //---------------------------------------------------------------
    @ResponseBody
    @RequestMapping(value = "/search/albums",method = RequestMethod.GET)
    @Permission(scope = "search",name = "search:album")
    @SearchResultConfig
    @GetClassifyCopyrightConfig
    public AlbumList searchAlbums(
                    CommonQueryParam commonParam,
                    @Valid SearchQueryParam searchParam,
                    BindingResult bindingResult,
                    @RequestParam(value = "calc_dimension",defaultValue = "4") Integer calcDimension,
                    @RequestParam(value = "contains_paid",required = false,defaultValue = "false") Boolean containsPaid){

        long startTime1 = System.currentTimeMillis();

        freeAudioApiPermissionCheckAspect.checkIsHavePermission(commonParam);

        if (bindingResult.hasErrors()){
            throw new OpenAPIRequestParamValidateException(bindingResult.getFieldError().getDefaultMessage());
        }
        ValidateUtil.validateSearchCalcDimension(calcDimension);
        Long category_id = searchParam.getCategory_id();

        //---------------------------------------------------------------
        // private String q;                // 查询关键词
        //        private Long   category_id;      // 分类
        //        private Long   radio_category_id;// 直播分类id

        if (isGoV2Search(searchParam, commonParam.getAppKey())){
            //is_paid (是否付费 1-付费 0-免费 不传的话查所有)
            Integer isPaid = BooleanUtils.isTrue(containsPaid) ? null : 0;

            //           calc_dimension (1-热门推荐，2-最新，3-最多播放)
            return v2SearchAlbumsHandler.handle(
                                                commonParam,
                                                null, //  id,
                                                searchParam.getQ(), //  title,
                                                null, //  uid,
                                                null, //  nickname,
                                                null, //  tags,
                                                isPaid, //  isPaid,
                                                null, //  priceType,
                                                category_id, //  categoryId,
                                                null, // categoryName,

                                                ObjectUtil.equalsAny(calcDimension, 4, 3) ? "play_count" : "created_at", // sortBy,
                                                true, // desc,
                                                startTime1, // startTime1,
                                                commonParam.getCount(), // count,
                                                commonParam.getPage()// page
            );
        }

        //---------------------------------------------------------------
        return handleSearch(commonParam, searchParam, calcDimension, containsPaid, category_id);
    }

    private AlbumList handleSearch(
                    CommonQueryParam commonParam,
                    SearchQueryParam searchParam,
                    Integer calcDimension,
                    Boolean containsPaid,
                    Long category_id){

        if (!categoryBizService1.isCanOutputData(commonParam.getAppID(), toLong(category_id))){
            log.warn(autoLog("searchAlbumsCanNotOutputData,categoryId:[{}] ", category_id));
            return new AlbumList();
        }

        //---------------------------------------------------------------

        Long categoryID = ValidateUtil.validateAndGetCategoryID(category_id);
        if (CategoryUtil.isVirtualCategory(categoryID)){
            categoryID = -1L;
        }

        AlbumList result = newSearchBizService.searchAlbumList(
                                                               searchParam.getQ(),
                                                               categoryID,
                                                               calcDimension,
                                                               new PagingRequestParam(commonParam.getCount(), commonParam.getPage()),
                                                               containsPaid,
                                                               commonParam.getAppKey());
        // 对搜索结果进行重排序
        if (SearchCalcDimensionType.hot.getCalcDimension() == calcDimension
                        || SearchCalcDimensionType.personal.getCalcDimension() == calcDimension){
            // 只对特定的合作方应用进行重排序
            if (StringUtils.contains(searchAlbumRerankConfig.getAppId(), String.valueOf(ThreadLocals.getAppId()))){
                // uid必须大于0或者deviceId不能为空
                if (commonParam.getUid() > 0 || StringUtils.isNotBlank(commonParam.getDeviceID())){
                    newSearchBizService.rerankSearchResult(
                                                           result,
                                                           commonParam.getUid(),
                                                           commonParam.getClientOsType(),
                                                           commonParam.getDeviceID(),
                                                           configName);
                }
            }
        }
        CoverUtil.updateCoverPath2HTTPS(result.getAlbums());
        return result;
    }

    /**
     * 是否走v2 search
     */
    private boolean isGoV2Search(SearchQueryParam searchParam,String appkey){
        //如果有radio 分类, 那么不走v2
        if (null != searchParam.getRadio_category_id()){
            return false;
        }
        //---------------------------------------------------------------
        String oldSearchAlbumsApiGoV2Appkeys = searchV2Config.getOldSearchAlbumsApiGoV2Appkeys();
        if (StringUtil.tokenizeToArrayContains(oldSearchAlbumsApiGoV2Appkeys, appkey)){
            //如果在配置中, 那么走v2
            log.info(autoLog("inOldSearchAlbumsApiGoV2AppkeysGoV2True"));
            return true;
        }
        //默认不走v2
        return false;
    }
}
