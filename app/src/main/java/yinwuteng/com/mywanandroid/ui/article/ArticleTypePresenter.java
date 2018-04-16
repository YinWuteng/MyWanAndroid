package yinwuteng.com.mywanandroid.ui.article;

import android.annotation.SuppressLint;

import com.blankj.utilcode.util.SPUtils;

import io.reactivex.functions.Consumer;
import yinwuteng.com.mywanandroid.R;
import yinwuteng.com.mywanandroid.base.BasePresenter;
import yinwuteng.com.mywanandroid.base.MyApplication;
import yinwuteng.com.mywanandroid.bean.Article;
import yinwuteng.com.mywanandroid.bean.DataResponse;
import yinwuteng.com.mywanandroid.constant.Constant;
import yinwuteng.com.mywanandroid.net.ApiService;
import yinwuteng.com.mywanandroid.net.RetrofitManager;
import yinwuteng.com.mywanandroid.ui.login.LoginActivity;
import yinwuteng.com.mywanandroid.utils.RxSchedulers;

/**
 * Create By yinwuteng
 * 2018/4/16.
 * ArticleTypePresenter
 */
public class ArticleTypePresenter extends BasePresenter<ArticleTypeView> {
    private boolean mIsRefresh;
    private int mPage, mCid;

    public ArticleTypePresenter() {
        this.mIsRefresh = true;
    }

    /**
     * 加载文章
     *
     * @param cid
     */
    @SuppressLint("CheckResult")
    public void loadKnowledgeSystemArticles(int cid) {
        this.mCid = cid;
        RetrofitManager.create(ApiService.class).getKnowledgeSystemArticles(mPage, mCid).compose(RxSchedulers.<DataResponse<Article>>applySchedulers()).subscribe(new Consumer<DataResponse<Article>>() {
            @Override
            public void accept(DataResponse<Article> articleDataResponse) throws Exception {
                int loadType = mIsRefresh ? Constant.LOADTYPE_REFRESH_SUCCESS : Constant.LOADTYPE_LOAD_MORE_SUCCESS;
                getView().setKnowledgeSystemArticles(articleDataResponse.getData(), loadType);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                int loadType = mIsRefresh ? Constant.LOADTYPE_REFRESH_ERROR : Constant.LOADTYPE_LOAD_MORE_ERROR;
                getView().setKnowledgeSystemArticles(new Article(), loadType);
            }
        });
    }

    /**
     * 刷新
     */
    public void refresh() {
        mPage = 0;
        mIsRefresh = true;
        loadKnowledgeSystemArticles(mCid);
    }

    /**
     * 加载更多
     */
    public void loadMore() {
        mPage++;
        mIsRefresh = false;
        loadKnowledgeSystemArticles(mCid);
    }

    /**
     * 收藏文章
     *
     * @param position 位置
     * @param bean     数据
     */
    @SuppressLint("CheckResult")
    public void collectArticle(final int position, final Article.DatasBean bean) {
        if (SPUtils.getInstance(Constant.SHARED_NAME).getBoolean(Constant.LOGIN_KEY)) { //若已登录
            if (bean.isCollect()) {//若已经收藏过，则移除收藏列表
                RetrofitManager.create(ApiService.class).removeCollectArticle(bean.getId(), -1).compose(RxSchedulers.<DataResponse>applySchedulers()).subscribe(new Consumer<DataResponse>() {
                    @Override
                    public void accept(DataResponse response) throws Exception {
                        if (response.getErrorCode() == 0) {
                            bean.setCollect(!bean.isCollect());
                            getView().collectArticleSuccess(position, bean);
                            getView().onSuccess(MyApplication.getAppContext().getString(R.string.collection_cancel_success));
                        } else {
                            getView().showFailed(MyApplication.getAppContext().getString(R.string.collection_cancel_failed));
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        getView().showFailed(throwable.getMessage());
                    }
                });
            } else {//若没收藏过，则直接收藏
                RetrofitManager.create(ApiService.class).addCollectArticle(bean.getId()).compose(RxSchedulers.<DataResponse>applySchedulers()).subscribe(new Consumer<DataResponse>() {
                    @Override
                    public void accept(DataResponse response) throws Exception {
                        if (response.getErrorCode() == 0) {
                            bean.setCollect(!bean.isCollect());
                            getView().collectArticleSuccess(position, bean);
                            getView().onSuccess(MyApplication.getAppContext().getString(R.string.collection_cancel_success));
                        } else {
                            getView().showFailed(MyApplication.getAppContext().getString(R.string.collection_cancel_failed));
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        getView().showFailed(throwable.getMessage());
                    }
                });
            }
        } else {//若未登录，则直接跳转登录界面
            LoginActivity.start();
        }
    }
}
