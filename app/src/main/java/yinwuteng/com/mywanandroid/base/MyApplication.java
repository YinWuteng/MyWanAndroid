package yinwuteng.com.mywanandroid.base;

import android.app.Application;
import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.Utils;
import com.raizlabs.android.dbflow.config.FlowManager;

import yinwuteng.com.mywanandroid.BuildConfig;


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
        FlowManager.init(this);
        Utils.init(this);
        initARouter();
    }

    /**
     * 初始化路由
     */
    private void initARouter() {
        if (BuildConfig.DEBUG) { // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openDebug();   // 打印日志
            ARouter.openLog();  // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }

        ARouter.init(this); // 尽可能早，推荐在Application中初始化
    }
}
