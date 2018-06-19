package com.wenliu.bookshare.friend;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wenliu.bookshare.Constants;
import com.wenliu.bookshare.R;
import com.wenliu.bookshare.ShareBook;
import com.wenliu.bookshare.base.BaseFragment;
import com.wenliu.bookshare.object.User;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class FriendFragment extends BaseFragment implements FriendContract.View {
    //region "BindView"
    Unbinder unbinder;
    @BindView(R.id.tv_friend_no_data)
    TextView mTvFriendNoData;
    @BindView(R.id.rv_friend)
    RecyclerView mRvFriend;
    //endregion

    private FriendContract.Presenter mPresenter;
    private FriendAdapter mFriendAdapter;
    private ArrayList<User> mFriends = new ArrayList<>();

    public FriendFragment() {
        // Required empty public constructor
    }

    public static FriendFragment newInstance() {
        return new FriendFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(Constants.TAG_FRIEND_FRAGMENT, "onCreateView: ");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_friend, container, false);
        unbinder = ButterKnife.bind(this, view);

        setRecyclerView();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(Constants.TAG_FRIEND_FRAGMENT, "onViewCreated: ");
        mPresenter.start();
        mPresenter.getMyFriends();
    }

    @Override
    public void setPresenter(FriendContract.Presenter presenter) {
        mPresenter = presenter;
    }

    private void setRecyclerView() {
        mFriendAdapter = new FriendAdapter(getContext(),mPresenter, mFriends);
        mRvFriend.setLayoutManager(new LinearLayoutManager(ShareBook.getAppContext()));
        mRvFriend.setAdapter(mFriendAdapter);
    }

    @Override
    public void onDestroyView() {
        Log.d(Constants.TAG_FRIEND_FRAGMENT, "onDestroyView: ");
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void showFriends(ArrayList<User> friends) {
        mFriendAdapter.updateData(friends);
    }

    @Override
    public void isNoFriendData(boolean isNoFriendData) {
        Log.d(Constants.TAG_FRIEND_FRAGMENT, "isNoFriendData: " + isNoFriendData);
        mTvFriendNoData.setVisibility(isNoFriendData ? View.VISIBLE : View.GONE);
        mRvFriend.setVisibility(isNoFriendData ? View.INVISIBLE : View.VISIBLE);
    }

}
