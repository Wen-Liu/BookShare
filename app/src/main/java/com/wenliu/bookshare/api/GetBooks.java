package com.wenliu.bookshare.api;

import com.wenliu.bookshare.object.Book;

import java.util.ArrayList;

/**
 * Created by wen on 2018/5/3.
 */

public class GetBooks {
    private ArrayList<Book> mBooks;

    public GetBooks() {
        mBooks = new ArrayList<>();
    }

    public ArrayList<Book> getBooks() {
        return mBooks;
    }

    public void setBooks(ArrayList<Book> books) {
        mBooks = books;
    }
}
