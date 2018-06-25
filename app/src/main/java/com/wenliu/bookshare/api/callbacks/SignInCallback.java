package com.wenliu.bookshare.api.callbacks;

/**
 * Created by wen on 2018/5/8.
 */

public interface SignInCallback {

    void onCompleted();

    void onError(String errorMessage);
}
