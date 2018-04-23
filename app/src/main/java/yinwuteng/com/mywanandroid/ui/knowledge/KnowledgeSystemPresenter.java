package yinwuteng.com.mywanandroid.ui.knowledge;

import android.annotation.SuppressLint;

import java.util.List;

import io.reactivex.functions.Consumer;
import yinwuteng.com.mywanandroid.base.BasePresenter;
import yinwuteng.com.mywanandroid.bean.DataResponse;
import yinwuteng.com.mywanandroid.bean.KnowledgeSystem;
import yinwuteng.com.mywanandroid.net.ApiService;
import yinwuteng.com.mywanandroid.net.RetrofitManager;
import yinwuteng.com.mywanandroid.utils.RxSchedulers;

/**
 * Create By yinwuteng
 * 2018/4/21.
 * 知识体系控制器
 */
public class KnowledgeSystemPresenter extends BasePresenter<KnowledgeSystemView> {
    public KnowledgeSystemPresenter() {
    }

    /**
     * 加载知识体系
     */
    @SuppressLint("CheckResult")
    public void loadKnowledgeSystem() {
        getView().showLoading();
        RetrofitManager.create(ApiService.class).getKnowledgeSystems().compose(RxSchedulers.<DataResponse<List<KnowledgeSystem>>>applySchedulers()).subscribe(new Consumer<DataResponse<List<KnowledgeSystem>>>() {
            @Override
            public void accept(DataResponse<List<KnowledgeSystem>> dataResponse) throws Exception {
                getView().setKnowledgeSystems(dataResponse.getData());
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                getView().showFailed(throwable.getMessage());
            }
        });
    }

    /**
     * 刷新
     */
    public void refresh() {
        loadKnowledgeSystem();
    }
}
