package yinwuteng.com.mywanandroid.db;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.Date;

/**
 * Create By yinwuteng
 * 2018/5/12.
 * 历史记录模型
 * DBFlow会根据你的类名自动生成一个声明，以此为例
 *这个类对应的表名为 HistoryModel_Table
 */
@Table(database = AppDatabase.class)
public class HistoryModel extends BaseModel{
    @PrimaryKey(autoincrement = true)
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Column
    private String name;
    @Column
    private Date date;

}
