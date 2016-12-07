package com.wkswind.leanote.gson;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * JSON串中的字符串时间转为long
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface UTC2Long {
    /**
     * 默认处理时间格式为iso8601
     * @return
     */
    String dateFormat() default "yyyy-MM-dd'T'hh:mm:ssZZZZZ";
}
