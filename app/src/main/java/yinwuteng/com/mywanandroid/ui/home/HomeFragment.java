package yinwuteng.com.mywanandroid.ui.home;


import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import yinwuteng.com.mywanandroid.R;
import yinwuteng.com.mywanandroid.base.BaseFragment;
import yinwuteng.com.mywanandroid.bean.Article;
import yinwuteng.com.mywanandroid.bean.KnowledgeSystem;
import yinwuteng.com.mywanandroid.constant.Constant;
import yinwuteng.com.mywanandroid.ui.article.ArticleAdapter;
import yinwuteng.com.mywanandroid.ui.article.ArticleContentActivity;
import yinwuteng.com.mywanandroid.utils.GlideImageLoader;


/**
 * Created by yinwuteng on 2018/4/5.
 * HomeFragment
 */

public class HomeFragment extends BaseFragment<HomeView, HomePresenter> implements HomeView, SwipeRefreshLayout.OnRefreshListener, ArticleAdapter.OnItemClickListener, ArticleAdapter.OnItemChildClickListener, ArticleAdapter.RequestLoadMoreListener {

    private SwipeRefreshLayout swipeRefreshLayout;
    private ArticleAdapter mArticleAdapter = new ArticleAdapter();
    /**
     * 轮播动画控件
     */
    private Banner mBannerAds;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View view) {
        RecyclerView rvHomeArticles = view.findViewById(R.id.rvHomeArticles);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        /**设置RecyclerView*/
        rvHomeArticles.setLayoutManager(new LinearLayoutManager(getContext()));
        rvHomeArticles.setAdapter(mArticleAdapter);
        /**设置BannerHeadView*/
        View mHomeBannerHearView = LayoutInflater.from(getContext()).inflate(R.layout.layout_home_banner, null);
        mBannerAds = mHomeBannerHearView.findViewById(R.id.banner_ads);
        mArticleAdapter.addHeaderView(mHomeBannerHearView);
        /**设置事件监听事件*/
        mArticleAdapter.setOnItemClickListener(this);
        mArticleAdapter.setOnItemChildClickListener(this);
        swipeRefreshLayout.setOnRefreshListener(this);
        mArticleAdapter.setOnLoadMoreListener(this);
        /**请求数据*/
        mPresenter.loadHomeData();

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
    public void setHomeBanners(final List<yinwuteng.com.mywanandroid.bean.Banner> banners) {
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
   ArticleContentActivity.start(banners.get(position).getId(),banners.get(position).getUrl()
   ,banners.get(position).getTitle(),null);
            }
        });
    }

    /**
     * 加载文章数据
     *
     * @param article  文章
     * @param loadType 加载类型
     */
    @Override
    public void setHomeArticles(Article article, int loadType) {
        setLoadDataResult(mArticleAdapter, swipeRefreshLayout, article.getDatas(), loadType);
    }

    @Override
    public void collectArticlesSuccess(int position, Article.DatasBean bean) {
        mArticleAdapter.setData(position, bean);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    /**
     * 跳转文章详情
     *
     * @param adapter  适配器
     * @param view     视图
     * @param position 位置
     */
    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ArticleContentActivity.start(mArticleAdapter.getItem(position).getId(),
                mArticleAdapter.getItem(position).getLink(),
                mArticleAdapter.getItem(position).getTitle(),
                mArticleAdapter.getItem(position).getAuthor());
    }

    /**
     * 跳转相关类别
     *
     * @param adapter
     * @param view
     * @param position
     */
    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        if (view.getId() == R.id.tvChapterName) {
            List<KnowledgeSystem.ChildrenBean> childrenBeans = new ArrayList<>();
            childrenBeans.add(new KnowledgeSystem.ChildrenBean(mArticleAdapter.getItem(position).getChapterId(), mArticleAdapter.getItem(position).getChapterName()));
            //跳转界面并传值
            ARouter.getInstance().build("article/ArticleTypeActivity").withString(Constant.CONTENT_TITLE_KEY, mArticleAdapter.getItem(position).getChapterName()).withObject(Constant.CONTENT_CHILDREN_DATA_KEY, childrenBeans).navigation();
        } else if (view.getId() == R.id.ivCollect) {
            //收藏文章
            mPresenter.collectArticles(position, mArticleAdapter.getItem(position));
        }
    }

    /**
     * 加载更多
     */
    @Override
    public void onLoadMoreRequested() {
        mPresenter.loadMore();

    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }
}
