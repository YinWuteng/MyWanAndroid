package yinwuteng.com.mywanandroid.di.scope;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Created by yinwuteng on 2018/3/10.
 * 自定义ContextLife注解接口
 */
//注解标示
@Qualifier
//表明这个注解应该被javadoc工具记录
@Documented
//标示注解将被jvm保留
@Retention(RetentionPolicy.RUNTIME)
public @interface ContextLife {
    String value() default "Application";
}
