package yinwuteng.com.mywanandroid.ui.home;

import android.annotation.SuppressLint;

import com.blankj.utilcode.util.SPUtils;


import java.util.HashMap;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

import io.reactivex.functions.Function3;
import yinwuteng.com.mywanandroid.R;
import yinwuteng.com.mywanandroid.base.BasePresenter;
import yinwuteng.com.mywanandroid.base.MyApplication;
import yinwuteng.com.mywanandroid.bean.Article;
import yinwuteng.com.mywanandroid.bean.Banner;
import yinwuteng.com.mywanandroid.bean.DataResponse;
import yinwuteng.com.mywanandroid.bean.User;
import yinwuteng.com.mywanandroid.constant.Constant;
import yinwuteng.com.mywanandroid.net.ApiService;
import yinwuteng.com.mywanandroid.net.RetrofitManager;
import yinwuteng.com.mywanandroid.utils.ArticleUtils;
import yinwuteng.com.mywanandroid.utils.RxSchedulers;

/**
 * Created by yinwuteng on 2018/4/5.
 * HomePresenter
 * 主界面控制器
 */

public class HomePresenter extends BasePresenter<HomeView> {
    private int mPage;
    private boolean mIsRefresh;

    public HomePresenter() {
        this.mIsRefresh = true;
    }

    /**
     * 加载头部类容
     */
    @SuppressLint("CheckResult")
    public void loadHomeBanners() {
        RetrofitManager.create(ApiService.class).getHomeBanners().compose(RxSchedulers.<DataResponse<List<Banner>>>applySchedulers()).subscribe(new Consumer<DataResponse<List<Banner>>>() {
            @Override
            public void accept(DataResponse<List<Banner>> listDataResponse) throws Exception {
                getView().setHomeBanners(listDataResponse.getData());
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                getView().showFailed(throwable.getMessage());
            }
        });
    }

    /**
     * 加载文章内容
     */
    @SuppressLint("CheckResult")
    public void loadHomeArticles() {
        RetrofitManager.create(ApiService.class)
                .getHomeArticles(mPage)
                .compose(RxSchedulers.<DataResponse<Article>>applySchedulers())
                .subscribe(new Consumer<DataResponse<Article>>() {
            @Override
            public void accept(DataResponse<Article> articleDataResponse) throws Exception {
                int loadType = mIsRefresh ? Constant.LOADTYPE_REFRESH_SUCCESS : Constant.LOADTYPE_LOAD_MORE_SUCCESS;
                getView().setHomeArticles(articleDataResponse.getData(), loadType);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                int loadType = mIsRefresh ? Constant.LOADTYPE_REFRESH_ERROR : Constant.LOADTYPE_LOAD_MORE_ERROR;
                getView().setHomeArticles(new Article(), loadType);
            }
        });
    }

    /**
     * 刷新
     */
    public void refresh() {
        mPage = 0;
        mIsRefresh = true;
        loadHomeBanners();
        loadHomeArticles();
    }

    /**
     * 加载更多
     */
    public void loadMore() {
        mPage++;
        mIsRefresh = false;
        loadHomeArticles();
    }

    public void collectArticles(int position, Article.DatasBean bean) {
        /**文章收藏*/
        ArticleUtils.collectArtivle(getView(),position,bean);
    }

    /**
     * 加载主页数据
     */

    @SuppressLint("CheckResult")
    public void loadHomeData() {
        getView().showLoading();
        String username = SPUtils.getInstance(Constant.SHARED_NAME).getString(Constant.USERNAME_KEY);
        String password = SPUtils.getInstance(Constant.SHARED_NAME).getString(Constant.PASSWORD_KEY);
        Observable<DataResponse<User>> observableUser = RetrofitManager.create(ApiService.class).login(username, password);
        Observable<DataResponse<List<Banner>>> observableBanner = RetrofitManager.create(ApiService.class).getHomeBanners();
        Observable<DataResponse<Article>> observableArticle = RetrofitManager.create(ApiService.class).getHomeArticles(mPage);
        /**
         * 使用zip的目的是等待上面的三个网络请求均完成后再开始执行保存任务
         */
        Observable.zip(observableUser, observableBanner, observableArticle, new Function3<DataResponse<User>, DataResponse<List<Banner>>, DataResponse<Article>, Map<String, Object>>() {
            @Override
            public Map<String, Object> apply(DataResponse<User> response, DataResponse<List<Banner>> listDataResponse, DataResponse<Article> articleDataResponse) throws Exception {
                Map<String, Object> objMap = new HashMap<>();
                objMap.put(Constant.USERNAME_KEY, response);
                objMap.put(Constant.BANNER_KEY, listDataResponse.getData());
                objMap.put(Constant.ARTICLE_KEY, articleDataResponse.getData());
                return objMap;
            }
        }).compose(RxSchedulers.<Map<String, Object>>applySchedulers()).subscribe(new Consumer<Map<String, Object>>() {
            @Override
            public void accept(Map<String, Object> map) throws Exception {
                /**当用户登录后没有退出，然后再次进入到主页时*/
                DataResponse<User> dataResponse = (DataResponse<User>) map.get(Constant.USERNAME_KEY);
                if (dataResponse.getErrorCode() == 0) {
                    getView().onSuccess(MyApplication.getAppContext().getString(R.string.auto_login_success));
                } else {
                    getView().showFailed(String.valueOf(dataResponse.getErrorMsg()));
                }
                List<Banner> banners = (List<Banner>) map.get(Constant.BANNER_KEY);
                Article article = (Article) map.get(Constant.ARTICLE_KEY);
                getView().setHomeBanners(banners);
                getView().setHomeArticles(article, Constant.LOADTYPE_REFRESH_SUCCESS);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                getView().showFailed(throwable.getMessage());
            }
        });
    }
}
