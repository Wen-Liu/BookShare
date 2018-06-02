package com.wenliu.bookshare.friend;

import com.wenliu.bookshare.base.BasePresenter;
import com.wenliu.bookshare.base.BaseView;
import com.wenliu.bookshare.object.User;

import java.util.ArrayList;

public interface FriendContract {

    interface View extends BaseView<Presenter> {

        void showFriends(ArrayList<User> friends);

    }

    interface Presenter extends BasePresenter {

    }


}
