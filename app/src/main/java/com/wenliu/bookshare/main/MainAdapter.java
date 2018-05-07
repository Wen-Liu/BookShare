package com.wenliu.bookshare.main;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wenliu.bookshare.Constants;
import com.wenliu.bookshare.R;
import com.wenliu.bookshare.object.Book;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wen on 2018/5/5.
 */

public class MainAdapter extends RecyclerView.Adapter {
    private MainContract.Presenter mPresenter;
    private ArrayList<Book> mBooks;

    public MainAdapter(ArrayList<Book> books, MainContract.Presenter presenter) {
        mBooks = books;
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

        // Book image
        Picasso.get().load(mBooks.get(position).getImage()).into(((MainViewHolder) holder).getImVMainBookCover());
        // Book title
        ((MainViewHolder) holder).getTvMainTitle().setText(mBooks.get(position).getTitle());

    }

    @Override
    public int getItemCount() {
        return mBooks.size();
    }

    public static class MainViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ImV_main_book_cover)
        ImageView mImVMainBookCover;
        @BindView(R.id.tv_main_title)
        TextView mTvMainTitle;
        @BindView(R.id.tv_main_subtitle)
        TextView mTvMainSubtitle;
        @BindView(R.id.tv_main_author)
        TextView mTvMainAuthor;

        public MainViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            Log.d(Constants.TAG_MAIN_ADAPTER, "MainViewHolder");

        }

        public ImageView getImVMainBookCover() {
            return mImVMainBookCover;
        }

        public void setImVMainBookCover(ImageView imVMainBookCover) {
            mImVMainBookCover = imVMainBookCover;
        }

        public TextView getTvMainTitle() {
            return mTvMainTitle;
        }

        public void setTvMainTitle(TextView tvMainTitle) {
            mTvMainTitle = tvMainTitle;
        }

        public TextView getTvMainSubtitle() {
            return mTvMainSubtitle;
        }

        public void setTvMainSubtitle(TextView tvMainSubtitle) {
            mTvMainSubtitle = tvMainSubtitle;
        }

        public TextView getTvMainAuthor() {
            return mTvMainAuthor;
        }

        public void setTvMainAuthor(TextView tvMainAuthor) {
            mTvMainAuthor = tvMainAuthor;
        }
    }


    public void updateData(ArrayList<Book> books) {
        Log.d(Constants.TAG_MAIN_ADAPTER, "updateData, data count= " + books.size());

        mBooks.clear();
        mBooks = books;
        notifyDataSetChanged();
    }
}
