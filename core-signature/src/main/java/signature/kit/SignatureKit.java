package signature.kit;

import com.alibaba.fastjson.JSON;
import com.feilong.spring.web.util.WebSpringUtil;
import com.ximalaya.openplatform.appservice.thrift.iface.App;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import signature.entity.BaseSignatureRequest;

import javax.servlet.http.HttpServletRequest;

import static com.feilong.context.log.RequestLogHelper.autoRequestInfo;

/**
 */
@Slf4j
public class SignatureKit{

    public static void checkSig(BaseSignatureRequest baseSignatureRequest, App app, long now){
        long requestTime = baseSignatureRequest.getTimestamp();

        // 请求时间误差在5分钟
        if (Math.abs(now - requestTime) > 1000 * 60 * 5L){
            throw new SignatureRuntimeException(400, "请求过期，请重新生成请求签名");
        }

        //---------------------------------------------------------------
        String sig;
        String requestJson = JSON.toJSONString(baseSignatureRequest);
        try{
            String requestParam = baseSignatureRequest.getRequestParameterKeyAndValue();
            String appSecret = app.getAppSecret();
            String serverAuthStaticKey = app.getServerAuthStaticKey();

            String paramsBase64EncodedStr = Base64Kit.encodeToString(requestParam);
            sig = CodecKit.hmacSHA1ToStr(paramsBase64EncodedStr, appSecret + serverAuthStaticKey);

        }catch (Exception e){
            log.error(autoRequestInfo("生成sig失败 baseSignatureRequest {}", requestJson), e);
            return;
        }
        //---------------------------------------------------------------

        if (StringUtils.isNotBlank(sig)){
            String requestSig = baseSignatureRequest.getSig();
            if (sig.equals(requestSig)){
                // do nothing
                //没必要输出成功的日志
                // log.info(autoRequestInfo("验签成功 baseSignatureRequest {}", requestJson));
                return;
            }

            HttpServletRequest request = WebSpringUtil.getRequest();
            Object contentType = request == null ? null : request.getContentType();
            String pattern = "签名验证失败,contentType:[{}] ourSign:[{}] requestSig:[{}] requestJson:{}";
            log.warn(autoRequestInfo(pattern, contentType, sig, requestSig, requestJson));

            throw new SignatureRuntimeException(101, "验签失败,非法请求");
        }

    }

}
