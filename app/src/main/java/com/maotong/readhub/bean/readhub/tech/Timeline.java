
package com.maotong.readhub.bean.readhub.tech;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Timeline {

    @SerializedName("activeType")
    @Expose
    private String activeType;
    @SerializedName("items")
    @Expose
    private Items items;
    @SerializedName("highQualityNews")
    @Expose
    private List<Object> highQualityNews = null;
    @SerializedName("error")
    @Expose
    private Object error;

    public String getActiveType() {
        return activeType;
    }

    public void setActiveType(String activeType) {
        this.activeType = activeType;
    }

    public Items getItems() {
        return items;
    }

    public void setItems(Items items) {
        this.items = items;
    }

    public List<Object> getHighQualityNews() {
        return highQualityNews;
    }

    public void setHighQualityNews(List<Object> highQualityNews) {
        this.highQualityNews = highQualityNews;
    }

    public Object getError() {
        return error;
    }

    public void setError(Object error) {
        this.error = error;
    }

}
