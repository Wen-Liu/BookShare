package com.wenliu.bookshare.profile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;

import com.wenliu.bookshare.base.BasePresenter;
import com.wenliu.bookshare.base.BaseView;
import com.wenliu.bookshare.object.User;

/**
 * Created by wen on 2018/5/17.
 */

public interface ProfileContract {

    interface View extends BaseView<Presenter> {

        void showImageOnView(Bitmap bitmap);

        void showAddFriendDialog(boolean showAlert);

        void isShowLoadingDialog(boolean isLoading);

        void isAddDialogShow(boolean isShow);

        void showFriendProfileActivity(User friend);

        void setUserImage(Uri userImageUri);
    }

    interface Presenter extends BasePresenter {
        void result(int requestCode, int resultCode, Intent intent);

        void getPhotoUri(Uri uri);

        void getPhotoFromGallery();
    }
}
