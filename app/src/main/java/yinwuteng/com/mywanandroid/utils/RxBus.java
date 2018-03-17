package yinwuteng.com.mywanandroid.utils;

import io.reactivex.Flowable;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;

/**
 * Created by yinwuteng on 2018/3/17.
 * RxBus
 */

public class RxBus {
    private static volatile RxBus sRxBus;
    //主题
    private final FlowableProcessor<Object> mBus;

    //PublishSubject只会把在订阅发生的时间点之后来自原始的Observable的数据发射给观察者
    public RxBus() {
        mBus = PublishProcessor.create().toSerialized();
    }

    //单例RxBus
    public static RxBus getxRxBus() {
        if (sRxBus == null) {
            synchronized (RxBus.class) {
                if (sRxBus == null) {
                    sRxBus = new RxBus();
                }
            }
        }
        return sRxBus;
    }

    /**
     * 提供了一个心得事件
     *
     * @param o
     */
    public void post(Object o) {
        mBus.onNext(o);
    }

    /**
     * 根据传递 eventype类型返回特定类型的(eventype)的被观察者
     * @param eventType
     * @param <T>
     * @return
     */
    public <T> Flowable<T> toFlowable(Class<T> eventType) {
        return mBus.ofType(eventType);
    }
}
