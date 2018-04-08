package yinwuteng.com.mywanandroid.ui.login;

import yinwuteng.com.mywanandroid.base.BaseView;
import yinwuteng.com.mywanandroid.bean.User;

/**
 * Created by yinwuteng on 2018/4/5.
 * 登录接口
 */

public interface LoginView  extends BaseView{
    void loginSuccess(User user);
}
