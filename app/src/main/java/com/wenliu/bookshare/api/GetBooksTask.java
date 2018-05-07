package com.wenliu.bookshare.api;

import android.os.AsyncTask;
import android.util.Log;

import com.wenliu.bookshare.Constants;

/**
 * Created by wen on 2018/5/6.
 */

public class GetBooksTask extends AsyncTask<Void, Void, Void> {

    private GetBooksCallback mCallback;

    public GetBooksTask(GetBooksCallback callback) {
        mCallback = callback;
    }


    @Override
    protected Void doInBackground(Void... voids) {
        Log.d(Constants.TAG_GET_BOOKS_TASK, "doInBackground ");

        new FirebaseApiHelper().getMyBooks(mCallback);
        return null;
    }
}
