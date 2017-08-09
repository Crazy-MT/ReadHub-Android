
package com.maotong.readhub.bean.readhub.hot;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EntityRelatedTopic {

    @SerializedName("entityId")
    @Expose
    private Integer entityId;
    @SerializedName("entityName")
    @Expose
    private String entityName;
    @SerializedName("eventType")
    @Expose
    private Integer eventType;
    @SerializedName("eventTypeLabel")
    @Expose
    private String eventTypeLabel;
    @SerializedName("data")
    @Expose
    private List<Datum_> data = null;

    public Integer getEntityId() {
        return entityId;
    }

    public void setEntityId(Integer entityId) {
        this.entityId = entityId;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public Integer getEventType() {
        return eventType;
    }

    public void setEventType(Integer eventType) {
        this.eventType = eventType;
    }

    public String getEventTypeLabel() {
        return eventTypeLabel;
    }

    public void setEventTypeLabel(String eventTypeLabel) {
        this.eventTypeLabel = eventTypeLabel;
    }

    public List<Datum_> getData() {
        return data;
    }

    public void setData(List<Datum_> data) {
        this.data = data;
    }

}
