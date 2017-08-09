
package com.maotong.readhub.bean.readhub.hot;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewsArray implements Parcelable{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("groupId")
    @Expose
    private Integer groupId;
    @SerializedName("siteName")
    @Expose
    private String siteName;
    @SerializedName("mobileUrl")
    @Expose
    private String mobileUrl;
    @SerializedName("authorName")
    @Expose
    private String authorName;
    @SerializedName("duplicateId")
    @Expose
    private Integer duplicateId;
    @SerializedName("publishDate")
    @Expose
    private String publishDate;

    protected NewsArray(Parcel in) {
        url = in.readString();
        title = in.readString();
        siteName = in.readString();
        mobileUrl = in.readString();
        authorName = in.readString();
        publishDate = in.readString();
    }

    public static final Creator<NewsArray> CREATOR = new Creator<NewsArray>() {
        @Override
        public NewsArray createFromParcel(Parcel in) {
            return new NewsArray(in);
        }

        @Override
        public NewsArray[] newArray(int size) {
            return new NewsArray[size];
        }
    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getMobileUrl() {
        return mobileUrl;
    }

    public void setMobileUrl(String mobileUrl) {
        this.mobileUrl = mobileUrl;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public Integer getDuplicateId() {
        return duplicateId;
    }

    public void setDuplicateId(Integer duplicateId) {
        this.duplicateId = duplicateId;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
        dest.writeString(title);
        dest.writeString(siteName);
        dest.writeString(mobileUrl);
        dest.writeString(authorName);
        dest.writeString(publishDate);
    }
}
