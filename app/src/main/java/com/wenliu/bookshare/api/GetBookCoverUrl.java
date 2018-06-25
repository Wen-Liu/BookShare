package com.wenliu.bookshare.api;

/**
 * Created by wen on 2018/5/1.
 */

public class GetBookCoverUrl {
    // get cover: http://api.findbook.tw/book/cover/[ISBN].jpg
    // get cover: http://findbook-document.ecoworkinc.com/openapi/user-guide

    private static final String BASE_URL = "http://api.findbook.tw/book/cover/";

    public static String getUrl(String isbn) {
        return BASE_URL + isbn + ".jpg";
    }
}
