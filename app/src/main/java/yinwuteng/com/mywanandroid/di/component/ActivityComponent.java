package yinwuteng.com.mywanandroid.di.component;

import android.app.Activity;
import android.content.Context;

import dagger.Component;
import yinwuteng.com.mywanandroid.di.module.ActivityModule;
import yinwuteng.com.mywanandroid.di.scope.ContextLife;
import yinwuteng.com.mywanandroid.di.scope.PerActivity;
import yinwuteng.com.mywanandroid.ui.my.LoginActivity;

/**
 * Created by yinwuteng on 2018/3/10.
 * activity连接器
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    @ContextLife("Activity")
    Context getActivityContext();

    @ContextLife("Application")
    Context getApplicationContext();

    Activity getActivity();

    void inject(LoginActivity activity);

}
