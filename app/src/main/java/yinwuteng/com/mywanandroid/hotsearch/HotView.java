package yinwuteng.com.mywanandroid.hotsearch;

import java.util.List;

import yinwuteng.com.mywanandroid.base.BaseView;
import yinwuteng.com.mywanandroid.bean.Book;
import yinwuteng.com.mywanandroid.bean.HotKey;

/**
 * Create By yinwuteng
 * 2018/5/8.
 * 热门标签视图
 */
public interface HotView extends BaseView {
    void setHotData(List<HotKey> hotKeys, List<Book> books);

}
