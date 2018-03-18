package yinwuteng.com.mywanandroid.ui.login;

import io.reactivex.functions.Consumer;
import yinwuteng.com.mywanandroid.bean.DataResponse;
import yinwuteng.com.mywanandroid.bean.User;
import yinwuteng.com.mywanandroid.net.ApiService;
import yinwuteng.com.mywanandroid.net.RetrofitManager;
import yinwuteng.com.mywanandroid.utils.RxSchedulers;

/**
 * Created by yinwuteng on 2018/3/18.
 */

public class LoginPresent {
    private LoginView loginView;

    public LoginPresent(LoginView view) {
        this.loginView = view;
    }

    public void login(String username, String password) {
        RetrofitManager.create(ApiService.class).login(username, password).compose(RxSchedulers.<DataResponse<User>>applySchedulers()).subscribe(new Consumer<DataResponse<User>>() {
            @Override
            public void accept(DataResponse<User> response) throws Exception {
                if (response.getErrorCode() == 0) {
                    loginView.loginSuccess(response.getData());
                } else {
                    loginView.loginFailed(String.valueOf(response.getErrorMsg()));
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                loginView.loginFailed(throwable.getMessage());
            }
        });

    }
}
