package com.wenliu.bookshare.profile;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import com.wenliu.bookshare.Constants;
import com.wenliu.bookshare.ShareBook;

import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by wen on 2018/5/17.
 */

public class ProfilePresenter implements ProfileContract.Presenter {

    private ProfileContract.View mProfileView;

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
}
