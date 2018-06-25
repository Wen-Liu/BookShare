package com.wenliu.bookshare.api;

import android.util.Log;

import com.google.gson.Gson;
import com.wenliu.bookshare.Constants;
import com.wenliu.bookshare.UserManager;
import com.wenliu.bookshare.object.Book;
import com.wenliu.bookshare.object.googlebook.Item;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wen on 2018/5/3.
 */

public class ShareBookParser {

    public static String parseBookDetailUrl(String jsonString) {
        JSONObject jsonObject;
        String bookDetailUrl = "";
        int totalItems;

        try {
            jsonObject = new JSONObject(jsonString);
            totalItems = jsonObject.getInt("totalItems");

            if (totalItems > 0) {
                bookDetailUrl = jsonObject.getJSONArray("items").getJSONObject(0).getString("selfLink");
                Log.d(Constants.TAG_SHARE_BOOK_PARSER, "bookDetailUrl: " + bookDetailUrl);
            } else {
                Log.d(Constants.TAG_SHARE_BOOK_PARSER, "totalItems: " + totalItems);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return bookDetailUrl;
    }

    public static Book parseBookData(String jsonString) {
        Item googleBookData;
        Book book;

        Gson gson = new Gson();
        googleBookData = gson.fromJson(jsonString, Item.class);
        book = parseBook(googleBookData);

        FirebaseApiHelper.getInstance().uploadGoogleBook(googleBookData.getVolumeInfo().getIndustryIdentifiers().get(1).getIdentifier(), googleBookData);
        FirebaseApiHelper.getInstance().uploadBook(book.getIsbn13(), book);

        Log.d(Constants.TAG_SHARE_BOOK_PARSER, "parseBookData");

        return book;
    }


    public static Book parseBook(Item item) {
        Book book = new Book();

        book.setBookSource("google");
        book.setCreateByUid(UserManager.getInstance().getUserId());
        book.setTitle(item.getVolumeInfo().getTitle());
        book.setSubtitle(item.getVolumeInfo().getSubtitle());
        book.setAuthor(item.getVolumeInfo().getAuthors());
        book.setIsbn10(item.getVolumeInfo().getIndustryIdentifiers().get(0).getIdentifier());
        book.setIsbn13(item.getVolumeInfo().getIndustryIdentifiers().get(1).getIdentifier());
        book.setPublisher(item.getVolumeInfo().getPublisher());
        book.setPublishDate(item.getVolumeInfo().getPublishedDate());
        book.setLanguage(checkLanguage(item.getVolumeInfo().getLanguage()));
        book.setImage(GetBookCoverUrl.getUrl(item.getVolumeInfo().getIndustryIdentifiers().get(1).getIdentifier()));

        return book;
    }

    public static String checkLanguage(String language) {

        switch (language) {
            case "zh":
                return "中文";
            case "zh-TW":
                return "繁體中文";
            case "zh-CN":
                return "簡體中文";
            case "en":
                return "English";
            case "es":
                return "Español";
            default:
                return language;
        }
    }
}
