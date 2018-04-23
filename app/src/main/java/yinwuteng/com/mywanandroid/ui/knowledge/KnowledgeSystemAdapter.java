package yinwuteng.com.mywanandroid.ui.knowledge;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import yinwuteng.com.mywanandroid.R;
import yinwuteng.com.mywanandroid.bean.KnowledgeSystem;

/**
 * Create By yinwuteng
 * 2018/4/21.
 * 知识体系适配器
 */
public class KnowledgeSystemAdapter extends BaseQuickAdapter<KnowledgeSystem, BaseViewHolder> {
    public KnowledgeSystemAdapter() {
        super(R.layout.item_knowledge_system, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, KnowledgeSystem item) {
        helper.setText(R.id.typeItemFirst, item.getName());
        StringBuffer sb = new StringBuffer();
        for (KnowledgeSystem.ChildrenBean childrenBean : item.getChildren()) {
            sb.append(childrenBean.getName() + "  ");
        }
        helper.setText(R.id.typeItemSecond, sb.toString());
    }
}
