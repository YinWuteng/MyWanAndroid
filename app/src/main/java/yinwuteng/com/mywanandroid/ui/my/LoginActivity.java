package yinwuteng.com.mywanandroid.ui.my;

import android.support.design.widget.TextInputEditText;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;
import yinwuteng.com.mywanandroid.R;
import yinwuteng.com.mywanandroid.base.BaseActivity;
import yinwuteng.com.mywanandroid.bean.User;
import yinwuteng.com.mywanandroid.constant.Constant;
import yinwuteng.com.mywanandroid.utils.RxBus;

/**
 * Created by yinwuteng on 2018/3/10.
 * 登录界面
 */
@Route(path = "/my/loginActivity")
public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {
    @BindView(R.id.etUsername)
    TextInputEditText mEtuUsername;
    @BindView(R.id.etPassword)
    TextInputEditText mEtPassword;

    /**
     * 获取布局
     *
     * @return
     */
    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    /**
     * 初始注解
     */
    @Override
    protected void initInjector() {
        mActivityComponent.inject(this);
    }

    /**
     * 初始布局
     */
    @Override
    protected void initView() {
        mEtuUsername.setText(SPUtils.getInstance(Constant.SHARED_NAME).getString(Constant.USERNAME_KEY));
        mEtPassword.setText(SPUtils.getInstance(Constant.SHARED_NAME).getString(Constant.PASSWORD_KEY));
    }

    /**
     * 显示返回键
     *
     * @return
     */
    @Override
    protected boolean showHomeAcUp() {
        return true;
    }

    @OnClick(R.id.btnLogin)
    public void login() {
        String username = mEtuUsername.getText().toString();
        String password = mEtPassword.getText().toString();
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            ToastUtils.showShort("用户名或密码不能为空");
            return;
        }
        if (mPresenter != null) {
            mPresenter.login(username, password);
        }
    }

    /**
     * 登录成功后保存数据
     *
     * @param user
     */
    @Override
    public void loginSuccess(User user) {
        SPUtils.getInstance(Constant.SHARED_NAME).put(Constant.LOGIN_KEY, true);
        SPUtils.getInstance(Constant.SHARED_NAME).put(Constant.USERNAME_KEY, user.getUsername());
        SPUtils.getInstance(Constant.SHARED_NAME).put(Constant.USERNAME_KEY, user.getPassword());
        //登录成功后通知其他界面刷新
        RxBus.getxRxBus().post(new LoginEvent());
        this.finish();
    }

    public static void start() {
        ARouter.getInstance().build("/my/LoginActivity").navigation();

    }
}
