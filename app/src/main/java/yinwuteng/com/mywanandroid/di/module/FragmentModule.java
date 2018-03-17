package yinwuteng.com.mywanandroid.di.module;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import dagger.Module;
import dagger.Provides;
import yinwuteng.com.mywanandroid.di.scope.ContextLife;
import yinwuteng.com.mywanandroid.di.scope.PerFragment;

/**
 * Created by yinwuteng on 2018/3/10.
 * FragmentModule
 */
@Module
public class FragmentModule {
    private Fragment mFragment;

    public FragmentModule(Fragment fragment) {
        mFragment = fragment;
    }

    @Provides
    @PerFragment
    @ContextLife("Activity")
    public Context provideActivityContext() {
        return mFragment.getActivity();
    }

    @Provides
    @PerFragment
    public Activity provideActivity() {
        return mFragment.getActivity();
    }

    @Provides
    @PerFragment
    public Fragment providefragment() {
        return mFragment;
    }
}
