package com.wenliu.bookshare.object;

public class LentBook {

    private String mUserId;
    private String mName;
    private String mUserImage;

    private String mTitle;
    private String mIsbn13;
    private String mBookImage;
    private int mBookReadStatus;

    private String mLentStatus;
    private String mLendStartDay;
    private String mLendReturnDay;

    public LentBook() {
        mUserId = "";
        mName = "";
        mUserImage = "";
        mTitle = "";
        mIsbn13 = "";
        mBookImage = "";
        mBookReadStatus = -1;
        mLentStatus = "";
        mLendStartDay = "";
        mLendReturnDay = "";
    }

    public LentBook(User user, BookCustomInfo bookCustomInfo) {
        mUserId = user.getId();
        mName = user.getName();
        mUserImage = user.getImage();
        mTitle = bookCustomInfo.getTitle();
        mIsbn13 = bookCustomInfo.getIsbn13();
        mBookImage = bookCustomInfo.getImage();
        mBookReadStatus = bookCustomInfo.getBookReadStatus();
        mLentStatus = "";
        mLendStartDay = "";
        mLendReturnDay = "";
    }

    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String userId) {
        mUserId = userId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getUserImage() {
        return mUserImage;
    }

    public void setUserImage(String userImage) {
        mUserImage = userImage;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getIsbn13() {
        return mIsbn13;
    }

    public void setIsbn13(String isbn13) {
        mIsbn13 = isbn13;
    }

    public String getBookImage() {
        return mBookImage;
    }

    public void setBookImage(String bookImage) {
        mBookImage = bookImage;
    }

    public int getBookReadStatus() {
        return mBookReadStatus;
    }

    public void setBookReadStatus(int bookReadStatus) {
        mBookReadStatus = bookReadStatus;
    }

    public String getLentStatus() {
        return mLentStatus;
    }

    public void setLentStatus(String lentStatus) {
        mLentStatus = lentStatus;
    }

    public String getLendStartDay() {
        return mLendStartDay;
    }

    public void setLendStartDay(String lendStartDay) {
        mLendStartDay = lendStartDay;
    }

    public String getLendReturnDay() {
        return mLendReturnDay;
    }

    public void setLendReturnDay(String lendReturnDay) {
        mLendReturnDay = lendReturnDay;
    }
}
