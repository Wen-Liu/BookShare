package com.wenliu.bookshare.profile;

import android.graphics.Bitmap;
import android.net.Uri;

import com.wenliu.bookshare.base.BasePresenter;
import com.wenliu.bookshare.base.BaseView;
import com.wenliu.bookshare.object.User;

import java.util.ArrayList;

/**
 * Created by wen on 2018/5/17.
 */

public interface ProfileContract {

    interface View extends BaseView<Presenter> {

        void showImageOnView(Bitmap bitmap);

        void showAddFriendDialog(boolean showAlert);

        void showFriend(ArrayList<User> friends);

    }

    interface Presenter extends BasePresenter {

        void getPhotoUri(Uri uri);

        void checkUserByEmail(String email);

        void getMyFriends();

        void uploadProfileImage(Uri imageUri);

    }

}
