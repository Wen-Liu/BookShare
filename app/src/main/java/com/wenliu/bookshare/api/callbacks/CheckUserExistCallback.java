package com.wenliu.bookshare.api.callbacks;

import com.wenliu.bookshare.object.User;

public interface CheckUserExistCallback {

    void userExist(User user);

    void notExist();
}
