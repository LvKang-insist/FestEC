package com.diabin.latte.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Copyright (C)
 *
 * @file:
 * @author: 345
 * @Time: 2019/4/24 13:09
 * @description: ${DESCRIPTION}
 */

@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface AppRegisterGenerator {
    String packageName();
    Class<?> registerTemplate();
}
