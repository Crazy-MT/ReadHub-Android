
package com.maotong.readhub.bean.readhub.news;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SSRENV {

    @SerializedName("platform")
    @Expose
    private String platform;

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

}
