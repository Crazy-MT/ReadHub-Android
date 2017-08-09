package com.maotong.readhub.api.readhubnews;


import com.maotong.readhub.bean.readhub.news.DataList;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;


public interface ReadHubApi {

    @GET("/news?pageSize=10")
    Observable<DataList> getMoreData(@Query("lastCursor") String offset);

}
