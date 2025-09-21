package signature.entity;

import com.alibaba.fastjson.JSON;
import com.feilong.json.JsonUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import signature.kit.HttpResponseStatusCode;
import signature.kit.SignatureRuntimeException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.util.*;

import static com.feilong.context.log.RequestLogHelper.autoRequestInfo;
import static signature.kit.HttpResponseStatusCode.HTTP_STATUS_SERVER_ERROR;

/**
 */
@Slf4j
@Data
public class BaseSignatureRequest{

    private String appKey;

    private long   timestamp;

    /**
     * 按照字典顺序拼接的 k v 字符串
     */
    private String requestParameterKeyAndValue;

    private String sig;

    private BaseSignatureRequest(){
    }

    //---------------------------------------------------------------

    public BaseSignatureRequest(List<Object> requestList){
        HttpServletRequest httpServletRequest = null;

        List<Object> bizRequest = Lists.newArrayList();
        for (Object arg : requestList){
            if (arg instanceof HttpServletResponse){
                continue;
            }
            if (arg instanceof HttpServletRequest){
                httpServletRequest = (HttpServletRequest) arg;
                continue;
            }
            bizRequest.add(arg);
        }
        if (httpServletRequest == null){
            throw new SignatureRuntimeException(HTTP_STATUS_SERVER_ERROR, "服务端异常,缺少httpServletRequest参数,请检查");
        }

        //---------------------------------------------------------------
        Map<String, Object> paramsMap = Maps.newHashMap();
        // get请求或者post表单请求通过遍历httpServletRequest来完成字段的填充
        paramsMap.putAll(getRequestParamsMap(httpServletRequest));
        if ((httpServletRequest.getMethod().contains("POST") || httpServletRequest.getMethod().contains("post"))){
            String contentType = httpServletRequest.getContentType();
            //                log.debug("contentType :{} ", contentType);
            if (StringUtils.isNotBlank(contentType) && (contentType.contains("json") || contentType.contains("JSON"))){
                // post通过JSON方式请求，通过反射遍历入参对象的每个字段，填充在paramsMap里面
                buildParamsMap(paramsMap, bizRequest);
            }
        }

        //---------------------------------------------------------------
        if (paramsMap.isEmpty()){
            throw new SignatureRuntimeException(HttpResponseStatusCode.HTTP_STATUS_BAD_REQUEST, "参数为空");
        }

        //---------------------------------------------------------------
        Object paramSigValue = paramsMap.get("sig");
        Object paramTimestampValue = paramsMap.get("timestamp");
        if (paramSigValue == null && paramTimestampValue == null){
            throw new SignatureRuntimeException(HTTP_STATUS_SERVER_ERROR, "验签参数缺失");
        }

        //---------------------------------------------------------------
        try{
            Object object = paramTimestampValue;
            if (object instanceof Long){
                timestamp = (Long) object;
            }else{
                timestamp = Long.parseLong(String.valueOf(object));
            }
            sig = (String) paramSigValue;
            appKey = (String) paramsMap.get("app_key");
        }catch (Exception e){
            log.error(autoRequestInfo("验签参数解析失败 {}", JsonUtil.toString(paramsMap)), e);
            throw new SignatureRuntimeException(HttpResponseStatusCode.HTTP_STATUS_BAD_REQUEST, "验签参数解析失败");
        }
        requestParameterKeyAndValue = buildRequestString(paramsMap);
    }

    //---------------------------------------------------------------

    private static Map<String, String> getRequestParamsMap(HttpServletRequest request){
        Map<String, String> requestParamMap = Maps.newHashMap();
        Enumeration<String> requestParamName = request.getParameterNames();
        while (requestParamName.hasMoreElements()){
            String paramName = requestParamName.nextElement();
            requestParamMap.put(paramName, request.getParameter(paramName));
        }
        return requestParamMap;
    }

    private static String buildRequestString(Map<String, Object> paramsMap){
        TreeMap<String, String> paramsTreeMap = new TreeMap<>();
        // 清除不参与运算的字段
        paramsMap.forEach((k,v) -> {
            if ("sig".equals(k)){
                return;
            }
            if (v == null || v instanceof Collection || v.getClass().isArray()){
                paramsTreeMap.put(k, "");
            }else{
                paramsTreeMap.put(k, String.valueOf(v));
            }
        });
        // 空和集合val全是按照空字符串来参与运算
        StringBuilder keyAndValue = new StringBuilder();
        paramsTreeMap.forEach((k,v) -> keyAndValue.append(k).append("=").append(v).append("&"));
        return keyAndValue.deleteCharAt(keyAndValue.length() - 1).toString();
    }

    //---------------------------------------------------------------

    private void buildParamsMap(Map<String, Object> paramsMap,List<Object> bizRequest){
        if (bizRequest == null || bizRequest.isEmpty()){
            return;
        }

        //---------------------------------------------------------------
        for (Object arg : bizRequest){
            List<Field> declaredFieldList = Lists.newArrayList();
            declaredFieldList.addAll(Arrays.asList((arg.getClass().getDeclaredFields())));
            // post请求body如果继承 BasePostParam 那么需要遍历这里面字段
            if (BasePostParam.class.equals(arg.getClass().getSuperclass())){
                declaredFieldList.addAll(Arrays.asList(arg.getClass().getSuperclass().getDeclaredFields()));
            }
            for (Field field : declaredFieldList){
                field.setAccessible(true);
                try{
                    Object val = field.get(arg);
                    //                    log.debug("build {} {} {}", field.getGenericType().getTypeName(), field.getName(), val);
                    checkFieldType(field.getGenericType().getTypeName());
                    paramsMap.put(field.getName(), val);
                }catch (IllegalAccessException e){
                    log.error(autoRequestInfo("入参解析解析失败 {} ", field), e);
                }
            }
        }
    }

    private void checkFieldType(String fieldTypeName){
        List<String> typeNameList = Lists.newArrayList();
        typeNameList.add("java.lang.String");
        typeNameList.add("java.lang.Integer");
        typeNameList.add("java.lang.Long");
        typeNameList.add("java.lang.Boolean");
        typeNameList.add("java.lang.Byte");
        typeNameList.add("java.lang.Double");
        typeNameList.add("java.lang.Float");
        typeNameList.add("java.lang.Short");
        typeNameList.add("java.lang.Short");
        typeNameList.add("int");
        typeNameList.add("long");
        typeNameList.add("boolean");
        typeNameList.add("byte");
        typeNameList.add("double");
        typeNameList.add("float");
        typeNameList.add("short");
        typeNameList.add("char");
        typeNameList.add("int[]");
        typeNameList.add("long[]");
        typeNameList.add("boolean[]");
        typeNameList.add("byte[]");
        typeNameList.add("double[]");
        typeNameList.add("float[]");
        typeNameList.add("short[]");
        typeNameList.add("char[]");
        if (!typeNameList.contains(fieldTypeName) && !fieldTypeName.contains("java.util.List")){
            log.error(
                            autoRequestInfo(
                                            "验签目前只支持下列数据类型 fieldTypeName:[{}] java.util.List,{}",
                                            fieldTypeName,
                                            JSON.toJSONString(typeNameList)));
            throw new SignatureRuntimeException(HTTP_STATUS_SERVER_ERROR, "系统异常");
        }
    }

}
