package com.admin.festec.example.generators;

import com.diabin.latte.annotations.EntryGenerator;
import com.diabin.latte.wechat.templates.WXEntryTemplate;

/**
 * Copyright (C)
 *
 * @file: WeChatEntry
 * @author: 345
 * @Time: 2019/4/24 19:41
 * @description: ${DESCRIPTION}
 */
@EntryGenerator(
        packageName = "com.admin.festec.example",
        entryTemplate = WXEntryTemplate.class
)
public interface WeChatEntry {
}
