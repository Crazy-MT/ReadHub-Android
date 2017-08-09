package com.maotong.readhub.bean.readhub.hot.newdata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.maotong.readhub.bean.readhub.hot.Datum;

import java.util.List;

/**
 * Created by yoush on 2017/8/5.
 */

public class DataList {

    @SerializedName("data")
    @Expose
    private List<Datum> data = null;

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

}
