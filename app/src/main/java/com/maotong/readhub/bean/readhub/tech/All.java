
package com.maotong.readhub.bean.readhub.tech;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class All {

    @SerializedName("data")
    @Expose
    private List<Object> data = null;
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
    @SerializedName("error")
    @Expose
    private Object error;

    public List<Object> getData() {
        return data;
    }

    public void setData(List<Object> data) {
        this.data = data;
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

    public Object getError() {
        return error;
    }

    public void setError(Object error) {
        this.error = error;
    }

}
