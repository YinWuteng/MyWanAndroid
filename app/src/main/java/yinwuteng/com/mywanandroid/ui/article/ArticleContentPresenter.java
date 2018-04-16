package yinwuteng.com.mywanandroid.ui.article;

import android.annotation.SuppressLint;

import com.blankj.utilcode.util.SPUtils;

import io.reactivex.functions.Consumer;
import yinwuteng.com.mywanandroid.R;
import yinwuteng.com.mywanandroid.base.BasePresenter;
import yinwuteng.com.mywanandroid.base.BaseView;
import yinwuteng.com.mywanandroid.base.MyApplication;
import yinwuteng.com.mywanandroid.bean.DataResponse;
import yinwuteng.com.mywanandroid.constant.Constant;
import yinwuteng.com.mywanandroid.net.ApiService;
import yinwuteng.com.mywanandroid.net.RetrofitManager;
import yinwuteng.com.mywanandroid.ui.login.LoginActivity;
import yinwuteng.com.mywanandroid.utils.RxSchedulers;

/**
 * Create By yinwuteng
 * 2018/4/15.
 * ArticleContentPresenter
 */
public class ArticleContentPresenter extends BasePresenter<BaseView> {
    public ArticleContentPresenter() {

    }
    /**
     * 收藏文章
     *
     * @param id 文章id
     */
    @SuppressLint("CheckResult")
    public void collectArticle(int id) {
        //如果已经登录
        if (SPUtils.getInstance(Constant.SHARED_NAME).getBoolean(Constant.LOGIN_KEY)) {
            RetrofitManager.create(ApiService.class).addCollectArticle(id).compose(RxSchedulers.<DataResponse>applySchedulers()).subscribe(new Consumer<DataResponse>() {
                @Override
                public void accept(DataResponse response) throws Exception {
                    if (response.getErrorCode() == 0) {
                        getView().onSuccess(MyApplication.getAppContext().getString(R.string.collection_success));
                    } else {
                        getView().showFailed(MyApplication.getAppContext().getString(R.string.collection_failed));
                    }
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) throws Exception {
                    getView().showFailed(throwable.getMessage());
                }
            });
        } else {
            LoginActivity.start();
        }
    }

    /**
     * 收藏站外文章
     *
     * @param title  标题
     * @param author 作者
     * @param link   链接
     */
    @SuppressLint("CheckResult")
    public void collectOutsideArticle(final String title, String author, String link) {
        if (SPUtils.getInstance(Constant.SHARED_NAME).getBoolean(Constant.LOGIN_KEY)) {
            RetrofitManager.create(ApiService.class).addCollectOutsideArticle(title, author, link).compose(RxSchedulers.<DataResponse>applySchedulers()).subscribe(new Consumer<DataResponse>() {
                @Override
                public void accept(DataResponse response) throws Exception {
                    if (response.getErrorCode() == 0) {
                        getView().onSuccess(MyApplication.getAppContext().getString(R.string.collection_success));
                    } else {
                        getView().showFailed(MyApplication.getAppContext().getString(R.string.collection_failed));
                    }
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) throws Exception {
                    getView().showFailed(throwable.getMessage());
                }
            });
        } else {
            LoginActivity.start();
        }
    }
}
