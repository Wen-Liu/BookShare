package com.wenliu.bookshare.api.callbacks;

import com.wenliu.bookshare.object.Book;

/**
 * Created by wen on 2018/5/10.
 */

public interface CheckBookExistCallback {
    public void onCompleted(Book book);
    public void onError();
}
