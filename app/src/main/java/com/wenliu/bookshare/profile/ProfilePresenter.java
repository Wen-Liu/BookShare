package com.wenliu.bookshare.profile;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.wenliu.bookshare.Constants;
import com.wenliu.bookshare.R;
import com.wenliu.bookshare.ShareBook;
import com.wenliu.bookshare.api.FirebaseApiHelper;
import com.wenliu.bookshare.api.callbacks.AddFriendCallback;
import com.wenliu.bookshare.api.callbacks.CheckUserExistCallback;
import com.wenliu.bookshare.api.callbacks.GetFriendsCallback;
import com.wenliu.bookshare.friend.FriendFragment;
import com.wenliu.bookshare.friend.FriendPresenter;
import com.wenliu.bookshare.object.User;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by wen on 2018/5/17.
 */

public class ProfilePresenter implements ProfileContract.Presenter {

    private FirebaseApiHelper mFirebaseApiHelper = FirebaseApiHelper.newInstance();
    private ProfileContract.View mProfileView;
    private FragmentManager mFragmentManager;
    private FriendFragment mFriendFragment;
    private FriendPresenter mFriendPresenter;

    public static final String FRIEND = "FRIEND";

    public ProfilePresenter(ProfileContract.View profileView, FragmentManager fragmentManager) {
        Log.d(Constants.TAG_PROFILE_PRESENTER, "ProfilePresenter: ");
        mProfileView = profileView;
        mFragmentManager = fragmentManager;
    }

    @Override
    public void start() {
        mProfileView.setPresenter(this);
        transToFriend();
    }

    @Override
    public void getPhotoUri(Uri uri) {
        Log.d(Constants.TAG_PROFILE_PRESENTER, "getPhotoUri: ");
        InputStream inputStream = null;
        try {
            inputStream = ShareBook.getAppContext().getContentResolver().openInputStream(uri);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        mProfileView.showImageOnView(bitmap);
    }

    @Override
    public void checkUserByEmail(String email) {
        mFirebaseApiHelper.checkUserByEmail(email, new CheckUserExistCallback() {
            @Override
            public void userExist(User user) {
                mFirebaseApiHelper.sendFriendRequest(user, new AddFriendCallback() {
                    @Override
                    public void onCompleted() {
                        getMyFriends();
                        mProfileView.isAddDialogShow(false);
                    }
                });
            }

            @Override
            public void notExist() {
                mProfileView.showAddFriendDialog(true);
            }
        });
    }

    @Override
    public void getMyFriends() {

        mProfileView.showProgressDialog(true);

        mFirebaseApiHelper.getMyFriends(new GetFriendsCallback() {
            @Override
            public void onCompleted(ArrayList<User> friends) {
                mFriendFragment.showFriends(friends);
//                mProfileView.showFriend(friends);
//                mProfileView.isNoFriendData(false);
                mProfileView.showProgressDialog(false);
            }

            @Override
            public void noFriendData() {
                Log.d(Constants.TAG_PROFILE_PRESENTER, "noFriendData: ");
//                mProfileView.isNoFriendData(true);
                mProfileView.showProgressDialog(false);
            }

            @Override
            public void onError(String errorMessage) {
                mProfileView.showProgressDialog(false);
            }
        });
    }

    @Override
    public void uploadProfileImage(Uri imageUri) {
        mFirebaseApiHelper.uploadProfileImage(imageUri);
    }

    @Override
    public void transToFriendProfile(User friend) {
        mProfileView.showFriendProfile(friend);
    }

    @Override
    public void transToFriend() {
        Log.d(Constants.TAG_PROFILE_PRESENTER, "transToFriend: ");

        FragmentTransaction transaction = mFragmentManager.beginTransaction();

        if (mFriendFragment == null) mFriendFragment = FriendFragment.newInstance();
        if (!mFriendFragment.isAdded()) {
            transaction.add(R.id.frame_container_profile, mFriendFragment, FRIEND);
        } else {
            transaction.show(mFriendFragment);
        }
        transaction.commit();

        if (mFriendPresenter == null) {
            mFriendPresenter = new FriendPresenter(mFriendFragment);
        }
    }


}
