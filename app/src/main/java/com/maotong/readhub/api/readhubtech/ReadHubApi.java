package com.maotong.readhub.api.readhubtech;


import com.maotong.readhub.bean.readhub.tech.newdata.DataList;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface ReadHubApi {

    @GET("/technews?pageSize=10")
    Observable<DataList> getMoreData(@Query("lastCursor") String offset);

}
