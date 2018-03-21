package yinwuteng.com.mywanandroid.ui.home;


import android.text.Html;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;


import yinwuteng.com.mywanandroid.R;
import yinwuteng.com.mywanandroid.bean.Article;

/**
 * Created by yinwuteng on 2018/3/20.
 * 主页文章适配器
 */

public class ArticleAdapter extends BaseQuickAdapter<Article.DatasBean, BaseViewHolder> {
    private boolean mChapterNameVisible = true;
    private boolean mIsMyColection = false;

    public ArticleAdapter() {
        super(R.layout.item_article);

    }

    /**
     * 数据绑定与赋值
     *
     * @param helper
     * @param item
     */
    @Override
    protected void convert(BaseViewHolder helper, Article.DatasBean item) {
        //绑定数据
        helper.setText(R.id.tvAuthor, item.getAuthor());
        helper.setText(R.id.tvNiceDate, item.getNiceDate());
        helper.setText(R.id.tvTitle, Html.fromHtml(item.getTitle()));
        //是否关注
        if (mIsMyColection) item.setCollect(mIsMyColection);
        helper.setImageResource(R.id.ivCollect, item.isCollect() ? R.drawable.ic_action_like : R.drawable.ic_action_no_like);

        helper.addOnClickListener(R.id.tvChapterName);
        helper.addOnClickListener(R.id.ivCollect);
        helper.setVisible(R.id.tvChapterName, mChapterNameVisible);
    }

    /**
     * 是否显示章节名称
     *
     * @param chapterNameVisible
     */
    public void setChapterNameVisible(boolean chapterNameVisible) {
        this.mChapterNameVisible = chapterNameVisible;
    }

    /**
     * 是否关注
     *
     * @param isMyCollection
     */
    public void isMyCollection(boolean isMyCollection) {
        this.mIsMyColection = isMyCollection;
    }
}
