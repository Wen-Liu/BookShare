package com.wenliu.bookshare.object.GoogleBook;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by wen on 2018/5/2.
 */
public class ReadingModes {

    @SerializedName("text")
    @Expose
    private Boolean text;

    @SerializedName("image")
    @Expose
    private Boolean image;

    public Boolean getText() {
        return text;
    }

    public void setText(Boolean text) {
        this.text = text;
    }

    public Boolean getImage() {
        return image;
    }

    public void setImage(Boolean image) {
        this.image = image;
    }

}
