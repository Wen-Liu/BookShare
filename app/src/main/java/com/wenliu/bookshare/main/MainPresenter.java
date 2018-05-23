package com.wenliu.bookshare.main;

import android.util.Log;
import android.widget.ImageView;

import com.wenliu.bookshare.Constants;
import com.wenliu.bookshare.api.callbacks.GetBooksCallback;
import com.wenliu.bookshare.api.GetBooksTask;
import com.wenliu.bookshare.object.BookCustomInfo;

import java.util.ArrayList;

/**
 * Created by wen on 2018/5/4.
 */

public class MainPresenter implements MainContract.Presenter {

    private final MainContract.View mMainView;
    private boolean isLoading = false;

    public MainPresenter(MainContract.View mainView) {
        mMainView = mainView;
        mMainView.setPresenter(this);
    }

    @Override
    public void start() {
        Log.d(Constants.TAG_MAIN_PRESENTER, "start");
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
                public void onCompleted(ArrayList<BookCustomInfo> bookCustomInfos, int[] bookStatusAll) {
                    setLoading(false);
                    Log.d(Constants.TAG_MAIN_PRESENTER, "GetBooksTask onCompleted");
                    mMainView.showBooks(bookCustomInfos);
                    mMainView.showMyBookStatus(bookStatusAll);
                    mMainView.showProgressDialog(false);
                }

                @Override
                public void onError(String errorMessage) {
                    Log.d(Constants.TAG_MAIN_PRESENTER, "GetBooksTask onError: " + errorMessage);
                    setLoading(false);
                    mMainView.showProgressDialog(false);
                }
            }).execute();
        }
    }

    @Override
    public void openDetail(BookCustomInfo bookCustomInfo, ImageView imageView) {
        Log.d(Constants.TAG_MAIN_PRESENTER, "openDetail: ");
        mMainView.showDetailUi(bookCustomInfo, imageView);
    }


    public void setLoading(boolean loading) {
        isLoading = loading;
    }

}
