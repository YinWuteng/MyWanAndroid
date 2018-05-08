package yinwuteng.com.mywanandroid.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;


import yinwuteng.com.mywanandroid.R;
import yinwuteng.com.mywanandroid.bean.Article;
import yinwuteng.com.mywanandroid.constant.Constant;

/**
 * Created by yinwuteng on 2018/4/5.
 * 基类fragment
 */

public abstract class BaseFragment<V extends BaseView, P extends BasePresenter> extends Fragment implements BaseView {
    protected P mPresenter;
    private View mRootView;

    protected abstract int getLayoutId();

    protected abstract void initView(View view);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPresenter();
        ARouter.getInstance().inject(this);
        if (!NetworkUtils.isConnected()) showNoNet();
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
        mPresenter.attachView((V)this);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inflaterView(inflater, container);
        initView(mRootView);
        return mRootView;
    }
    /**
     * 设置加载数据结果
     *
     * @param baseQuickAdapter 适配器
     * @param refreshLayout    刷新控件
     * @param list             数据
     * @param loadType         加载类型
     */
    protected void setLoadDataResult(BaseQuickAdapter<Article.DatasBean, BaseViewHolder> baseQuickAdapter, SwipeRefreshLayout refreshLayout, List<Article.DatasBean> list, int loadType) {
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
    public void onDestroy() {
        super.onDestroy();
        //解除绑定
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    @Override
    public void onSuccess(String message) {

    }

    @Override
    public void showFailed(String message) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
    @Override
    public void showNoNet() {
        ToastUtils.showShort(R.string.no_network_connection);
    }

    /**
     * 设置View
     *
     * @param inflater
     * @param container
     */

    private void inflaterView(LayoutInflater inflater, @Nullable ViewGroup container) {
        if (mRootView == null) {
            mRootView = inflater.inflate(getLayoutId(), container, false);
        }
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
