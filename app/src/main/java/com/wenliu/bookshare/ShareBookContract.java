package com.wenliu.bookshare;

import android.content.Intent;
import android.widget.ImageView;

import com.wenliu.bookshare.base.BasePresenter;
import com.wenliu.bookshare.base.BaseView;
import com.wenliu.bookshare.object.BookCustomInfo;

/**
 * Created by wen on 2018/5/2.
 */

public interface ShareBookContract {

    interface View extends BaseView<Presenter> {
        void setEditText(String isbn);

        void setEditTextError(String error);

        void transToDetail(BookCustomInfo bookCustomInfo);

        void showEditDialog(BookCustomInfo bookCustomInfo);
    }


    interface Presenter extends BasePresenter {

        void result(int requestCode, int resultCode, Intent intent);

        void transToMain();

        void transToDetail(BookCustomInfo bookCustomInfo);

        boolean isIsbnValid(String isbn);

        void checkIsbnValid(boolean isIsbnValid, String isbn);

        void refreshMainFragment();

        void refreshDetailFragment(BookCustomInfo bookCustomInfo);

        int[] getMyBookStatus();

        void goToEditDialog(BookCustomInfo bookCustomInfo);
    }

}
