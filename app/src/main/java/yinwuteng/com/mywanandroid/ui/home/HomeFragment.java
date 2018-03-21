package yinwuteng.com.mywanandroid.ui.home;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.youth.banner.Banner;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import yinwuteng.com.mywanandroid.R;
import yinwuteng.com.mywanandroid.base.BaseFragment;

/**
 * Created by yinwuteng on 2018/3/19.
 * 主页
 */

public class HomeFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.rvHomeArticles)
    RecyclerView rvHomeArticles;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    /**
     * 轮播动画控件
     */
    private Banner mBannerAds;
    private View mHmoeBannerHearView;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View view) {
        /**设置RecyclerView*/
        rvHomeArticles.setLayoutManager(new LinearLayoutManager(getContext()));

    }

    @Override
    public void onRefresh() {

    }

}
