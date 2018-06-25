package com.wenliu.bookshare.api;

import android.os.AsyncTask;
import android.util.Log;

import com.wenliu.bookshare.Constants;
import com.wenliu.bookshare.api.callbacks.GetBookDataCallback;
import com.wenliu.bookshare.object.Book;

import java.io.IOException;

/**
 * Created by wen on 2018/5/3.
 */

public class GetBookDataTask extends AsyncTask<Void, String, Book> {
    private String mBookUrl;
    private GetBookDataCallback mCallback;
    private String mErrorMessage;

    public GetBookDataTask(String bookUrl, GetBookDataCallback callback) {
        mBookUrl = bookUrl;
        mCallback = callback;
    }

    @Override
    protected Book doInBackground(Void... voids) {
        Book bookData = null;

        try {
            Log.d(Constants.TAG_GET_BOOK_DATA_TASK, "========== GetBookDataTask ==========");
            bookData = GoogleApiHelper.getBookDataByUrl(mBookUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bookData;
    }

    @Override
    protected void onPostExecute(Book book) {
        super.onPostExecute(book);

        if (book != null) {
            Log.d(Constants.TAG_GET_BOOK_DATA_TASK, "onPostExecute");
            mCallback.onCompleted(book);

        } else if (!mErrorMessage.equals("")) {
            mCallback.onError(mErrorMessage);

        } else {
            Log.d(Constants.TAG_GET_BOOK_DATA_TASK, "GetBookId fail");
        }

    }
}
