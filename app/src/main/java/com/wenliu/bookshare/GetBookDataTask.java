package com.wenliu.bookshare;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by wen on 2018/5/1.
 */

public class GetBookCoverTask extends AsyncTask<Void, String, Void> {

    private ImageView mImageView;
    private String mIsbn;
    private static final String BASE_URL = "https://www.googleapis.com/books/v1/volumes?q=isbn:";
    private String mBookdata;
    private final static String TAG = "wen_GetBookCoverTask";


    public GetBookCoverTask(String isbn) {
        this.mIsbn = isbn;
    }


    @Override
    protected Void doInBackground(Void... voids) {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(BASE_URL + mIsbn)
                .get()
                .build();

        try {
            Response response = client.newCall(request).execute();
            mBookdata = response.body().string();
            Log.d(TAG, "mBookdata = " + mBookdata);


        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


// get cover: http://api.findbook.tw/book/cover/[ISBN].jpg
// 9789863478546 深入淺出Android開發
// 9789864060764 做工的人

}
