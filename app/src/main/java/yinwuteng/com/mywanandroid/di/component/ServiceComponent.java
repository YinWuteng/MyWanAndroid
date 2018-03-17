package yinwuteng.com.mywanandroid.di.component;

import android.content.Context;

import dagger.Component;
import yinwuteng.com.mywanandroid.di.module.ServiceModule;
import yinwuteng.com.mywanandroid.di.scope.ContextLife;
import yinwuteng.com.mywanandroid.di.scope.PerService;

/**
 * Created by yinwuteng on 2018/3/10.
 * ServiceComponent
 */
@PerService
@Component(dependencies = ApplicationComponent.class, modules = ServiceModule.class)
public interface ServiceComponent {
    @ContextLife("Service")
    Context getServiceContext();

    @ContextLife("Application")
    Context getApplicationContext();
}
