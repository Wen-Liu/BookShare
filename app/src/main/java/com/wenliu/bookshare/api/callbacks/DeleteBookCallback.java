package com.wenliu.bookshare.api.callbacks;

import com.wenliu.bookshare.object.Book;

public interface DeleteBookCallback {
    public void onCompleted();
    public void onError();
}
