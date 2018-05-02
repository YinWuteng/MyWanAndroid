package yinwuteng.com.mywanandroid.utils;

import io.reactivex.Flowable;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;

/**
 * Create By yinwuteng
 * 2018/4/23.
 * RxBus工具
 */
public class RxBus {
    private static volatile RxBus sRxBus;
    //主题
    private final FlowableProcessor<Object> mBus;

    /**
     * PublishSubject只会把在订阅发生的时间点之后来自原始的Observable的数据发送给观察者
     */
    public RxBus() {
        mBus = PublishProcessor.create().toSerialized();
    }

    /**
     * 单例RxBus
     *
     * @return
     */
    public static RxBus getInstance() {
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
     * 提供一个新的事件
     * @param o
     */
    public void post(Object o){
        mBus.onNext(o);
    }

    /**
     * 根据传递的eventType类型返回特定类型eventType的被观察者
     * @param eventType 传递事件类型
     * @param <T>
     * @return
     */
    public <T> Flowable<T> toFlowable(Class<T> eventType){
        return mBus.ofType(eventType);
    }
}
