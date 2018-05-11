package com.wenliu.bookshare.object.GoogleBook;

import com.wenliu.bookshare.object.Book;

/**
 * Created by wen on 2018/5/10.
 */

public class MyBook extends Book {

    private String mPurchaseDate;
    private String mPurchasePrice;

    public MyBook() {
        super();
        mPurchaseDate = "";
        mPurchasePrice = "";
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
}
