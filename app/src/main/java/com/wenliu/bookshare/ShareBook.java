package com.wenliu.bookshare;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

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

    public static void makeShortToast(String toast){
        Toast.makeText(mContext, toast, Toast.LENGTH_SHORT);
    }

}
