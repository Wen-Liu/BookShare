package com.wenliu.bookshare.friendprofile;

import com.wenliu.bookshare.api.callbacks.GetFriendBooksCallback;
import com.wenliu.bookshare.base.BasePresenter;
import com.wenliu.bookshare.base.BaseView;
import com.wenliu.bookshare.object.BookCustomInfo;
import com.wenliu.bookshare.object.User;

import java.util.ArrayList;

public interface FriendProfileContract {

    interface View extends BaseView<Presenter> {
        void showFriendBooks(ArrayList<BookCustomInfo> bookCustomInfos);

        void showConfirmDialog(BookCustomInfo bookCustomInfo);

    }

    interface Presenter extends BasePresenter {

        void getFriendBooks(String uId);

        void openDetailPage(BookCustomInfo bookCustomInfo);

        void confirmBorrowRequest(BookCustomInfo bookCustomInfo);

        void sendBorrowRequest(User friend, BookCustomInfo bookCustomInfo);
    }

}
