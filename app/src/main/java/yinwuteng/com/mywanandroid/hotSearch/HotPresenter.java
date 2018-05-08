package yinwuteng.com.mywanandroid.hotSearch;

import android.annotation.SuppressLint;

import java.util.List;
import java.util.Map;


import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import yinwuteng.com.mywanandroid.base.BasePresenter;
import yinwuteng.com.mywanandroid.bean.Book;
import yinwuteng.com.mywanandroid.bean.DataResponse;
import yinwuteng.com.mywanandroid.bean.HotKey;
import yinwuteng.com.mywanandroid.net.ApiService;
import yinwuteng.com.mywanandroid.net.RetrofitManager;
import yinwuteng.com.mywanandroid.utils.RxSchedulers;

/**
 * Create By yinwuteng
 * 2018/5/8.
 * 热门标签控制器
 */
public class HotPresenter extends BasePresenter<HotView> {
    public HotPresenter() {
    }

    @SuppressLint("CheckResult")
    public void loadHotData() {
        getView().showLoading();
        Observable<DataResponse<List<Book>>> observableBook = RetrofitManager.create(ApiService.class).getHotBooks();
        Observable<DataResponse<List<HotKey>>> observableHotkey = RetrofitManager.create(ApiService.class).getHotKeys();
        Observable.zip(observableBook, observableHotkey, new BiFunction<DataResponse<List<Book>>, DataResponse<List<HotKey>>, Object>() {
            @Override
            public Map<String, Object> apply(DataResponse<List<Book>> listDataResponse, DataResponse<List<HotKey>> listDataResponse2) throws Exception {


                return null;
            }
        }).compose(RxSchedulers.applySchedulers()).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {

            }
        });

    }
}
