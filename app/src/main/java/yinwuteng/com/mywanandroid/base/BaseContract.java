package yinwuteng.com.mywanandroid.base;

import com.trello.rxlifecycle2.LifecycleTransformer;

/**
 * Created by yinwuteng on 2018/3/21.
 * 连接器基类,通用基类写法
 */

public interface BaseContract {
    interface BasePresenter<T extends BaseContract.BaseView> {
        /**
         * 连接视图
         *
         * @param view
         */
        void attachView(T view);

        /**
         * 解除连接
         */
        void detachView();
    }

    interface BaseView {

        /**
         * 显示进度
         */
        void showLoading();

        /**
         * 隐藏进度
         */
        void hideLoading();

        /**
         * 显示请求成功
         *
         * @param message
         */
        void showSuccess(String message);

        /**
         * 失败重试
         *
         * @param message
         */
        void showFailed(String message);

        /**
         * 重试
         */
        void onRetry();

        /**
         * 当前网络不可用
         */
        void showNoNet();

        /**
         * 视图绑定生命周期
         *
         * @param <T>
         * @return
         */
        <T> LifecycleTransformer<T> bindToLife();
    }
}
