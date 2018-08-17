package yinwuteng.com.mywanandroid.hotsearch;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

import yinwuteng.com.mywanandroid.R;
import yinwuteng.com.mywanandroid.base.BaseActivity;
import yinwuteng.com.mywanandroid.bean.Book;
import yinwuteng.com.mywanandroid.bean.HotKey;
import yinwuteng.com.mywanandroid.constant.Constant;
import yinwuteng.com.mywanandroid.ui.home.article.ArticleContentActivity;

/**
 * Create By yinwuteng
 * 2018/5/9.
 */

@Route(path = "/hotsearch/HotActivity")
public class HotActivity extends BaseActivity<HotView, HotPresenter> implements HotView, SwipeRefreshLayout.OnRefreshListener {
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private TagFlowLayout mTtlBookMarks, mTflHotKeys, mTflHotFriends;
    private HotAdapter<HotKey> mHotKeyAdapter;
    private HotAdapter<Book> mBookAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_hot;
    }

    @Override
    protected void initView() {
        setToolbarTitle("热门");

        RecyclerView mRecyclerView = findViewById(R.id.rvHots);
        mSwipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        CommonHotAdapter mCommonHotAdapter = new CommonHotAdapter();

        /**设置recycleView*/
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mCommonHotAdapter);

        /**设置HotHeadView*/
        View mHotHeadView = LayoutInflater.from(this).inflate(R.layout.layout_hot_head, null);
        mTtlBookMarks = mHotHeadView.findViewById(R.id.tflBookMarks);
        mTflHotKeys = mHotHeadView.findViewById(R.id.tflHotKeys);
        mTflHotFriends = mHotHeadView.findViewById(R.id.tflHotFriends);
        mCommonHotAdapter.addHeaderView(mHotHeadView);

        /**设置监听*/
        setListener();

        /**请求数据*/
        getPresenter().loadHotData();
    }

    @Override
    protected boolean showHomeAsUp() {
        return true;
    }

    private void setListener() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mTflHotKeys.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                String name = mHotKeyAdapter.getItem(position).getName();
                ARouter.getInstance().build("/hotsearch/MySearchActivity").withString(Constant.CONTENT_HOT_NAME_KET, name).navigation();
                return false;
            }
        });

        mTflHotFriends.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                ArticleContentActivity.start(mBookAdapter.getItem(position).getId(), mBookAdapter.getItem(position).getLink(), mBookAdapter.getItem(position).getName(), null);
                return false;
            }
        });

    }

    @Override
    protected HotPresenter createPresenter() {
        return new HotPresenter();
    }

    @Override
    public void showLoading() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void showFailed(String message) {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void setHotData(List<HotKey> hotKeys, List<Book> books) {
        mHotKeyAdapter = new HotAdapter<>(this, hotKeys);
        mTflHotKeys.setAdapter(mHotKeyAdapter);
        mBookAdapter = new HotAdapter<>(this, books);
        mTflHotFriends.setAdapter(mBookAdapter);

        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        getPresenter().refresh();
    }

    public static void start() {
        ARouter.getInstance().build("/hotsearch/HotActivity").navigation();
    }
}
