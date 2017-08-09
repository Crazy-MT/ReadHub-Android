
package com.maotong.readhub.bean.readhub.hot;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Datum implements Parcelable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("newsArray")
    @Expose
    private List<NewsArray> newsArray = null;
    @SerializedName("order")
    @Expose
    private Integer order;
    @SerializedName("publishDate")
    @Expose
    private String publishDate;
    @SerializedName("relatedTopicArray")
    @Expose
    private List<Object> relatedTopicArray = null;
    @SerializedName("summary")
    @Expose
    private String summary;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("entityRelatedTopics")
    @Expose
    private List<EntityRelatedTopic> entityRelatedTopics = null;
    @SerializedName("nelData")
    @Expose
    private NelData nelData;
    @SerializedName("isBeforeLastRead")
    @Expose
    private Boolean isBeforeLastRead;

    public Datum(){}

    protected Datum(Parcel in) {
        id = in.readString();
        createdAt = in.readString();
        publishDate = in.readString();
        summary = in.readString();
        title = in.readString();
        updatedAt = in.readString();
    }

    public static final Creator<Datum> CREATOR = new Creator<Datum>() {
        @Override
        public Datum createFromParcel(Parcel in) {
            return new Datum(in);
        }

        @Override
        public Datum[] newArray(int size) {
            return new Datum[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public List<NewsArray> getNewsArray() {
        return newsArray;
    }

    public void setNewsArray(List<NewsArray> newsArray) {
        this.newsArray = newsArray;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public List<Object> getRelatedTopicArray() {
        return relatedTopicArray;
    }

    public void setRelatedTopicArray(List<Object> relatedTopicArray) {
        this.relatedTopicArray = relatedTopicArray;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<EntityRelatedTopic> getEntityRelatedTopics() {
        return entityRelatedTopics;
    }

    public void setEntityRelatedTopics(List<EntityRelatedTopic> entityRelatedTopics) {
        this.entityRelatedTopics = entityRelatedTopics;
    }

    public NelData getNelData() {
        return nelData;
    }

    public void setNelData(NelData nelData) {
        this.nelData = nelData;
    }

    public Boolean getIsBeforeLastRead() {
        return isBeforeLastRead;
    }

    public void setIsBeforeLastRead(Boolean isBeforeLastRead) {
        this.isBeforeLastRead = isBeforeLastRead;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(createdAt);
        dest.writeString(publishDate);
        dest.writeString(summary);
        dest.writeString(title);
        dest.writeString(updatedAt);
    }
}
