package com.wenliu.bookshare.main;

import android.support.v4.content.ContextCompat;
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
import com.wenliu.bookshare.object.BookCustomInfo;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wen on 2018/5/5.
 */

public class MainAdapter extends RecyclerView.Adapter {
    private MainContract.Presenter mPresenter;
    private ArrayList<BookCustomInfo> mBookCustomInfos;

    public MainAdapter(ArrayList<BookCustomInfo> bookCustomInfos, MainContract.Presenter presenter) {
        mBookCustomInfos = bookCustomInfos;
        mPresenter = presenter;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        Log.d(Constants.TAG_MAIN_ADAPTER, "onCreateViewHolder");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_linear, parent, false);
        return new MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        Log.d(Constants.TAG_MAIN_ADAPTER, "onBindViewHolder");

        if (mBookCustomInfos.get(position).isHaveBook()) {
            ((MainViewHolder) holder).getTvItemBookBorrowStatus().setText(ShareBook.getAppContext().getString(R.string.book_status_lendable));
            ((MainViewHolder) holder).getTvItemBookBorrowStatus().setBackground(ContextCompat.getDrawable(ShareBook.getAppContext(), R.drawable.shape_book_status_green));
        } else {
            ((MainViewHolder) holder).getTvItemBookBorrowStatus().setText(ShareBook.getAppContext().getString(R.string.book_status_lend_out));
            ((MainViewHolder) holder).getTvItemBookBorrowStatus().setBackground(ContextCompat.getDrawable(ShareBook.getAppContext(), R.drawable.shape_book_status_red));
        }

        setBookStatusView(mBookCustomInfos.get(position).getBookReadStatus(), holder);

        // Book image
        Glide.with(ShareBook.getAppContext()).load(mBookCustomInfos.get(position).getImage()).into(((MainViewHolder) holder).getImVMainBookCover());
        // Book title
        ((MainViewHolder) holder).getTvMainTitle().setText(mBookCustomInfos.get(position).getTitle());
        // Book subTitle
        if (mBookCustomInfos.get(position).getSubtitle().length() > 0) {
            ((MainViewHolder) holder).getTvMainSubtitle().setText(mBookCustomInfos.get(position).getSubtitle());
        } else {
            ((MainViewHolder) holder).getTvMainSubtitle().setVisibility(View.GONE);
        }

        // Book Author
//        Log.d(Constants.TAG_MAIN_ADAPTER, "getAuthor= " + mBookCustomInfos.get(position).getAuthor());
//        List<String> authors = mBooks.get(position).getAuthor();
        if (mBookCustomInfos.get(position).getAuthor().size() > 0) {
            ((MainViewHolder) holder).getTvMainAuthor().setText(mBookCustomInfos.get(position).getAuthor().get(0));
        } else {
            ((MainViewHolder) holder).getTvMainAuthor().setVisibility(View.GONE);
        }
    }


    private void setBookStatusView(int bookReadStatus, RecyclerView.ViewHolder holder) {
        String statusString = "";
        int statusBackgroundColor = 0;

        switch (bookReadStatus) {
            case Constants.READING:
                statusString = ShareBook.getAppContext().getString(R.string.book_status_reading);
                statusBackgroundColor = R.drawable.shape_book_status_yellow;
                break;
            case Constants.READ:
                statusString = ShareBook.getAppContext().getString(R.string.book_status_read);
                statusBackgroundColor = R.drawable.shape_book_status_green;
                break;
            case Constants.UNREAD:
                statusString = ShareBook.getAppContext().getString(R.string.book_status_unread);
                statusBackgroundColor = R.drawable.shape_book_status_red;
                break;
        }
        ((MainViewHolder) holder).getTvItemBookStatus().setText(statusString);
        ((MainViewHolder) holder).getTvItemBookStatus().setBackground(ContextCompat.getDrawable(ShareBook.getAppContext(), statusBackgroundColor));
    }


    @Override
    public int getItemCount() {
        return mBookCustomInfos.size();
    }

    public class MainViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ImV_main_book_cover)
        ImageView mImVMainBookCover;
        @BindView(R.id.tv_main_title)
        TextView mTvMainTitle;
        @BindView(R.id.tv_main_subtitle)
        TextView mTvMainSubtitle;
        @BindView(R.id.tv_main_author)
        TextView mTvMainAuthor;
        @BindView(R.id.tv_item_book_borrow_status)
        TextView mTvItemBookBorrowStatus;
        @BindView(R.id.tv_item_book_status)
        TextView mTvItemBookStatus;


        public MainViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            Log.d(Constants.TAG_MAIN_ADAPTER, "MainViewHolder");
        }

        @OnClick(R.id.llayout_item_main)
        public void onViewClicked() {
            Log.d(Constants.TAG_MAIN_ADAPTER, "onViewClicked ");
            mPresenter.openDetail(mBookCustomInfos.get(getAdapterPosition()), mImVMainBookCover);
        }

        public TextView getTvItemBookBorrowStatus() {
            return mTvItemBookBorrowStatus;
        }

        public TextView getTvItemBookStatus() {
            return mTvItemBookStatus;
        }

        public ImageView getImVMainBookCover() {
            return mImVMainBookCover;
        }

        public TextView getTvMainTitle() {
            return mTvMainTitle;
        }

        public TextView getTvMainSubtitle() {
            return mTvMainSubtitle;
        }

        public TextView getTvMainAuthor() {
            return mTvMainAuthor;
        }
    }


    public void updateData(ArrayList<BookCustomInfo> bookCustomInfos) {
        Log.d(Constants.TAG_MAIN_ADAPTER, "updateData, data count= " + bookCustomInfos.size());

        mBookCustomInfos.clear();
        mBookCustomInfos = bookCustomInfos;
        Collections.reverse(mBookCustomInfos);
        notifyDataSetChanged();
    }
}
