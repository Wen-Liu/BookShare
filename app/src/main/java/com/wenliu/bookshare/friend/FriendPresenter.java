package com.wenliu.bookshare.friend;

public class FriendPresenter implements FriendContract.Presenter {

    private FriendContract.View mFriendView;

    public FriendPresenter(FriendContract.View friendView) {
        mFriendView = friendView;
        mFriendView.setPresenter(this);
    }

    @Override
    public void start() {
        mFriendView.setPresenter(this);
    }
}
