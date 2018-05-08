package com.wenliu.bookshare.api.callbacks;

import com.wenliu.bookshare.api.GetBooks;

/**
 * Created by wen on 2018/5/3.
 */

public interface GetBookIdCallback {
    public void onCompleted(String bookid);
    public void onError(String errorMessage);
}
