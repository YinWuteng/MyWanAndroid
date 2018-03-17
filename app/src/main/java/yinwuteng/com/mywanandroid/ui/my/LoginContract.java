package yinwuteng.com.mywanandroid.ui.my;

import yinwuteng.com.mywanandroid.base.BaseContTract;
import yinwuteng.com.mywanandroid.bean.User;

/**
 * Created by yinwuteng on 2018/3/11.
 * LoginContract
 */

public interface LoginContract {
    interface View extends BaseContTract.BaseView {
        void loginSuccess(User user);
    }

    interface Presenter extends BaseContTract.BasePresenter<LoginContract.View> {
        void login(String userName, String password);
    }
}
