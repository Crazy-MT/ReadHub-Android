
package com.maotong.readhub.bean.readhub.news;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class All {

    @SerializedName("data")
    @Expose
    private List<Datum> data = null;
    @SerializedName("totalItems")
    @Expose
    private Integer totalItems;
    @SerializedName("pageSize")
    @Expose
    private Integer pageSize;
    @SerializedName("isLoadAll")
    @Expose
    private Boolean isLoadAll;
    @SerializedName("autoLoading")
    @Expose
    private Boolean autoLoading;
    @SerializedName("init")
    @Expose
    private Boolean init;

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public Integer getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(Integer totalItems) {
        this.totalItems = totalItems;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Boolean getIsLoadAll() {
        return isLoadAll;
    }

    public void setIsLoadAll(Boolean isLoadAll) {
        this.isLoadAll = isLoadAll;
    }

    public Boolean getAutoLoading() {
        return autoLoading;
    }

    public void setAutoLoading(Boolean autoLoading) {
        this.autoLoading = autoLoading;
    }

    public Boolean getInit() {
        return init;
    }

    public void setInit(Boolean init) {
        this.init = init;
    }

}
