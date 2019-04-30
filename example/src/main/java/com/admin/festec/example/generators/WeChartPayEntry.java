package com.admin.festec.example.generators;

import com.diabin.latte.annotations.EntryGenerator;
import com.diabin.latte.annotations.PayEntryGenerator;
import com.diabin.latte.wechat.templates.WXEntryTemplate;
import com.diabin.latte.wechat.templates.WXPayEntryTemplate;

/**
 * Copyright (C)
 *
 * @file: WeChartPayEntry
 * @author: 345
 * @Time: 2019/4/24 19:41
 * @description: ${DESCRIPTION}
 */
@PayEntryGenerator(
        packageName = "com.admin.festec.example",
        payEntryGenerator = WXPayEntryTemplate.class
)
public interface WeChartPayEntry {
}
