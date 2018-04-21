package yinwuteng.com.mywanandroid.ui.home.article;


import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;


import yinwuteng.com.mywanandroid.R;
import yinwuteng.com.mywanandroid.base.BaseActivity;
import yinwuteng.com.mywanandroid.bean.Article;
import yinwuteng.com.mywanandroid.constant.Constant;

/**
 * Create By yinwuteng
 * 2018/4/16.
 * 文章类别列表
 */
@Route(path = "/article/ArticleTypeActivity")
public class ArticleTypeActivity extends BaseActivity<ArticleTypeView, ArticleTypePresenter> implements ArticleTypeView, ArticleAdapter.OnItemClickListener, ArticleAdapter.OnItemChildClickListener, ArticleAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {
    private SwipeRefreshLayout mSwipeRefreshLayout;
    //获取toolbar title
    @Autowired(name = Constant.CONTENT_TITLE_KEY)
    public String title;

    //获取cid
    @Autowired(name = Constant.CONTENT_CID_KEY)
    public int cid;


    private ArticleAdapter articleAdapter = new ArticleAdapter();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_article_type;
    }

    @Override
    protected void initView() {
        /**设置标题*/
        setToolbarTitle(title);
        /**初始化控件*/
        mSwipeRefreshLayout = findViewById(R.id.refresh);
        RecyclerView mRecyclerView = findViewById(R.id.recycleView);

        /**设置recycleView*/
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mRecyclerView.setAdapter(articleAdapter);

        /**隐藏文章类型*/
        articleAdapter.setChapterNameVisible(false);

        /**设置监听事件*/
        articleAdapter.setOnItemClickListener(this);
        articleAdapter.setOnItemChildClickListener(this);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        articleAdapter.setOnLoadMoreListener(this);

        /**加载数据*/

        getPresenter().loadKnowledgeSystemArticles(cid);
    }

    @Override
    protected ArticleTypePresenter createPresenter() {
        return new ArticleTypePresenter();
    }

    @Override
    protected boolean showHomeAsUp() {
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_type_content, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menuShare) { //分享

        } else if (item.getItemId() == R.id.menuSearch) {//搜索
            ARouter.getInstance().build("/hotsearch/SearchActivity").navigation();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showLoading() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void onSuccess(String message) {

    }

    @Override
    public void showFailed(String message) {

    }

    @Override
    public void setKnowledgeSystemArticles(Article article, int loadType) {
        setLoadDataResult(articleAdapter, mSwipeRefreshLayout, article.getDatas(), loadType);
    }

    @Override
    public void collectArticleSuccess(int position, Article.DatasBean bean) {
        articleAdapter.setData(position, bean);
    }

    /**
     * 赞
     *
     * @param adapter
     * @param view
     * @param position
     */
    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        if (view.getId() == R.id.ivCollect) {
            getPresenter().collectArticle(position, articleAdapter.getItem(position));
        }
    }

    /**
     * 文章详情
     *
     * @param adapter
     * @param view
     * @param position
     */
    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ArticleContentActivity.start(articleAdapter.getItem(position).getId(), articleAdapter.getItem(position).getLink(), articleAdapter.getItem(position).getTitle(), articleAdapter.getItem(position).getAuthor());
    }

    @Override
    public void onRefresh() {
        getPresenter().refresh();
    }

    @Override
    public void onLoadMoreRequested() {
        getPresenter().loadMore();
    }

}
