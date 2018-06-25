package com.wenliu.bookshare.lent;

import android.util.Log;

import com.wenliu.bookshare.Constants;
import com.wenliu.bookshare.api.FirebaseApiHelper;
import com.wenliu.bookshare.api.callbacks.GetLendStatusCallback;
import com.wenliu.bookshare.object.LentBook;

import java.util.ArrayList;

public class LentPresenter implements LentContract.Presenter {

    private LentContract.View mLentView;
    private FirebaseApiHelper mFirebaseApiHelper = FirebaseApiHelper.getInstance();


    public LentPresenter(LentContract.View lentView) {
        Log.d(Constants.TAG_LENT_PRESENTER, "LentPresenter: ");
        mLentView = lentView;
        mLentView.setPresenter(this);
    }

    @Override
    public void start() {
        getMyLentData();
    }

    @Override
    public void getMyLentData() {
        mLentView.isShowLoading(true);

        mFirebaseApiHelper.getMyLendStatus(new GetLendStatusCallback() {
            @Override
            public void onCompleted(ArrayList<LentBook> lentBooks) {
                Log.d(Constants.TAG_LENT_PRESENTER, "getMyLentData onCompleted: ");
                mLentView.showLent(lentBooks);
                mLentView.isNoLentData(false);
                mLentView.isShowLoading(false);
            }

            @Override
            public void noLendData() {
                Log.d(Constants.TAG_LENT_PRESENTER, "getMyLentData noLendData: ");
                mLentView.isNoLentData(true);
                mLentView.isShowLoading(false);
            }

            @Override
            public void onError(String errorMessage) {
                Log.d(Constants.TAG_LENT_PRESENTER, "getMyLentData onError: ");
                mLentView.isShowLoading(false);
            }
        });
    }

    @Override
    public void confirmReject(LentBook lentBook) {
        mLentView.showConfirmReject(lentBook);
    }

    @Override
    public void confirmAccept(LentBook lentBook) {
        mLentView.showConfirmAccept(lentBook);
    }

    @Override
    public void confirmReturnBook(LentBook lentBook) {
        mLentView.showConfirmReturnBook(lentBook);
    }

    @Override
    public void sendBorrowAccept(LentBook lentBook) {
        mFirebaseApiHelper.acceptLendRequest(lentBook);
    }

    @Override
    public void sendBorrowReject(LentBook lentBook) {
        mFirebaseApiHelper.rejectLendRequest(lentBook);
    }
}
