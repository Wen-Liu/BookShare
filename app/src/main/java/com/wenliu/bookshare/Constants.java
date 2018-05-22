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
    public final static String FIREBASE_BOOKSTATUS = "bookStatus";

    // User Manager
    public final static String USER_DATA = "user_data";
    public final static String USER_NAME = "user_name";
    public final static String USER_EMAIL = "user_email";
    public final static String USER_IMAGE = "user_image";
    public final static String USER_ID = "user_id";

    // Book Status
    public final static String BOOKSTATUS = "bookStatus";
    public final static int MY_BOOK_UNREAD = 0;
    public final static int MY_BOOK_READ = 1; // my books count = MY_BOOK_UNREAD + MY_BOOK_READ
    public final static int MY_BOOK_LENT = 2;
    public final static int BORROW = 3;
    public final static int READ = 4; // total read = MY_BOOK_READ + READ;

    //
    public static final int REQUEST_IMAGE_CAPTURE = 1;
}
