package com.wenliu.bookshare.api;

import android.os.AsyncTask;

/**
 * Created by wen on 2018/5/3.
 */

public class GetBookDataTask extends AsyncTask<Void, String, String> {
    private String mBookUrl;
    private GetBookDataCallback mCallback;
    private String mErrorMessage;

    public GetBookDataTask(String bookUrl, GetBookDataCallback callback) {
        mBookUrl = bookUrl;
        mCallback = callback;
    }

    @Override
    protected String doInBackground(Void... voids) {
        return null;
    }
}
