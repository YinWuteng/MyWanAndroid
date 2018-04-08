package yinwuteng.com.mywanandroid.ui.home;



import java.util.List;

import yinwuteng.com.mywanandroid.base.BaseView;
import yinwuteng.com.mywanandroid.bean.Article;
import yinwuteng.com.mywanandroid.bean.Banner;

/**
 * Created by yinwuteng on 2018/4/5.
 * HomeView
 */

public interface HomeView extends BaseView {
    /**
     * 设置动态切换头部
     *
     * @param banners 头部动画
     */
    void setHomeBanners(List<Banner> banners);
    /**
     * 设置加载文章
     *
     * @param article  文章
     * @param loadType 加载类型
     */
    void setHomeArticles(Article article, int loadType);

    /**
     * 收藏文章成功
     *
     * @param position 文章所在位置
     * @param bean     内容
     */
    void collectArticlesSuccess(int position, Article.DatasBean bean);
}
