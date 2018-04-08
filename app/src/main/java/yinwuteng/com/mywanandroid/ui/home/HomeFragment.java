package yinwuteng.com.mywanandroid.ui.home;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import yinwuteng.com.mywanandroid.R;
import yinwuteng.com.mywanandroid.base.BaseFragment;
import yinwuteng.com.mywanandroid.bean.Article;
import yinwuteng.com.mywanandroid.ui.article.ArticleAdapter;
import yinwuteng.com.mywanandroid.utils.GlideImageLoader;


/**
 * Created by yinwuteng on 2018/4/5.
 * HomeFragment
 */

public class HomeFragment extends BaseFragment<HomeView, HomePresenter> implements HomeView, SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView rvHomeArticles;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ArticleAdapter mArticleAdapter = new ArticleAdapter();
    /**
     * 轮播动画控件
     */
    private Banner mBannerAds;
    private View mHomeBannerHearView;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View view) {
        rvHomeArticles = view.findViewById(R.id.rvHomeArticles);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        /**设置RecyclerView*/
        rvHomeArticles.setLayoutManager(new LinearLayoutManager(getContext()));
        rvHomeArticles.setAdapter(mArticleAdapter);
        /**设置BannerHeadView*/
        mHomeBannerHearView = LayoutInflater.from(getContext()).inflate(R.layout.layout_home_banner, null);
        mBannerAds = (Banner) mHomeBannerHearView.findViewById(R.id.banner_ads);
        mArticleAdapter.addHeaderView(mHomeBannerHearView);
        /**设置事件监听事件*/
        /**请求数据*/
        mPresenter.loadHomeBanners();
    }


    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter();
    }

    @Override
    public void showLoading() {
        swipeRefreshLayout.setRefreshing(true);
    }


    @Override
    public void onSuccess(String message) {

    }

    @Override
    public void showFailed(String message) {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        mPresenter.refresh();
    }

    @Override
    public void setHomeBanners(List<yinwuteng.com.mywanandroid.bean.Banner> banners) {
        List<String> images = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        for (yinwuteng.com.mywanandroid.bean.Banner banner : banners) {
            images.add(banner.getImagePath());
            titles.add(banner.getTitle());
        }
        /**banner加载图片和标题*/
        mBannerAds.setImages(images).setBannerTitles(titles).setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE).setImageLoader(new GlideImageLoader()).start();
        //设置banner点击事件
        mBannerAds.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {

            }
        });
    }

    @Override
    public void setHomeArticles(Article article, int loadType) {

    }

    @Override
    public void collectArticlesSuccess(int position, Article.DatasBean bean) {

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
