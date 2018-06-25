package com.wenliu.bookshare.main;

import android.util.Log;
import com.wenliu.bookshare.Constants;
import com.wenliu.bookshare.api.FirebaseApiHelper;
import com.wenliu.bookshare.api.GetBooksTask;
import com.wenliu.bookshare.api.callbacks.AlertDialogCallback;
import com.wenliu.bookshare.api.callbacks.DeleteBookCallback;
import com.wenliu.bookshare.api.callbacks.GetBooksCallback;
import com.wenliu.bookshare.object.BookCustomInfo;

import java.util.ArrayList;

/**
 * Created by wen on 2018/5/4.
 */

public class MainPresenter implements MainContract.Presenter {
    private final MainContract.View mMainView;
    private boolean isLoading = false;
    private static ArrayList<BookCustomInfo> newBookCustomInfos = new ArrayList<>();

    public MainPresenter(MainContract.View mainView) {
        Log.d(Constants.TAG_MAIN_PRESENTER, "constructor: ");
        mMainView = mainView;
        mMainView.setPresenter(this);
    }

    @Override
    public void start() {
        Log.d(Constants.TAG_MAIN_PRESENTER, "start: ");
        loadBooks();
    }

    @Override
    public void loadBooks() {
        Log.d(Constants.TAG_MAIN_PRESENTER, "loadBooks");

        if (!isLoading) {
            setLoading(true);
            mMainView.showProgressDialog(true);

            new GetBooksTask(new GetBooksCallback() {
                @Override
                public void onCompleted(ArrayList<BookCustomInfo> bookCustomInfos, int[] bookStatusInfo) {
                    Log.d(Constants.TAG_MAIN_PRESENTER, "GetBooksTask onCompleted: ");

                    setLoading(false);
                    mMainView.showBooks(bookCustomInfos);
                    mMainView.showMyBookStatus(bookStatusInfo);
                    mMainView.isNoBookData(false);
                    mMainView.showProgressDialog(false);
                }

                @Override
                public void noBookData(ArrayList<BookCustomInfo> bookCustomInfos, int[] bookStatusInfo) {
                    Log.d(Constants.TAG_MAIN_PRESENTER, "GetBooksTask noBookData: ");

                    setLoading(false);
                    mMainView.showBooks(bookCustomInfos);
                    mMainView.showMyBookStatus(bookStatusInfo);
                    mMainView.isNoBookData(true);
                    mMainView.showProgressDialog(false);
                }

                @Override
                public void onError(String errorMessage, int[] bookStatusInfo) {
                    Log.d(Constants.TAG_MAIN_PRESENTER, "GetBooksTask onError: " + errorMessage);

                    setLoading(false);
                    mMainView.showMyBookStatus(bookStatusInfo);
                    mMainView.isNoBookData(true);
                    mMainView.showProgressDialog(false);
                }
            }).execute();
        }
    }

    @Override
    public void openDetail(BookCustomInfo bookCustomInfo) {
        Log.d(Constants.TAG_MAIN_PRESENTER, "openDetail: ");
        mMainView.showDetailUi(bookCustomInfo);
    }

    @Override
    public void showAlertDialog(String title, final AlertDialogCallback callback) {
        mMainView.showAlertDialog(title, callback);
    }

    @Override
    public ArrayList<BookCustomInfo> dataFilter(ArrayList<BookCustomInfo> bookCustomInfos, int filter) {
        Log.d(Constants.TAG_MAIN_PRESENTER, "DataFilter: ");
        newBookCustomInfos.clear();

        switch (filter) {
            case Constants.FILTER_BOOK_ALL:
                Log.d(Constants.TAG_MAIN_PRESENTER, "DataFilter: Constants.FILTER_BOOK_ALL " + Constants.FILTER_BOOK_ALL);
                return bookCustomInfos;

            case Constants.FILTER_BOOK_OWN:
                for (BookCustomInfo bookCustomInfo : bookCustomInfos) {
                    if (bookCustomInfo.isHaveBook()) {
                        newBookCustomInfos.add(bookCustomInfo);
                    }
                }
                Log.d(Constants.TAG_MAIN_PRESENTER, "DataFilter: Constants.FILTER_BOOK_OWN " + Constants.FILTER_BOOK_OWN + "  " + newBookCustomInfos.size());
                return newBookCustomInfos;

            case Constants.FILTER_BOOK_UNREAD:
                for (BookCustomInfo bookCustomInfo : bookCustomInfos) {
                    if (bookCustomInfo.getBookReadStatus() == Constants.UNREAD) {
                        newBookCustomInfos.add(bookCustomInfo);
                    }
                }
                Log.d(Constants.TAG_MAIN_PRESENTER, "DataFilter: Constants.FILTER_BOOK_UNREAD " + Constants.FILTER_BOOK_UNREAD + "  " + newBookCustomInfos.size());
                return newBookCustomInfos;

            case Constants.FILTER_BOOK_READING:
                for (BookCustomInfo bookCustomInfo : bookCustomInfos) {
                    if (bookCustomInfo.getBookReadStatus() == Constants.READING) {
                        newBookCustomInfos.add(bookCustomInfo);
                    }
                }
                Log.d(Constants.TAG_MAIN_PRESENTER, "DataFilter: Constants.FILTER_BOOK_READING " + Constants.FILTER_BOOK_READING + "  " + newBookCustomInfos.size());
                return newBookCustomInfos;

            case Constants.FILTER_BOOK_READ:
                for (BookCustomInfo bookCustomInfo : bookCustomInfos) {
                    if (bookCustomInfo.getBookReadStatus() == Constants.READ) {
                        newBookCustomInfos.add(bookCustomInfo);
                    }
                }
                Log.d(Constants.TAG_MAIN_PRESENTER, "DataFilter: Constants.FILTER_BOOK_READ " + Constants.FILTER_BOOK_READ + "  " + newBookCustomInfos.size());
                return newBookCustomInfos;

            default:
                return bookCustomInfos;
        }
    }


    @Override
    public void deleteBook(String isbn, DeleteBookCallback callback) {
        Log.d(Constants.TAG_MAIN_PRESENTER, "deleteBook: ");
        FirebaseApiHelper.getInstance().deleteMyBook(isbn, callback);
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }
}
