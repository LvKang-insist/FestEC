package com.diabin.latte.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Copyright (C)
 *
 * @file: PayEntryGenerator
 * @author: 345
 * @Time: 2019/4/24 13:07
 * @description: ${DESCRIPTION}
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface PayEntryGenerator {
    String packageName();
    Class<?> payEntryGenerator();
}
