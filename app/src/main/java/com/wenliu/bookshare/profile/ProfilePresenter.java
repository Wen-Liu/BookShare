package com.wenliu.bookshare.profile;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.wenliu.bookshare.Constants;
import com.wenliu.bookshare.ShareBook;
import com.wenliu.bookshare.api.FirebaseApiHelper;
import com.wenliu.bookshare.friend.FriendFragment;
import com.wenliu.bookshare.friend.FriendPresenter;

import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by wen on 2018/5/17.
 */

public class ProfilePresenter implements ProfileContract.Presenter {

    private FirebaseApiHelper mFirebaseApiHelper = FirebaseApiHelper.getInstance();
    private ProfileContract.View mProfileView;
    private FragmentManager mFragmentManager;
    private FriendFragment mFriendFragment;
    private FriendPresenter mFriendPresenter;

    public static final String FRIEND = "FRIEND";

    public ProfilePresenter(ProfileContract.View profileView, FragmentManager fragmentManager) {
        Log.d(Constants.TAG_PROFILE_PRESENTER, "ProfilePresenter: ");
        mProfileView = profileView;
        mProfileView.setPresenter(this);

        mFragmentManager = fragmentManager;
    }

    @Override
    public void start() {
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
    public void uploadProfileImage(Uri imageUri) {
        mFirebaseApiHelper.uploadProfileImage(imageUri);
    }


}
