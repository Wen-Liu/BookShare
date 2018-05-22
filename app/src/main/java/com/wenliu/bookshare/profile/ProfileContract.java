package com.wenliu.bookshare.profile;

import android.graphics.Bitmap;
import android.net.Uri;

import com.wenliu.bookshare.base.BasePresenter;
import com.wenliu.bookshare.base.BaseView;

/**
 * Created by wen on 2018/5/17.
 */

public interface ProfileContract {

    interface View extends BaseView<Presenter> {

        void showImageOnView(Bitmap bitmap);

    }

    interface Presenter extends BasePresenter {

        void getPhotoUri(Uri uri);

    }

}
