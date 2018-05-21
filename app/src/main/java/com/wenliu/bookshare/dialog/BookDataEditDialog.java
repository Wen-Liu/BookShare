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
import com.wenliu.bookshare.api.FirebaseApiHelper;
import com.wenliu.bookshare.object.Book;
import com.wenliu.bookshare.object.BookCustomInfo;

import java.util.List;

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
    private BookCustomInfo mBookCustomInfo;
    private ShareBookActivity mShareBookActivity;
    private ShareBookContract.Presenter mPresenter;
    private String mTitle;
    private String mSubtitle;
    private List<String> mAuthor;
    private String mPublisher;
    private String mPublishDate;
    private String mLanguage;
    private String mPurchaseDate;
    private String mPurchasePrice;


    public BookDataEditDialog(@NonNull Context context, ShareBookActivity activity, ShareBookContract.Presenter presenter, Book book) {
        super(context);
        setContentView(R.layout.dialog_book_data_edit);
        ButterKnife.bind(this);

        Log.d(Constants.TAG_BOOK_DATA_EDIT_DIALOG, "BookDataEditDialog constructor");
        mContext = context;
        mShareBookActivity = activity;
        mPresenter = presenter;
        mBook = book;
        mBookCustomInfo = new BookCustomInfo(book);

        init();
    }


    // init the book data get from server
    private void init() {

        if (mBookCustomInfo.getTitle() != null) {
            mEtDialogBookTitle.setText(mBook.getTitle());
        }

        if (mBookCustomInfo.getSubtitle() != null) {
            mEtDialogBookSubtitle.setText(mBook.getSubtitle());
        }

        if (mBookCustomInfo.getAuthor() != null && mBook.getAuthor().size() > 0) {
            mEtDialogBookAuthor.setText(mBook.getAuthor().get(0));
        }

        if (mBookCustomInfo.getIsbn13() != null) {
            mTvDialogBookIsbn.setText(mBook.getIsbn13());
        }

        if (mBookCustomInfo.getPublisher() != null) {
            mEtDialogBookPublisher.setText(mBook.getPublisher());
        }

        if (mBookCustomInfo.getPublishDate() != null) {
            mEtDialogBookPublishDate.setText(mBook.getPublishDate());
        }

        if (mBookCustomInfo.getLanguage() != null) {
            mEtDialogBookLanguage.setText(mBook.getLanguage());
        }

    }

    @OnClick(R.id.btn_book_data_send)
    public void onViewClicked() {

        mBookCustomInfo.setTitle(mEtDialogBookTitle.getText().toString());
        mBookCustomInfo.setSubtitle(mEtDialogBookSubtitle.getText().toString());
//        mAuthor.add(mEtDialogBookTitle.getText().toString());
//        mBook.setAuthor(mAuthor);
        mBookCustomInfo.setPublisher(mEtDialogBookPublisher.getText().toString());
        mBookCustomInfo.setPublishDate(mEtDialogBookPublishDate.getText().toString());
        mBookCustomInfo.setLanguage(mEtDialogBookLanguage.getText().toString());

        mBookCustomInfo.setPurchaseDate(mEtDialogBookPurchaseDate.getText().toString());
        mBookCustomInfo.setPurchasePrice(mEtDialogBookPurchasePrice.getText().toString());

        FirebaseApiHelper.newInstance().uploadMyBook(mBookCustomInfo.getIsbn13(), mBookCustomInfo);

        mPresenter.refreshMainFragment();
        dismiss();
    }
}
