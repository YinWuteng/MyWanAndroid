package yinwuteng.com.mywanandroid.di.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import yinwuteng.com.mywanandroid.base.MyApplication;
import yinwuteng.com.mywanandroid.di.scope.ContextLife;
import yinwuteng.com.mywanandroid.di.scope.PerApp;

/**
 * Created by yinwuteng on 2018/3/10.
 * ApplicationModule
 */
@Module
public class ApplicationModule {
    private MyApplication myApplication;

    public ApplicationModule(MyApplication application) {
        myApplication = application;
    }

    @Provides
    @PerApp
    @ContextLife("Application")
    public Context provideApplicationContext() {
        return myApplication.getApplicationContext();
    }
}
