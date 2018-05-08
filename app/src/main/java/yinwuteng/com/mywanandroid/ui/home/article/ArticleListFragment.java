package yinwuteng.com.mywanandroid.ui.home.article;

import android.annotation.SuppressLint;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;

import io.reactivex.functions.Consumer;
import yinwuteng.com.mywanandroid.R;
import yinwuteng.com.mywanandroid.base.BaseFragment;
import yinwuteng.com.mywanandroid.bean.Article;
import yinwuteng.com.mywanandroid.constant.Constant;
import yinwuteng.com.mywanandroid.ui.login.LoginEvent;
import yinwuteng.com.mywanandroid.utils.RxBus;

/**
 * Create By yinwuteng
 * 2018/5/7.
 */
@Route(path = "/article/ArticleListFragment")
public class ArticleListFragment extends BaseFragment<ArticleTypeView, ArticleTypePresenter> implements ArticleTypeView, ArticleAdapter.OnItemClickListener, ArticleAdapter.OnItemChildClickListener, SwipeRefreshLayout.OnRefreshListener, ArticleAdapter.RequestLoadMoreListener {

    private SwipeRefreshLayout mSwipeRefreshLayout;

    //获取传过来的cid
    @Autowired(name = Constant.CONTENT_CID_KEY)
    public int cid;

    private ArticleAdapter mArticleAdapter = new ArticleAdapter();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_article_list;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initView(View view) {
        RecyclerView mRecyclerView = view.findViewById(R.id.rvArticleList);
        mSwipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);

        /**隐藏文章类型*/
        mArticleAdapter.setChapterNameVisible(false);

        /**设置recycleView*/
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mArticleAdapter);

        /**设置监听事件*/
        mArticleAdapter.setOnItemClickListener(this);
        mArticleAdapter.setOnItemChildClickListener(this);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mArticleAdapter.setOnLoadMoreListener(this);

        /**请求数据*/
        getPresenter().loadKnowledgeSystemArticles(cid);

        /**登录成功刷新*/
        RxBus.getInstance().toFlowable(LoginEvent.class).subscribe(new Consumer<LoginEvent>() {
            @Override
            public void accept(LoginEvent loginEvent) throws Exception {
                getPresenter().refresh();
            }
        });
    }

    @Override
    public void showLoading() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void onRefresh() {
        getPresenter().refresh();
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        if (view.getId() == R.id.ivCollect) {
            getPresenter().collectArticle(position, mArticleAdapter.getItem(position));
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
    protected ArticleTypePresenter createPresenter() {
        return new ArticleTypePresenter();
    }

    @Override
    public void setKnowledgeSystemArticles(Article article, int loadType) {
        setLoadDataResult(mArticleAdapter, mSwipeRefreshLayout, article.getDatas(), loadType);
    }

    @Override
    public void collectArticleSuccess(int position, Article.DatasBean bean) {
        mArticleAdapter.setData(position, bean);
    }
}
