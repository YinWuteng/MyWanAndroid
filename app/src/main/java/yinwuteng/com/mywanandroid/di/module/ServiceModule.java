package yinwuteng.com.mywanandroid.di.module;

import android.app.Service;
import android.content.Context;

import dagger.Module;
import dagger.Provides;
import yinwuteng.com.mywanandroid.di.scope.ContextLife;
import yinwuteng.com.mywanandroid.di.scope.PerService;

/**
 * Created by yinwuteng on 2018/3/10.
 * ServiceModule
 */
@Module
public class ServiceModule {
    private Service mService;

    public ServiceModule(Service service) {
        mService = service;
    }

    @Provides
    @PerService
    @ContextLife("Service")
    public Context ProvideServiceContext() {
        return mService;
    }
}
