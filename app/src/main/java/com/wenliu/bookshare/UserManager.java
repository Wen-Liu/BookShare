package com.wenliu.bookshare;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by wen on 2018/5/7.
 */

public class UserManager {

    private static final UserManager instance = new UserManager();
    private static String mUserId;
    private static String mUserEmail;
    private static String mUserName;
    private static String mUserImage;
    private static String mFbToken;

    SharedPreferences mUserData = ShareBook.getAppContext().getSharedPreferences(Constants.USER_DATA, Context.MODE_PRIVATE);

    // private constructor，這樣其他物件就沒辦法直接用new來取得新的實體
    private UserManager() {
    }

    // 因為constructor已經private，所以需要另外提供方法讓其他程式調用這個類別
    public static UserManager getInstance() {
        return instance;
    }

    public String getUserId() {
        return mUserData.getString(Constants.USER_ID, null);
    }

    public String getUserEmail() {
        return mUserData.getString(Constants.USER_EMAIL, null);
    }

    public String getUserName() {
        return mUserData.getString(Constants.USER_NAME, null);
    }

    public String getUserImage() {
        return mUserData.getString(Constants.USER_IMAGE, null);
    }

//    public String getFbToken() {
//        return mUserData.getString(Constants.USER_ID, null);
//    }

}
