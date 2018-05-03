package com.wenliu.bookshare.object.GoogleBook;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by wen on 2018/5/3.
 */

public class Dimensions {

    @SerializedName("height")
    @Expose
    private String height;

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }
}
