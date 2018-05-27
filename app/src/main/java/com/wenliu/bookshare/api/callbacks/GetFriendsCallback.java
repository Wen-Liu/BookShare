package com.wenliu.bookshare.api.callbacks;

import com.wenliu.bookshare.object.User;

import java.util.ArrayList;

public interface GetFriendsCallback {
    public void onCompleted(ArrayList<User> friends);

    public void noFriendData();

    public void onError(String errorMessage);
}
