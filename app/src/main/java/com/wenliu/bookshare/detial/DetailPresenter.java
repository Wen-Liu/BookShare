package com.wenliu.bookshare.detial;

import android.util.Log;

import com.wenliu.bookshare.Constants;
import com.wenliu.bookshare.ShareBookContract;
import com.wenliu.bookshare.ShareBookPresenter;
import com.wenliu.bookshare.object.BookCustomInfo;

/**
 * Created by wen on 2018/5/14.
 */

public class DetailPresenter implements DetailContract.Presenter {

    private final DetailContract.View mDetailView;
    private ShareBookContract.Presenter mSharebookPresenter;
    private BookCustomInfo mBookCustomInfo;

    public DetailPresenter(DetailContract.View detailView, ShareBookPresenter presenter, BookCustomInfo bookCustomInfo) {
        Log.d(Constants.TAG_DETAIL_PRESENTER, "DetailPresenter: ");

        mDetailView = detailView;
        mDetailView.setPresenter(this);
        mSharebookPresenter = presenter;
        mBookCustomInfo = bookCustomInfo;
    }

    @Override
    public void start() {
        Log.d(Constants.TAG_DETAIL_PRESENTER, "start: ");
        mDetailView.showBook(mBookCustomInfo);
    }

    @Override
    public void showBookDataEditDialog(BookCustomInfo bookCustomInfo) {
        Log.d(Constants.TAG_DETAIL_PRESENTER, "showBookDataEditDialog: ");
        mSharebookPresenter.goToEditDialog(bookCustomInfo);
    }

    @Override
    public void setBookData(BookCustomInfo bookCustomInfo) {
        Log.d(Constants.TAG_DETAIL_PRESENTER, "setBookData: ");
        mBookCustomInfo = bookCustomInfo;
    }
}
