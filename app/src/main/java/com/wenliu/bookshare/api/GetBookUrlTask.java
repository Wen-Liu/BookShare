package com.wenliu.bookshare.api;

import android.os.AsyncTask;
import android.util.Log;

import com.wenliu.bookshare.Constants;
import com.wenliu.bookshare.R;
import com.wenliu.bookshare.ShareBook;
import com.wenliu.bookshare.api.callbacks.GetBookUrlCallback;

import java.io.IOException;

/**
 * Created by wen on 2018/5/1.
 */

public class GetBookUrlTask extends AsyncTask<Void, String, String> {

    private String mIsbn;
    private GetBookUrlCallback mCallback;
    private String mErrorMessage = "";

    // get cover: http://api.findbook.tw/book/cover/[ISBN].jpg
    // 9789863478546 深入淺出Android開發
    // 9789864060764 做工的人

    public GetBookUrlTask(String isbn, GetBookUrlCallback callback) {
        mIsbn = isbn;
        mCallback = callback;
    }


    @Override
    protected String doInBackground(Void... voids) {

        String bookUrl = null;

        try {
            Log.d(Constants.TAG_GET_BOOK_URL_TASK, "========== GetBookUrlTask ==========");
            bookUrl = GoogleApiHelper.getBookDetailUrlByIsbn(mIsbn);

        } catch (IOException e) {
            mErrorMessage = e.getMessage();
            e.printStackTrace();
        }
        Log.d(Constants.TAG_GET_BOOK_URL_TASK, "bookUrl: " + bookUrl);

        return bookUrl;
    }

    @Override
    protected void onPostExecute(String bookUrl) {
        super.onPostExecute(bookUrl);

        if (!bookUrl.isEmpty()) {
            Log.d(Constants.TAG_GET_BOOK_URL_TASK, "onPostExecute");
            mCallback.onCompleted(bookUrl);

        } else if (!"".equals(mErrorMessage)) {
            Log.d(Constants.TAG_GET_BOOK_URL_TASK, "onPostExecute onError");
            mCallback.onError(mErrorMessage);

        } else if (bookUrl.isEmpty()) {
            Log.d(Constants.TAG_GET_BOOK_URL_TASK, "onPostExecute bookUrl.isEmpty()");
            mCallback.onError(ShareBook.getAppContext().getString(R.string.error_cannot_get_book_data));

        } else {
            Log.d(Constants.TAG_GET_BOOK_URL_TASK, "onPostExecute GetBookId fail");

        }
    }
}
