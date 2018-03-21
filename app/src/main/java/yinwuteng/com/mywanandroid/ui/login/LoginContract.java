package yinwuteng.com.mywanandroid.ui.login;

import yinwuteng.com.mywanandroid.base.BaseContract;
import yinwuteng.com.mywanandroid.bean.User;

/**
 * Created by yinwuteng on 2018/3/21.
 * 登录连接器
 */

public interface LoginContract {
    interface View extends BaseContract.BaseView {
        void loginSuccess(User user);
    }

    interface Presenter extends BaseContract.BasePresenter<LoginContract.View> {
        void login(String username, String password);
    }
}
