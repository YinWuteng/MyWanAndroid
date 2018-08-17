package yinwuteng.com.mywanandroid.hotsearch;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;

import java.lang.reflect.Field;
import java.util.List;

import yinwuteng.com.mywanandroid.R;

/**
 * Create By yinwuteng
 * 2018/5/2.
 * 热门标签适配器
 */
public class HotAdapter<T> extends TagAdapter<T> {
    private Context mContext;
    private LayoutInflater mInflate;

    public HotAdapter(Context context, List<T> datas) {
        super(datas);
        this.mContext = context;
        this.mInflate = LayoutInflater.from(mContext);
    }

    @Override
    public View getView(FlowLayout parent, int position, T item) {
        View view = mInflate.inflate(R.layout.item_hot, parent, false);
        TextView tvTittle = view.findViewById(R.id.tvTitle);
        int parseColor = 0;
        try {
            String name = "";
            Field[] fields = item.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                if (field.getName().equals("name")) {
                    name = (String) field.get(item);
                    break;
                }
            }
            tvTittle.setText(name);
            String str = Integer.toHexString((int) (Math.random() * 16777215));
            parseColor = Color.parseColor("#".concat(str));
            tvTittle.setTextColor(parseColor);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            parseColor = mContext.getResources().getColor(R.color.colorAccent);
        }
        tvTittle.setTextColor(parseColor);
        return view;
    }
}
