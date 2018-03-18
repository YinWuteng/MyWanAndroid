package yinwuteng.com.mywanandroid.ui.login;


import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;


import com.blankj.utilcode.util.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;
import yinwuteng.com.mywanandroid.R;
import yinwuteng.com.mywanandroid.base.BaseActivity;
import yinwuteng.com.mywanandroid.bean.User;


/**
 * Created by yinwuteng on 2018/3/18.
 * 登录activity
 */

public class LoginActivity extends BaseActivity implements LoginView {
    private LoginPresent present = new LoginPresent(this);

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
            present.login(username, password);
        }
    }

    @Override
    public void loginSuccess(User user) {
        ToastUtils.showShort("登录成功");
    }

    @Override
    public void loginFailed(String msg) {
        ToastUtils.showShort("登录失败");
    }
}
