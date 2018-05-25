package com.wenliu.bookshare.profile;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wenliu.bookshare.Constants;
import com.wenliu.bookshare.ImageManager;
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

    private Context mContext;
    private ArrayList<User> mFriends;
    private ArrayList<String> mNumbers;
    private ArrayList<String> mPhotos;
    private ImageManager mImageManager = new ImageManager(ShareBook.getAppContext());


    public ProfileAdapter(Context context, ArrayList<User> friends) {
        mContext = context;
        mFriends = friends;
        mNumbers = new ArrayList<>();
        mNumbers.add("Enid");
        mNumbers.add("Luke");
        mNumbers.add("Wayne Chen");
        mNumbers.add("Aaron");
        mNumbers.add("David");
        mNumbers.add("Andy");

        mPhotos = new ArrayList<>();
        mPhotos.add("https://scontent.ftpe7-2.fna.fbcdn.net/v/t1.0-9/27858409_10212643130566372_921206124007085657_n.jpg?_nc_fx=ftpe7-2&_nc_cat=0&oh=afd6c322790c6482b8d996a3f0323c78&oe=5BBDF2D2");
        mPhotos.add("https://scontent.ftpe7-3.fna.fbcdn.net/v/t1.0-9/16807517_1522771627763430_1797252123822453030_n.jpg?_nc_fx=ftpe7-2&_nc_cat=0&oh=64c70d136ea78de8b310e0a746a04472&oe=5B89DD56");
        mPhotos.add("https://scontent.ftpe7-2.fna.fbcdn.net/v/t1.0-9/20431606_1820894694594419_1941659903632630302_n.jpg?_nc_fx=ftpe7-2&_nc_cat=0&oh=088ac49b23b13d765c0a70d2803f767c&oe=5B92D8D5");
        mPhotos.add("https://scontent.ftpe7-2.fna.fbcdn.net/v/t1.0-9/29694909_2087950431220987_294947656529871708_n.jpg?_nc_fx=ftpe7-2&_nc_cat=0&oh=e18aed8ac74d128fe59a62aa7875af25&oe=5B941754");
        mPhotos.add("https://scontent.ftpe7-4.fna.fbcdn.net/v/t31.0-8/15591479_1190767794352803_8465831974099662491_o.jpg?_nc_fx=ftpe7-2&_nc_cat=0&oh=a49bc5d55e37169d94af759814941a3e&oe=5BBF9D77");
        mPhotos.add("https://scontent.ftpe7-4.fna.fbcdn.net/v/t31.0-8/17504932_1708878342462541_5943255062600487605_o.jpg?_nc_fx=ftpe7-2&_nc_cat=0&oh=9040cf73f3c24ed5c4359793c423d182&oe=5B82AD3F");


        Log.d(Constants.TAG_PROFILE_ADAPTER, "ProfileAdapter: ");
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_profile_linear, parent, false);
        return new ProfileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


        mImageManager.loadCircleImage(mPhotos.get(position),((ProfileAdapter.ProfileViewHolder) holder).getIvItemFriendImage());
//        Glide.with(ShareBook.getAppContext())
//                .load(mFriends.get(position).getImage())
//                .into(((ProfileViewHolder) holder).getIvItemFriendImage());

        ((ProfileAdapter.ProfileViewHolder) holder).getTvItemFriendName().setText(mNumbers.get(position));


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
