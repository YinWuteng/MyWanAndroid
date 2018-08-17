package yinwuteng.com.mywanandroid.hotsearch;

import android.annotation.SuppressLint;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;
import yinwuteng.com.mywanandroid.R;
import yinwuteng.com.mywanandroid.base.BaseActivity;
import yinwuteng.com.mywanandroid.bean.Article;
import yinwuteng.com.mywanandroid.bean.KnowledgeSystem;
import yinwuteng.com.mywanandroid.constant.Constant;
import yinwuteng.com.mywanandroid.db.HistoryModel;
import yinwuteng.com.mywanandroid.ui.home.article.ArticleAdapter;
import yinwuteng.com.mywanandroid.ui.home.article.ArticleContentActivity;
import yinwuteng.com.mywanandroid.ui.login.LoginEvent;
import yinwuteng.com.mywanandroid.utils.RxBus;

/**
 * Create By yinwuteng
 * 2018/5/9.
 */
@Route(path = "/hotsearch/MySearchActivity")
public class MySearchActivity extends BaseActivity<MySearchView, SearchPresenter> implements MySearchView, ArticleAdapter.OnItemClickListener, ArticleAdapter.OnItemChildClickListener, SwipeRefreshLayout.OnRefreshListener, ArticleAdapter.RequestLoadMoreListener {
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ArticleAdapter mArticleAdapter;
    @Autowired(name = Constant.CONTENT_HOT_NAME_KET)
    public String hotNameKey;

    private HistoryAdapter historyAdapter;
    private List<HistoryModel> mHistoryModels;
    private TagFlowLayout mTfHistorys;
    private SearchView mSearchView;
    private View mSearchHeadView;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initView() {


        RecyclerView mRecyclerView = findViewById(R.id.rvArticleList);
        mSwipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        mArticleAdapter=new ArticleAdapter();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mArticleAdapter);

        /**设置SearchHeadView*/
        mSearchHeadView = LayoutInflater.from(this).inflate(R.layout.layout_search_head, null);
        mTfHistorys = mSearchHeadView.findViewById(R.id.tflHistorys);
        mArticleAdapter.addHeaderView(mSearchHeadView);

        /**设置监听*/
        mArticleAdapter.setOnItemClickListener(this);
        mArticleAdapter.setOnItemChildClickListener(this);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mArticleAdapter.setOnLoadMoreListener(this);

        /**加载历史搜索记录*/
        getPresenter().loadHistory();

        /**登录成功刷新*/
        RxBus.getInstance().toFlowable(LoginEvent.class).subscribe(new Consumer<LoginEvent>() {
            @Override
            public void accept(LoginEvent loginEvent) throws Exception {
                getPresenter().refresh();
            }
        });

        mTfHistorys.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                String name = historyAdapter.getItem(position).getName();
                mSearchView.setQuery(name, false);
                getPresenter().loadSearchArticles(name);
                return false;
            }
        });
    }

    @Override
    protected boolean showHomeAsUp() {
        return true;
    }

    @Override
    protected SearchPresenter createPresenter() {
        return new SearchPresenter();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        mSearchView = (SearchView) menu.findItem(R.id.menuSearch).getActionView();
        mSearchView.setMaxWidth(1920);
        mSearchView.setIconified(false);
        mSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                MySearchActivity.this.finish();
                return true;
            }
        });

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                getPresenter().addHistory(query);
                getPresenter().loadSearchArticles(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
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
            getPresenter().collectArticle(position, mArticleAdapter.getItem(position));
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ArticleContentActivity.start(mArticleAdapter.getItem(position).getId(), mArticleAdapter.getItem(position).getLink(), mArticleAdapter.getItem(position).getTitle(), mArticleAdapter.getItem(position).getAuthor());
    }

    @Override
    public void showLoading() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void onLoadMoreRequested() {
        getPresenter().loadMore();
    }


    @Override
    public void setSearchArticles(Article articles, int loadType) {
        setLoadDataResult(mArticleAdapter, mSwipeRefreshLayout, articles.getDatas(), loadType);
    }

    @Override
    public void collectArticlesSuccess(int position, Article.DatasBean bean) {
        mArticleAdapter.setData(position, bean);
    }

    @Override
    public void setHistory(List<HistoryModel> historyModels) {
        this.mHistoryModels = historyModels;
        historyAdapter = new HistoryAdapter(this, mHistoryModels);
        mTfHistorys.setAdapter(historyAdapter);
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void addHistorySuccess(HistoryModel historyModel) {
        if (mHistoryModels != null) mHistoryModels.add(0, historyModel);
        historyAdapter.notifyDataChanged();
    }

    public static void start() {
        ARouter.getInstance().build("/hotsearch/MySearchActivity").navigation();
    }
}
