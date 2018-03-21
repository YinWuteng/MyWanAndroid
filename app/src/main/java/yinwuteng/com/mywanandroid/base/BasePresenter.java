package yinwuteng.com.mywanandroid.base;

/**
 * Created by yinwuteng on 2018/3/21.
 * 基类控制器
 */

public class BasePresenter<T extends BaseContract.BaseView> implements BaseContract.BasePresenter<T> {
    protected T mView;

    @Override
    public void attachView(T view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        if (mView != null) {
            mView = null;
        }
    }
}
