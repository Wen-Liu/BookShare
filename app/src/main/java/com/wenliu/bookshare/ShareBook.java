package com.wenliu.bookshare;

import android.app.Application;
import android.content.Context;

/**
 * Created by wen on 2018/5/4.
 */

public class ShareBook extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static Context getAppContext() {
        return mContext;
    }

}
