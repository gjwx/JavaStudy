package signature.service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.ximalaya.openplatform.appservice.thrift.iface.AppService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import signature.kit.SignatureRuntimeException;
import com.ximalaya.openplatform.appservice.thrift.iface.App;
import javax.annotation.Resource;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import com.ximalaya.openplatform.appservice.thrift.iface.AppState;
import static com.feilong.context.log.RequestLogHelper.autoRequestInfo;

/**
 */
@Slf4j
@Component
public class AppServiceClient{

    @Resource(name = "appService")
    private AppService.Iface                appService;

    //---------------------------------------------------------------

    private static final Cache<String, App> APP_LOCAL_CACHE = CacheBuilder.newBuilder()   //
                    .maximumSize(4000)                                                    //
                    .expireAfterWrite(300, TimeUnit.SECONDS)                              //
                    .build();

    //---------------------------------------------------------------

    public App getAppByAppKey(String appKey){
        App app = APP_LOCAL_CACHE.getIfPresent(appKey);
        if (app == null){
            app = doGetAppByAppKey(appKey);
            // 空值也会存
            APP_LOCAL_CACHE.put(appKey, app);
        }

        //---------------------------------------------------------------
        if (app == null){
            log.error(autoRequestInfo("appIsNull"));
            throw new SignatureRuntimeException(400, "your app is not active");
        }

        if (Objects.equals(app.getId(), 0)){
            log.error(autoRequestInfo("appId0"));
            throw new SignatureRuntimeException(400, "your app is not active!");
        }

        if (app.getState() != AppState.ACTIVE_NORMALLY){
            log.error(autoRequestInfo("appStatusNotActiveNormally"));
            throw new SignatureRuntimeException(400, "your app state is not active");
        }
        return app;
    }

    //---------------------------------------------------------------

    private App doGetAppByAppKey(String appKey){
        try{
            return appService.getByAppKey(appKey);
        }catch (Exception e){
            log.error(autoRequestInfo("appService.getByAppKey error {}", appKey), e);
            throw new SignatureRuntimeException(400, "query app exception");
        }
    }
}
