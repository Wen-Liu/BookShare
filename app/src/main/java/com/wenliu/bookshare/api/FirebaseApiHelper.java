package com.wenliu.bookshare.api;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.wenliu.bookshare.Constants;
import com.wenliu.bookshare.UserManager;
import com.wenliu.bookshare.api.callbacks.AddFriendCallback;
import com.wenliu.bookshare.api.callbacks.CheckBookExistCallback;
import com.wenliu.bookshare.api.callbacks.CheckUserExistCallback;
import com.wenliu.bookshare.api.callbacks.DeleteBookCallback;
import com.wenliu.bookshare.api.callbacks.GetBooksCallback;
import com.wenliu.bookshare.api.callbacks.GetFriendBooksCallback;
import com.wenliu.bookshare.api.callbacks.GetFriendsCallback;
import com.wenliu.bookshare.api.callbacks.GetLendStatusCallback;
import com.wenliu.bookshare.api.callbacks.GetUserInfoCallback;
import com.wenliu.bookshare.api.callbacks.SignUpCallback;
import com.wenliu.bookshare.object.Book;
import com.wenliu.bookshare.object.BookCustomInfo;
import com.wenliu.bookshare.object.googlebook.Item;
import com.wenliu.bookshare.object.LentBook;
import com.wenliu.bookshare.object.User;

import java.util.ArrayList;

/**
 * Created by wen on 2018/5/3.
 */

public class FirebaseApiHelper {
    // Write a message to the database
    private static final FirebaseApiHelper instance = new FirebaseApiHelper();
    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mGetRef = mDatabase.getReference();
    private StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
    private ValueEventListener mFriendListener;
    private ValueEventListener mLendListener;

    public static FirebaseApiHelper getInstance() {
        return instance;
    }

    //region relate to user data
    public void uploadUser(User user, SignUpCallback callback) {
        Log.d(Constants.TAG_FIREBASE_API_HELPER, "uploadUser");

        mGetRef.child(Constants.FIREBASE_NODE_USERS).child(user.getId()).setValue(user);
        callback.onCompleted();
    }

    public void getUserInfo(String uid, final GetUserInfoCallback callback) {
        Log.d(Constants.TAG_FIREBASE_API_HELPER, "getUserInfo: ");

        Query userInfoQuery = mGetRef.child(Constants.FIREBASE_NODE_USERS).child(uid).orderByValue();
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
                Log.d(Constants.TAG_FIREBASE_API_HELPER, "onCancelled: getUserInfo"
                        + databaseError.getMessage().toString());
                callback.onError(databaseError.getMessage());
            }
        });
    }

    public void uploadProfileImage(Uri uri) {

        StorageReference riversRef = mStorageRef.child("Profile_images/" + UserManager.getInstance().getUserId() + ".jpg");

        riversRef.putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        setUserImageUrl(downloadUrl.toString());
                        Log.d(Constants.TAG_FIREBASE_API_HELPER, "uploadProfileImage + Url " + downloadUrl);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Log.d(Constants.TAG_FIREBASE_API_HELPER, "uploadProfileImage + onFailure ");

                    }
                });
    }

    private void setUserImageUrl(String userImageUrl) {
        Log.d(Constants.TAG_FIREBASE_API_HELPER, "setUserImageUrl: " + userImageUrl);

        mGetRef.child(Constants.FIREBASE_NODE_USERS).child(UserManager.getInstance().getUserId())
                .child(Constants.FIREBASE_USER_IMAGE).setValue(userImageUrl);

        UserManager.getInstance().setUserImage(userImageUrl);
    }
    //endregion

    //region relate to book
    public void checkBookDataExist(final String isbn, final CheckBookExistCallback callback) {
        Log.d(Constants.TAG_FIREBASE_API_HELPER, "checkBookDataExist");

        Query myBooksQuery = mGetRef.child(Constants.FIREBASE_NODE_BOOKS).orderByKey().equalTo(isbn);
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
        mGetRef.child(Constants.FIREBASE_NODE_BOOKS).child(isbn).setValue(book);
    }

    public void uploadMyBook(String isbn, BookCustomInfo bookCustomInfo) {
        Log.d(Constants.TAG_FIREBASE_API_HELPER, "uploadMyBook ");

        // upload to user's book data
        mGetRef.child(Constants.FIREBASE_NODE_USERS)
                .child(UserManager.getInstance().getUserId())
                .child(Constants.FIREBASE_NODE_BOOKS)
                .child(isbn)
                .setValue(bookCustomInfo);
    }

    public void deleteMyBook(String isbn, DeleteBookCallback callback) {
        Log.d(Constants.TAG_FIREBASE_API_HELPER, "deleteMyBook");

        mGetRef.child(Constants.FIREBASE_NODE_USERS)
                .child(UserManager.getInstance().getUserId())
                .child(Constants.FIREBASE_NODE_BOOKS)
                .child(isbn)
                .removeValue();

        callback.onCompleted();
    }

    public void getFriendBooks(String uid, final GetFriendBooksCallback callback) {
        Log.d(Constants.TAG_FIREBASE_API_HELPER, "getFriendBooks");

        final Query friendBooksQuery = mGetRef.child(Constants.FIREBASE_NODE_USERS)
                .child(uid)
                .child(Constants.FIREBASE_NODE_BOOKS)
                .orderByChild(Constants.FIREBASE_HAVE_BOOK)
                .equalTo(true);

        friendBooksQuery.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ArrayList<BookCustomInfo> bookCustomInfos = new ArrayList<>();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        BookCustomInfo bookCustomInfo = snapshot.getValue(BookCustomInfo.class);
                        bookCustomInfos.add(bookCustomInfo);
                    }
                    Log.d(Constants.TAG_FIREBASE_API_HELPER, "getFriendBooks data exists ");
                    callback.onCompleted(bookCustomInfos);

                } else {
                    Log.d(Constants.TAG_FIREBASE_API_HELPER, "getFriendBooks get nothing ");
                    callback.noBookData(bookCustomInfos);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(Constants.TAG_FIREBASE_API_HELPER, "onCancelled: " + databaseError.getMessage().toString());
                callback.onError(databaseError.getMessage());
            }
        });
    }

    public void getMyBooks(final GetBooksCallback callback) {
        Log.d(Constants.TAG_FIREBASE_API_HELPER, "getMyBooks");
        final int[] bookStatusAll = new int[6];

        final Query myBooksQuery = mGetRef.child(Constants.FIREBASE_NODE_USERS)
                .child(UserManager.getInstance().getUserId())
                .child(Constants.FIREBASE_NODE_BOOKS)
                .orderByChild(Constants.FIREBASE_CREATE_TIME);

        myBooksQuery.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ArrayList<BookCustomInfo> bookCustomInfos = new ArrayList<>();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        BookCustomInfo bookCustomInfo = snapshot.getValue(BookCustomInfo.class);

                        switch (bookCustomInfo.getBookReadStatus()) {
                            case Constants.UNREAD:
                                bookStatusAll[Constants.UNREAD] += 1;
                                break;
                            case Constants.READ:
                                bookStatusAll[Constants.READ] += 1;
                                break;
                            case Constants.READING:
                                bookStatusAll[Constants.READING] += 1;
                                break;
                            case Constants.LENT:
                                bookStatusAll[Constants.LENT] += 1;
                                break;
                            case Constants.BORROW:
                                bookStatusAll[Constants.BORROW] += 1;
                                break;

                            default:
                                break;
                        }

                        if (bookCustomInfo.isHaveBook()) {
                            bookStatusAll[Constants.MY_BOOK] += 1;
                        }

                        bookCustomInfos.add(bookCustomInfo);
                    }
                    Log.d(Constants.TAG_FIREBASE_API_HELPER, "getMyBooks data exists ");
                    callback.onCompleted(bookCustomInfos, bookStatusAll);

                } else {
                    Log.d(Constants.TAG_FIREBASE_API_HELPER, "getMyBooks get nothing ");
                    callback.noBookData(bookCustomInfos, bookStatusAll);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(Constants.TAG_FIREBASE_API_HELPER, "onCancelled: " + databaseError.getMessage().toString());
                callback.onError(databaseError.getMessage(), bookStatusAll);
            }
        });
    }
    //endregion

    //region relate to friend
    public void getMyFriends(final GetFriendsCallback callback) {
        Log.d(Constants.TAG_FIREBASE_API_HELPER, "getMyFriends: ");

        final Query myFriendQuery = mGetRef.child(Constants.FIREBASE_NODE_USERS)
                .child(UserManager.getInstance().getUserId())
                .child(Constants.FIREBASE_NODE_FRIENDS)
                .orderByChild(Constants.FIREBASE_FRIEND_STATUS);

        mFriendListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ArrayList<User> friends = new ArrayList<>();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        User friend = snapshot.getValue(User.class);
                        friends.add(friend);
                    }
                    Log.d(Constants.TAG_FIREBASE_API_HELPER, "getMyFriends data exists ");
                    callback.onCompleted(friends);

                } else {
                    Log.d(Constants.TAG_FIREBASE_API_HELPER, "getMyFriends get nothing ");
                    callback.noFriendData();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(Constants.TAG_FIREBASE_API_HELPER, "onCancelled: " + databaseError.getMessage().toString());
                callback.onError(databaseError.getMessage());
            }
        };

        myFriendQuery.addValueEventListener(mFriendListener);
    }

    public void removeGetMyFriendsListener() {

        mGetRef.child(Constants.FIREBASE_NODE_USERS)
                .child(UserManager.getInstance().getUserId())
                .child(Constants.FIREBASE_NODE_FRIENDS)
                .removeEventListener(mFriendListener);
    }

    public void checkUserByEmail(String email, final CheckUserExistCallback callback) {
        Log.d(Constants.TAG_FIREBASE_API_HELPER, "checkUserByEmail");

        final Query myBooksQuery = mGetRef.child(Constants.FIREBASE_NODE_USERS)
                .orderByChild(Constants.FIREBASE_USER_EMAIL)
                .equalTo(email);

        myBooksQuery.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<BookCustomInfo> bookCustomInfos = new ArrayList<>();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        User user = snapshot.getValue(User.class);

                        if (user.getId().equals(UserManager.getInstance().getUserId())) {
                            callback.notExist();
                        } else {
                            callback.userExist(user);
                        }
                    }
                    Log.d(Constants.TAG_FIREBASE_API_HELPER, "checkUserByEmail data exists ");

                } else {
                    callback.notExist();
                    Log.d(Constants.TAG_FIREBASE_API_HELPER, "checkUserByEmail nothing ");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(Constants.TAG_FIREBASE_API_HELPER, "checkUserByEmail onCancelled: " + databaseError.getMessage().toString());
                callback.notExist();
            }
        });
    }

    public void sendFriendRequest(User friend, AddFriendCallback callback) {
        Log.d(Constants.TAG_FIREBASE_API_HELPER, "sendFriendRequest: ");

        User user = UserManager.getInstance().getUser();
        user.setCreateTime(String.valueOf(System.currentTimeMillis() / 1000));
        friend.setCreateTime(String.valueOf(System.currentTimeMillis() / 1000));

        mGetRef.child(Constants.FIREBASE_NODE_USERS).child(user.getId()).child(Constants.FIREBASE_NODE_FRIENDS).child(friend.getId()).setValue(friend);
        mGetRef.child(Constants.FIREBASE_NODE_USERS).child(user.getId()).child(Constants.FIREBASE_NODE_FRIENDS).child(friend.getId()).child(Constants.FIREBASE_FRIEND_STATUS).setValue(Constants.FIREBASE_FRIEND_SEND);

        mGetRef.child(Constants.FIREBASE_NODE_USERS).child(friend.getId()).child(Constants.FIREBASE_NODE_FRIENDS).child(user.getId()).setValue(user);
        mGetRef.child(Constants.FIREBASE_NODE_USERS).child(friend.getId()).child(Constants.FIREBASE_NODE_FRIENDS).child(user.getId()).child(Constants.FIREBASE_FRIEND_STATUS).setValue(Constants.FIREBASE_FRIEND_RECEIVE);
        callback.onCompleted();
    }

    public void acceptFriendRequest(User friend) {
        String selfId = UserManager.getInstance().getUser().getId();
        mGetRef.child(Constants.FIREBASE_NODE_USERS).child(selfId).child(Constants.FIREBASE_NODE_FRIENDS).child(friend.getId()).child(Constants.FIREBASE_FRIEND_STATUS).setValue(Constants.FIREBASE_FRIEND_APPROVE);
        mGetRef.child(Constants.FIREBASE_NODE_USERS).child(friend.getId()).child(Constants.FIREBASE_NODE_FRIENDS).child(selfId).child(Constants.FIREBASE_FRIEND_STATUS).setValue(Constants.FIREBASE_FRIEND_APPROVE);
    }

    public void rejectFriendRequest(User friend) {
        String selfId = UserManager.getInstance().getUser().getId();
        Log.d(Constants.TAG_FIREBASE_API_HELPER, "rejectFriendRequest: selfId " + selfId);
        mGetRef.child(Constants.FIREBASE_NODE_USERS).child(selfId).child(Constants.FIREBASE_NODE_FRIENDS).child(friend.getId()).removeValue();
        mGetRef.child(Constants.FIREBASE_NODE_USERS).child(friend.getId()).child(Constants.FIREBASE_NODE_FRIENDS).child(selfId).removeValue();
    }
    //endregion

    //region relate to lend book
    public void borrowBook(User friend, BookCustomInfo bookCustomInfo) {
        Log.d(Constants.TAG_FIREBASE_API_HELPER, "borrowBook: ");

        String lentBookKey = friend.getId() + "_" + bookCustomInfo.getIsbn13() + "_" + UserManager.getInstance().getUserId();
        LentBook lentBook = new LentBook(friend, bookCustomInfo);

        mGetRef.child(Constants.FIREBASE_NODE_USERS).child(UserManager.getInstance().getUserId()).child(Constants.FIREBASE_NODE_LENT).child(lentBookKey).setValue(lentBook);
        mGetRef.child(Constants.FIREBASE_NODE_USERS).child(UserManager.getInstance().getUserId()).child(Constants.FIREBASE_NODE_LENT).child(lentBookKey).child(Constants.FIREBASE_LENT_STATUS).setValue(Constants.FIREBASE_LENT_SEND);

        mGetRef.child(Constants.FIREBASE_NODE_USERS).child(friend.getId()).child(Constants.FIREBASE_NODE_LENT).child(lentBookKey).setValue(lentBook);
        mGetRef.child(Constants.FIREBASE_NODE_USERS).child(friend.getId()).child(Constants.FIREBASE_NODE_LENT).child(lentBookKey).child(Constants.FIREBASE_LENT_STATUS).setValue(Constants.FIREBASE_LENT_RECEIVE);
    }

    public void getMyLendStatus(final GetLendStatusCallback callback) {
        Log.d(Constants.TAG_FIREBASE_API_HELPER, "getMyLendStatus: ");

        final Query myLentQuery = mGetRef.child(Constants.FIREBASE_NODE_USERS)
                .child(UserManager.getInstance().getUserId())
                .child(Constants.FIREBASE_NODE_LENT)
                .orderByChild(Constants.FIREBASE_LENT_STATUS);

        mLendListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ArrayList<LentBook> lentBooks = new ArrayList<>();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        LentBook lentBook = snapshot.getValue(LentBook.class);
                        lentBooks.add(lentBook);
                    }
                    Log.d(Constants.TAG_FIREBASE_API_HELPER, "getMyLendStatus data exists ");
                    callback.onCompleted(lentBooks);

                } else {
                    Log.d(Constants.TAG_FIREBASE_API_HELPER, "getMyLendStatus get nothing ");
                    callback.noLendData();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(Constants.TAG_FIREBASE_API_HELPER, "getMyLendStatus onCancelled: " + databaseError.getMessage().toString());
                callback.onError(databaseError.getMessage());
            }
        };

        myLentQuery.addValueEventListener(mLendListener);
    }

    public void removeGetMyLendStatusListener() {
        mGetRef.child(Constants.FIREBASE_NODE_USERS)
                .child(UserManager.getInstance().getUserId())
                .child(Constants.FIREBASE_NODE_LENT)
                .removeEventListener(mLendListener);
    }

    public void acceptLendRequest(LentBook lentBook) {
        Log.d(Constants.TAG_FIREBASE_API_HELPER, "acceptLendRequest: selfId ");
        String lentBookKey = lentBook.getLenderId() + "_" + lentBook.getIsbn13() + "_" + lentBook.getBorrowerId();

        mGetRef.child(Constants.FIREBASE_NODE_USERS).child(lentBook.getLenderId()).child(Constants.FIREBASE_NODE_LENT).child(lentBookKey).setValue(lentBook);
        mGetRef.child(Constants.FIREBASE_NODE_USERS).child(lentBook.getBorrowerId()).child(Constants.FIREBASE_NODE_LENT).child(lentBookKey).setValue(lentBook);
    }

    public void rejectLendRequest(LentBook lentBook) {
        String lentBookKey = lentBook.getLenderId() + "_" + lentBook.getIsbn13() + "_" + lentBook.getBorrowerId();

        Log.d(Constants.TAG_FIREBASE_API_HELPER, "rejectLendRequest: selfId ");
        mGetRef.child(Constants.FIREBASE_NODE_USERS).child(lentBook.getLenderId()).child(Constants.FIREBASE_NODE_LENT).child(lentBookKey).removeValue();
        mGetRef.child(Constants.FIREBASE_NODE_USERS).child(lentBook.getBorrowerId()).child(Constants.FIREBASE_NODE_LENT).child(lentBookKey).removeValue();
    }

    public void confirmBookReturn(LentBook lentBook) {
        String lentBookKey = lentBook.getLenderId() + "_" + lentBook.getIsbn13() + "_" + lentBook.getBorrowerId();

        Log.d(Constants.TAG_FIREBASE_API_HELPER, "rejectLendRequest: selfId ");
        mGetRef.child(Constants.FIREBASE_NODE_USERS).child(lentBook.getLenderId()).child(Constants.FIREBASE_NODE_LENT).child(lentBookKey).removeValue();
        mGetRef.child(Constants.FIREBASE_NODE_USERS).child(lentBook.getBorrowerId()).child(Constants.FIREBASE_NODE_LENT).child(lentBookKey).removeValue();
    }
    //endregion
}
