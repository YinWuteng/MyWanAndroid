package yinwuteng.com.mywanandroid.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;

import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import yinwuteng.com.mywanandroid.R;
import yinwuteng.com.mywanandroid.constant.Constant;

/**
 * Created by yinwuteng on 2018/3/18.
 * Activity基类
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected Toolbar mToolbar;

    private Unbinder unbinder;

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
        int layoutId = getLayoutId();
        setContentView(layoutId);
        unbinder = ButterKnife.bind(this);
        initToolbar();
        initView();
        if (!NetworkUtils.isConnected()) showNoNet();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    public void showNoNet(){

        ToastUtils.showShort("无网络连接");
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


    /**
     * 设置toolbar标题
     *
     * @param title
     */
    private void setToolbarTitle(String title) {
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


}
