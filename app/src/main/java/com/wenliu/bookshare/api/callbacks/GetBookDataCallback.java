package com.wenliu.bookshare.api.callbacks;

import com.wenliu.bookshare.object.Book;
import com.wenliu.bookshare.object.GoogleBook.Item;

/**
 * Created by wen on 2018/5/3.
 */

public interface GetBookDataCallback {
    public void onCompleted(Book book);
    public void onError(String errorMessage);
}
