package com.wenliu.bookshare.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wenliu.bookshare.Constants;
import com.wenliu.bookshare.R;
import com.wenliu.bookshare.ShareBookActivity;
import com.wenliu.bookshare.ShareBookContract;
import com.wenliu.bookshare.object.Book;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wen on 2018/5/10.
 */

public class BookDataEditDialog extends Dialog {

    @BindView(R.id.et_dialog_book_title)
    EditText mEtDialogBookTitle;
    @BindView(R.id.et_dialog_book_subtitle)
    EditText mEtDialogBookSubtitle;
    @BindView(R.id.et_dialog_book_author)
    EditText mEtDialogBookAuthor;
    @BindView(R.id.tv_dialog_book_isbn)
    TextView mTvDialogBookIsbn;
    @BindView(R.id.et_dialog_book_publisher)
    EditText mEtDialogBookPublisher;
    @BindView(R.id.et_dialog_book_publish_date)
    EditText mEtDialogBookPublishDate;
    @BindView(R.id.et_dialog_book_language)
    EditText mEtDialogBookLanguage;
    @BindView(R.id.et_dialog_book_purchase_date)
    EditText mEtDialogBookPurchaseDate;
    @BindView(R.id.et_dialog_book_purchase_price)
    EditText mEtDialogBookPurchasePrice;
    @BindView(R.id.l_layout_book_data_edit)
    LinearLayout mLLayoutBookDataEdit;
    @BindView(R.id.btn_book_data_send)
    Button mBtnBookDataSend;

    private Context mContext;
    private Book mBook;
    private ShareBookActivity mShareBookActivity;
    private ShareBookContract.Presenter mPresenter;

    public BookDataEditDialog(@NonNull Context context, ShareBookActivity activity, ShareBookContract.Presenter presenter, Book book) {
        super(context);
        setContentView(R.layout.dialog_book_data_edit);
        ButterKnife.bind(this);

        Log.d(Constants.TAG_BOOK_DATA_EDIT_DIALOG, "BookDataEditDialog constructor");
        mContext = context;
        mShareBookActivity = activity;
        mPresenter = presenter;
        mBook = book;

        init();
    }


    private void init() {

        if (mBook.getTitle() != null) {
            mEtDialogBookTitle.setText(mBook.getTitle());
        }

        if (mBook.getSubtitle() != null) {
            mEtDialogBookSubtitle.setText(mBook.getSubtitle());
        }

        if (mBook.getAuthor() != null && mBook.getAuthor().size()>0) {
            mEtDialogBookAuthor.setText(mBook.getAuthor().get(0));
        }

        if (mBook.getIsbn13() != null) {
            mTvDialogBookIsbn.setText(mBook.getIsbn13());
        }

        if (mBook.getPublisher() != null) {
            mEtDialogBookPublisher.setText(mBook.getPublisher());
        }

        if (mBook.getPublishDate() != null) {
            mEtDialogBookPublishDate.setText(mBook.getPublishDate());
        }

        if (mBook.getLanguage() != null) {
            mEtDialogBookLanguage.setText(mBook.getLanguage());
        }

    }

    @OnClick(R.id.btn_book_data_send)
    public void onViewClicked() {


    }
}
