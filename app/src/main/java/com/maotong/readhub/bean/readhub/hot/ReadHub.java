
package com.maotong.readhub.bean.readhub.hot;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReadHub {

    @SerializedName("routing")
    @Expose
    private Routing routing;
    @SerializedName("app")
    @Expose
    private App app;
    @SerializedName("topic")
    @Expose
    private Topic topic;
    @SerializedName("timeline")
    @Expose
    private Timeline timeline;
    @SerializedName("topic_detail")
    @Expose
    private TopicDetail topicDetail;
    @SerializedName("@@dva")
    @Expose
    private Integer dva;

    public Routing getRouting() {
        return routing;
    }

    public void setRouting(Routing routing) {
        this.routing = routing;
    }

    public App getApp() {
        return app;
    }

    public void setApp(App app) {
        this.app = app;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public Timeline getTimeline() {
        return timeline;
    }

    public void setTimeline(Timeline timeline) {
        this.timeline = timeline;
    }

    public TopicDetail getTopicDetail() {
        return topicDetail;
    }

    public void setTopicDetail(TopicDetail topicDetail) {
        this.topicDetail = topicDetail;
    }

    public Integer getDva() {
        return dva;
    }

    public void setDva(Integer dva) {
        this.dva = dva;
    }

}
