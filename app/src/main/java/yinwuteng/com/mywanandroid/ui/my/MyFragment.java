package yinwuteng.com.mywanandroid.ui.my;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.functions.Consumer;
import yinwuteng.com.mywanandroid.R;
import yinwuteng.com.mywanandroid.base.BaseFragment;
import yinwuteng.com.mywanandroid.base.BasePresenter;
import yinwuteng.com.mywanandroid.constant.Constant;
import yinwuteng.com.mywanandroid.net.CookiesManager;
import yinwuteng.com.mywanandroid.ui.login.LoginActivity;
import yinwuteng.com.mywanandroid.ui.login.LoginEvent;
import yinwuteng.com.mywanandroid.utils.RxBus;

/**
 * Created by yinwuteng on 2018/4/6.
 * 我的
 */

public class MyFragment extends BaseFragment implements View.OnClickListener {
    private CircleImageView mCivAvatar; //头像
    private TextView mTvNick; //名称
    private LinearLayout mLlLogout;
    private boolean mIsLogin; //是否登录

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initView(View view) {
        mCivAvatar = view.findViewById(R.id.civAvatar);
        mTvNick = view.findViewById(R.id.tvNick);
        mLlLogout = view.findViewById(R.id.llLogout);
        TextView mTvMyCollection = view.findViewById(R.id.tvMyCollection);
        TextView mMyBookMark = view.findViewById(R.id.tvMyBookmark);
        TextView mSetting = view.findViewById(R.id.tvSetting);

        mCivAvatar.setOnClickListener(this);

        mLlLogout.setOnClickListener(this);
        mTvMyCollection.setOnClickListener(this);
        mMyBookMark.setOnClickListener(this);
        mSetting.setOnClickListener(this);

        //设置用户登录
        setUserStatusInfo();

        /**登录成功重新设置新用户*/
        RxBus.getInstance().toFlowable(LoginEvent.class).subscribe(new Consumer<LoginEvent>() {
            @Override
            public void accept(LoginEvent loginEvent) throws Exception {
                setUserStatusInfo();
            }
        });

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.civAvatar://跳转登录
                if (!mIsLogin) LoginActivity.start();
                break;
            case R.id.tvMyCollection: //跳转我的收藏
                if (mIsLogin) {
                    ARouter.getInstance().build("/my/MyCollectionActivity").navigation();
                } else {
                    ToastUtils.showShort(R.string.not_login);
                }
                break;
            case R.id.tvMyBookmark://跳转我的书签
                if (mIsLogin) {
                    ARouter.getInstance().build("/my/MyBookmarkActivity").navigation();
                } else {
                    ToastUtils.showShort(R.string.not_login);
                }
                break;
            case R.id.tvSetting: //我的设置
                ARouter.getInstance().build("/setting/SettingActivity").navigation();
                break;
            case R.id.llLogout: //退出登录
                //退出登录
                logout();
                break;
        }
    }

    /**
     * 退出登录
     */
    private void logout() {
        /**设置退出登录*/
        SPUtils.getInstance(Constant.SHARED_NAME).clear();
        setUserStatusInfo();
        /**清除cookies*/
        CookiesManager.clearAllCookies();
        /**发送退出登录消息*/
        RxBus.getInstance().post(new LoginEvent());
    }


    /**
     * 设置用户登录信息
     */
    private void setUserStatusInfo() {
        mIsLogin = SPUtils.getInstance(Constant.SHARED_NAME).getBoolean(Constant.LOGIN_KEY);
        if (mIsLogin) {
            mCivAvatar.setImageResource(R.drawable.ic_head_portrait);
            mTvNick.setText(SPUtils.getInstance(Constant.SHARED_NAME).getString(Constant.USERNAME_KEY));
            mLlLogout.setVisibility(View.VISIBLE);
        } else {
            mCivAvatar.setImageResource(R.drawable.ic_avatar);
            mTvNick.setText(R.string.click_avatar_login);
            mLlLogout.setVisibility(View.GONE);
        }
    }

    public static MyFragment newInstance() {
        return new MyFragment();
    }
}
