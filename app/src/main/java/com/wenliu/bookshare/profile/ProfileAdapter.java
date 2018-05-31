package com.wenliu.bookshare.profile;

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

public class ProfileAdapter extends RecyclerView.Adapter {

    private ProfileContract.Presenter mPresenter;
    private Context mContext;
    private ArrayList<User> mFriends = new ArrayList<>();
    private ImageManager mImageManager = new ImageManager(ShareBook.getAppContext());

    public ProfileAdapter(Context context, ProfileContract.Presenter presenter, ArrayList<User> friends) {
        mPresenter = presenter;
        mContext = context;
        mFriends = friends;

        Log.d(Constants.TAG_PROFILE_ADAPTER, "ProfileAdapter: ");
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(Constants.TAG_PROFILE_ADAPTER, "onCreateViewHolder: ");

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_profile_linear, parent, false);
        return new ProfileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Log.d(Constants.TAG_PROFILE_ADAPTER, "onBindViewHolder: ");

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
            ((ProfileViewHolder) holder).getTvItemFriendInfo().setText("想加入你為好友");

        } else if (mFriends.get(position).getStatus().equals(Constants.FIREBASE_FRIEND_SEND)) {
            ((ProfileViewHolder) holder).isReceiveRequest(false);
            ((ProfileViewHolder) holder).isSendRequest(true);
            ((ProfileViewHolder) holder).getTvItemFriendInfo().setText("等待對方接受好友邀請中");
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

                    FirebaseApiHelper.newInstance().rejectFriendRequest(mFriends.get(getAdapterPosition()));
                    mFriends.remove(getAdapterPosition());
                    notifyDataSetChanged();
                    break;

                case R.id.btn_friend_accept:
                    Toast.makeText(ShareBook.getAppContext(), "accept " + mFriends.get(getAdapterPosition()).getName(), Toast.LENGTH_SHORT).show();

                    FirebaseApiHelper.newInstance().acceptFriendRequest(mFriends.get(getAdapterPosition()));
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
            Log.d(Constants.TAG_PROFILE_ADAPTER, "ProfileViewHolder: ");
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
        Log.d(Constants.TAG_PROFILE_ADAPTER, "updateData, data count= " + friends.size());

        mFriends = new ArrayList<>(friends);
        notifyDataSetChanged();
    }

    private void fakeData() {
        //    private ArrayList<String> mNumbers;
//    private ArrayList<String> mPhotos;
        //        mNumbers = new ArrayList<>();
//        mNumbers.add("Enid");
//        mNumbers.add("Luke");
//        mNumbers.add("Wayne Chen");
//        mNumbers.add("Aaron");
//        mNumbers.add("David");
//        mNumbers.add("Andy");
//
//        mPhotos = new ArrayList<>();
//        mPhotos.add("https://scontent.ftpe7-2.fna.fbcdn.net/v/t1.0-9/27858409_10212643130566372_921206124007085657_n.jpg?_nc_fx=ftpe7-2&_nc_cat=0&oh=afd6c322790c6482b8d996a3f0323c78&oe=5BBDF2D2");
//        mPhotos.add("https://scontent.ftpe7-3.fna.fbcdn.net/v/t1.0-9/16807517_1522771627763430_1797252123822453030_n.jpg?_nc_fx=ftpe7-2&_nc_cat=0&oh=64c70d136ea78de8b310e0a746a04472&oe=5B89DD56");
//        mPhotos.add("https://scontent.ftpe7-2.fna.fbcdn.net/v/t1.0-9/20431606_1820894694594419_1941659903632630302_n.jpg?_nc_fx=ftpe7-2&_nc_cat=0&oh=088ac49b23b13d765c0a70d2803f767c&oe=5B92D8D5");
//        mPhotos.add("https://scontent.ftpe7-2.fna.fbcdn.net/v/t1.0-9/29694909_2087950431220987_294947656529871708_n.jpg?_nc_fx=ftpe7-2&_nc_cat=0&oh=e18aed8ac74d128fe59a62aa7875af25&oe=5B941754");
//        mPhotos.add("https://scontent.ftpe7-4.fna.fbcdn.net/v/t31.0-8/15591479_1190767794352803_8465831974099662491_o.jpg?_nc_fx=ftpe7-2&_nc_cat=0&oh=a49bc5d55e37169d94af759814941a3e&oe=5BBF9D77");
//        mPhotos.add("https://scontent.ftpe7-4.fna.fbcdn.net/v/t31.0-8/17504932_1708878342462541_5943255062600487605_o.jpg?_nc_fx=ftpe7-2&_nc_cat=0&oh=9040cf73f3c24ed5c4359793c423d182&oe=5B82AD3F");
    }


}
