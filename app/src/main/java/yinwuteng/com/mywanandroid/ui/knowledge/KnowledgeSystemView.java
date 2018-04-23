package yinwuteng.com.mywanandroid.ui.knowledge;

import java.util.List;

import yinwuteng.com.mywanandroid.base.BaseView;
import yinwuteng.com.mywanandroid.bean.KnowledgeSystem;

/**
 * Create By yinwuteng
 * 2018/4/21.
 * 知识体系视图接口
 */
public interface KnowledgeSystemView extends BaseView{
   void setKnowledgeSystems(List<KnowledgeSystem> knowledgeSystems);
}
