package yinwuteng.com.mywanandroid.base;

import android.app.Application;
import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.Utils;
import com.raizlabs.android.dbflow.config.FlowManager;

import yinwuteng.com.mywanandroid.BuildConfig;
import yinwuteng.com.mywanandroid.di.component.ApplicationComponent;
import yinwuteng.com.mywanandroid.di.component.DaggerActivityComponent;
import yinwuteng.com.mywanandroid.di.component.DaggerApplicationComponent;
import yinwuteng.com.mywanandroid.di.module.ApplicationModule;

/**
 * Created by yinwuteng on 2018/3/10.
 * MyApplication
 */

public class MyApplication extends Application {
    private ApplicationComponent mApplicationComponent;
    private static MyApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        initApplicationComponent();
        //初始化utilCode工具类
        Utils.init(this);
        //注解数据库初始化
        FlowManager.init(this);
    }

    /**
     * 初始化路由
     */
    private void ininARouter() {
        if (BuildConfig.DEBUG) { //这两行必须卸载init之前，否则这些配置在init过程将无效
            ARouter.openLog(); //打印日志
            ARouter.openDebug(); //开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this); //
    }

    /**
     * 初始化ApplicationComponent
     */
    private void initApplicationComponent() {
        mApplicationComponent = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();
    }

    public ApplicationComponent getmApplicationComponent() {
        return mApplicationComponent;
    }

    public static Context getAppContext() {
        return mInstance.getApplicationContext();
    }

    public static MyApplication getInstance() {
        return mInstance;
    }
}
