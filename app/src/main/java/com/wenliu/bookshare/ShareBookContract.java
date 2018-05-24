package com.wenliu.bookshare;

import android.widget.ImageView;

import com.wenliu.bookshare.base.BasePresenter;
import com.wenliu.bookshare.base.BaseView;
import com.wenliu.bookshare.object.Book;
import com.wenliu.bookshare.object.BookCustomInfo;
import com.wenliu.bookshare.object.GoogleBook.MyBook;

/**
 * Created by wen on 2018/5/2.
 */

public interface ShareBookContract {

    interface View extends BaseView<Presenter> {
        void setEditText(String isbn);

        void setEditTextError(String error);

        void transToDetail(BookCustomInfo bookCustomInfo, ImageView imageView);

        void showEditDialog(BookCustomInfo bookCustomInfo);
    }


    interface Presenter extends BasePresenter {
        void transToMain();

        void transToDetail(BookCustomInfo bookCustomInfo, ImageView imageView);

        boolean isIsbnValid(String isbn);

        void checkIsbnValid(boolean isIsbnValid, String isbn);

        void refreshMainFragment();

        void refreshDetailFragment(BookCustomInfo bookCustomInfo);

        int[] getMyBookStatus();

        void goToEditDialog(BookCustomInfo bookCustomInfo);
    }

}
