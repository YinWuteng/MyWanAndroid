package yinwuteng.com.mywanandroid.ui.login;

import yinwuteng.com.mywanandroid.bean.User;

/**
 * Created by yinwuteng on 2018/3/18.
 * LoginView
 */

 interface LoginView {
    /**
     * 登录成功
     * @param user
     */
    void loginSuccess(User user);

    /**
     * 登录失败
     * @param msg
     */
    void loginFailed(String msg);
}
