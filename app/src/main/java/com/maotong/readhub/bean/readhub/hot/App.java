
package com.maotong.readhub.bean.readhub.hot;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class App {

    @SerializedName("sizeMode")
    @Expose
    private String sizeMode;
    @SerializedName("globalMessages")
    @Expose
    private List<Object> globalMessages = null;
    @SerializedName("ads")
    @Expose
    private List<Object> ads = null;
    @SerializedName("isWechatReady")
    @Expose
    private Boolean isWechatReady;
    @SerializedName("SSR_ENV")
    @Expose
    private SSRENV sSRENV;
    @SerializedName("indexs")
    @Expose
    private List<Integer> indexs = null;

    public String getSizeMode() {
        return sizeMode;
    }

    public void setSizeMode(String sizeMode) {
        this.sizeMode = sizeMode;
    }

    public List<Object> getGlobalMessages() {
        return globalMessages;
    }

    public void setGlobalMessages(List<Object> globalMessages) {
        this.globalMessages = globalMessages;
    }

    public List<Object> getAds() {
        return ads;
    }

    public void setAds(List<Object> ads) {
        this.ads = ads;
    }

    public Boolean getIsWechatReady() {
        return isWechatReady;
    }

    public void setIsWechatReady(Boolean isWechatReady) {
        this.isWechatReady = isWechatReady;
    }

    public SSRENV getSSRENV() {
        return sSRENV;
    }

    public void setSSRENV(SSRENV sSRENV) {
        this.sSRENV = sSRENV;
    }

    public List<Integer> getIndexs() {
        return indexs;
    }

    public void setIndexs(List<Integer> indexs) {
        this.indexs = indexs;
    }

}
