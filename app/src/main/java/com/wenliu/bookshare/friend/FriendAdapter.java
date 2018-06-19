package com.wenliu.bookshare.friend;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wenliu.bookshare.Constants;
import com.wenliu.bookshare.ImageManager;
import com.wenliu.bookshare.R;
import com.wenliu.bookshare.ShareBook;
import com.wenliu.bookshare.api.FirebaseApiHelper;
import com.wenliu.bookshare.object.User;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by wen on 2018/5/17.
 */

public class FriendAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private FriendContract.Presenter mPresenter;
    private ArrayList<User> mFriends = new ArrayList<>();
    private ImageManager mImageManager = new ImageManager(ShareBook.getAppContext());

    public FriendAdapter(Context context, FriendContract.Presenter presenter, ArrayList<User> friends) {
        Log.d(Constants.TAG_FRIEND_ADAPTER, "FriendAdapter: ");
        mContext = context;
        mPresenter = presenter;
        mFriends = friends;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        Log.d(Constants.TAG_FRIEND_ADAPTER, "onCreateViewHolder: ");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_friend_linear, parent, false);
        return new ProfileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        Log.d(Constants.TAG_FRIEND_ADAPTER, "onBindViewHolder: ");

        mImageManager.loadCircleImage(mFriends.get(position).getImage(), ((ProfileViewHolder) holder).getIvItemFriendImage());
        ((ProfileViewHolder) holder).getTvItemFriendName().setText(mFriends.get(position).getName());
        ((ProfileViewHolder) holder).getTvItemFriendEmail().setText(mFriends.get(position).getEmail());

        if (mFriends.get(position).getStatus().equals(Constants.FIREBASE_FRIEND_APPROVE)) {
            ((ProfileViewHolder) holder).isReceiveRequest(false);
            ((ProfileViewHolder) holder).isSendRequest(false);
            ((ProfileViewHolder) holder).getTvItemFriendInfo().setText("");

        } else if (mFriends.get(position).getStatus().equals(Constants.FIREBASE_FRIEND_RECEIVE)) {
            ((ProfileViewHolder) holder).isReceiveRequest(true);
            ((ProfileViewHolder) holder).isSendRequest(false);
            ((ProfileViewHolder) holder).getTvItemFriendInfo().setText(mContext.getResources().getString(R.string.friend_receive_request));

        } else if (mFriends.get(position).getStatus().equals(Constants.FIREBASE_FRIEND_SEND)) {
            ((ProfileViewHolder) holder).isReceiveRequest(false);
            ((ProfileViewHolder) holder).isSendRequest(true);
            ((ProfileViewHolder) holder).getTvItemFriendInfo().setText(mContext.getResources().getString(R.string.friend_send_request));
        }
    }

    @Override
    public int getItemCount() {
        return mFriends.size();
    }


    public class ProfileViewHolder extends RecyclerView.ViewHolder {
        //region "BindView"
        @BindView(R.id.iv_item_friend_image)
        ImageView mIvItemFriendImage;
        @BindView(R.id.tv_item_friend_name)
        TextView mTvItemFriendName;
        @BindView(R.id.tv_item_friend_email)
        TextView mTvItemFriendEmail;
        @BindView(R.id.tv_item_friend_info)
        TextView mTvItemFriendInfo;
        @BindView(R.id.ll_user_info)
        LinearLayout mLlUserInfo;
        @BindView(R.id.ll_add_friend)
        LinearLayout mLlAddFriend;
        @BindView(R.id.llayout_item_profile)
        LinearLayout mLlayoutItemProfile;

        @BindView(R.id.btn_friend_reject)
        Button mBtnFriendReject;
        @BindView(R.id.btn_friend_accept)
        Button mBtnFriendAccept;
        @BindView(R.id.btn_friend_send)
        Button mBtnFriendSend;
        //endregion

        @OnClick({R.id.btn_friend_reject, R.id.btn_friend_accept, R.id.btn_friend_send, R.id.llayout_item_profile})
        public void onViewClicked(View view) {
            switch (view.getId()) {
                case R.id.btn_friend_reject:
                    Toast.makeText(ShareBook.getAppContext(), "reject " + mFriends.get(getAdapterPosition()).getName(), Toast.LENGTH_SHORT).show();

                    FirebaseApiHelper.getInstance().rejectFriendRequest(mFriends.get(getAdapterPosition()));
                    mFriends.remove(getAdapterPosition());
                    notifyDataSetChanged();
                    break;

                case R.id.btn_friend_accept:
                    Toast.makeText(ShareBook.getAppContext(), "accept " + mFriends.get(getAdapterPosition()).getName(), Toast.LENGTH_SHORT).show();

                    FirebaseApiHelper.getInstance().acceptFriendRequest(mFriends.get(getAdapterPosition()));
                    mFriends.get(getAdapterPosition()).setStatus(Constants.FIREBASE_FRIEND_APPROVE);
                    notifyDataSetChanged();
                    break;

                case R.id.btn_friend_send:
                    Toast.makeText(ShareBook.getAppContext(), "send " + mFriends.get(getAdapterPosition()).getName(), Toast.LENGTH_SHORT).show();

                    break;

                case R.id.llayout_item_profile:
                    mPresenter.transToFriendProfile(mFriends.get(getAdapterPosition()));

                    break;
            }
        }

        public ProfileViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
//            Log.d(Constants.TAG_FRIEND_ADAPTER, "ProfileViewHolder: ");
        }

        public ImageView getIvItemFriendImage() {
            return mIvItemFriendImage;
        }

        public TextView getTvItemFriendName() {
            return mTvItemFriendName;
        }

        public TextView getTvItemFriendEmail() {
            return mTvItemFriendEmail;
        }

        public TextView getTvItemFriendInfo() {
            return mTvItemFriendInfo;
        }

        public LinearLayout getLlUserInfo() {
            return mLlUserInfo;
        }

        public LinearLayout getLlAddFriend() {
            return mLlAddFriend;
        }

        public void isSendRequest(boolean isSend) {
            mBtnFriendSend.setVisibility(isSend ? View.VISIBLE : View.GONE);
        }

        public void isReceiveRequest(boolean isReceive) {
            mBtnFriendReject.setVisibility(isReceive ? View.VISIBLE : View.GONE);
            mBtnFriendAccept.setVisibility(isReceive ? View.VISIBLE : View.GONE);
        }
    }

    public void updateData(ArrayList<User> friends) {
        Log.d(Constants.TAG_FRIEND_ADAPTER, "updateData, data count= " + friends.size());
        mFriends = new ArrayList<>(friends);
        notifyDataSetChanged();
    }

}
