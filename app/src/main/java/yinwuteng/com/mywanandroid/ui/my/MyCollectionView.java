package yinwuteng.com.mywanandroid.ui.my;

import yinwuteng.com.mywanandroid.base.BaseView;
import yinwuteng.com.mywanandroid.bean.Article;

/**
 * Create By yinwuteng
 * 2018/5/3.
 * 我的收藏
 */
public interface MyCollectionView extends BaseView{
    /**
     * 我收藏的文章
     *
     * @param articles
     * @param loadType
     */
    void setMyCollectArticles(Article articles, int loadType);

    /**
     * 为收藏成功
     * @param position
     */
    void unCollectArticleSuccess(int position);
}
