package com.wenliu.bookshare.api;

import android.util.Log;

import com.wenliu.bookshare.Constants;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by wen on 2018/5/2.
 */

public class ShareBookClient {
    /**
     * GET
     *
     * @param url
     * @return
     * @throws IOException
     */
    public String get(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        // Request build
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        Log.d(Constants.TAG_SHARE_BOOK_CLIENT, "GET " + request.url());

        Response response = client.newCall(request).execute();
        return doResponse(response);
    }


    private String doResponse(Response response) throws IOException {
        Log.d(Constants.TAG_SHARE_BOOK_CLIENT, "Response Code: " + response.code());

        if (response.isSuccessful()) {
            String responseData = response.body().string();
            Log.d(Constants.TAG_SHARE_BOOK_CLIENT, "Response Data: " + responseData);
            return responseData;

        } else {
            Log.d(Constants.TAG_SHARE_BOOK_CLIENT, "Response error");
            throw new IOException("Unexpected code " + response);
        }
    }
}
