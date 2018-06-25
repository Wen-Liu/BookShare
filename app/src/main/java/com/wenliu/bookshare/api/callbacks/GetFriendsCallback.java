package com.wenliu.bookshare.api.callbacks;

import com.wenliu.bookshare.object.User;

import java.util.ArrayList;

public interface GetFriendsCallback {
    
    void onCompleted(ArrayList<User> friends);

    void noFriendData();

    void onError(String errorMessage);
}
