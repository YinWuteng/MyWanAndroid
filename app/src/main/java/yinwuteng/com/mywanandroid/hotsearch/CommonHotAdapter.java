package yinwuteng.com.mywanandroid.hotsearch;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import yinwuteng.com.mywanandroid.R;
import yinwuteng.com.mywanandroid.bean.Book;

/**
 * Create By yinwuteng
 * 2018/5/9.
 */
public class CommonHotAdapter extends BaseQuickAdapter<Book, BaseViewHolder> {
    public CommonHotAdapter() {
        super(R.layout.item_hot, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, Book item) {
        helper.setText(R.id.tvTitle, item.getName());
    }
}
