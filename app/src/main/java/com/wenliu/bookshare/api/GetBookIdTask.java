package com.wenliu.bookshare.api;

import android.os.AsyncTask;
import android.util.Log;

import com.wenliu.bookshare.Constants;

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

            Log.d(Constants.TAG_GET_BOOK_DATA_TASK, " ");
            bookUrl = ApiHelper.getBookIdByIsbn(mIsbn);

//            String id = new JSONObject(mBookData).getJSONArray("items").getJSONObject(0).getString("id");
//            Log.d(Constants.TAG_GET_BOOK_DATA_TASK, "id: " + id);
//
//            Gson gson = new Gson();
//            SearchResult searchResult = gson.fromJson(mBookData, SearchResult.class);
//
//            Title = searchResult.getItems().get(0).getVolumeInfo().getTitle().toString();
//            Log.d(Constants.TAG_GET_BOOK_DATA_TASK, "Title = " + Title);
//
//
//            // Write a message to the database
//            FirebaseDatabase database = FirebaseDatabase.getInstance();
//            DatabaseReference myRef = database.getReference("google");
//
//            myRef.child(searchResult.getItems().get(0).getId()).setValue(searchResult);

        } catch (IOException e) {
            mErrorMessage = e.getMessage();
            e.printStackTrace();
//        } catch (JSONException e) {
//            e.printStackTrace();
        }
            Log.d(Constants.TAG_GET_BOOK_DATA_TASK, "bookUrl: " + bookUrl);

        return bookUrl;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        if(s !=null){
            Log.d(Constants.TAG_GET_BOOK_DATA_TASK, "onPostExecute");
            mCallback.onCompleted(s);

        } else if (!mErrorMessage.equals("")) {
            mCallback.onError(mErrorMessage);

        } else {
            Log.d(Constants.TAG_GET_BOOK_DATA_TASK, "GetBook fail");
        }
    }
}
