package com.m.demo.annotation;

import java.lang.annotation.*;

/**
 * author:M
 * describe:
 * date:2020/9/20 13:34
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface UserId {
}
