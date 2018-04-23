package yinwuteng.com.mywanandroid.ui.my;

import android.annotation.SuppressLint;

import java.util.List;

import io.reactivex.functions.Consumer;
import yinwuteng.com.mywanandroid.base.BasePresenter;
import yinwuteng.com.mywanandroid.bean.Book;
import yinwuteng.com.mywanandroid.bean.DataResponse;
import yinwuteng.com.mywanandroid.net.ApiService;
import yinwuteng.com.mywanandroid.net.RetrofitManager;
import yinwuteng.com.mywanandroid.utils.RxSchedulers;

/**
 * Create By yinwuteng
 * 2018/4/23.
 * 我的书签控制器
 */
public class MyBookPresenter extends BasePresenter<MyBookmarkView> {
    private boolean mIsRefresh;

    public MyBookPresenter() {
        this.mIsRefresh = true;
    }

    /**
     * 加载我的书签
     */
    @SuppressLint("CheckResult")
    public void loadMyBookmarks() {
        getView().showLoading();
        RetrofitManager.create(ApiService.class).getBookmarks().compose(RxSchedulers.<DataResponse<List<Book>>>applySchedulers()).subscribe(new Consumer<DataResponse<List<Book>>>() {
            @Override
            public void accept(DataResponse<List<Book>> listDataResponse) throws Exception {
                getView().setMyBooknarks(listDataResponse.getData());
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                getView().showFailed(throwable.getMessage());
            }
        });
    }


    /**
     * 编辑标签
     *
     * @param id
     * @param name
     * @param link
     */
    public void editBookmark(int id, String name, String link) {

    }

    /**
     * 删除标签
     *
     * @param id
     */
    public void delBookmark(int id) {

    }

    public void refresh() {
        mIsRefresh = true;
        loadMyBookmarks();
    }
}
