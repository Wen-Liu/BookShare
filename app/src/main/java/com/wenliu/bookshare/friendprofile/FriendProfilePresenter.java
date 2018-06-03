package com.wenliu.bookshare.friendprofile;

import android.util.Log;

import com.wenliu.bookshare.Constants;
import com.wenliu.bookshare.api.FirebaseApiHelper;
import com.wenliu.bookshare.api.callbacks.GetBooksCallback;
import com.wenliu.bookshare.api.callbacks.GetFriendBooksCallback;
import com.wenliu.bookshare.object.BookCustomInfo;
import com.wenliu.bookshare.object.User;

import java.util.ArrayList;

public class FriendProfilePresenter implements FriendProfileContract.Presenter {

    private FriendProfileContract.View mFriendProfileView;

    public FriendProfilePresenter(FriendProfileContract.View friendProfileView) {
        mFriendProfileView = friendProfileView;
        Log.d(Constants.TAG_FRIEND_PROFILE_PRESENTER, "constructor: ");
    }

    @Override
    public void start() {
        mFriendProfileView.setPresenter(this);
    }

    @Override
    public void getFriendBooks(String uid) {
        Log.d(Constants.TAG_FRIEND_PROFILE_PRESENTER, "getFriendBooks: ");

        FirebaseApiHelper.newInstance().getFriendBooks(uid, new GetFriendBooksCallback() {
            @Override
            public void onCompleted(ArrayList<BookCustomInfo> bookCustomInfos) {
                mFriendProfileView.showFriendBooks(bookCustomInfos);
            }

            @Override
            public void noBookData(ArrayList<BookCustomInfo> bookCustomInfos) {

            }

            @Override
            public void onError(String errorMessage) {

            }
        });
    }

    @Override
    public void openDetailPage(BookCustomInfo bookCustomInfo) {

    }

    @Override
    public void confirmBorrowRequest(BookCustomInfo bookCustomInfo) {
        mFriendProfileView.showConfirmDialog(bookCustomInfo);
    }

    @Override
    public void sendBorrowRequest(User friend, BookCustomInfo bookCustomInfo) {
        FirebaseApiHelper.newInstance().borrowBook(friend, bookCustomInfo);
    }
}
