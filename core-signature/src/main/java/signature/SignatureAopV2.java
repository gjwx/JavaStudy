package signature;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SignatureAopV2 {

    String value() default "";

    /**
     * 自定义日志是否打印
     */
    ParamPrintOption paramPrint() default ParamPrintOption.UNCONFIG;

    enum ParamPrintOption {

        /**
         * 未配置
         */
        UNCONFIG("UNCONFIG", "未配置"),

        /**
         * 打印
         */
        PRINT("PRINT", "打印"),

        /**
         * 忽略打印
         */
        IGNORE("IGNORE", "忽略打印");

        /**
         * 代码
         */
        private String code;

        /**
         * desc
         */
        private String desc;

        ParamPrintOption(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }

    }
}
