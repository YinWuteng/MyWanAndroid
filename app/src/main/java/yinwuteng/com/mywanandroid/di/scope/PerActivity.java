package yinwuteng.com.mywanandroid.di.scope;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


import javax.inject.Scope;

/**
 * Created by yinwuteng on 2018/3/10.
 * 自定义PerActivity接口
 */
@Scope
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface PerActivity {
}
