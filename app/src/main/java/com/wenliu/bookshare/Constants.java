package com.wenliu.bookshare;

/**
 * Created by wen on 2018/5/2.
 */

public class Constants {

    // Log
    public final static String TAG_BASE_ACTIVITY = "wen-BaseActivity";
    public final static String TAG_LOGIN_ACTIVITY = "wen-LoginActivity";
    public final static String TAG_SHAREBOOK_ACTIVITY = "wen-ShareBookActivity";
    public final static String TAG_SHAREBOOK_PRESENTER = "wen-ShareBookActivity";
    public final static String TAG_PROFILE_ACTIVITY = "wen-ProfileActivity";
    public final static String TAG_PROFILE_PRESENTER = "wen-ProfileActivity";

    public final static String TAG_MAIN_FRAGMENT = "wen-MainFragment";
    public final static String TAG_MAIN_PRESENTER = "wen-MainPresenter";
    public final static String TAG_DETAIL_FRAGMENT = "wen-DetailFragment";
    public final static String TAG_DETAIL_PRESENTER = "wen-DetailPresenter";

    public final static String TAG_MAIN_ADAPTER = "wen-MainAdapter";
    public final static String TAG_PROFILE_ADAPTER = "wen-ProfileAdapter";

    public final static String TAG_INPUT_ISBN_DIALOG = "wen-InputIsbnDialog";
    public final static String TAG_BOOK_DATA_EDIT_DIALOG = "wen-BookDataEditDialog";

    public final static String TAG_GET_BOOK_DATA_TASK = "wen-GetBookDataTask";
    public final static String TAG_GET_BOOK_URL_TASK = "wen-GetBookUrlTask";
    public final static String TAG_GET_BOOKS_TASK = "wen-GetBooksTask";

    public final static String TAG_FIREBASE_API_HELPER = "wen-FirebaseApiHelper";
    public final static String TAG_GOOGLE_API_HELPER = "wen-GoogleApiHelper";

    public final static String TAG_USERMANAGER = "wen-UserManager";
    public final static String TAG_SHARE_BOOK_CLIENT = "wen-ShareBookClient";
    public final static String TAG_SHARE_BOOK_PARSER = "wen-ShareBookParser";

    // Firebase
    public final static String FIREBASE_GOOGLE_BOOKS = "GoogleBooks";
    public final static String FIREBASE_USERS = "Users";
    public final static String FIREBASE_BOOKS = "Books";
    public final static String FIREBASE_FRIENDS = "Friends";
    public final static String FIREBASE_IMAGE = "image";
    public final static String FIREBASE_EMAIL = "email";
    public final static String FIREBASE_CREATE_TIME = "createTime";
    public final static String FIREBASE_FRIEND_STATUS = "status";
    public final static String FIREBASE_FRIEND_SEND = "send";
    public final static String FIREBASE_FRIEND_RECEIVE = "receive";
    public final static String FIREBASE_FRIEND_APPROVE = "approve";

    // User Manager
    public final static String USER_DATA = "user_data";
    public final static String USER_NAME = "user_name";
    public final static String USER_EMAIL = "user_email";
    public final static String USER_IMAGE = "user_image";
    public final static String USER_ID = "user_id";

    // Book Status
    public final static String BOOKSTATUS = "bookStatus";
    public final static int MY_BOOK = 0;
    public final static int UNREAD = 1;
    public final static int READING = 2;
    public final static int READ = 3;
    public final static int LENT = 4;
    public final static int BORROW = 5;

    // filter
    public final static int BOOK_ALL = 0;
    public final static int BOOK_OWN = 1;
    public final static int BOOK_UNREAD = 2;
    public final static int BOOK_READING = 3;
    public final static int BOOK_READ = 4;


    // Profile activity result
    public static final int GET_PHOTO_FROM_GALLERY = 0;
    public static final int GET_PHOTO_CROP = 1;

    // permission relative
    public static final int PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 100;
    public static final int PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 101;
    public static final int PERMISSIONS_REQUEST_CAMERA = 102;
}
