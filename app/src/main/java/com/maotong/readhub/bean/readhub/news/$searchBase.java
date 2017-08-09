
package com.maotong.readhub.bean.readhub.news;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class $searchBase {

    @SerializedName("search")
    @Expose
    private String search;
    @SerializedName("searchBase")
    @Expose
    private String searchBase;

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getSearchBase() {
        return searchBase;
    }

    public void setSearchBase(String searchBase) {
        this.searchBase = searchBase;
    }

}
