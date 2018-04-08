package yinwuteng.com.mywanandroid.ui.knowledge;

import android.view.View;

import yinwuteng.com.mywanandroid.R;
import yinwuteng.com.mywanandroid.base.BaseFragment;
import yinwuteng.com.mywanandroid.base.BasePresenter;

/**
 * Created by yinwuteng on 2018/4/6.
 */

public class KnowledgeFragment extends BaseFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_knowledge;
    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void onSuccess(String message) {

    }

    @Override
    public void showFailed(String message) {

    }


}
