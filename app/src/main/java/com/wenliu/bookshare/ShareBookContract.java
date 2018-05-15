package com.wenliu.bookshare;

import com.wenliu.bookshare.base.BasePresenter;
import com.wenliu.bookshare.base.BaseView;
import com.wenliu.bookshare.object.Book;
import com.wenliu.bookshare.object.GoogleBook.MyBook;

/**
 * Created by wen on 2018/5/2.
 */

public interface ShareBookContract {

    interface View extends BaseView<Presenter> {
        void setEditText(String isbn);

        void setEditTextError(String error);

        void transToDetail(Book book);
    }


    interface Presenter extends BasePresenter {
        void transToMain();

        void transToDetail(Book book);

        boolean isIsbnValid(String isbn);

        void checkIsbnValid(boolean isIsbnValid, String isbn);

        void refreshMainFragment();

    }

}
