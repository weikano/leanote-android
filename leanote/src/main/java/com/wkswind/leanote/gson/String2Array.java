package com.wkswind.leanote.gson;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标志这个字段转JSON时要输出成Array，从JSON解析时要转成String，并用{@link String2Array.delimiter}来分割
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface String2Array {
    String delimiter();
}
