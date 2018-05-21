package com.wenliu.bookshare.api;

import android.util.Log;

import com.google.gson.Gson;
import com.wenliu.bookshare.Constants;
import com.wenliu.bookshare.UserManager;
import com.wenliu.bookshare.object.Book;
import com.wenliu.bookshare.object.GoogleBook.Item;

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

            if (totalItems > 0){
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

        FirebaseApiHelper.newInstance().uploadGoogleBook(googleBookData.getVolumeInfo().getIndustryIdentifiers().get(1).getIdentifier(), googleBookData);
        FirebaseApiHelper.newInstance().uploadBook(book.getIsbn13(), book);

        Log.d(Constants.TAG_SHARE_BOOK_PARSER, "parseBookData");

        return book;
    }


    public static Book parseBook(Item item) {
        Book oBook = new Book();

        oBook.setBookSource("google");
        oBook.setCreateByUid(UserManager.getInstance().getUserId());
        oBook.setTitle(item.getVolumeInfo().getTitle());
        oBook.setSubtitle(item.getVolumeInfo().getSubtitle());
        oBook.setAuthor(item.getVolumeInfo().getAuthors());
        oBook.setIsbn10(item.getVolumeInfo().getIndustryIdentifiers().get(0).getIdentifier());
        oBook.setIsbn13(item.getVolumeInfo().getIndustryIdentifiers().get(1).getIdentifier());
        oBook.setPublisher(item.getVolumeInfo().getPublisher());
        oBook.setPublishDate(item.getVolumeInfo().getPublishedDate());
        oBook.setLanguage(item.getVolumeInfo().getLanguage());
        oBook.setImage(GetBookCoverUrl.GetUrl(item.getVolumeInfo().getIndustryIdentifiers().get(1).getIdentifier()));

        return oBook;
    }


}
