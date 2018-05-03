package com.wenliu.bookshare.api;

import android.os.AsyncTask;
import android.util.Log;

import com.wenliu.bookshare.Constants;
import com.wenliu.bookshare.object.GoogleBook.Item;

import java.io.IOException;

/**
 * Created by wen on 2018/5/3.
 */

public class GetBookDataTask extends AsyncTask<Void, String, Item> {
    private String mBookUrl;
    private GetBookDataCallback mCallback;
    private String mErrorMessage;

    public GetBookDataTask(String bookUrl, GetBookDataCallback callback) {
        mBookUrl = bookUrl;
        mCallback = callback;
    }

    @Override
    protected Item doInBackground(Void... voids) {
        Item bookData = null;

        try {
            Log.d(Constants.TAG_GET_BOOK_DATA_TASK, "=================================");
            bookData = ApiHelper.getBookDataById(mBookUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bookData;
    }

    @Override
    protected void onPostExecute(Item item) {
        super.onPostExecute(item);

        if(item !=null){
            Log.d(Constants.TAG_GET_BOOK_DATA_TASK, "onPostExecute");
            mCallback.onCompleted(item);

        } else if (!mErrorMessage.equals("")) {
            mCallback.onError(mErrorMessage);

        } else {
            Log.d(Constants.TAG_GET_BOOK_DATA_TASK, "GetBookId fail");
        }

    }
}
