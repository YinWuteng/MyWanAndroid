package yinwuteng.com.mywanandroid.base;

/**
 * Created by yinwuteng on 2018/4/5.
 * 基类presenter
 */

public abstract class BasePresenter<T extends BaseView> {
  private T  mView;

    /**
     * 绑定view层
     * @param view
     */
  public void attachView(T view){
      this.mView=view;
  }

    /**
     * 解除绑定V层
     */
  public void detachView(){
      mView=null;
  }

    /**
     * 获取V层
     * @return
     */
  public T getView(){
      return mView;
  }
}
