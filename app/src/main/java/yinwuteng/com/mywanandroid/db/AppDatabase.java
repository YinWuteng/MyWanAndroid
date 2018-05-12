package yinwuteng.com.mywanandroid.db;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Create By yinwuteng
 * 2018/5/12.
 * 数据库创建
 */
@Database(name =AppDatabase.NAME,version = AppDatabase.VERSION)
public class AppDatabase {
    public static final String NAME="MyWanAndroid-db";
    public static final int VERSION=1;
}
