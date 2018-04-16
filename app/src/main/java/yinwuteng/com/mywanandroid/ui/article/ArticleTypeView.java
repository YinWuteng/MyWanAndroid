package yinwuteng.com.mywanandroid.ui.article;

import yinwuteng.com.mywanandroid.base.BaseView;
import yinwuteng.com.mywanandroid.bean.Article;

/**
 * Create By yinwuteng
 * 2018/4/16.
 * ArticleTypeView
 */
public interface ArticleTypeView extends BaseView {
    /**
     * 加载文章
     * @param article 文章
     * @param loadType 加载类型
     */
    void setKnowledgeSystemArticles(Article article, int loadType);

    /**
     * 收藏文章成功
     * @param position 文章位置
     * @param bean 位置内容
     */
    void collectArticleSuccess(int position,Article.DatasBean bean);
}
