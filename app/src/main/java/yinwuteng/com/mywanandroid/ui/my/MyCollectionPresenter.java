package yinwuteng.com.mywanandroid.ui.my;

import android.annotation.SuppressLint;

import io.reactivex.functions.Consumer;
import yinwuteng.com.mywanandroid.base.BasePresenter;
import yinwuteng.com.mywanandroid.bean.Article;
import yinwuteng.com.mywanandroid.bean.DataResponse;
import yinwuteng.com.mywanandroid.constant.Constant;
import yinwuteng.com.mywanandroid.net.ApiService;
import yinwuteng.com.mywanandroid.net.RetrofitManager;
import yinwuteng.com.mywanandroid.utils.ArticleUtils;
import yinwuteng.com.mywanandroid.utils.RxSchedulers;

/**
 * Create By yinwuteng
 * 2018/5/3.
 * 我的收藏控制器
 */
public class MyCollectionPresenter extends BasePresenter<MyCollectionView> {
    private boolean mIsRefresh;
    private int mPage;

    public MyCollectionPresenter() {
        this.mIsRefresh = true;
    }

    /**
     * 加载我的收藏
     */
    @SuppressLint("CheckResult")
    public void loadMyCollectArticles() {

        if (mIsRefresh) getView().showLoading();
        RetrofitManager.create(ApiService.class).getCollectionArticles(mPage).compose(RxSchedulers.<DataResponse<Article>>applySchedulers()).subscribe(new Consumer<DataResponse<Article>>() {
            @Override
            public void accept(DataResponse<Article> articleDataResponse) throws Exception {

                int loadType = mIsRefresh ? Constant.LOADTYPE_REFRESH_SUCCESS : Constant.LOADTYPE_LOAD_MORE_SUCCESS;
                getView().setMyCollectArticles(articleDataResponse.getData(), loadType);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                int loadType = mIsRefresh ? Constant.LOADTYPE_REFRESH_ERROR : Constant.LOADTYPE_LOAD_MORE_ERROR;
                getView().setMyCollectArticles(new Article(), loadType);
            }
        });
    }

    public void refresh() {
        mPage = 0;
        mIsRefresh = true;
        loadMyCollectArticles();
    }

    public void loadMore() {
        mPage++;
        mIsRefresh = false;
        loadMyCollectArticles();
    }

    public void unCollectArticle(final int position, final Article.DatasBean bean) {
        ArticleUtils.collectArtivle(getView(), position, bean);
    }
}
