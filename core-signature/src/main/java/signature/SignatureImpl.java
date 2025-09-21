package signature;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.ximalaya.openplatform.appservice.thrift.iface.App;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import signature.entity.BaseSignatureRequest;
import signature.kit.SignatureKit;
import signature.kit.SignatureRuntimeException;
import signature.service.AppServiceClient;
import signature.service.FootballStressTestingConfig;

import java.time.Instant;
import java.util.Arrays;

import static com.feilong.context.log.RequestLogHelper.autoRequestInfo;
import static signature.kit.HttpResponseStatusCode.HTTP_STATUS_BAD_REQUEST;

/**

 */
@Slf4j
@Aspect
@Order(-1)
@Component
public class SignatureImpl{

    @Autowired
    private AppServiceClient appServiceClient;

    @Autowired
    private FootballStressTestingConfig footballStressTestingConfig;

    //---------------------------------------------------------------

    @Around("@annotation(signatureAop)")
    public Object signature(ProceedingJoinPoint pjp,SignatureAop signatureAop) throws Throwable{
        long now = Instant.now().toEpochMilli();
        Object[] args = pjp.getArgs();
        String className = pjp.getTarget().getClass().getName();
        String methodName = pjp.getSignature().getName();
        String inParam = Arrays.toString(args);

        //---------------------------------------------------------------
        BaseSignatureRequest request = null;
        Object outParam = null;

        String commonLog = String.format("className:%s,methodName:%s,inParam:%s", className, methodName, inParam);

        try{
            // 构建基本请求参数
            request = new BaseSignatureRequest(Lists.newArrayList(args));
            App app = appServiceClient.getAppByAppKey(request.getAppKey());
            if (app != null && StringUtils.isNotBlank(app.getAppSecret())){
                String appKey = app.getAppKey();
                if (!footballStressTestingConfig.isStressTestingAppKey(appKey)){
                    SignatureKit.checkSig(request, app, now);
                }
                outParam = pjp.proceed();
            }else{
                log.error(autoRequestInfo("appService.getByAppKey 信息缺失 , {}, app {}", commonLog, app));
                throw new SignatureRuntimeException(HTTP_STATUS_BAD_REQUEST, "未查询到appKey对应的开发者信息,请联系喜马技术");
            }

            //---------------------------------------------------------------

            long last = Instant.now().toEpochMilli() - now;
            log.info(
                            autoRequestInfo(
                                            "commonLog:[{}] | 耗时:{}  request:{} ---> 返回:[{}]",
                                            commonLog,
                                            last,
                                            JSON.toJSONString(request),
                                            outParam));

            return outParam;
        }catch (Throwable e){
            log.warn(autoRequestInfo("commonLog:[{}] | request:[{}] ", commonLog, JSON.toJSONString(request)), e);
            throw e;
        }
    }

}
