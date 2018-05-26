package com.wenliu.bookshare.main;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wenliu.bookshare.Constants;
import com.wenliu.bookshare.ImageManager;
import com.wenliu.bookshare.R;
import com.wenliu.bookshare.ShareBook;
import com.wenliu.bookshare.api.callbacks.AlertDialogCallback;
import com.wenliu.bookshare.api.callbacks.DeleteBookCallback;
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
    private ArrayList<BookCustomInfo> mBookCustomInfos = new ArrayList<>();
    private ImageManager mImageManager = new ImageManager(ShareBook.getAppContext());

    public MainAdapter(ArrayList<BookCustomInfo> bookCustomInfos, MainContract.Presenter presenter) {
        Log.d(Constants.TAG_MAIN_ADAPTER, "MainAdapter: ");
//        mBookCustomInfos = bookCustomInfos;
        mPresenter = presenter;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_linear, parent, false);
        return new MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        setBookHaveView(mBookCustomInfos.get(position).isHaveBook(), ((MainViewHolder) holder).getTvItemBookBorrowStatus());
        setBookStatusView(mBookCustomInfos.get(position).getBookReadStatus(), holder);

        mImageManager.loadUrlImage(mBookCustomInfos.get(position).getImage(), ((MainViewHolder) holder).getImVMainBookCover());
        ((MainViewHolder) holder).getTvMainTitle().setText(mBookCustomInfos.get(position).getTitle());

        if (mBookCustomInfos.get(position).getSubtitle().length() > 0) {
            ((MainViewHolder) holder).getTvMainSubtitle().setText(mBookCustomInfos.get(position).getSubtitle() + " ");
            ((MainViewHolder) holder).getTvMainSubtitle().setVisibility(View.VISIBLE);
        } else {
            ((MainViewHolder) holder).getTvMainSubtitle().setVisibility(View.GONE);
        }

        if (mBookCustomInfos.get(position).getAuthor().size() > 0) {
            String author = mBookCustomInfos.get(position).getAuthor().get(0);
            for (int i = 1; i < mBookCustomInfos.get(position).getAuthor().size(); i++) {
                author += ", " + mBookCustomInfos.get(position).getAuthor().get(i);
            }
            ((MainViewHolder) holder).getTvMainAuthor().setText(author);
            ((MainViewHolder) holder).getTvMainAuthor().setVisibility(View.VISIBLE);
        } else {
            ((MainViewHolder) holder).getTvMainAuthor().setVisibility(View.GONE);
        }
    }


    private void setBookHaveView(boolean haveBook, TextView tvBookBorrowStatus) {
        if (haveBook) {
            tvBookBorrowStatus.setText(ShareBook.getAppContext().getString(R.string.book_status_lendable));
            tvBookBorrowStatus.setBackground(ContextCompat.getDrawable(ShareBook.getAppContext(), R.drawable.shape_book_status_green));
            tvBookBorrowStatus.setTextColor(ContextCompat.getColor(ShareBook.getAppContext(), R.color.Green_600));
        } else {
            tvBookBorrowStatus.setText(ShareBook.getAppContext().getString(R.string.book_status_lend_out));
            tvBookBorrowStatus.setBackground(ContextCompat.getDrawable(ShareBook.getAppContext(), R.drawable.shape_book_status_red));
            tvBookBorrowStatus.setTextColor(ContextCompat.getColor(ShareBook.getAppContext(), R.color.Red_600));
        }
    }

    private void setBookStatusView(int bookReadStatus, RecyclerView.ViewHolder holder) {
        String statusString = "";
        int statusBackgroundColor = 0;
        int statusTextColor = 0;

        switch (bookReadStatus) {
            case Constants.READING:
                statusString = ShareBook.getAppContext().getString(R.string.book_status_reading);
                statusBackgroundColor = R.drawable.shape_book_status_yellow;
                statusTextColor = R.color.Yellow_600;
                break;
            case Constants.READ:
                statusString = ShareBook.getAppContext().getString(R.string.book_status_read);
                statusBackgroundColor = R.drawable.shape_book_status_green;
                statusTextColor = R.color.Green_600;
                break;
            case Constants.UNREAD:
                statusString = ShareBook.getAppContext().getString(R.string.book_status_unread);
                statusBackgroundColor = R.drawable.shape_book_status_red;
                statusTextColor = R.color.Red_600;
                break;
        }
        ((MainViewHolder) holder).getTvItemBookStatus().setText(statusString);
        ((MainViewHolder) holder).getTvItemBookStatus().setBackground(ContextCompat.getDrawable(ShareBook.getAppContext(), statusBackgroundColor));
        ((MainViewHolder) holder).getTvItemBookStatus().setTextColor(ContextCompat.getColor(ShareBook.getAppContext(), statusTextColor));
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
        @BindView(R.id.tv_main_book_borrow_status)
        TextView mTvItemBookBorrowStatus;
        @BindView(R.id.tv_main_book_status)
        TextView mTvItemBookStatus;

        public MainViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
//            Log.d(Constants.TAG_MAIN_ADAPTER, "MainViewHolder");
        }

        @OnClick({R.id.llayout_item_main, R.id.btn_main_delete})
        public void onViewClicked(View view) {
            switch (view.getId()) {
                case R.id.llayout_item_main:
                    Log.d(Constants.TAG_MAIN_ADAPTER, "llayout_item_main Clicked position: " + getAdapterPosition());
                    mPresenter.openDetail(mBookCustomInfos.get(getAdapterPosition()), mImVMainBookCover);
                    break;
                case R.id.btn_main_delete:
                    Log.d(Constants.TAG_MAIN_ADAPTER, "btn_main_delete Clicked position: " + getAdapterPosition());

                    mPresenter.showAlertDialog(mBookCustomInfos.get(getAdapterPosition()).getTitle(), new AlertDialogCallback() {
                        @Override
                        public void onCompleted() {
                            mPresenter.deleteBook(mBookCustomInfos.get(getAdapterPosition()).getIsbn13(), new DeleteBookCallback() {
                                @Override
                                public void onCompleted() {
                                    Log.d(Constants.TAG_MAIN_ADAPTER, "deleteBook onCompleted position: " + getAdapterPosition());
                                    mPresenter.loadBooks();
                                }
                            });
                        }

                        @Override
                        public void onCancel() {
                            Log.d(Constants.TAG_MAIN_ADAPTER, "deleteBook onCancel: ");
                        }
                    });

                    break;
            }
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

        mBookCustomInfos = new ArrayList<>(bookCustomInfos);

//        mBookCustomInfos.clear();
//        for(BookCustomInfo bookCustomInfo: bookCustomInfos){
//            mBookCustomInfos.add(bookCustomInfo);
//        }
        Collections.reverse(mBookCustomInfos);
        notifyDataSetChanged();
    }
}
