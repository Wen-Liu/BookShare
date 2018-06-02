package com.wenliu.bookshare.friend;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wenliu.bookshare.R;
import com.wenliu.bookshare.ShareBook;
import com.wenliu.bookshare.main.MainAdapter;
import com.wenliu.bookshare.object.User;
import com.wenliu.bookshare.profile.ProfileAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class FriendFragment extends Fragment implements FriendContract.View {

    //region "BindView"
    @BindView(R.id.rv_friend)
    RecyclerView mRvFriend;
    Unbinder unbinder;
    //endregion

    private FriendContract.Presenter mPresenter;
    private ProfileAdapter mProfileAdapter;
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_friend, container, false);
        unbinder = ButterKnife.bind(this, view);

        setRecyclerView();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.start();
    }

    private void setRecyclerView() {
        mProfileAdapter = new ProfileAdapter(mPresenter, mFriends);
        mRvFriend.setLayoutManager(new LinearLayoutManager(ShareBook.getAppContext()));
        mRvFriend.setAdapter(mProfileAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void setPresenter(FriendContract.Presenter presenter) {
        if (presenter == null) {
            ShareBook.makeShortToast("MainContract.Presenter is null!");
        } else {
            mPresenter = presenter;
        }
    }

    @Override
    public void showFriends(ArrayList<User> friends) {
        mProfileAdapter.updateData(friends);
    }
}
