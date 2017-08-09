
package com.maotong.readhub.bean.readhub.hot;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Topic {

    @SerializedName("data")
    @Expose
    private List<Datum> data = null;
    @SerializedName("openList")
    @Expose
    private List<Object> openList = null;
    @SerializedName("pageSize")
    @Expose
    private Integer pageSize;
    @SerializedName("totalItems")
    @Expose
    private Integer totalItems;
    @SerializedName("init")
    @Expose
    private Boolean init;
    @SerializedName("isLoadAll")
    @Expose
    private Boolean isLoadAll;
    @SerializedName("autoLoading")
    @Expose
    private Boolean autoLoading;
    @SerializedName("newTopicCount")
    @Expose
    private Integer newTopicCount;
    @SerializedName("error")
    @Expose
    private Object error;

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public List<Object> getOpenList() {
        return openList;
    }

    public void setOpenList(List<Object> openList) {
        this.openList = openList;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(Integer totalItems) {
        this.totalItems = totalItems;
    }

    public Boolean getInit() {
        return init;
    }

    public void setInit(Boolean init) {
        this.init = init;
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

    public Integer getNewTopicCount() {
        return newTopicCount;
    }

    public void setNewTopicCount(Integer newTopicCount) {
        this.newTopicCount = newTopicCount;
    }

    public Object getError() {
        return error;
    }

    public void setError(Object error) {
        this.error = error;
    }

}
