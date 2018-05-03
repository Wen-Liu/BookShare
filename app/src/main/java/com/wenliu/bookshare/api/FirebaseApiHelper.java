package com.wenliu.bookshare.api;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.wenliu.bookshare.Constants;
import com.wenliu.bookshare.object.Book;
import com.wenliu.bookshare.object.GoogleBook.Item;

import java.io.IOException;

/**
 * Created by wen on 2018/5/3.
 */

public class FirebaseApiHelper {
    // Write a message to the database

    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mGetRef = mDatabase.getReference();

    public void uploadBooks(String isbn, Book book) {
        Log.d(Constants.TAG_FIREBASE_API_HELPER, "uploadBooks");
        mGetRef.child("Books").child(isbn).setValue(book);
    }

    public void uploadGoogleBook(String id, Item item) {
        Log.d(Constants.TAG_FIREBASE_API_HELPER, "uploadGoogleBook");
        mGetRef.child("GoogleBooks").child(id).setValue(item);
    }
}
