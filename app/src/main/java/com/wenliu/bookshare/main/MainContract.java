package com.wenliu.bookshare.main;

import android.widget.ImageView;

import com.wenliu.bookshare.api.callbacks.AlertDialogCallback;
import com.wenliu.bookshare.api.callbacks.DeleteBookCallback;
import com.wenliu.bookshare.base.BasePresenter;
import com.wenliu.bookshare.base.BaseView;
import com.wenliu.bookshare.object.Book;
import com.wenliu.bookshare.object.BookCustomInfo;
import com.wenliu.bookshare.object.GoogleBook.MyBook;

import java.util.ArrayList;

/**
 * Created by wen on 2018/5/4.
 */

public interface MainContract {

    interface View extends BaseView<Presenter> {

        void showBooks(ArrayList<BookCustomInfo> bookCustomInfos);

        void showDetailUi(BookCustomInfo bookCustomInfo, ImageView imageView);

        void showMyBookStatus(int[] bookStatusAll);

        void showProgressDialog(boolean show);

        void showAlertDialog(String title, AlertDialogCallback callback);
    }

    interface Presenter extends BasePresenter {

        void loadBooks();

        void deleteBook(String isbn, DeleteBookCallback callback);

        void openDetail(BookCustomInfo bookCustomInfo, ImageView imageView);

        void showAlertDialog(String title, AlertDialogCallback callback);
    }


}
