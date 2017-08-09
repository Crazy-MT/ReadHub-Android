
package com.maotong.readhub.bean.readhub.news;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LocationBeforeTransitions {

    @SerializedName("pathname")
    @Expose
    private String pathname;
    @SerializedName("search")
    @Expose
    private String search;
    @SerializedName("hash")
    @Expose
    private String hash;
    @SerializedName("state")
    @Expose
    private Object state;
    @SerializedName("action")
    @Expose
    private String action;
    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("query")
    @Expose
    private Query query;
    @SerializedName("$searchBase")
    @Expose
    private com.maotong.readhub.bean.readhub.news.$searchBase $searchBase;

    public String getPathname() {
        return pathname;
    }

    public void setPathname(String pathname) {
        this.pathname = pathname;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Object getState() {
        return state;
    }

    public void setState(Object state) {
        this.state = state;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }

    public com.maotong.readhub.bean.readhub.news.$searchBase get$searchBase() {
        return $searchBase;
    }

    public void set$searchBase(com.maotong.readhub.bean.readhub.news.$searchBase $searchBase) {
        this.$searchBase = $searchBase;
    }

}
