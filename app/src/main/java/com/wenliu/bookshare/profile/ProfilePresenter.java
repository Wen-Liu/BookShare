package com.wenliu.bookshare.profile;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import com.wenliu.bookshare.Constants;
import com.wenliu.bookshare.ShareBook;
import com.wenliu.bookshare.api.FirebaseApiHelper;
import com.wenliu.bookshare.api.callbacks.AddFriendCallback;
import com.wenliu.bookshare.api.callbacks.CheckUserExistCallback;
import com.wenliu.bookshare.api.callbacks.GetFriendsCallback;
import com.wenliu.bookshare.object.User;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by wen on 2018/5/17.
 */

public class ProfilePresenter implements ProfileContract.Presenter {

    private ProfileContract.View mProfileView;
    private FirebaseApiHelper mFirebaseApiHelper = FirebaseApiHelper.newInstance();

    public ProfilePresenter(ProfileContract.View profileView) {
        Log.d(Constants.TAG_PROFILE_PRESENTER, "ProfilePresenter: ");
        mProfileView = profileView;
    }

    @Override
    public void start() {
        mProfileView.setPresenter(this);
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
                mFirebaseApiHelper.addFriend(user , new AddFriendCallback() {
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
                mProfileView.showFriend(friends);
                mProfileView.isNoFriendData(false);
                mProfileView.showProgressDialog(false);
            }

            @Override
            public void noFriendData() {
                Log.d(Constants.TAG_PROFILE_PRESENTER, "noFriendData: ");
                mProfileView.isNoFriendData(true);
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


}
