package com.wenliu.bookshare.profile;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wenliu.bookshare.Constants;
import com.wenliu.bookshare.R;
import com.wenliu.bookshare.ShareBook;
import com.wenliu.bookshare.main.MainAdapter;
import com.wenliu.bookshare.object.User;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by wen on 2018/5/17.
 */

public class ProfileAdapter extends RecyclerView.Adapter {

    private ArrayList<User> mFriends;
    private ArrayList<String> mNumbers;

    public ProfileAdapter(ArrayList<User> friends) {
        mFriends = friends;
        mNumbers = new ArrayList<>();
        mNumbers.add("a");
        mNumbers.add("b");
        mNumbers.add("c");
        mNumbers.add("d");
        mNumbers.add("e");
        mNumbers.add("f");
        mNumbers.add("g");
        mNumbers.add("h");
        mNumbers.add("i");
        mNumbers.add("j");

        Log.d(Constants.TAG_PROFILE_ADAPTER, "ProfileAdapter: ");
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_profile_linear, parent, false);
        return new ProfileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

//        Glide.with(ShareBook.getAppContext())
//                .load(mFriends.get(position).getImage())
//                .into(((ProfileViewHolder) holder).getIvItemFriendImage());

        ((ProfileAdapter.ProfileViewHolder) holder).getTvItemFriendName().setText(mNumbers.get(position).toString());


    }

    @Override
    public int getItemCount() {
        return mNumbers.size();
    }


    public class ProfileViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_item_friend_image)
        ImageView mIvItemFriendImage;
        @BindView(R.id.tv_item_friend_name)
        TextView mTvItemFriendName;
        @BindView(R.id.tv_item_friend_email)
        TextView mTvItemFriendEmail;
        @BindView(R.id.tv_item_friend_info)
        TextView mTvItemFriendInfo;

        public ProfileViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public ImageView getIvItemFriendImage() {
            return mIvItemFriendImage;
        }

        public void setIvItemFriendImage(ImageView ivItemFriendImage) {
            mIvItemFriendImage = ivItemFriendImage;
        }

        public TextView getTvItemFriendName() {
            return mTvItemFriendName;
        }

        public void setTvItemFriendName(TextView tvItemFriendName) {
            mTvItemFriendName = tvItemFriendName;
        }

        public TextView getTvItemFriendEmail() {
            return mTvItemFriendEmail;
        }

        public void setTvItemFriendEmail(TextView tvItemFriendEmail) {
            mTvItemFriendEmail = tvItemFriendEmail;
        }

        public TextView getTvItemFriendInfo() {
            return mTvItemFriendInfo;
        }

        public void setTvItemFriendInfo(TextView tvItemFriendInfo) {
            mTvItemFriendInfo = tvItemFriendInfo;
        }
    }


    public void updateData(ArrayList<User> friends) {
        Log.d(Constants.TAG_MAIN_ADAPTER, "updateData, data count= " + friends.size());

        mFriends.clear();
        mFriends = friends;
        notifyDataSetChanged();
    }


}
