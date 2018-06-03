package com.wenliu.bookshare.object;

import com.wenliu.bookshare.UserManager;

public class LentBook {

    private String mLenderId;
    private String mLenderName;
    private String mLenderImage;

    private String mBorrowerId;
    private String mBorrowerName;
    private String mBorrowerImage;

    private String mTitle;
    private String mIsbn13;
    private String mBookImage;
    private int mBookReadStatus;

    private String mLentStatus;
    private String mLendStartDay;
    private String mLendReturnDay;

    public LentBook() {
        mLenderId = "";
        mLenderName = "";
        mLenderImage = "";
        mBorrowerId = "";
        mBorrowerName = "";
        mBorrowerImage = "";
        mTitle = "";
        mIsbn13 = "";
        mBookImage = "";
        mBookReadStatus = -1;
        mLentStatus = "";
        mLendStartDay = "";
        mLendReturnDay = "";
    }

    public LentBook(User user, BookCustomInfo bookCustomInfo) {
        mLenderId = UserManager.getInstance().getUserId();
        mLenderName = UserManager.getInstance().getUserName();
        mLenderImage = UserManager.getInstance().getUserImage();
        mBorrowerId = user.getId();
        mBorrowerName = user.getName();
        mBorrowerImage = user.getImage();
        mTitle = bookCustomInfo.getTitle();
        mIsbn13 = bookCustomInfo.getIsbn13();
        mBookImage = bookCustomInfo.getImage();
        mBookReadStatus = bookCustomInfo.getBookReadStatus();
        mLentStatus = "";
        mLendStartDay = "";
        mLendReturnDay = "";
    }

    public String getLenderId() {
        return mLenderId;
    }

    public void setLenderId(String lenderId) {
        mLenderId = lenderId;
    }

    public String getLenderName() {
        return mLenderName;
    }

    public void setLenderName(String lenderName) {
        mLenderName = lenderName;
    }

    public String getLenderImage() {
        return mLenderImage;
    }

    public void setLenderImage(String lenderImage) {
        mLenderImage = lenderImage;
    }

    public String getBorrowerId() {
        return mBorrowerId;
    }

    public void setBorrowerId(String borrowerId) {
        mBorrowerId = borrowerId;
    }

    public String getBorrowerName() {
        return mBorrowerName;
    }

    public void setBorrowerName(String borrowerName) {
        mBorrowerName = borrowerName;
    }

    public String getBorrowerImage() {
        return mBorrowerImage;
    }

    public void setBorrowerImage(String borrowerImage) {
        mBorrowerImage = borrowerImage;
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
