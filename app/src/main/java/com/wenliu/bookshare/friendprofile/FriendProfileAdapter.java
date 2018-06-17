package com.wenliu.bookshare.friendprofile;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wenliu.bookshare.Constants;
import com.wenliu.bookshare.ImageManager;
import com.wenliu.bookshare.R;
import com.wenliu.bookshare.ShareBook;
import com.wenliu.bookshare.object.BookCustomInfo;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FriendProfileAdapter extends RecyclerView.Adapter {
    private FriendProfileContract.Presenter mPresenter;
    private ArrayList<BookCustomInfo> mBookCustomInfos;
    private ImageManager mImageManager = new ImageManager(ShareBook.getAppContext());

    public FriendProfileAdapter(ArrayList<BookCustomInfo> bookCustomInfos, FriendProfileContract.Presenter presenter) {
        Log.d(Constants.TAG_FRIEND_PROFILE_ADAPTER, "FriendProfileAdapter: ");
        mPresenter = presenter;
        mBookCustomInfos = new ArrayList<>(bookCustomInfos);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fprofile_linear, parent, false);
        return new FriendBookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        mImageManager.loadImageUrl(mBookCustomInfos.get(position).getImage(), ((FriendBookViewHolder) holder).getIvFprofileBookCover());
        ((FriendBookViewHolder) holder).getTvFprofileTitle().setText(mBookCustomInfos.get(position).getTitle());

        if (mBookCustomInfos.get(position).getSubtitle().length() > 0) {
            ((FriendBookViewHolder) holder).getTvFprofileSubtitle().setText(mBookCustomInfos.get(position).getSubtitle() + " ");
            ((FriendBookViewHolder) holder).getTvFprofileSubtitle().setVisibility(View.VISIBLE);
        } else {
            ((FriendBookViewHolder) holder).getTvFprofileSubtitle().setVisibility(View.GONE);
        }

        if (mBookCustomInfos.get(position).getAuthor().size() > 0) {
            String author = mBookCustomInfos.get(position).getAuthor().get(0);
            for (int i = 1; i < mBookCustomInfos.get(position).getAuthor().size(); i++) {
                author += ", " + mBookCustomInfos.get(position).getAuthor().get(i);
            }
            ((FriendBookViewHolder) holder).getTvFprofileAuthor().setText(author);
            ((FriendBookViewHolder) holder).getTvFprofileAuthor().setVisibility(View.VISIBLE);
        } else {
            ((FriendBookViewHolder) holder).getTvFprofileAuthor().setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return mBookCustomInfos.size();
    }

    public class FriendBookViewHolder extends RecyclerView.ViewHolder {

        //region "BindView"
        @BindView(R.id.iv_fprofile_book_cover)
        ImageView mIvFprofileBookCover;
        @BindView(R.id.tv_fprofile_title)
        TextView mTvFprofileTitle;
        @BindView(R.id.tv_fprofile_subtitle)
        TextView mTvFprofileSubtitle;
        @BindView(R.id.tv_fprofile_author)
        TextView mTvFprofileAuthor;
        @BindView(R.id.llayout_item_fprofile)
        LinearLayout mLlayoutItemFprofile;
        @BindView(R.id.btn_borrow_book)
        Button mBtnBorrowBook;
        //endregion

        public FriendBookViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @OnClick({R.id.llayout_item_fprofile, R.id.btn_borrow_book})
        public void onViewClicked(View view) {
            switch (view.getId()) {
                case R.id.llayout_item_fprofile:
                    Log.d(Constants.TAG_FRIEND_PROFILE_ADAPTER, "llayout_item_fprofile Clicked position: " + getAdapterPosition());
//                    mPresenter.openDetailPage(mBookCustomInfos.get(getAdapterPosition()));
                    break;

                case R.id.btn_borrow_book:
                    Log.d(Constants.TAG_FRIEND_PROFILE_ADAPTER, "btn_borrow_book Clicked position: " + getAdapterPosition());
                    mPresenter.confirmBorrowRequest(mBookCustomInfos.get(getAdapterPosition()));
                    break;


                default:
                    break;
            }
        }

        public ImageView getIvFprofileBookCover() {
            return mIvFprofileBookCover;
        }

//        public TextView getTvFprofileBookStatus() {
//            return mTvFprofileBookStatus;
//        }
//
//        public TextView getTvFprofileBookBorrowStatus() {
//            return mTvFprofileBookBorrowStatus;
//        }

        public TextView getTvFprofileTitle() {
            return mTvFprofileTitle;
        }

        public TextView getTvFprofileSubtitle() {
            return mTvFprofileSubtitle;
        }

        public TextView getTvFprofileAuthor() {
            return mTvFprofileAuthor;
        }
    }

    public void updateData(ArrayList<BookCustomInfo> bookCustomInfos) {
        Log.d(Constants.TAG_FRIEND_PROFILE_ADAPTER, "updateData, data count= " + bookCustomInfos.size());

        mBookCustomInfos = new ArrayList<>(bookCustomInfos);
        Collections.reverse(mBookCustomInfos);
        notifyDataSetChanged();
    }
}
