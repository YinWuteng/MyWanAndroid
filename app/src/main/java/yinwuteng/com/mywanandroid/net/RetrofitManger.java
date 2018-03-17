package yinwuteng.com.mywanandroid.net;

import com.blankj.utilcode.util.NetworkUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import yinwuteng.com.mywanandroid.base.MyApplication;
import yinwuteng.com.mywanandroid.constant.Constant;

/**
 * Created by yinwuteng on 2018/3/14.
 * Retrofie管理类
 */

public class RetrofitManger {
    /**
     * 连接超时时间
     */
    private static long CONNECT_TIMEOUT = 60L;
    /**
     * 读超时时间
     */
    private static long READ_TIMEOUT = 10L;
    /**
     * 写超时时间
     */
    private static long WRITE_TIMEOUT = 10L;
    /**
     * 设缓存有效期为1天
     */
    private static final long CACHE_STALE_SEC = 60 * 60 * 24;
    /**
     * 查询缓存的Cache-Control设置，为if-only-cache时只查询缓存而不会请求服务器
     * max-stale可以配合设置缓存失效时间
     */
    public static final String CACHE_CONTROL_CACHE = "onle-if-cached,max-stale=" + CACHE_STALE_SEC;

    private static volatile OkHttpClient mOkhttpClient;
    /**
     * 云端响应头拦截器，用来配置缓存策略
     */
    private static final Interceptor mRewriteCacheControlInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            //网络未连接，从缓存中获取请求
            if (!NetworkUtils.isConnected()) {
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
            }
            Response originaResponse = chain.proceed(request);
            if (NetworkUtils.isConnected()) {
                //有网的时候就读取接口上的@Headers里的配置，可以在这里进行统一的配置
                String cacheControl = request.cacheControl().toString();
                return originaResponse.newBuilder()
                        .header("Cache-Control", cacheControl)
                        .removeHeader("Pragma")
                        .build();
            } else {
                return originaResponse.newBuilder()
                        .header("Cache-Control",
                                "public only-if-cached,max-stale"
                                        + CACHE_CONTROL_CACHE)
                        .removeHeader("Pragma")
                        .build();
            }
        }
    };

    /**
     * 日志拦截器
     */
    private static final Interceptor mLoggingInterceptor=new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
           Request request=chain.request();
            return chain.proceed(request);
        }
    };
    /**
     * 获取OkHttpClient实例
     */
    private static OkHttpClient getmOkhttpClient(){
        if (mOkhttpClient!=null){
            synchronized (RetrofitManger.class){
                Cache cache=new Cache(new File(MyApplication.getAppContext().getCacheDir(),"HttpCache"),
                        1024*1024*100);
                if (mOkhttpClient==null){
                    mOkhttpClient=new OkHttpClient.Builder().cache(cache)
                            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                            .readTimeout(READ_TIMEOUT,TimeUnit.SECONDS)
                            .writeTimeout(WRITE_TIMEOUT,TimeUnit.SECONDS)
                            .addInterceptor(mRewriteCacheControlInterceptor)
                            .addInterceptor(mLoggingInterceptor)
                            .cookieJar(new CookiesManger())
                            .build();
                }
            }
        }
        return mOkhttpClient;
    }
    /**
     * 获取Service
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T create(Class<T> clazz) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constant.REQUEST_BASE_URL)
                .client(getmOkhttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build();
        return retrofit.create(clazz);
    }
}
