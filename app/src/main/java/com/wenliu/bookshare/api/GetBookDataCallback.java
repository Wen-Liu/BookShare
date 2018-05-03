package com.wenliu.bookshare.api;

/**
 * Created by wen on 2018/5/3.
 */

public interface GetBookDataCallback {
    public void onCompleted(String bookId);
    public void onError(String errorMessage);
}
