package com.wenliu.bookshare.api;

import android.util.Log;

import com.wenliu.bookshare.Constants;
import com.wenliu.bookshare.object.Book;

import java.io.IOException;

/**
 * Created by wen on 2018/5/2.
 */

public class GoogleApiHelper {
    private static final String GOOGLE_BOOK_SEARCH_BASE_URL = "https://www.googleapis.com/books/v1/volumes?q=isbn:";

    public static String getBookDetailUrlByIsbn(String isbn) throws IOException {
        Log.d(Constants.TAG_GOOGLE_API_HELPER, "getBookUrlByIsbn");

        try {
            return ShareBookParser.parseBookDetailUrl(new ShareBookClient().get(GOOGLE_BOOK_SEARCH_BASE_URL + isbn));

        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }

    public static Book getBookDataByUrl(String url) throws IOException {
        Log.d(Constants.TAG_GOOGLE_API_HELPER, "getBookDataByUrl");

        try {
            return ShareBookParser.parseBookData(new ShareBookClient().get(url));

        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }

}
