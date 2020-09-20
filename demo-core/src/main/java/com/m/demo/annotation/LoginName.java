package com.m.demo.annotation;

import java.lang.annotation.*;

/**
 * author:M
 * describe:
 * date:2020/9/20 16:40
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginName {
}
