package yinwuteng.com.mywanandroid.hotsearch;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;

import java.util.List;

import yinwuteng.com.mywanandroid.R;
import yinwuteng.com.mywanandroid.db.HistoryModel;

/**
 * Create By yinwuteng
 * 2018/5/12.
 */
public class HistoryAdapter extends TagAdapter<HistoryModel> {
   private Context mContext;
   private LayoutInflater mInfalte;

    public HistoryAdapter(Context context, List<HistoryModel> datas){
       super(datas);
       this.mContext=context;
       this.mInfalte=LayoutInflater.from(mContext);
   }

    @Override
    public View getView(FlowLayout parent, int position, HistoryModel historyModel) {
        View view=mInfalte.inflate(R.layout.item_history,parent,false);
        TextView tvTitle =view.findViewById(R.id.tvTitle);
        int parseColor = 0;
        try {
            tvTitle.setText(historyModel.getName());
            String str = Integer.toHexString((int) (Math.random() * 16777215));
            parseColor = Color.parseColor("#".concat(str));
            tvTitle.setTextColor(parseColor);
        } catch (Exception e) {
            e.printStackTrace();
            parseColor = mContext.getResources().getColor(R.color.colorAccent);
        }
        tvTitle.setTextColor(parseColor);
        return view;
    }
}
