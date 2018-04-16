package yinwuteng.com.mywanandroid.utils;

import android.annotation.SuppressLint;

import com.blankj.utilcode.util.SPUtils;

import io.reactivex.functions.Consumer;
import yinwuteng.com.mywanandroid.R;
import yinwuteng.com.mywanandroid.base.BaseView;
import yinwuteng.com.mywanandroid.base.MyApplication;
import yinwuteng.com.mywanandroid.bean.Article;
import yinwuteng.com.mywanandroid.bean.DataResponse;
import yinwuteng.com.mywanandroid.constant.Constant;
import yinwuteng.com.mywanandroid.net.ApiService;
import yinwuteng.com.mywanandroid.net.RetrofitManager;
import yinwuteng.com.mywanandroid.ui.home.HomeView;
import yinwuteng.com.mywanandroid.ui.login.LoginActivity;

/**
 * Create By yinwuteng
 * 2018/4/15.
 * 文章操作工具类
 */
public class ArticleUtils {
    /**
     * 文章收藏
     *
     * @param view     视图
     * @param position 位置
     * @param bean     数据bean
     */
    @SuppressLint("CheckResult")
    public static void collectArtivle(final BaseView view, final int position, final Article.DatasBean bean) {
        //判断是否登录
        if (SPUtils.getInstance(Constant.SHARED_NAME).getBoolean(Constant.LOGIN_KEY)) {
            if (bean.isCollect()) {//若已经收藏过，再次点击则为删除收藏
                RetrofitManager.create(ApiService.class).removeCollectArticle(bean.getId(), -1).compose(RxSchedulers.<DataResponse>applySchedulers()).subscribe(new Consumer<DataResponse>() {
                    @Override
                    public void accept(DataResponse response) throws Exception {
                        if (response.getErrorCode() == 0) {
                            bean.setCollect(!bean.isCollect());
                            if (view instanceof HomeView) {//如果是首页，则收藏成功
                                ((HomeView) view).collectArticlesSuccess(position, bean);
                            }
                            //取消收藏成功
                            view.onSuccess(MyApplication.getAppContext().getString(R.string.collection_cancel_success));
                        } else {
                            //取消收藏失败
                            view.showFailed(MyApplication.getAppContext().getString(R.string.collection_cancel_failed));
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        view.showFailed(throwable.getMessage());
                    }
                });
            } else { //若之前没有收藏过,则点击为收藏
                RetrofitManager.create(ApiService.class).addCollectArticle(bean.getId()).compose(RxSchedulers.<DataResponse>applySchedulers()).subscribe(new Consumer<DataResponse>() {
                    @Override
                    public void accept(DataResponse response) throws Exception {
                        if (response.getErrorCode() == 0) {
                            bean.setCollect(!bean.isCollect());
                            if (view instanceof HomeView) {
                                ((HomeView) view).collectArticlesSuccess(position, bean);
                            }
                            view.onSuccess(MyApplication.getAppContext().getString(R.string.collection_success));
                        } else {
                            view.showFailed(MyApplication.getAppContext().getString(R.string.collection_failed));
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        view.showFailed(throwable.getMessage());
                    }
                });
            }
        } else {
            LoginActivity.start();
        }
    }


}
