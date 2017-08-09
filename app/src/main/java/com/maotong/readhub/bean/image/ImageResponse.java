package com.maotong.readhub.bean.image;

import com.google.gson.annotations.SerializedName;


public class ImageResponse {
    @SerializedName("data")
    private ImageData data;

    public ImageData getData() {
        return data;
    }

    public void setData(ImageData data) {
        this.data = data;
    }
}
