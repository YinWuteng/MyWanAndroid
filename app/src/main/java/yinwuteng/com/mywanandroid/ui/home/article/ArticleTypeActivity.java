package yinwuteng.com.mywanandroid.ui.home.article;


import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
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


import java.util.List;

import yinwuteng.com.mywanandroid.R;
import yinwuteng.com.mywanandroid.base.BaseActivity;
import yinwuteng.com.mywanandroid.base.BasePresenter;
import yinwuteng.com.mywanandroid.base.BaseView;
import yinwuteng.com.mywanandroid.bean.Article;
import yinwuteng.com.mywanandroid.bean.KnowledgeSystem;
import yinwuteng.com.mywanandroid.constant.Constant;

/**
 * Create By yinwuteng
 * 2018/4/16.
 * 文章类别列表
 */
@Route(path = "/article/ArticleTypeActivity")
public class ArticleTypeActivity extends BaseActivity {
    @Autowired(name = Constant.CONTENT_TITLE_KEY)
    public String title;

    @Autowired(name = Constant.CONTENT_CHILDREN_DATA_KEY)
    public List<KnowledgeSystem.ChildrenBean> childrenData;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_article_type;
    }

    @Override
    protected void initView() {
        TabLayout mTabArticleType = findViewById(R.id.tabArticleTypes);
        ViewPager mVpArticleTypes = findViewById(R.id.vpArticleTypes);

        setToolbarTitle(title);
        ArticleTypeFragmentPagerAdapter mArticleTypeFragmentPagerAdapter = new ArticleTypeFragmentPagerAdapter(getSupportFragmentManager(), childrenData);
        mVpArticleTypes.setAdapter(mArticleTypeFragmentPagerAdapter);
        mTabArticleType.setupWithViewPager(mVpArticleTypes);
    }

    @Override
    protected BasePresenter createPresenter() {
        return new BasePresenter() {
            @Override
            public BaseView getView() {
                return super.getView();
            }
        };
    }

    @Override
    protected boolean showHomeAsUp() {
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_type_content,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menuShare) { //分享

        } else if (item.getItemId() == R.id.menuSearch) {//搜索
            ARouter.getInstance().build("/hotsearch/MySearchActivity").navigation();
        }
        return super.onOptionsItemSelected(item);
    }




}
