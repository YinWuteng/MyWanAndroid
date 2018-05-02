package yinwuteng.com.mywanandroid.ui.my;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

import yinwuteng.com.mywanandroid.R;
import yinwuteng.com.mywanandroid.base.BaseActivity;
import yinwuteng.com.mywanandroid.bean.Book;
import yinwuteng.com.mywanandroid.hotSearch.HotAdapter;
import yinwuteng.com.mywanandroid.ui.home.article.ArticleContentActivity;

/**
 * Create By yinwuteng
 * 2018/4/23.
 * 我的书签
 */
@Route(path = "/my/MyBookmarkActivity")
public class MyBookmarkActivity extends BaseActivity<MyBookmarkView, MyBookPresenter> implements MyBookmarkView, SwipeRefreshLayout.OnRefreshListener {
    private TagFlowLayout mTagFlowLayout;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private HotAdapter<Book> mBookmarkAdpater;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_bookmark;
    }

    @Override
    protected void initView() {
        mTagFlowLayout = findViewById(R.id.tflMyBookmarks);
        mSwipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);


        mSwipeRefreshLayout.setOnRefreshListener(this);
        getPresenter().loadMyBookmarks();

        //跳转文章类容
        mTagFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                ArticleContentActivity.start(mBookmarkAdpater.getItem(position).getId(), mBookmarkAdpater.getItem(position).getLink(), mBookmarkAdpater.getItem(position).getName(), null);
                return false;
            }
        });
    }

    @Override
    protected boolean showHomeAsUp() {
        return true;
    }

    @Override
    protected MyBookPresenter createPresenter() {
        return new MyBookPresenter();
    }


    @Override
    public void onRefresh() {
        getPresenter().refresh();
    }

    @Override
    public void showLoading() {

        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void setMyBookMarks(List<Book> bookmarks) {
        mBookmarkAdpater = new HotAdapter<>(this, bookmarks);
        mTagFlowLayout.setAdapter(mBookmarkAdpater);
        mSwipeRefreshLayout.setRefreshing(false);
    }
}
