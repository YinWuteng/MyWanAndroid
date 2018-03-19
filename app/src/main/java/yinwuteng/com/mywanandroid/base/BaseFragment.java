package yinwuteng.com.mywanandroid.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import yinwuteng.com.mywanandroid.constant.Constant;

/**
 * Created by yinwuteng on 2018/3/19.
 * fragment基类
 */

public abstract class BaseFragment extends Fragment {
    private Unbinder unbinder;
    private View mRootView, mErrorView, mEmptyView;

    protected abstract int getLayoutId();

    protected abstract void initView(View view);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!NetworkUtils.isConnected()) showNoNet();
    }

    private void showNoNet() {
        ToastUtils.showShort("无网络连接");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inflaterView(inflater, container);
        unbinder = ButterKnife.bind(this, mRootView);
        initView(mRootView);
        return mRootView;
    }

    /**
     * 设置加载数据结果
     *
     * @param baseQuickAdapter
     * @param refreshLayout
     * @param list
     * @param loadType
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
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
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
}
