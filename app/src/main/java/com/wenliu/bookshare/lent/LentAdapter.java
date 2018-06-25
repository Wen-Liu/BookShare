package com.wenliu.bookshare.lent;

import android.content.Context;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.daimajia.swipe.SwipeLayout;
import com.wenliu.bookshare.Constants;
import com.wenliu.bookshare.ImageManager;
import com.wenliu.bookshare.R;
import com.wenliu.bookshare.ShareBook;
import com.wenliu.bookshare.UserManager;
import com.wenliu.bookshare.object.LentBook;
import java.util.ArrayList;


public class LentAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private LentContract.Presenter mPresenter;
    private ArrayList<LentBook> mLentBooks;
    private ImageManager mImageManager = new ImageManager(ShareBook.getAppContext());


    public LentAdapter(Context context, LentContract.Presenter presenter, ArrayList<LentBook> lentBooks) {
        Log.d(Constants.TAG_LENT_ADAPTER, "LentAdapter: ");
        mContext = context;
        mPresenter = presenter;
        mLentBooks = lentBooks;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lent_linear, parent, false);
        return new LentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        mImageManager.loadImageUrl(mLentBooks.get(position).getBookImage(), ((LentViewHolder) holder).getIvLentBookCover());

        String message = "";
        if (mLentBooks.get(position).getLenderId().equals(UserManager.getInstance().getUserId())) {
            message = mLentBooks.get(position).getBorrowerName() + mContext.getResources().getString(R.string.lent_want_to_borrow) + mLentBooks.get(position).getTitle(); //TODO
        } else {
            message = mContext.getResources().getString(R.string.lent_borrow_from) + mLentBooks.get(position).getLenderName() + mContext.getResources().getString(R.string.lent_borrow_from_2) + mLentBooks.get(position).getTitle();
        }

        ((LentViewHolder) holder).getTvLentMessage().setText(message);
        ((LentViewHolder) holder).getTvLentStartDate().setText(mLentBooks.get(position).getLendStartDay());
        ((LentViewHolder) holder).getTvLentReturnDate().setText(mLentBooks.get(position).getLendReturnDay());


        if (mLentBooks.get(position).getLentStatus().equals(Constants.FIREBASE_LENT_APPROVE)) {
            ((LentViewHolder) holder).isReceiveRequest(false);
            ((LentViewHolder) holder).isSendRequest(false);
            ((LentViewHolder) holder).isShowDate(true);
            ((LentViewHolder) holder).getTvLentStatus().setText(mContext.getResources().getString(R.string.lent_status_approve));

        } else if (mLentBooks.get(position).getLentStatus().equals(Constants.FIREBASE_LENT_RECEIVE)) {
            ((LentViewHolder) holder).isReceiveRequest(true);
            ((LentViewHolder) holder).isSendRequest(false);
            ((LentViewHolder) holder).isShowDate(false);
            ((LentViewHolder) holder).getTvLentStatus().setText(mContext.getResources().getString(R.string.lent_status_receive));

        } else if (mLentBooks.get(position).getLentStatus().equals(Constants.FIREBASE_LENT_SEND)) {
            ((LentViewHolder) holder).isReceiveRequest(false);
            ((LentViewHolder) holder).isSendRequest(true);
            ((LentViewHolder) holder).isShowDate(false);
            ((LentViewHolder) holder).getTvLentStatus().setText(mContext.getResources().getString(R.string.lent_status_send));
        }

        if (mLentBooks.get(position).getLentStatus().equals(Constants.FIREBASE_LENT_APPROVE)
                && mLentBooks.get(position).getLenderId().equals(UserManager.getInstance().getUserId())) {
            Log.d(Constants.TAG_LENT_ADAPTER, "swipe true: position " + position);
            ((LentViewHolder) holder).getSwipelayoutItemLent().setSwipeEnabled(true);
        } else {
            Log.d(Constants.TAG_LENT_ADAPTER, "swipe false: postion " + position);
            ((LentViewHolder) holder).getSwipelayoutItemLent().setSwipeEnabled(false);
        }

    }

    @Override
    public int getItemCount() {
        return mLentBooks.size();
    }


    public class LentViewHolder extends RecyclerView.ViewHolder {
        //region "BindView"
        @BindView(R.id.iv_lent_book_cover)
        ImageView mIvLentBookCover;
        @BindView(R.id.tv_lent_message)
        TextView mTvLentMessage;
        @BindView(R.id.tv_lent_start_date)
        TextView mTvLentStartDate;
        @BindView(R.id.tv_lent_return_date)
        TextView mTvLentReturnDate;
        @BindView(R.id.tv_lent_status)
        TextView mTvLentStatus;

        @BindView(R.id.btn_lent_reject)
        Button mBtnLentReject;
        @BindView(R.id.btn_lent_accept)
        Button mBtnLentAccept;
        @BindView(R.id.btn_lent_send)
        Button mBtnLentSend;
        @BindView(R.id.llayout_lent_start_date)
        LinearLayout mLlayoutLentStartDate;
        @BindView(R.id.llayout_lent_due_date)
        LinearLayout mLlayoutLentDueDate;
        @BindView(R.id.Swipelayout_item_lent)
        SwipeLayout mSwipelayoutItemLent;
        //endregion

        public LentViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @OnClick({R.id.btn_lent_reject, R.id.btn_lent_accept, R.id.btn_lent_send, R.id.llayout_return_book})
        public void onViewClicked(View view) {
            switch (view.getId()) {
                case R.id.btn_lent_reject:
                    Log.d(Constants.TAG_LENT_ADAPTER, "onViewClicked btn_lent_reject: ");
                    mPresenter.confirmReject(mLentBooks.get(getAdapterPosition()));
                    break;

                case R.id.btn_lent_accept:
                    Log.d(Constants.TAG_LENT_ADAPTER, "onViewClicked btn_lent_accept: ");
                    mPresenter.confirmAccept(mLentBooks.get(getAdapterPosition()));
                    break;

                case R.id.btn_lent_send:
                    Log.d(Constants.TAG_LENT_ADAPTER, "onViewClicked btn_lent_send: ");
                    break;

                case R.id.llayout_return_book:
                    mPresenter.confirmReturnBook(mLentBooks.get(getAdapterPosition()));
                    break;

                default:
                    break;
            }
        }

        public SwipeLayout getSwipelayoutItemLent() {
            return mSwipelayoutItemLent;
        }

        public ImageView getIvLentBookCover() {
            return mIvLentBookCover;
        }

        public TextView getTvLentMessage() {
            return mTvLentMessage;
        }

        public TextView getTvLentStartDate() {
            return mTvLentStartDate;
        }

        public TextView getTvLentReturnDate() {
            return mTvLentReturnDate;
        }

        public TextView getTvLentStatus() {
            return mTvLentStatus;
        }

        public Button getBtnLentReject() {
            return mBtnLentReject;
        }

        public Button getBtnLentAccept() {
            return mBtnLentAccept;
        }

        public Button getBtnLentSend() {
            return mBtnLentSend;
        }


        public void isSendRequest(boolean isSend) {
            mBtnLentSend.setVisibility(isSend ? View.VISIBLE : View.GONE);
        }

        public void isReceiveRequest(boolean isReceive) {
            mBtnLentAccept.setVisibility(isReceive ? View.VISIBLE : View.GONE);
            mBtnLentReject.setVisibility(isReceive ? View.VISIBLE : View.GONE);
        }

        public void isShowDate(boolean isShowDate) {
            mLlayoutLentStartDate.setVisibility(isShowDate ? View.VISIBLE : View.GONE);
            mLlayoutLentDueDate.setVisibility(isShowDate ? View.VISIBLE : View.GONE);
        }
    }

    public void updateData(ArrayList<LentBook> lentBooks) {
        Log.d(Constants.TAG_LENT_ADAPTER, "updateData, data count= " + lentBooks.size());
        mLentBooks = new ArrayList<>(lentBooks);
        notifyDataSetChanged();
    }

}
