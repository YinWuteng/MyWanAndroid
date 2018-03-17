package yinwuteng.com.mywanandroid.base;


import com.trello.rxlifecycle2.LifecycleTransformer;

/**
 * Created by yinwuteng on 2018/3/10.
 * basecontract
 */

public interface BaseContTract {
    interface BasePresenter<T extends BaseContTract.BaseView> {
       //创建视图
        void attachView(T view);
       //销毁视图
        void detachView();
    }

    interface BaseView {
        //显示进度中
        void showLoading();

        //隐藏进度
        void hideLoading();

        //显示请求成功
        void showSuccess(String message);

        //失败重试
        void showFailed(String message);

        //显示当前网络不可用
        void showNoNet();

        //重试
        void onRetry();

        /**
         * 绑定生命周期
         *
         * @param <T>
         * @return
         */
        <T> LifecycleTransformer<T> bindToLife();


    }
}
