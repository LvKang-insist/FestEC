package com.admin.festec.example.generators;

import com.diabin.latte.annotations.AppRegisterGenerator;
import com.diabin.latte.wechat.templates.AppRegisterTemplate;

/**
 * Copyright (C)
 *
 * @file: AppRegisiter
 * @author: 345
 * @Time: 2019/4/24 19:42
 * @description: ${DESCRIPTION}
 */
@AppRegisterGenerator(
        packageName = "com.admin.festec.example",
        registerTemplate = AppRegisterTemplate.class
)
public interface AppRegisiter {
}
