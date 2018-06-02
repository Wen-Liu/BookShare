package com.wenliu.bookshare.lent;


import android.util.Log;

import com.wenliu.bookshare.Constants;

public class LentPresenter implements LentContract.Presenter {

    private LentContract.View mLentView;

    public LentPresenter(LentContract.View lentView) {
        Log.d(Constants.TAG_LENT_PRESENTER, "LentPresenter: ");
        mLentView = lentView;
        mLentView.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
