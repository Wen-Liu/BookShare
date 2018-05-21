package com.wenliu.bookshare.object;

/**
 * Created by wen on 2018/5/7.
 */

public class User {

    private String mName;
    private String mEmail;
    private String mImage;
    private String mId;
    private int mCreateTime;

    public User() {
        mName = "";
        mEmail = "";
        mImage = "";
        mId = "";
        mCreateTime = 0;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        mImage = image;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public int getCreateTime() {
        return mCreateTime;
    }

    public void setCreateTime(int createTime) {
        mCreateTime = createTime;
    }
}
