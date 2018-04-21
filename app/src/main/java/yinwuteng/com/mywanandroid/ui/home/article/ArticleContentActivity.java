package yinwuteng.com.mywanandroid.ui.home.article;

import android.content.Intent;
import android.net.Uri;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.ChromeClientCallbackManager;

import yinwuteng.com.mywanandroid.R;
import yinwuteng.com.mywanandroid.base.BaseActivity;
import yinwuteng.com.mywanandroid.base.BaseView;
import yinwuteng.com.mywanandroid.constant.Constant;

/**
 * Create By yinwuteng
 * 2018/4/15.
 */
@Route(path = "/article/ArticleContentActivity")
public class ArticleContentActivity extends BaseActivity<BaseView, ArticleContentPresenter> implements BaseView {
    @Autowired
    public int id;
    @Autowired
    public String url;
    @Autowired
    public String title;
    @Autowired
    public String author;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_article_content;
    }

    @Override
    protected void initView() {
        FrameLayout mWebContent = findViewById(R.id.webContent);
        AgentWeb.with(this)//传入Activity or Fragment
                .setAgentWebParent(mWebContent, new LinearLayout.LayoutParams(-1, -1)) //传入AgentWeb 的父控件 ，如果父控件为 RelativeLayout ， 那么第二参数需要传入 RelativeLayout.LayoutParams ,第一个参数和第二个参数应该对应。
                .useDefaultIndicator() //使用默认进度条
                .defaultProgressBarColor() //使用默认进度条颜色
                .setReceivedTitleCallback(mReceivedTitleCallback).createAgentWeb().ready().go(url);
    }

    @Override
    protected ArticleContentPresenter createPresenter() {
        return new ArticleContentPresenter();
    }

    @Override
    protected boolean showHomeAsUp() {
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_content, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menuShare) {//分享
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_article_url, getString(R.string.app_name), title, url));
            intent.setType("text/plain");
            startActivity(intent);
        } else if (item.getItemId() == R.id.menuLike) {//收藏
            if (id == 0) getPresenter().collectOutsideArticle(title, author, url);
            else getPresenter().collectArticle(id);
        } else if (item.getItemId() == R.id.menuBrowser) {//用系统浏览器打开
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setData(Uri.parse(url));
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onSuccess(String message) {

    }

    @Override
    public void showFailed(String message) {

    }

    public static void start(int id, String url, String title, String author) {
        ARouter.getInstance().build("/article/ArticleContentActivity").withInt(Constant.CONTETE_ID_KEY, id).withString(Constant.CONTENT_URL_KEY, url).withString(Constant.CONTENT_TITLE_KEY, title).withString(Constant.CONRENT_AUTHOR_KEY, author).navigation();
    }

    private ChromeClientCallbackManager.ReceivedTitleCallback mReceivedTitleCallback = new ChromeClientCallbackManager.ReceivedTitleCallback() {
        @Override
        public void onReceivedTitle(WebView view, String title) {
            setToolbarTitle(title);
        }
    };
}
