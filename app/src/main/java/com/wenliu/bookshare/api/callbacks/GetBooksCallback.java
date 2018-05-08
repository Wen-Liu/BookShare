package com.wenliu.bookshare.api.callbacks;

import com.wenliu.bookshare.object.Book;

import java.util.ArrayList;

/**
 * Created by wen on 2018/5/6.
 */

public interface GetBooksCallback {
    public void onCompleted(ArrayList<Book> books);
    public void onError(String errorMessage);
}
