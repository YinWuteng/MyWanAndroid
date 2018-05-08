package yinwuteng.com.mywanandroid.ui.home.article;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.alibaba.android.arouter.launcher.ARouter;

import java.util.ArrayList;
import java.util.List;

import yinwuteng.com.mywanandroid.bean.KnowledgeSystem;
import yinwuteng.com.mywanandroid.constant.Constant;

/**
 * Create By yinwuteng
 * 2018/5/7.
 */
public class ArticleTypeFragmentPagerAdapter extends FragmentPagerAdapter {
    private List<KnowledgeSystem.ChildrenBean> mChildrenDatas;
    private List<ArticleListFragment> mArticleListFragments;

    public ArticleTypeFragmentPagerAdapter(FragmentManager fm, List<KnowledgeSystem.ChildrenBean> childrenBeans) {
        super(fm);
        this.mChildrenDatas = childrenBeans;
        mArticleListFragments = new ArrayList<>();
        if (mChildrenDatas == null) return;
        for (KnowledgeSystem.ChildrenBean childrenBean : mChildrenDatas) {
            //添加类型文章
            ArticleListFragment articleListFragment = (ArticleListFragment) ARouter.getInstance().build("/article/ArticleListFragment")
                    .withInt(Constant.CONTENT_CID_KEY, childrenBean.getId()).navigation();
            mArticleListFragments.add(articleListFragment);
        }
    }

    @Override
    public Fragment getItem(int position) {
        return mArticleListFragments.get(position);
    }

    @Override
    public int getCount() {
        return mArticleListFragments.size();
    }

    public CharSequence getPageTitle(int position) {
        return mChildrenDatas.get(position).getName();
    }
}
