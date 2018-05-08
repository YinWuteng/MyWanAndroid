package yinwuteng.com.mywanandroid.ui.my;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import yinwuteng.com.mywanandroid.R;
import yinwuteng.com.mywanandroid.base.BaseActivity;
import yinwuteng.com.mywanandroid.bean.Article;
import yinwuteng.com.mywanandroid.bean.KnowledgeSystem;
import yinwuteng.com.mywanandroid.constant.Constant;
import yinwuteng.com.mywanandroid.ui.home.article.ArticleAdapter;
import yinwuteng.com.mywanandroid.ui.home.article.ArticleContentActivity;

/**
 * Create By yinwuteng
 * 2018/4/23.
 * 我的收藏
 */
@Route(path = "/my/MyCollectionActivity")
public class MyCollectionActivity extends BaseActivity<MyCollectionView, MyCollectionPresenter> implements MyCollectionView, SwipeRefreshLayout.OnRefreshListener, ArticleAdapter.OnItemClickListener, ArticleAdapter.OnItemChildClickListener, ArticleAdapter.RequestLoadMoreListener {
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ArticleAdapter mArticleAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_collection;
    }

    @Override
    protected void initView() {
        RecyclerView mRvMyCollectionArticles = findViewById(R.id.rvMyCollectionArticles);
        mSwipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        mArticleAdapter = new ArticleAdapter();

        /**设置RecycleView*/
        mRvMyCollectionArticles.setLayoutManager(new LinearLayoutManager(this));
        mRvMyCollectionArticles.setAdapter(mArticleAdapter);

        /**设置监听事件*/
        mArticleAdapter.setOnItemClickListener(this);
        mArticleAdapter.setOnItemChildClickListener(this);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mArticleAdapter.setOnLoadMoreListener(this);
        mArticleAdapter.isMyColection(true);

        /**请求数据*/
        getPresenter().loadMyCollectArticles();
    }


    @Override
    protected MyCollectionPresenter createPresenter() {
        return new MyCollectionPresenter();
    }

    @Override
    public void showLoading() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    protected boolean showHomeAsUp() {
        return true;
    }

    @Override
    public void onRefresh() {
        getPresenter().refresh();
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

        if (view.getId() == R.id.tvChapterName) {
            List<KnowledgeSystem.ChildrenBean> childrenBeans = new ArrayList<>();
            childrenBeans.add(new KnowledgeSystem.ChildrenBean(mArticleAdapter.getItem(position).getChapterId(), mArticleAdapter.getItem(position).getChapterName()));
            ARouter.getInstance().build("/article/ArticleTypeActivity").withString(Constant.CONTENT_TITLE_KEY, mArticleAdapter.getItem(position).getChapterName()).withObject(Constant.CONTENT_CHILDREN_DATA_KEY, childrenBeans).navigation();
        } else if (view.getId() == R.id.ivCollect) {
            //收藏文章
            getPresenter().unCollectArticle(position, mArticleAdapter.getItem(position));
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ArticleContentActivity.start(mArticleAdapter.getItem(position).getId(), mArticleAdapter.getItem(position).getLink(), mArticleAdapter.getItem(position).getTitle(), mArticleAdapter.getItem(position).getAuthor());
    }

    @Override
    public void onLoadMoreRequested() {
        getPresenter().loadMore();

    }


    @Override
    public void setMyCollectArticles(Article articles, int loadType) {
        setLoadDataResult(mArticleAdapter, mSwipeRefreshLayout, articles.getDatas(), loadType);
    }

    @Override
    public void unCollectArticleSuccess(int position) {
        mArticleAdapter.remove(position);
    }
}
