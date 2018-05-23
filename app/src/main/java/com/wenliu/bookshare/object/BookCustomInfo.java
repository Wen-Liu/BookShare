package com.wenliu.bookshare.object;

import java.util.ArrayList;
import java.util.List;

public class BookCustomInfo {

    private String mTitle;
    private String mSubtitle;
    private List<String> mAuthor;
    private String mIsbn10;
    private String mIsbn13;
    private String mPublisher;
    private String mPublishDate;
    private String mLanguage;
    private String mImage;

    private String mPurchaseDate;
    private String mPurchasePrice;
    private String mComment;
    private double mBookScore;
    private int mBookReadStatus;
    private boolean haveBook;
    private int mCreateTime;

    public BookCustomInfo() {
        mTitle = "";
        mSubtitle = "";
        mAuthor = new ArrayList<>();
        mIsbn10 = "";
        mIsbn13 = "";
        mPublisher = "";
        mPublishDate = "";
        mLanguage = "";
        mImage = "";
        mPurchaseDate = "";
        mPurchasePrice = "";
        mComment = "";
        mBookScore = 0;
        mBookReadStatus = 0;
        haveBook = false;
        mCreateTime = Integer.parseInt(String.valueOf(System.currentTimeMillis() / 1000));
    }

    public BookCustomInfo(Book book) {
        mTitle = book.getTitle();
        mSubtitle = book.getSubtitle();
        mAuthor = book.getAuthor();
        mIsbn10 = book.getIsbn10();
        mIsbn13 = book.getIsbn13();
        mPublisher = book.getPublisher();
        mPublishDate = book.getPublishDate();
        mLanguage = book.getLanguage();
        mImage = book.getImage();

        mPurchaseDate = "";
        mPurchasePrice = "";
        mComment = "";
        mBookScore = 0;
        mBookReadStatus = 0;
        haveBook = false;
        mCreateTime = Integer.parseInt(String.valueOf(System.currentTimeMillis() / 1000));
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getSubtitle() {
        return mSubtitle;
    }

    public void setSubtitle(String subtitle) {
        mSubtitle = subtitle;
    }

    public List<String> getAuthor() {
        return mAuthor;
    }

    public void setAuthor(List<String> author) {
        mAuthor = author;
    }

    public String getIsbn10() {
        return mIsbn10;
    }

    public void setIsbn10(String isbn10) {
        mIsbn10 = isbn10;
    }

    public String getIsbn13() {
        return mIsbn13;
    }

    public void setIsbn13(String isbn13) {
        mIsbn13 = isbn13;
    }

    public String getPublisher() {
        return mPublisher;
    }

    public void setPublisher(String publisher) {
        mPublisher = publisher;
    }

    public String getPublishDate() {
        return mPublishDate;
    }

    public void setPublishDate(String publishDate) {
        mPublishDate = publishDate;
    }

    public String getLanguage() {
        return mLanguage;
    }

    public void setLanguage(String language) {
        mLanguage = language;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        mImage = image;
    }

    public String getPurchaseDate() {
        return mPurchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        mPurchaseDate = purchaseDate;
    }

    public String getPurchasePrice() {
        return mPurchasePrice;
    }

    public void setPurchasePrice(String purchasePrice) {
        mPurchasePrice = purchasePrice;
    }

    public String getComment() {
        return mComment;
    }

    public void setComment(String comment) {
        mComment = comment;
    }

    public double getBookScore() {
        return mBookScore;
    }

    public void setBookScore(double bookScore) {
        mBookScore = bookScore;
    }

    public int getBookReadStatus() {
        return mBookReadStatus;
    }

    public void setBookReadStatus(int bookReadStatus) {
        mBookReadStatus = bookReadStatus;
    }

    public boolean isHaveBook() {
        return haveBook;
    }

    public void setHaveBook(boolean haveBook) {
        this.haveBook = haveBook;
    }

    public int getCreateTime() {
        return mCreateTime;
    }

    public void setCreateTime(int createTime) {
        mCreateTime = createTime;
    }
}
