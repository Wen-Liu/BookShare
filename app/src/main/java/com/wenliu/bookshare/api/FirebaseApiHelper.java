package com.wenliu.bookshare.api;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.wenliu.bookshare.Constants;
import com.wenliu.bookshare.UserManager;
import com.wenliu.bookshare.api.callbacks.CheckBookExistCallback;
import com.wenliu.bookshare.api.callbacks.GetBooksCallback;
import com.wenliu.bookshare.api.callbacks.GetUserInfoCallback;
import com.wenliu.bookshare.api.callbacks.SignInCallback;
import com.wenliu.bookshare.api.callbacks.SignUpCallback;
import com.wenliu.bookshare.object.Book;
import com.wenliu.bookshare.object.BookCustomInfo;
import com.wenliu.bookshare.object.GoogleBook.Item;
import com.wenliu.bookshare.object.User;

import org.json.JSONObject;
import org.json.JSONStringer;

import java.util.ArrayList;

/**
 * Created by wen on 2018/5/3.
 */

public class FirebaseApiHelper {
    // Write a message to the database

    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mGetRef = mDatabase.getReference();

    public static FirebaseApiHelper newInstance() {
        return new FirebaseApiHelper();
    }

    public void uploadUser(User user, SignUpCallback callback) {
        Log.d(Constants.TAG_FIREBASE_API_HELPER, "uploadUser");

        mGetRef.child(Constants.FIREBASE_USERS).child(user.getId()).setValue(user);
        callback.onCompleted();
    }

    public void getUserInfo(String uid, final GetUserInfoCallback callback) {
        Log.d(Constants.TAG_FIREBASE_API_HELPER, "getUserInfo: ");

        Query userInfoQuery = mGetRef.child(Constants.FIREBASE_USERS).child(uid).orderByValue();
        userInfoQuery.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Log.d(Constants.TAG_FIREBASE_API_HELPER, "dataSnapshot.exists()");
                    User user = dataSnapshot.getValue(User.class);
                    callback.onCompleted(user);
                } else {
                    callback.onError("c");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(Constants.TAG_FIREBASE_API_HELPER, "onCancelled: getUserInfo" + databaseError.getMessage().toString());
                callback.onError(databaseError.getMessage());
            }
        });
    }

    public void checkBookDataExist(final String isbn, final CheckBookExistCallback callback) {
        Log.d(Constants.TAG_FIREBASE_API_HELPER, "checkBookDataExist");

        Query myBooksQuery = mGetRef.child(Constants.FIREBASE_BOOKS).orderByKey().equalTo(isbn);
        myBooksQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Log.d(Constants.TAG_FIREBASE_API_HELPER, "checkBookDataExist true");
                    Book book = dataSnapshot.child(isbn).getValue(Book.class);
                    callback.onCompleted(book);
                } else {
                    Log.d(Constants.TAG_FIREBASE_API_HELPER, "checkBookDataExist false");
                    callback.onError();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void uploadGoogleBook(String isbn, Item item) {
        Log.d(Constants.TAG_FIREBASE_API_HELPER, "uploadGoogleBook");
        mGetRef.child(Constants.FIREBASE_GOOGLE_BOOKS).child(isbn).setValue(item);
    }

    public void uploadBook(String isbn, Book book) {
        Log.d(Constants.TAG_FIREBASE_API_HELPER, "uploadBook");

        // upload to the common book pool
        mGetRef.child(Constants.FIREBASE_BOOKS).child(isbn).setValue(book);
    }

    public void uploadMyBook(String isbn, BookCustomInfo bookCustomInfo) {
        Log.d(Constants.TAG_FIREBASE_API_HELPER, "uploadMyBook ");

        // upload to user's book data
        mGetRef.child(Constants.FIREBASE_USERS)
                .child(UserManager.getInstance().getUserId())
                .child(Constants.FIREBASE_BOOKS)
                .child(isbn)
                .setValue(bookCustomInfo);
    }

    public void getMyBooks(final GetBooksCallback callback) {
        Log.d(Constants.TAG_FIREBASE_API_HELPER, "getMyBooks");

        Query myBooksQuery = mGetRef.child(Constants.FIREBASE_USERS)
                .child(UserManager.getInstance().getUserId())
                .child(Constants.FIREBASE_BOOKS)
                .orderByChild("createTime");

        myBooksQuery.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ArrayList<BookCustomInfo> mBookCustomInfos = new ArrayList<>();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        BookCustomInfo bookCustomInfo = snapshot.getValue(BookCustomInfo.class);
                        mBookCustomInfos.add(bookCustomInfo);
                    }
                    Log.d(Constants.TAG_FIREBASE_API_HELPER, "getMyBooks data exists ");
                    callback.onCompleted(mBookCustomInfos);
                } else {
                    Log.d(Constants.TAG_FIREBASE_API_HELPER, "getMyBooks get nothing ");
                    callback.onError("get nothing");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(Constants.TAG_FIREBASE_API_HELPER, "onCancelled: " + databaseError.getMessage().toString());
                callback.onError(databaseError.getMessage());
            }
        });
    }

    public void getMyBooks2(final GetBooksCallback callback) {
        Log.d(Constants.TAG_FIREBASE_API_HELPER, "getMyBooks");

        Query myBooksQuery = mGetRef.child(Constants.FIREBASE_BOOKS).orderByValue();
        myBooksQuery.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ArrayList<Book> mBooks = new ArrayList<>();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Book book = snapshot.getValue(Book.class);
                        mBooks.add(book);
                    }
                    Log.d(Constants.TAG_FIREBASE_API_HELPER, "getMyBooks data exists ");
//                    callback.onCompleted(mBooks);
                } else {
                    Log.d(Constants.TAG_FIREBASE_API_HELPER, "getMyBooks get nothing ");
                    callback.onError("get nothing");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(Constants.TAG_FIREBASE_API_HELPER, "onCancelled: " + databaseError.getMessage().toString());
                callback.onError(databaseError.getMessage());
            }
        });
    }

}
