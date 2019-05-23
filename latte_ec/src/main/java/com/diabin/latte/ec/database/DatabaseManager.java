package com.diabin.latte.ec.database;

import android.content.Context;


import com.diabin.latte.ec.mytable.DaoMaster;
import com.diabin.latte.ec.mytable.DaoSession;

import org.greenrobot.greendao.database.Database;

/**
 * Copyright (C)
 *
 * @file: DatabaseManager
 * @author: 345
 * @Time: 2019/4/22 19:34
 * @description:
 */
public class DatabaseManager {

    private DaoSession mDaoSession = null;
    private DatabaseManager(){}

    public DatabaseManager init(Context context){
        initDao(context);
        return this;
    }
    /**
     * 内部类 的单例模式
     */
    private static final class Holder{
        private static final DatabaseManager INSTANCE = new DatabaseManager();
    }

    /**
     * @return 返回实例
     */
    public static DatabaseManager getInstance(){
        return Holder.INSTANCE;
    }

    /**
     * 初始化，并创建表
     * @param context
     */
    private void initDao(Context context){
        //自定义的类：ReleaseOpenHelper 继承自 DaoMaster.OpenHelper
        //通过 DaoMaster的内部类 可以得到 SQLiteOpenHelper 的对象
        // 第二个参数为 数据库的名称
        final ReleaseOpenHelper helper = new ReleaseOpenHelper(context,"fast_ec.db");
        //获取可读写 数据库
        final Database db =helper.getWritableDb();
        //拿到 DaoSession 对象
        mDaoSession = new DaoMaster(db).newSession();
    }
    public final DaoSession getDao(){
        return mDaoSession;
    }
}
