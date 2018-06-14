package com.wenliu.bookshare.friend;

import android.util.Log;

import com.wenliu.bookshare.Constants;
import com.wenliu.bookshare.api.FirebaseApiHelper;
import com.wenliu.bookshare.api.callbacks.AddFriendCallback;
import com.wenliu.bookshare.api.callbacks.CheckUserExistCallback;
import com.wenliu.bookshare.api.callbacks.GetFriendsCallback;
import com.wenliu.bookshare.object.User;
import com.wenliu.bookshare.profile.ProfileActivity;

import java.util.ArrayList;

public class FriendPresenter implements FriendContract.Presenter {
    private FirebaseApiHelper mFirebaseApiHelper = FirebaseApiHelper.getInstance();
    private FriendContract.View mFriendView;
    private ProfileActivity mProfileActivity;

    public FriendPresenter(FriendContract.View friendView ,ProfileActivity profileActivity ) {
        Log.d(Constants.TAG_FRIEND_PRESENTER, "FriendPresenter: ");
        mFriendView = friendView;
        mFriendView.setPresenter(this);
        mProfileActivity = profileActivity;
    }

    @Override
    public void start() {
    }

    @Override
    public void checkUserByEmail(String email) {
        mFirebaseApiHelper.checkUserByEmail(email, new CheckUserExistCallback() {
            @Override
            public void userExist(User user) {
                mFirebaseApiHelper.sendFriendRequest(user, new AddFriendCallback() {
                    @Override
                    public void onCompleted() {
                        mProfileActivity.isAddDialogShow(false);
                    }
                });
            }

            @Override
            public void notExist() {
                mProfileActivity.showAddFriendDialog(true);
            }
        });
    }

    @Override
    public void getMyFriends() {
        mFriendView.isShowLoading(true);

        mFirebaseApiHelper.getMyFriends(new GetFriendsCallback() {
            @Override
            public void onCompleted(ArrayList<User> friends) {
                Log.d(Constants.TAG_FRIEND_PRESENTER, "getMyFriends onCompleted: ");
                mFriendView.showFriends(friends);
                mFriendView.isNoFriendData(false);
                mFriendView.isShowLoading(false);
            }

            @Override
            public void noFriendData() {
                Log.d(Constants.TAG_FRIEND_PRESENTER, "getMyFriends noFriendData: ");
                mFriendView.isNoFriendData(true);
                mFriendView.isShowLoading(false);
            }

            @Override
            public void onError(String errorMessage) {
                Log.d(Constants.TAG_FRIEND_PRESENTER, "getMyFriends onError: ");
                mFriendView.isShowLoading(false);
            }
        });
    }

    @Override
    public void transToFriendProfile(User friend) {
        mProfileActivity.showFriendProfileActivity(friend);
    }
}
