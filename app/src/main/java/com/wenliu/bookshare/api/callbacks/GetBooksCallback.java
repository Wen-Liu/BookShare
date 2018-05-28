package com.wenliu.bookshare.api.callbacks;

import com.wenliu.bookshare.object.BookCustomInfo;

import java.util.ArrayList;

/**
 * Created by wen on 2018/5/6.
 */

public interface GetBooksCallback {
    public void onCompleted(ArrayList<BookCustomInfo> bookCustomInfos, int[] bookStatusInfo);

    public void noBookData(ArrayList<BookCustomInfo> bookCustomInfos, int[] bookStatusInfo);

    public void onError(String errorMessage, int[] bookStatusInfo);
}
