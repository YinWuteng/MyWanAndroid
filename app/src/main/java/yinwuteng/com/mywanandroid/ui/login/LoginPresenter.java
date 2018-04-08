package yinwuteng.com.mywanandroid.ui.login;

import io.reactivex.functions.Consumer;
import yinwuteng.com.mywanandroid.base.BasePresenter;
import yinwuteng.com.mywanandroid.bean.DataResponse;
import yinwuteng.com.mywanandroid.bean.User;
import yinwuteng.com.mywanandroid.net.ApiService;
import yinwuteng.com.mywanandroid.net.RetrofitManager;
import yinwuteng.com.mywanandroid.utils.RxSchedulers;

/**
 * Created by yinwuteng on 2018/4/5.
 * 登录控制器
 */

public class LoginPresenter extends BasePresenter<LoginView>{
    public LoginPresenter(){

    }

    public void login(String username,String password){
        RetrofitManager.create(ApiService.class)
                .login(username, password)
                .compose(RxSchedulers.<DataResponse<User>>applySchedulers())
                .subscribe(new Consumer<DataResponse<User>>() {
                    @Override
                    public void accept(DataResponse<User> response) throws Exception {
                        if (response.getErrorCode() == 0) {
                            getView().loginSuccess(response.getData());
                            getView().onSuccess("登录成功");
                        } else {
                            getView().showFailed(String.valueOf(response.getErrorMsg()));
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        getView().showFailed(throwable.getMessage());
                    }
                });
    }
}
