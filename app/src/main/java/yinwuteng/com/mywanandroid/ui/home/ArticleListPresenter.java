package yinwuteng.com.mywanandroid.ui.home;

import android.content.Intent;

import com.blankj.utilcode.util.SPUtils;

import io.reactivex.functions.Consumer;
import yinwuteng.com.mywanandroid.R;
import yinwuteng.com.mywanandroid.base.BaseContract;
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
 * Created by yinwuteng on 2018/3/20.
 * 获取文章详情控制器
 */

public class ArticleListPresenter extends BasePresenter<ArticleListContract.View> implements ArticleListContract.Presenter {
    /**
     * 是否刷新
     */
    private boolean mIsRefresh;
    /**
     * 页码
     */
    private int mPage;
    /**
     * 文章分类id
     */
    private int mCid;


    public ArticleListPresenter() {
        this.mIsRefresh = true;
    }


    @Override
    public void loadKnowledgeSystemArticles(int cid) {
        this.mCid = cid;
        RetrofitManager.create(ApiService.class).getKnowledgeSystemArticles(mPage, cid).compose(RxSchedulers.<DataResponse<Article>>applySchedulers()).compose(mView.<DataResponse<Article>>bindToLife()).subscribe(new Consumer<DataResponse<Article>>() {
            @Override
            public void accept(DataResponse<Article> dataResponse) throws Exception {
                int loadType = mIsRefresh ? Constant.LOADTYPE_REFRESH_SUCCESS : Constant.LOADTYPE_REFRESH_ERROR;
                //设置数据到视图上
                mView.setKnowledgeSystemArticles(dataResponse.getData(), loadType);

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                int loadType = mIsRefresh ? Constant.LOADTYPE_REFRESH_ERROR : Constant.LOADTYPE_REFRESH_ERROR;
                mView.setKnowledgeSystemArticles(new Article(), loadType);
            }
        });
    }

    @Override
    public void refresh() {
        mPage = 0;
        mIsRefresh = true;
        loadKnowledgeSystemArticles(mCid);
    }

    @Override
    public void loadMore() {
        mPage++;
        mIsRefresh = false;
        loadKnowledgeSystemArticles(mCid);
    }

    @Override
    public void collectArticle(final int position, final Article.DatasBean bean) {
       //若用户已经登录
        if (SPUtils.getInstance(Constant.SHARED_NAME).getBoolean(Constant.LOGIN_KEY)) {
            if (bean.isCollect()) {//若文章被收藏了
                RetrofitManager.create(ApiService.class).removeCollectArticle(bean.getId(), -1).compose(RxSchedulers.<DataResponse>applySchedulers()).compose(mView.<DataResponse>bindToLife()).subscribe(new Consumer<DataResponse>() {
                    @Override
                    public void accept(DataResponse dataResponse) throws Exception {
                        if (dataResponse.getErrorCode() == 0) {
                            bean.setCollect(!bean.isCollect());
                            mView.collectArticlesSuccess(position, bean);
                            mView.showSuccess(MyApplication.getAppContext().getString(R.string.collection_cancel_success));
                        } else {
                            mView.showFailed(MyApplication.getAppContext().getString(R.string.collection_cancel_failed, dataResponse.getData()));
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.showFailed(throwable.getMessage());
                    }
                });
            } else {//若文章没有被收藏
                RetrofitManager.create(ApiService.class).addCollectArticle(bean.getId()).compose(RxSchedulers.<DataResponse>applySchedulers()).compose(mView.<DataResponse>bindToLife()).subscribe(new Consumer<DataResponse>() {
                    @Override
                    public void accept(DataResponse dataResponse) throws Exception {
                        if (dataResponse.getErrorCode() == 0) {
                            bean.setCollect(!bean.isCollect());
                            mView.collectArticlesSuccess(position, bean);
                            mView.showSuccess(MyApplication.getAppContext().getString(R.string.collection_success));
                        } else {
                            mView.showFailed(MyApplication.getAppContext().getString(R.string.collection_failed, dataResponse.getErrorMsg()));
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.showFailed(throwable.getMessage());
                    }
                });
            }

        } else {
            //跳转到登录界面,此时需要采用路由跳转

        }

    }

}
