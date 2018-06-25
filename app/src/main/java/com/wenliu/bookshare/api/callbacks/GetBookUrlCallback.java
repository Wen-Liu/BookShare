package com.wenliu.bookshare.api.callbacks;

/**
 * Created by wen on 2018/5/3.
 */

public interface GetBookUrlCallback {

    void onCompleted(String bookid);

    void onError(String errorMessage);
}
