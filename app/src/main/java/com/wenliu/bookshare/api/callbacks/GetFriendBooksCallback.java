package com.wenliu.bookshare.api.callbacks;

import com.wenliu.bookshare.object.BookCustomInfo;

import java.util.ArrayList;

public interface GetFriendBooksCallback {

    public void onCompleted(ArrayList<BookCustomInfo> bookCustomInfos);

    public void noBookData(ArrayList<BookCustomInfo> bookCustomInfos);

    public void onError(String errorMessage);
}
