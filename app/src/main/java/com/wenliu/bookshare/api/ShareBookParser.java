package com.wenliu.bookshare.api;

import android.util.Log;

import com.google.gson.Gson;
import com.wenliu.bookshare.Constants;
import com.wenliu.bookshare.object.GoogleBook.Item;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wen on 2018/5/3.
 */

public class ShareBookParser {

    public static String parseBookId(String jsonString) {
        String beanGetBookId = "";

        try {
            beanGetBookId = new JSONObject(jsonString).getJSONArray("items").getJSONObject(0).getString("selfLink");
            Log.d(Constants.TAG_SHARE_BOOK_PARSER, "id: " + beanGetBookId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return beanGetBookId;
    }

    public static Item parseBookData(String jsonString) {
        Item beanGetBookId;

        Gson gson = new Gson();
        beanGetBookId = gson.fromJson(jsonString, Item.class);

        Log.d(Constants.TAG_SHARE_BOOK_PARSER, "parseBookData");

        return beanGetBookId;
    }


}
