package com.wenliu.bookshare.api.callbacks;

import com.wenliu.bookshare.object.User;

public interface CheckUserExistCallback {
    public void userExist(User user);
    public void notExist();
}
