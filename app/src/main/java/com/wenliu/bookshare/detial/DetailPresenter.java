package com.wenliu.bookshare.detial;

import android.util.Log;

import com.wenliu.bookshare.Constants;
import com.wenliu.bookshare.object.Book;
import com.wenliu.bookshare.object.GoogleBook.MyBook;

/**
 * Created by wen on 2018/5/14.
 */

public class DetailPresenter implements DetailContract.Presenter {

    private final DetailContract.View mDetailView;
    private Book mBook;

    public DetailPresenter(DetailContract.View detailView, Book book) {
        Log.d(Constants.TAG_DETAIL_PRESENTER, "DetailPresenter: ");

        mDetailView = detailView;
        mDetailView.setPresenter(this);

        mBook = book;
    }

    @Override
    public void start() {
        Log.d(Constants.TAG_DETAIL_PRESENTER, "start: ");
        mDetailView.showBook(mBook);
    }

    @Override
    public void hideToolbar() {
        mDetailView.setToolbarVisibility(false);
    }

    @Override
    public void showToolbar() {
        mDetailView.setToolbarVisibility(true);
    }
}
