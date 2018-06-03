package com.wenliu.bookshare.api.callbacks;

import com.wenliu.bookshare.object.LentBook;

import java.util.ArrayList;

public interface GetLendStatusCallback {

    public void onCompleted(ArrayList<LentBook> lentBooks);

    public void noLendData();

    public void onError(String errorMessage);
}
