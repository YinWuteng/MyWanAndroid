package yinwuteng.com.mywanandroid.hotsearch;

import android.annotation.SuppressLint;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import yinwuteng.com.mywanandroid.base.BasePresenter;
import yinwuteng.com.mywanandroid.bean.Book;
import yinwuteng.com.mywanandroid.bean.DataResponse;
import yinwuteng.com.mywanandroid.bean.HotKey;
import yinwuteng.com.mywanandroid.constant.Constant;
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
        Observable.zip(observableBook, observableHotkey, new BiFunction<DataResponse<List<Book>>, DataResponse<List<HotKey>>, Map<String, Object>>() {
            @Override
            public Map<String, Object> apply(DataResponse<List<Book>> response, DataResponse<List<HotKey>> response2) throws Exception {
                Map<String, Object> objMap = new HashMap<>();
                objMap.put(Constant.CONTENT_HOT_KEY, response2.getData());
                objMap.put(Constant.CONTENT_HOT_BOOK_KEY, response.getData());
                return objMap;
            }
        }).compose(RxSchedulers.<Map<String, Object>>applySchedulers()).subscribe(new Consumer<Map<String, Object>>() {
            @Override
            public void accept(Map<String, Object> map) throws Exception {
                List<HotKey> hotKeys = (List<HotKey>) map.get(Constant.CONTENT_HOT_KEY);
                List<Book> friends = (List<Book>) map.get(Constant.CONTENT_HOT_BOOK_KEY);
                getView().setHotData(hotKeys, friends);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                getView().showFailed(throwable.getMessage());
            }
        });
    }

    public void refresh(){
        loadHotData();
    }
}
