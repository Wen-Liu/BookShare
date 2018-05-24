package com.wenliu.bookshare.detial;

import android.util.Log;

import com.wenliu.bookshare.Constants;
import com.wenliu.bookshare.ShareBookContract;
import com.wenliu.bookshare.ShareBookPresenter;
import com.wenliu.bookshare.object.Book;
import com.wenliu.bookshare.object.BookCustomInfo;
import com.wenliu.bookshare.object.GoogleBook.MyBook;

/**
 * Created by wen on 2018/5/14.
 */

public class DetailPresenter implements DetailContract.Presenter {

    private final DetailContract.View mDetailView;
    private ShareBookContract.Presenter mPresenter;
    private BookCustomInfo mBookCustomInfo;

    public DetailPresenter(DetailContract.View detailView, ShareBookPresenter presenter, BookCustomInfo bookCustomInfo) {
        Log.d(Constants.TAG_DETAIL_PRESENTER, "DetailPresenter: ");

        mDetailView = detailView;
        mDetailView.setPresenter(this);
        mPresenter = presenter;
        mBookCustomInfo = bookCustomInfo;
    }

    @Override
    public void start() {
        Log.d(Constants.TAG_DETAIL_PRESENTER, "start: ");
        mDetailView.showBook(mBookCustomInfo);
    }

    @Override
    public void hideToolbar() {
        mDetailView.setToolbarVisibility(false);
    }

    @Override
    public void showToolbar() {
        mDetailView.setToolbarVisibility(true);
    }

    @Override
    public void hideFab() {
        mDetailView.setFabVisibility(false);
    }

    @Override
    public void showFab() {
        mDetailView.setFabVisibility(true);
    }

    @Override
    public void showBookDataEditDialog(BookCustomInfo bookCustomInfo) {
        mPresenter.goToEditDialog(bookCustomInfo);
    }
}
