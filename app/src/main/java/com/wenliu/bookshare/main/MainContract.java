package com.wenliu.bookshare.main;

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

        void showDetailUi(BookCustomInfo bookCustomInfo);

        void setMyBookStatus(int[] bookStatusAll);

    }

    interface Presenter extends BasePresenter {

        void loadBooks();

        void openDetail(BookCustomInfo bookCustomInfo);

    }


}
