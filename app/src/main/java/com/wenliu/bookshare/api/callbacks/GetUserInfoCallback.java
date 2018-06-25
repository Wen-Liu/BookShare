package com.wenliu.bookshare.api.callbacks;

import com.wenliu.bookshare.object.User;

/**
 * Created by wen on 2018/5/18.
 */

public interface GetUserInfoCallback {

    void onCompleted(User user);

    void onError(String error);
}
