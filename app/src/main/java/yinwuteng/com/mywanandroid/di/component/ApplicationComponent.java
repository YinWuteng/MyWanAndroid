package yinwuteng.com.mywanandroid.di.component;

import android.content.Context;

import dagger.Component;

import yinwuteng.com.mywanandroid.di.module.ApplicationModule;
import yinwuteng.com.mywanandroid.di.scope.ContextLife;
import yinwuteng.com.mywanandroid.di.scope.PerApp;

/**
 * Created by yinwuteng on 2018/3/10.
 * Application连接器
 */
@PerApp
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    @ContextLife("Application")
    Context getApplication();
}
