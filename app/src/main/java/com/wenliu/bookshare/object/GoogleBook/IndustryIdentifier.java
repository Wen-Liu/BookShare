package com.wenliu.bookshare.object.GoogleBook;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by wen on 2018/5/2.
 */
public class IndustryIdentifier {

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("identifier")
    @Expose
    private String identifier;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

}
