package com.wenliu.bookshare.object;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wen on 2018/5/3.
 */

public class Book {
    private String mBookSource;
    private String mCreateByUid;
    private String mTitle;
    private String mSubtitle;
    private List<String> mAuthor;
    private String mIsbn10;
    private String mIsbn13;
    private String mPublisher;
    private String mPublishDate;
    private String mLanguage;
    private String mImage;


    public Book() {
        mBookSource = "";
        mCreateByUid = "";
        mTitle = "";
        mSubtitle = "";
        mAuthor = new ArrayList<>();
        mIsbn10 = "";
        mIsbn13 = "";
        mPublisher = "";
        mPublishDate = "";
        mLanguage = "";
        mImage = "";
    }

    public String getBookSource() {
        return mBookSource;
    }

    public void setBookSource(String bookSource) {
        mBookSource = bookSource;
    }

    public String getCreateByUid() {
        return mCreateByUid;
    }

    public void setCreateByUid(String createByUid) {
        mCreateByUid = createByUid;
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
}
