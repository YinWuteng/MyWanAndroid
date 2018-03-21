package yinwuteng.com.mywanandroid.ui.home;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;


import com.chad.library.adapter.base.BaseQuickAdapter;

import butterknife.BindView;

import yinwuteng.com.mywanandroid.R;
import yinwuteng.com.mywanandroid.base.BaseFragment;
import yinwuteng.com.mywanandroid.bean.Article;

/**
 * Created by yinwuteng on 2018/3/20.
 * 首页文章列表
 */

public class ArticleListFragment extends BaseFragment<ArticleListPresenter> implements ArticleListContract.View, ArticleAdapter.OnItemClickListener, ArticleAdapter.OnItemChildClickListener, SwipeRefreshLayout.OnRefreshListener, ArticleAdapter.RequestLoadMoreListener {
    @BindView(R.id.rvArticleList)
    RecyclerView rvArticleList;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    public int cid;
    private ArticleAdapter mArticalAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_article_list;
    }

    @Override
    protected void initView(View view) {
        /**隐藏文章类型*/
        mArticalAdapter.setChapterNameVisible(false);
        /**设置recycleview*/
        rvArticleList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvArticleList.setAdapter(mArticalAdapter);

        /**设置监听事件*/
        mArticalAdapter.setOnItemClickListener(this);
        mArticalAdapter.setOnItemChildClickListener(this);
        swipeRefreshLayout.setOnRefreshListener(this);
        mArticalAdapter.setOnLoadMoreListener(this);

        //设置网络数据请求
        mPresenter.loadKnowledgeSystemArticles(cid);
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
   if (view.getId()==R.id.ivCollect){
       mPresenter.collectArticle(position,mArticalAdapter.getItem(position));
   }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
    //跳转文章详情界面
    }

    @Override
    public void showLoading() {

        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void onRefresh() {
        mPresenter.refresh();
    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.loadMore();
    }

    @Override
    public void setKnowledgeSystemArticles(Article article, int loadType) {
        setLoadDataResult(mArticalAdapter, swipeRefreshLayout, article.getDatas(), loadType);
    }

    @Override
    public void collectArticlesSuccess(int position, Article.DatasBean bean) {
        mArticalAdapter.setData(position, bean);
    }
}
