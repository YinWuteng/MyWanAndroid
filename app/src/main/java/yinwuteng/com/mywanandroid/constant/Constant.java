package yinwuteng.com.mywanandroid.constant;

/**
 * Created by yinwuteng on 2018/3/18.
 * 系统常量
 */

public class Constant {
    public static final String REQUEST_BASE_URL = "http://wanandroid.com/";
    /**
     * 数据刷新成功
     */
    public static final int LOADTYPE_REFRESH_SUCCESS = 1;
    /**
     * 数据刷新失败
     */
    public static final int LOADTYPE_REFRESH_ERROR = 2;
    /**
     * 加载更多数据成功
     */
    public static final int LOADTYPE_LOAD_MORE_SUCCESS = 3;
    /**
     * 加载更多数据失败
     */
    public static final int LOADTYPE_LOAD_MORE_ERROR = 4;

    /**
     * 每页数量
     */
    public static final int PAGE_SIZE = 20;
    public static final String SHARED_NAME = "_preferences";
    public static final String USERNAME_KEY = "username";
    public static final String PASSWORD_KEY = "password";
    public static final String LOGIN_KEY = "login";
}
