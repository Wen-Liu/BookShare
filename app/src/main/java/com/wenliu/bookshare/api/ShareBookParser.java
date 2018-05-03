package com.wenliu.bookshare.api;

import android.util.Log;

import com.wenliu.bookshare.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wen on 2018/5/3.
 */

public class ShareBookParser {

    public static String parseGetBookId(String jsonString) {
        String beanGetBookId = "";

        try {
            beanGetBookId = new JSONObject(jsonString).getJSONArray("items").getJSONObject(0).getString("selfLink");
            Log.d(Constants.TAG_SHARE_BOOK_PARSER, "id: " + beanGetBookId);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return beanGetBookId;
    }


}
