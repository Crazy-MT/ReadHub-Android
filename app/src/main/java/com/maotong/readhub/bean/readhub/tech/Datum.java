
package com.maotong.readhub.bean.readhub.tech;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("siteName")
    @Expose
    private String siteName;
    @SerializedName("authorName")
    @Expose
    private String authorName;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("summary")
    @Expose
    private String summary;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("publishDate")
    @Expose
    private String publishDate;
    @SerializedName("summaryAuto")
    @Expose
    private String summaryAuto;

    public Datum() {
    }

    public Datum(Integer id, String siteName, String authorName, String url, String summary, String title, String publishDate, String summaryAuto) {
        this.id = id;
        this.siteName = siteName;
        this.authorName = authorName;
        this.url = url;
        this.summary = summary;
        this.title = title;
        this.publishDate = publishDate;
        this.summaryAuto = summaryAuto;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getSummaryAuto() {
        return summaryAuto;
    }

    public void setSummaryAuto(String summaryAuto) {
        this.summaryAuto = summaryAuto;
    }

}
