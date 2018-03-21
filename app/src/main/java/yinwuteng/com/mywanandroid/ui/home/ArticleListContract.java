package yinwuteng.com.mywanandroid.ui.home;

import yinwuteng.com.mywanandroid.base.BaseContract;
import yinwuteng.com.mywanandroid.bean.Article;

/**
 * Created by yinwuteng on 2018/3/21.
 * 文章集合连接器,
 */

public interface ArticleListContract {
    interface View extends BaseContract.BaseView {
        /**
         * 设置知识体系下的文章到界面
         *
         * @param article
         * @param loadType
         */
        void setKnowledgeSystemArticles(Article article, int loadType);

        void collectArticlesSuccess(int position, Article.DatasBean bean);
    }

    interface Presenter extends BaseContract.BasePresenter<ArticleListContract.View> {

        /**
         * 加载知识系统文章
         *
         * @param cid
         */
        void loadKnowledgeSystemArticles(int cid);

        void refresh();

        void loadMore();

        void collectArticle(int position, Article.DatasBean bean);
    }
}
