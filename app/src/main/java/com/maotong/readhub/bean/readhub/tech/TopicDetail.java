
package com.maotong.readhub.bean.readhub.tech;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TopicDetail {

    @SerializedName("topicId")
    @Expose
    private Object topicId;
    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("init")
    @Expose
    private Boolean init;
    @SerializedName("error")
    @Expose
    private Object error;

    public Object getTopicId() {
        return topicId;
    }

    public void setTopicId(Object topicId) {
        this.topicId = topicId;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Boolean getInit() {
        return init;
    }

    public void setInit(Boolean init) {
        this.init = init;
    }

    public Object getError() {
        return error;
    }

    public void setError(Object error) {
        this.error = error;
    }

}
