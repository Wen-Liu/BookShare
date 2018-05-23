package com.wenliu.bookshare.main;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wenliu.bookshare.Constants;
import com.wenliu.bookshare.R;
import com.wenliu.bookshare.ShareBook;
import com.wenliu.bookshare.object.Book;
import com.wenliu.bookshare.object.BookCustomInfo;

import java.util.ArrayList;
import java.util.Collection;
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

        // Book image
        Glide.with(ShareBook.getAppContext()).load(mBookCustomInfos.get(position).getImage()).into(((MainViewHolder) holder).getImVMainBookCover());
//        Picasso.get().load(mBooks.get(position).getImage()).into(((MainViewHolder) holder).getImVMainBookCover());
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
        @BindView(R.id.llayout_item_main)
        LinearLayout mLlayoutItemMain;

        public MainViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

            Log.d(Constants.TAG_MAIN_ADAPTER, "MainViewHolder");
        }

        @OnClick(R.id.llayout_item_main)
        public void onViewClicked() {
            Log.d(Constants.TAG_MAIN_ADAPTER, "onViewClicked ");
            mPresenter.openDetail(mBookCustomInfos.get(getAdapterPosition()));
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


    public void updateData(ArrayList<BookCustomInfo> bookCustomInfos) {
        Log.d(Constants.TAG_MAIN_ADAPTER, "updateData, data count= " + bookCustomInfos.size());

        mBookCustomInfos.clear();
        mBookCustomInfos = bookCustomInfos;
        Collections.reverse(mBookCustomInfos);
        notifyDataSetChanged();
    }
}
