
package com.maotong.readhub.bean.readhub.tech.newdata;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataList {

    @SerializedName("data")
    @Expose
    private List<Datum> data = null;
    @SerializedName("pageSize")
    @Expose
    private Integer pageSize;
    @SerializedName("totalItems")
    @Expose
    private Integer totalItems;
    @SerializedName("totalPages")
    @Expose
    private Integer totalPages;

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
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

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

}
