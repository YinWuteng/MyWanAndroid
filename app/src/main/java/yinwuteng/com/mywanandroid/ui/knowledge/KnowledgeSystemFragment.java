package yinwuteng.com.mywanandroid.ui.knowledge;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import yinwuteng.com.mywanandroid.R;
import yinwuteng.com.mywanandroid.base.BaseFragment;
import yinwuteng.com.mywanandroid.bean.KnowledgeSystem;
import yinwuteng.com.mywanandroid.constant.Constant;

/**
 * Created by yinwuteng on 2018/4/6.
 * 知识体系
 */

public class KnowledgeSystemFragment extends BaseFragment<KnowledgeSystemView, KnowledgeSystemPresenter> implements KnowledgeSystemView, KnowledgeSystemAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private KnowledgeSystemAdapter mKnowledgeSystemAdapter = new KnowledgeSystemAdapter();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_knowledge;
    }

    @Override
    protected void initView(View view) {
        mSwipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        RecyclerView recyclerView = view.findViewById(R.id.rvKnowledgeSystems);

        /**设置recycleview*/
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mKnowledgeSystemAdapter);

        /***设置事件监听*/
        mKnowledgeSystemAdapter.setOnItemClickListener(this);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        /**设置数据*/
        getPresenter().loadKnowledgeSystem();
     }

    @Override
    protected KnowledgeSystemPresenter createPresenter() {
        return new KnowledgeSystemPresenter();
    }

    @Override
    public void showLoading() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void showFailed(String message) {

        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        getPresenter().refresh();
    }

    /**
     * 跳转详情列表
     *
     * @param adapter
     * @param view
     * @param position
     */
    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ARouter.getInstance().build("/article/ArticleTypeActivity")
                .withString(Constant.CONTENT_TITLE_KEY,mKnowledgeSystemAdapter.getItem(position).getName())
                .withObject(Constant.CONTENT_CHILDREN_DATA_KEY,mKnowledgeSystemAdapter.getItem(position).getChildren())
                .navigation();
    }

    /**
     * 加载知识体系
     *
     * @param knowledgeSystems
     */
    @Override
    public void setKnowledgeSystems(List<KnowledgeSystem> knowledgeSystems) {
        mKnowledgeSystemAdapter.setNewData(knowledgeSystems);
        mSwipeRefreshLayout.setRefreshing(false);
    }

    public static KnowledgeSystemFragment newInstance() {
        return new KnowledgeSystemFragment();
    }
}
