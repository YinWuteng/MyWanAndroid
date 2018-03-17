package yinwuteng.com.mywanandroid.di.component;

import android.app.Activity;
import android.content.Context;

import dagger.Component;
import yinwuteng.com.mywanandroid.di.module.FragmentModule;
import yinwuteng.com.mywanandroid.di.scope.ContextLife;
import yinwuteng.com.mywanandroid.di.scope.PerFragment;

/**
 * Created by yinwuteng on 2018/3/10.
 * FragmentComponent
 */

@PerFragment
@Component(dependencies = ApplicationComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {
    @ContextLife("Activity")
    Context getActivityContext();

    @ContextLife("Application")
    Context getApplicationContext();

    Activity getActivity();
}
