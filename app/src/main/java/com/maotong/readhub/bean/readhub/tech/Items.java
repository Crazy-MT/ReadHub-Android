
package com.maotong.readhub.bean.readhub.tech;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Items {

    @SerializedName("all")
    @Expose
    private All all;
    @SerializedName("tech")
    @Expose
    private Tech tech;

    public All getAll() {
        return all;
    }

    public void setAll(All all) {
        this.all = all;
    }

    public Tech getTech() {
        return tech;
    }

    public void setTech(Tech tech) {
        this.tech = tech;
    }

}
