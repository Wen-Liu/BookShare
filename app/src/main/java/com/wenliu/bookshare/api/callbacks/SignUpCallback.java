package com.wenliu.bookshare.api.callbacks;

/**
 * Created by wen on 2018/5/7.
 */

public interface SignUpCallback {
    public void onCompleted();
    public void onError(String errorMessage);
}
