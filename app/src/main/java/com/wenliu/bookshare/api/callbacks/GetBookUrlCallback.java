package com.wenliu.bookshare.api.callbacks;

/**
 * Created by wen on 2018/5/3.
 */

public interface GetBookUrlCallback {
    public void onCompleted(String bookid);
    public void onError(String errorMessage);
}
