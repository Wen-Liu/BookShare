package com.wenliu.bookshare;

/**
 * Created by wen on 2018/5/2.
 */

public class Constants {

    // Log
    public static final String TAG_SHARE_BOOK = "wen-ShareBook";
    public static final String TAG_BASE_ACTIVITY = "wen-BaseActivity";
    public static final String TAG_BASE_FRAGMENT = "wen-BaseFragment";

    public static final String TAG_LOGIN_ACTIVITY = "wen-LoginActivity";
    public static final String TAG_LOGIN_PRESENTER = "wen-LoginPresenter";
    public static final String TAG_SHAREBOOK_ACTIVITY = "wen-ShareBookActivity";
    public static final String TAG_SHAREBOOK_PRESENTER = "wen-ShareBookPresenter";
    public static final String TAG_PROFILE_ACTIVITY = "wen-ProfileActivity";
    public static final String TAG_PROFILE_PRESENTER = "wen-ProfilePresenter";
    public static final String TAG_FRIEND_PROFILE_ACTIVITY = "wen-FProfileActivity";
    public static final String TAG_FRIEND_PROFILE_PRESENTER = "wen-FProfilePresenter";
    public static final String TAG_FRIEND_PROFILE_ADAPTER = "wen-FProfileAdapter";

    public static final String TAG_MAIN_FRAGMENT = "wen-MainFragment";
    public static final String TAG_MAIN_PRESENTER = "wen-MainPresenter";
    public static final String TAG_MAIN_ADAPTER = "wen-MainAdapter";
    public static final String TAG_DETAIL_FRAGMENT = "wen-DetailFragment";
    public static final String TAG_DETAIL_PRESENTER = "wen-DetailPresenter";
    public static final String TAG_FRIEND_FRAGMENT = "wen-FriendFragment";
    public static final String TAG_FRIEND_PRESENTER = "wen-FriendPresenter";
    public static final String TAG_FRIEND_ADAPTER = "wen-FriendAdapter";
    public static final String TAG_LENT_FRAGMENT = "wen-LentFragment";
    public static final String TAG_LENT_PRESENTER = "wen-LentPresenter";
    public static final String TAG_LENT_ADAPTER = "wen-LentAdapter";

    public static final String TAG_INPUT_ISBN_DIALOG = "wen-InputIsbnDialog";
    public static final String TAG_BOOK_DATA_EDIT_DIALOG = "wen-BookDataEditDialog";

    public static final String TAG_GET_BOOK_DATA_TASK = "wen-GetBookDataTask";
    public static final String TAG_GET_BOOK_URL_TASK = "wen-GetBookUrlTask";
    public static final String TAG_GET_BOOKS_TASK = "wen-GetBooksTask";

    public static final String TAG_FIREBASE_API_HELPER = "wen-FirebaseApiHelper";
    public static final String TAG_GOOGLE_API_HELPER = "wen-GoogleApiHelper";

    public static final String TAG_USER_MANAGER = "wen-UserManager";
    public static final String TAG_SHARE_BOOK_CLIENT = "wen-ShareBookClient";
    public static final String TAG_SHARE_BOOK_PARSER = "wen-ShareBookParser";

    // Firebase
    public static final String FIREBASE_GOOGLE_BOOKS = "GoogleBooks";
    public static final String FIREBASE_NODE_USERS = "Users";
    public static final String FIREBASE_NODE_BOOKS = "Books";
    public static final String FIREBASE_NODE_FRIENDS = "Friends";
    public static final String FIREBASE_NODE_LENT = "Lent";

    public static final String FIREBASE_USER_IMAGE = "image";
    public static final String FIREBASE_USER_EMAIL = "email";
    public static final String FIREBASE_CREATE_TIME = "createTime";
    public static final String FIREBASE_HAVE_BOOK = "haveBook";
    public static final String FIREBASE_FRIEND_STATUS = "status";
    public static final String FIREBASE_FRIEND_RECEIVE = "0_receive";
    public static final String FIREBASE_FRIEND_SEND = "1_send";
    public static final String FIREBASE_FRIEND_APPROVE = "2_approve";
    public static final String FIREBASE_LENT_STATUS = "lentStatus";
    public static final String FIREBASE_LENT_RECEIVE = "0_receive";
    public static final String FIREBASE_LENT_SEND = "1_send";
    public static final String FIREBASE_LENT_APPROVE = "2_approve";

    // User Manager
    public static final String USER_DATA = "user_data";
    public static final String USER_NAME = "user_name";
    public static final String USER_EMAIL = "user_email";
    public static final String USER_IMAGE = "user_image";
    public static final String USER_ID = "user_id";

    // Book Status
    public static final String BUNDLE_BOOK_STATUS = "bookStatus";
    public static final int MY_BOOK = 0;
    public static final int UNREAD = 1;
    public static final int READING = 2;
    public static final int READ = 3;
    public static final int LENT = 4;
    public static final int BORROW = 5;

    // filter
    public static final int FILTER_BOOK_ALL = 0;
    public static final int FILTER_BOOK_OWN = 1;
    public static final int FILTER_BOOK_UNREAD = 2;
    public static final int FILTER_BOOK_READING = 3;
    public static final int FILTER_BOOK_READ = 4;

    // Profile activity result
    public static final int GET_PHOTO_FROM_GALLERY = 0;
    public static final int GET_PHOTO_CROP = 1;

    // permission relative
    public static final int PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 100;
    public static final int PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 101;
    public static final int PERMISSIONS_REQUEST_CAMERA = 102;
}
