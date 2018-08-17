package yinwuteng.com.mywanandroid.hotsearch;

import java.util.List;

import yinwuteng.com.mywanandroid.base.BaseView;
import yinwuteng.com.mywanandroid.bean.Article;
import yinwuteng.com.mywanandroid.db.HistoryModel;

/**
 * Create By yinwuteng
 * 2018/5/12.
 * 搜索视图
 */
public interface MySearchView extends BaseView{
    /***
     * 设置搜索文章
     * @param articles
     * @param loadType
     */
    void setSearchArticles(Article articles,int loadType);

    /***
     *收集文章成功
     * @param position
     * @param bean
     */
    void collectArticlesSuccess(int position,Article.DatasBean bean);

    /**
     * 设置历史记录
     * @param historyModels
     */
    void setHistory(List<HistoryModel> historyModels);

    /**
     * 添加历史记录成功
     * @param historyModel
     */
    void addHistorySuccess(HistoryModel historyModel);
}
