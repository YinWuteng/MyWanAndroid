package yinwuteng.com.mywanandroid.base;

/**
 * Created by yinwuteng on 2018/4/5.
 * 基类view接口
 */

public interface BaseView {
    /**
     * 请求成功
     */
    void onSuccess(String message);
    /**
     * 请求失败
     * @param message
     */
    void showFailed(String message);

    /**
     * 显示网络不可用
     */
    void showNoNet();
    /**
     * 显示进度
     */
    void showLoading();

    /**
     * 隐藏进度
     */
    void hideLoading();
}
