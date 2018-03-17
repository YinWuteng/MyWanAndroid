package yinwuteng.com.mywanandroid.net;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * Created by yinwuteng on 2018/3/14.
 * cookies管理类
 */

public class CookiesManger implements CookieJar {
    private static final PersistentCookiesStore cookiesStore = new PersistentCookiesStore();

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        if (cookies != null && cookies.size() > 0) {
            for (Cookie item : cookies) {
                cookiesStore.add(url, item);
            }
        }
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {

        return cookiesStore.get(url);
    }
    /**
     * 清除所有cookies
     */
    public  static void clearAllCookies(){
        cookiesStore.removeAll();
    }

    /**
     * 清除指定的cookie
     * @param url
     * @param cookie
     * @return
     */
    public  static boolean clearCookies(HttpUrl url,Cookie cookie){
        return  cookiesStore.remove(url,cookie);
    }
    /**
     * 获取cookie
     */
    public static List<Cookie> getCookies(){
        return cookiesStore.getCookies();
    }
}
