package yinwuteng.com.mywanandroid.ui.login;



import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;


import com.blankj.utilcode.util.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;
import yinwuteng.com.mywanandroid.R;
import yinwuteng.com.mywanandroid.base.BaseActivity;
import yinwuteng.com.mywanandroid.bean.DataResponse;
import yinwuteng.com.mywanandroid.bean.User;
import yinwuteng.com.mywanandroid.net.ApiService;
import yinwuteng.com.mywanandroid.net.RetrofitManager;
import yinwuteng.com.mywanandroid.utils.RxSchedulers;

/**
 * Created by yinwuteng on 2018/3/18.
 * 登录activity
 */

public class LoginActivity extends BaseActivity {


    @BindView(R.id.etUsername)
    TextInputEditText etUsername;
    @BindView(R.id.etPassword)
    TextInputEditText etPassword;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected boolean showHomeAsUp() {
        return true;
    }

    @OnClick(R.id.btnLogin)
    public void login() {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        if (!TextUtils.isEmpty(username) || !TextUtils.isEmpty(password)) {
            RetrofitManager.create(ApiService.class).login(username, password).compose(RxSchedulers.<DataResponse<User>>applySchedulers()).subscribe(new Consumer<DataResponse<User>>() {
                @Override
                public void accept(DataResponse<User> response) throws Exception {
                    if (response.getErrorCode() == 0) {
                        ToastUtils.showShort("登录成功");
                    } else {
                        ToastUtils.showShort("登录失败");
                    }
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) throws Exception {
                    ToastUtils.showShort("登录失败");
                }
            });
        }
    }
}
