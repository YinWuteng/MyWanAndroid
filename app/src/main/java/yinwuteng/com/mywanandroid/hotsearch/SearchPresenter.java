package yinwuteng.com.mywanandroid.hotsearch;

import android.annotation.SuppressLint;

import java.util.Date;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;
import yinwuteng.com.mywanandroid.base.BasePresenter;
import yinwuteng.com.mywanandroid.bean.Article;
import yinwuteng.com.mywanandroid.bean.DataResponse;
import yinwuteng.com.mywanandroid.constant.Constant;
import yinwuteng.com.mywanandroid.db.HistoryModel;
import yinwuteng.com.mywanandroid.db.HistoryModel_Table;
import yinwuteng.com.mywanandroid.net.ApiService;
import yinwuteng.com.mywanandroid.net.RetrofitManager;
import yinwuteng.com.mywanandroid.utils.ArticleUtils;
import yinwuteng.com.mywanandroid.utils.RxSchedulers;

import com.raizlabs.android.dbflow.sql.language.SQLite;

/**
 * Create By yinwuteng
 * 2018/5/12.
 */
public class SearchPresenter extends BasePresenter<MySearchView> {

    private int mPage;
    private boolean mIsRefresh;
    private String mK;

    public SearchPresenter() {
        this.mIsRefresh = true;
    }

    /***
     * 加载搜索文章
     * @param k 搜索关键字
     */
    @SuppressLint("CheckResult")
    public void loadSearchArticles(String k) {
        this.mK = k;
        RetrofitManager.create(ApiService.class).getSearchArticles(mPage, mK).compose(RxSchedulers.<DataResponse<Article>>applySchedulers()).subscribe(new Consumer<DataResponse<Article>>() {
            @Override
            public void accept(DataResponse<Article> articleDataResponse) throws Exception {
                int loadType = mIsRefresh ? Constant.LOADTYPE_REFRESH_SUCCESS : Constant.LOADTYPE_LOAD_MORE_SUCCESS;
                getView().setSearchArticles(articleDataResponse.getData(), loadType);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                int loadType = mIsRefresh ? Constant.LOADTYPE_REFRESH_ERROR : Constant.LOADTYPE_LOAD_MORE_ERROR;
                getView().setSearchArticles(new Article(), loadType);
            }
        });
    }

    /**
     * 刷新
     */
    public void refresh() {
        mPage = 0;
        mIsRefresh = true;
        loadSearchArticles(mK);
    }

    /**
     * 加载更多
     */
    public void loadMore() {
        mPage++;
        mIsRefresh = false;
        loadSearchArticles(mK);
    }

    /**
     * 收藏
     *
     * @param position
     * @param bean
     */
    public void collectArticle(final int position, Article.DatasBean bean) {
        ArticleUtils.collectArtivle(getView(), position, bean);
    }

    /**
     * 加载历史
     */
    @SuppressLint("CheckResult")
    public void loadHistory() {
        getView().showLoading();
        Observable.create(new ObservableOnSubscribe<List<HistoryModel>>() {
            @Override
            public void subscribe(ObservableEmitter<List<HistoryModel>> e) {
                List<HistoryModel> historyModels = SQLite.select().from(HistoryModel.class).orderBy(HistoryModel_Table.date, false).limit(10).offset(0).queryList();
                e.onNext(historyModels);
            }
        }).compose(RxSchedulers.<List<HistoryModel>>applySchedulers()).subscribe(new Consumer<List<HistoryModel>>() {
            @Override
            public void accept(List<HistoryModel> historyModels) throws Exception {
                getView().setHistory(historyModels);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                getView().showFailed(throwable.getMessage());
            }
        });
    }

    public void addHistory(String name){
        HistoryModel historyModel=new HistoryModel();
        historyModel.setName(name);
        historyModel.setDate(new Date());
        long id=historyModel.insert();
        if (id>0)getView().addHistorySuccess(historyModel);
    }
}
