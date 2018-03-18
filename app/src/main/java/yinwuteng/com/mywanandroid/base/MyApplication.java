package yinwuteng.com.mywanandroid.base;

import android.app.Application;
import android.content.Context;

import com.blankj.utilcode.util.Utils;


/**
 * Created by yinwuteng on 2018/3/18.
 * MyApplication
 */

public class MyApplication extends Application {
    private static MyApplication mInstance;

    public static MyApplication getInstance() {
        return mInstance;
    }

    public static Context getAppContext() {
        return mInstance.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        Utils.init(this);
    }
}
