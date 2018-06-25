package com.wenliu.bookshare.api.callbacks;

import com.wenliu.bookshare.object.BookCustomInfo;

import java.util.ArrayList;

public interface GetFriendBooksCallback {

    void onCompleted(ArrayList<BookCustomInfo> bookCustomInfos);

    void noBookData(ArrayList<BookCustomInfo> bookCustomInfos);

    void onError(String errorMessage);
}
