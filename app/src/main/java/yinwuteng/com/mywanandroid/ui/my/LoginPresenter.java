package yinwuteng.com.mywanandroid.ui.my;


import javax.inject.Inject;


import io.reactivex.functions.Consumer;
import yinwuteng.com.mywanandroid.base.BasePresenter;
import yinwuteng.com.mywanandroid.bean.DataResponse;
import yinwuteng.com.mywanandroid.bean.User;
import yinwuteng.com.mywanandroid.net.ApiService;

import yinwuteng.com.mywanandroid.net.RetrofitManger;
import yinwuteng.com.mywanandroid.utils.RxSchedulers;

/**
 * Created by yinwuteng on 2018/3/11.
 * 登录控制器
 */

public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter {
    @Inject
    public LoginPresenter() {

    }

    @Override
    public void login(String userName, String password) {
        RetrofitManger.create(ApiService.class)
                .login(userName, password)
                .compose(RxSchedulers.<DataResponse<User>>applySchedulers())
                .compose(mView.<DataResponse<User>>bindToLife()) //绑定视图生命周期
                .subscribe(new Consumer<DataResponse<User>>() {
                    @Override
                    public void accept(DataResponse<User> response) throws Exception {
                        if (response.getErrorCode() == 0) {
                            mView.loginSuccess(response.getData());
                        } else {
                            mView.showFailed(String.valueOf(response.getErrorMsg()));
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.showFailed(throwable.getMessage());
                    }
                });

    }
}
