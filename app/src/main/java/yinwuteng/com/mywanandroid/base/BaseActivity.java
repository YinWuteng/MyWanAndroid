package yinwuteng.com.mywanandroid.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.List;

import yinwuteng.com.mywanandroid.R;
import yinwuteng.com.mywanandroid.constant.Constant;

/**
 * Created by yinwuteng on 2018/4/5.
 * 基类activity
 */

public abstract class BaseActivity<V extends BaseView, P extends BasePresenter> extends RxAppCompatActivity implements BaseView {

    private P mPresenter;
    protected Toolbar mToolbar;



    protected abstract int getLayoutId();

    protected abstract void initView();

    /**
     * 是否显示返回键
     *
     * @return
     */
    protected boolean showHomeAsUp() {
        return false;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
        int layoutId = getLayoutId();
        setContentView(layoutId);
        initToolbar();
        initView();
        initPresenter();
        if (!NetworkUtils.isConnected()) showNoNet();
    }

    /**
     * 初始化toolbar
     */
    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar == null) {
            throw new NullPointerException("toolbar can not be null");
        }
        setSupportActionBar(mToolbar);
        //是否显示返回键
        getSupportActionBar().setDisplayHomeAsUpEnabled(showHomeAsUp());
        //toolbar除掉阴影
        getSupportActionBar().setElevation(0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mToolbar.setElevation(0);
        }
    }

    /**
     * 初始化presneter
     */
    private void initPresenter() {
        if (mPresenter == null) {
            mPresenter = createPresenter();
        }
        if (mPresenter == null) {
            throw new NullPointerException("presenter不能为空");
        }
        mPresenter.attachView((V) this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    //当api大于Android Lollipop（5.0），支持md动画
                    finishAfterTransition();
                } else {
                    finish();
                }
                break;
        }
        return true;
    }

    @Override
    public void showNoNet() {
        ToastUtils.showShort("无网络连接");
    }

    /**
     * 设置toolbar标题
     *
     * @param title
     */
    protected void setToolbarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    /**
     * 设置数据加载结果
     *
     * @param baseQuickAdapter tv是适配器
     * @param refreshLayout    刷新加载控件
     * @param list             数据集
     * @param loadType         加载类型
     */
    protected void setLoadDataResult(BaseQuickAdapter baseQuickAdapter, SwipeRefreshLayout refreshLayout, List list, int loadType) {
        switch (loadType) {
            case Constant.LOADTYPE_REFRESH_SUCCESS:
                baseQuickAdapter.setNewData(list);
                refreshLayout.setRefreshing(false);
                break;
            case Constant.LOADTYPE_REFRESH_ERROR:
                refreshLayout.setRefreshing(false);
                break;
            case Constant.LOADTYPE_LOAD_MORE_SUCCESS:
                if (list != null) baseQuickAdapter.addData(list);
                break;
            case Constant.LOADTYPE_LOAD_MORE_ERROR:
                baseQuickAdapter.loadMoreFail();
                break;
        }
        if (list == null || list.isEmpty() || list.size() < Constant.PAGE_SIZE) {
            baseQuickAdapter.loadMoreEnd(false);
        } else {
            baseQuickAdapter.loadMoreComplete();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解除绑定
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    /**
     * 创建presenter
     *
     * @return
     */
    protected abstract P createPresenter();

    public P getPresenter() {
        return mPresenter;
    }
}
