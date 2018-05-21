package com.wenliu.bookshare.api.callbacks;

import com.wenliu.bookshare.object.User;

/**
 * Created by wen on 2018/5/18.
 */

public interface GetUserInfoCallback {
    public void onCompleted(User user);
    public void onError(String error);
}
