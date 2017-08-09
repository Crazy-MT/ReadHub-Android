package com.maotong.readhub.api.zhihu;

import com.maotong.readhub.bean.UpdateItem;
import com.maotong.readhub.bean.image.ImageResponse;
import com.maotong.readhub.bean.zhihu.ZhihuDaily;
import com.maotong.readhub.bean.zhihu.ZhihuStory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;


public interface ZhihuApi {

    @GET("/api/4/news/latest")
    Observable<ZhihuDaily> getLastDaily();

    @GET("/api/4/news/before/{date}")
    Observable<ZhihuDaily> getTheDaily(@Path("date") String date);

    @GET("/api/4/news/{id}")
    Observable<ZhihuStory> getZhihuStory(@Path("id") String id);

    @GET("http://lab.zuimeia.com/wallpaper/category/1/?page_size=1")
    Observable<ImageResponse> getImage();

    @GET("http://caiyao.name/releases/MrUpdate.json")
    Observable<UpdateItem> getUpdateInfo();
}
