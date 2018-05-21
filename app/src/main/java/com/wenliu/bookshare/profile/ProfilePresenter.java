package com.wenliu.bookshare.profile;

import android.util.Log;

import com.wenliu.bookshare.Constants;

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
}
