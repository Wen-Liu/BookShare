package com.wenliu.bookshare.lent;

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
import com.wenliu.bookshare.object.LentBook;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LentAdapter extends RecyclerView.Adapter {

    private LentContract.Presenter mPresenter;
    private ArrayList<LentBook> mLentBooks;
    private ImageManager mImageManager = new ImageManager(ShareBook.getAppContext());


    public LentAdapter(LentContract.Presenter presenter, ArrayList<LentBook> lentBooks) {
        Log.d(Constants.TAG_LENT_ADAPTER, "LentAdapter: ");
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
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        mImageManager.loadUrlImage(mLentBooks.get(position).getBookImage(), ((LentViewHolder) holder).getIvLentBookCover());

        String message = mLentBooks.get(position).getBorrowerName() + " 借閱 " + mLentBooks.get(position).getTitle();
        ((LentViewHolder) holder).getTvLentMessage().setText(message);
        ((LentViewHolder) holder).getTvLentStartDate().setText(mLentBooks.get(position).getLendStartDay());
        ((LentViewHolder) holder).getTvLentReturnDate().setText(mLentBooks.get(position).getLendReturnDay());


        if (mLentBooks.get(position).getLentStatus().equals(Constants.FIREBASE_LENT_APPROVE)) {
            ((LentViewHolder) holder).isReceiveRequest(false);
            ((LentViewHolder) holder).isSendRequest(false);
            ((LentViewHolder) holder).isShowDate(true);
            ((LentViewHolder) holder).getTvLentStatus().setText("借閱中");

        } else if (mLentBooks.get(position).getLentStatus().equals(Constants.FIREBASE_LENT_RECEIVE)) {
            ((LentViewHolder) holder).isReceiveRequest(true);
            ((LentViewHolder) holder).isSendRequest(false);
            ((LentViewHolder) holder).isShowDate(false);
            ((LentViewHolder) holder).getTvLentStatus().setText("請確認是否借閱");

        } else if (mLentBooks.get(position).getLentStatus().equals(Constants.FIREBASE_LENT_SEND)) {
            ((LentViewHolder) holder).isReceiveRequest(false);
            ((LentViewHolder) holder).isSendRequest(true);
            ((LentViewHolder) holder).isShowDate(false);
            ((LentViewHolder) holder).getTvLentStatus().setText("等待對方確認借閱中");
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
        @BindView(R.id.llayout_item_lent)
        LinearLayout mLlayoutItemLent;
        @BindView(R.id.llayout_lent_start_date)
        LinearLayout mLlayoutLentStartDate;
        @BindView(R.id.llayout_lent_due_date)
        LinearLayout mLlayoutLentDueDate;
        //endregion

        public LentViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @OnClick({R.id.btn_lent_reject, R.id.btn_lent_accept, R.id.btn_lent_send})
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
            }
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

        public LinearLayout getLlayoutItemLent() {
            return mLlayoutItemLent;
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
