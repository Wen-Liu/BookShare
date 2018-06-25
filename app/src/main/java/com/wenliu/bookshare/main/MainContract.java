package com.wenliu.bookshare.main;

import com.wenliu.bookshare.api.callbacks.AlertDialogCallback;
import com.wenliu.bookshare.api.callbacks.DeleteBookCallback;
import com.wenliu.bookshare.base.BasePresenter;
import com.wenliu.bookshare.base.BaseView;
import com.wenliu.bookshare.object.BookCustomInfo;

import java.util.ArrayList;

/**
 * Created by wen on 2018/5/4.
 */

public interface MainContract {

    interface View extends BaseView<Presenter> {

        void showBooks(ArrayList<BookCustomInfo> bookCustomInfos);

        void showDetailUi(BookCustomInfo bookCustomInfo);

        void showMyBookStatus(int[] bookStatusAll);

        void showProgressDialog(boolean show);

        void showAlertDialog(String title, AlertDialogCallback callback);

        void isNoBookData(boolean isNoBookData);
    }

    interface Presenter extends BasePresenter {

        void loadBooks();

        void deleteBook(String isbn, DeleteBookCallback callback);

        void openDetail(BookCustomInfo bookCustomInfo);

        void showAlertDialog(String title, AlertDialogCallback callback);

        ArrayList<BookCustomInfo> dataFilter(ArrayList<BookCustomInfo> bookCustomInfos, int filter);
    }

}
