package com.wenliu.bookshare.api.callbacks;

import com.wenliu.bookshare.object.Book;

/**
 * Created by wen on 2018/5/3.
 */

public interface GetBookDataCallback {

    void onCompleted(Book book);

    void onError(String errorMessage);
}
