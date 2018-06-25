package com.wenliu.bookshare.api.callbacks;

import com.wenliu.bookshare.object.Book;

/**
 * Created by wen on 2018/5/10.
 */

public interface CheckBookExistCallback {

    void onCompleted(Book book);

    void onError();
}
