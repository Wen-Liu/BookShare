package com.wenliu.bookshare.api.callbacks;

import com.wenliu.bookshare.object.LentBook;

import java.util.ArrayList;

public interface GetLendStatusCallback {

    void onCompleted(ArrayList<LentBook> lentBooks);

    void noLendData();

    void onError(String errorMessage);
}
