package com.wenliu.bookshare.api;

import android.util.Log;

import com.google.gson.Gson;
import com.wenliu.bookshare.Constants;
import com.wenliu.bookshare.object.GoogleBook.Item;

import java.io.IOException;

/**
 * Created by wen on 2018/5/2.
 */

public class ApiHelper {
    private static final String GOOGLE_BOOK_SEARCH_BASE_URL = "https://www.googleapis.com/books/v1/volumes?q=isbn:";

    public static String getBookIdByIsbn(String isbn) throws IOException {
        Log.d(Constants.TAG_API_HELPER, "getBookIdByIsbn");

        try {
            return ShareBookParser.parseBookId(new ShareBookClient().get(GOOGLE_BOOK_SEARCH_BASE_URL + isbn));

        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }


    public static Item getBookDataById(String id) throws IOException {
        Log.d(Constants.TAG_API_HELPER, "getBookDataById");

        try {
            return ShareBookParser.parseBookData(new ShareBookClient().get(id));

        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }

}
