
package com.maotong.readhub.bean.readhub.hot;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Routing {

    @SerializedName("locationBeforeTransitions")
    @Expose
    private LocationBeforeTransitions locationBeforeTransitions;

    public LocationBeforeTransitions getLocationBeforeTransitions() {
        return locationBeforeTransitions;
    }

    public void setLocationBeforeTransitions(LocationBeforeTransitions locationBeforeTransitions) {
        this.locationBeforeTransitions = locationBeforeTransitions;
    }

}
