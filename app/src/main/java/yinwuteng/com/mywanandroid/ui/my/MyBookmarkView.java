package yinwuteng.com.mywanandroid.ui.my;

import java.util.List;

import yinwuteng.com.mywanandroid.base.BaseView;
import yinwuteng.com.mywanandroid.bean.Book;

/**
 * Create By yinwuteng
 * 2018/4/23.
 */
public interface MyBookmarkView extends BaseView {
    void setMyBookMarks(List<Book> bookmarks);
}
