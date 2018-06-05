package com.wenliu.bookshare.base;

import com.wenliu.bookshare.api.FirebaseApiHelper;

/**
 * Created by wen on 2018/5/2.
 */

public interface BasePresenter {
    public FirebaseApiHelper mFirebaseApiHelper = FirebaseApiHelper.newInstance();

    void start();
}
