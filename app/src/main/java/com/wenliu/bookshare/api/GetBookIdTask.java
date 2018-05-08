package com.wenliu.bookshare.api;

import android.os.AsyncTask;
import android.util.Log;

import com.wenliu.bookshare.Constants;
import com.wenliu.bookshare.api.callbacks.GetBookIdCallback;

import java.io.IOException;

import static android.support.v4.util.Preconditions.checkNotNull;

/**
 * Created by wen on 2018/5/1.
 */

public class GetBookIdTask extends AsyncTask<Void, String, String> {

    private String mIsbn;
    private GetBookIdCallback mCallback;
    private String mErrorMessage;

    // get cover: http://api.findbook.tw/book/cover/[ISBN].jpg
    // 9789863478546 深入淺出Android開發
    // 9789864060764 做工的人

    public GetBookIdTask(String isbn, GetBookIdCallback callback) {
        mIsbn = isbn;
        mCallback = callback;
    }


    @Override
    protected String doInBackground(Void... voids) {

        String bookUrl = null;

        try {
            Log.d(Constants.TAG_GET_BOOK_ID_TASK, "========== GetBookIdTask ==========");
            bookUrl = GoogleApiHelper.getBookIdByIsbn(mIsbn);

        } catch (IOException e) {
            mErrorMessage = e.getMessage();
            e.printStackTrace();
        }
            Log.d(Constants.TAG_GET_BOOK_ID_TASK, "bookUrl: " + bookUrl);

        return bookUrl;
    }

    @Override
    protected void onPostExecute(String bookUrl) {
        super.onPostExecute(bookUrl);

        if(bookUrl !=null){
            Log.d(Constants.TAG_GET_BOOK_ID_TASK, "onPostExecute");
            mCallback.onCompleted(bookUrl);

        } else if (!mErrorMessage.equals("")) {
            mCallback.onError(mErrorMessage);

        } else {
            Log.d(Constants.TAG_GET_BOOK_ID_TASK, "GetBookId fail");
        }
    }
}
