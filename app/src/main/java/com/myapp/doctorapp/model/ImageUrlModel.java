package com.myapp.doctorapp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ImageUrlModel implements Serializable {
    @SerializedName("ImageUrl")
    private String imageUrl;

    public ImageUrlModel(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "ImageUrlModel{" +
                "imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
