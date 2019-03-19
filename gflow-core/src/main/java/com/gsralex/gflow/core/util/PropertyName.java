package com.gsralex.gflow.core.util;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author gsralex
 * @date 2018/2/20
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface PropertyName {
    String name() default "";
}
