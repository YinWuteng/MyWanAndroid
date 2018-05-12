package yinwuteng.com.mywanandroid.net;


import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import yinwuteng.com.mywanandroid.bean.Article;
import yinwuteng.com.mywanandroid.bean.Banner;
import yinwuteng.com.mywanandroid.bean.Book;
import yinwuteng.com.mywanandroid.bean.DataResponse;
import yinwuteng.com.mywanandroid.bean.HotKey;
import yinwuteng.com.mywanandroid.bean.KnowledgeSystem;
import yinwuteng.com.mywanandroid.bean.User;

/**
 * Created by lw on 2018/1/23.
 * API接口
 */

public interface ApiService {
    /**
     * 获取首页数据
     *
     * @param page 页码
     * @return
     */
    @GET("/article/list/{page}/json")
    Observable<DataResponse<Article>> getHomeArticles(@Path("page") int page);


    /**
     * 登录
     *
     * @param username username
     * @param password password
     * @return Deferred<User>
     */
    @POST("/user/login")
    @FormUrlEncoded
    Observable<DataResponse<User>> login(@Field("username") String username, @Field("password") String password);

    /**
     * 注册
     *
     * @param username   username
     * @param password   password
     * @param repassword repassword
     * @return Deferred<User>
     */
    @POST("/user/register")
    @FormUrlEncoded
    Observable<DataResponse<User>> register(@Field("username") String username, @Field("password") String password, @Field("repassword") String repassword);

    /**
     * 知识体系
     * http://www.wanandroid.com/tree/json
     *
     * @return BannerResponse
     */
    @GET("/tree/json")
    Observable<DataResponse<List<KnowledgeSystem>>> getKnowledgeSystems();

    /**
     * 知识体系下的文章
     * http://www.wanandroid.com/article/list/0/json?cid=168
     *
     * @param page page
     * @param cid  cid
     */
    @GET("/article/list/{page}/json")
    Observable<DataResponse<Article>> getKnowledgeSystemArticles(@Path("page") int page, @Query("cid") int cid);

    /**
     * 收藏文章
     *
     * @param id id
     * @return Deferred<DataResponse>
     */
    @POST("/lg/collect/{id}/json")
    Observable<DataResponse> addCollectArticle(@Path("id") int id);

    /**
     * 收藏站外文章
     *
     * @param title  title
     * @param author author
     * @param link   link
     * @return Deferred<DataResponse>
     */
    @POST("/lg/collect/add/json")
    @FormUrlEncoded
    Observable<DataResponse> addCollectOutsideArticle(@Field("title") String title, @Field("author") String author, @Field("link") String link);

    /**
     * 删除收藏文章
     *
     * @param id       id
     * @param originId -1
     * @return Deferred<DataResponse>
     */
    @POST("/lg/uncollect/{id}/json")
    @FormUrlEncoded
    Observable<DataResponse> removeCollectArticle(@Path("id") int id, @Field("originId") int originId);

    /**
     * 获取头部
     *
     * @return
     */
    @GET("/banner/json")
    Observable<DataResponse<List<Banner>>> getHomeBanners();

    /**
     * 我的书签
     * http://www.wanandroid.com/lg/collect/usertools/json
     *
     * @return
     */
    @GET("/lg/collect/usertools/json")
    Observable<DataResponse<List<Book>>> getBookmarks();

    /**
     * 编辑书签
     *
     * @param id
     * @param name
     * @param link http://www.wanandroid.com/lg/collect/updatetool/json
     * @return
     */
    @POST("/lg/collect/usertools/json")
    @FormUrlEncoded
    Observable<DataResponse> editBookmark(@Field("id") int id, @Field("name") String name, @Field("link") String link);

    /**
     * 删除书签
     *
     * @param id http://www.wanandroid.com/lg/collect/deletetool/json
     * @return
     */
    @POST("/lg/collect/deletetool/json")
    @FormUrlEncoded
    Observable<DataResponse> delBookmark(@Field("id") int id);

    /**
     * 获取自己收藏的文章列表
     * @param page
     * @return
     */
    @GET("/lg/collect/list/{page}/json")
    Observable<DataResponse<Article>> getCollectionArticles(@Path("page") int page);

    /**
     * 常用网站
     * http://www.wanandroid.com/friend/json
     * @return
     */
    @GET("/friend/json")
    Observable<DataResponse<List<Book>>> getHotBooks();

    /**
     * 大家都在搜
     * http://www.wanandroid.com/hotkey/json
     * @return
     */
    @GET("/hotkey/json")
    Observable<DataResponse<List<HotKey>>> getHotKeys();

    /**
     * 搜索
     * @param page page
     * @param k post search key
     * @return
     */
    @POST("/article/query/{page}/json")
    @FormUrlEncoded
    Observable<DataResponse<Article>> getSearchArticles(@Path("page")int page,@Field("k") String k);

}
