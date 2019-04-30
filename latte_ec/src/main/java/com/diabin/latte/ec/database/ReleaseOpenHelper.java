package com.diabin.latte.ec.database;

import android.content.Context;

import com.diabin.latte.ec.mytable.DaoMaster;

/**
 * Copyright (C)
 *
 * @file: ReleaseOpenHelper
 * @author: 345
 * @Time: 2019/4/22 19:33
 * @description: 简单的数据库封装
 */
public class ReleaseOpenHelper extends DaoMaster.OpenHelper {

    public ReleaseOpenHelper(Context context, String name) {
        super(context, name);
    }
}
