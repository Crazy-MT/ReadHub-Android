package com.maotong.readhub.api.readhubnews;


import com.maotong.readhub.bean.readhub.news.DataList;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface ReadHubApi {

    @GET("/news?pageSize=10")
    Observable<DataList> getMoreData(@Query("lastCursor") String offset);

}
