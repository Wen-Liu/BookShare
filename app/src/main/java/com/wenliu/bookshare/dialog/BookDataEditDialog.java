package com.wenliu.bookshare.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;

import com.wenliu.bookshare.Constants;
import com.wenliu.bookshare.R;
import com.wenliu.bookshare.ShareBookActivity;
import com.wenliu.bookshare.ShareBookContract;
import com.wenliu.bookshare.api.FirebaseApiHelper;
import com.wenliu.bookshare.object.Book;
import com.wenliu.bookshare.object.BookCustomInfo;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wen on 2018/5/10.
 */

public class BookDataEditDialog extends Dialog implements CompoundButton.OnCheckedChangeListener {

    //region "BindView"
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
    @BindView(R.id.radioBtn_unread)
    RadioButton mRadioBtnUnread;
    @BindView(R.id.radioBtn_reading)
    RadioButton mRadioBtnReading;
    @BindView(R.id.radioBtn_read)
    RadioButton mRadioBtnRead;
    @BindView(R.id.checkBox_have_book)
    CheckBox mCheckBoxHaveBook;
    @BindView(R.id.llayout_dialog_purchase_date)
    LinearLayout mLlayoutDialogPurchaseDate;
    @BindView(R.id.llayout_dialog_purchase_price)
    LinearLayout mLlayoutDialogPurchasePrice;
    @BindView(R.id.et_dialog_book_reading_page)
    EditText mEtDialogBookReadingPage;
    @BindView(R.id.llayout_dialog_Reading_page)
    LinearLayout mLlayoutDialogReadingPage;
    //endregion

    private Context mContext;
    private BookCustomInfo mBookCustomInfo;
    private ShareBookActivity mShareBookActivity;
    private ShareBookContract.Presenter mPresenter;


    public BookDataEditDialog(@NonNull Context context, ShareBookActivity activity, ShareBookContract.Presenter presenter, Book book) {
        super(context);
        setContentView(R.layout.dialog_edit_book_data);
        ButterKnife.bind(this);

        Log.d(Constants.TAG_BOOK_DATA_EDIT_DIALOG, "constructor Book: ");
        mContext = context;
        mShareBookActivity = activity;
        mPresenter = presenter;
        mBookCustomInfo = new BookCustomInfo(book);
        initView();
    }

    public BookDataEditDialog(@NonNull Context context, ShareBookActivity activity, ShareBookContract.Presenter presenter, BookCustomInfo bookCustomInfo) {
        super(context);
        setContentView(R.layout.dialog_edit_book_data);
        ButterKnife.bind(this);

        Log.d(Constants.TAG_BOOK_DATA_EDIT_DIALOG, "constructor BookCustomInfo: ");
        mContext = context;
        mShareBookActivity = activity;
        mPresenter = presenter;
        mBookCustomInfo = bookCustomInfo;

        initView();
    }


    private void initView() {
        // init the book data get from server
        if (mBookCustomInfo.getTitle() != null) {
            mEtDialogBookTitle.setText(mBookCustomInfo.getTitle());
        }
        if (mBookCustomInfo.getSubtitle() != null) {
            mEtDialogBookSubtitle.setText(mBookCustomInfo.getSubtitle());
        }
        if (mBookCustomInfo.getAuthor() != null && mBookCustomInfo.getAuthor().size() > 0) {
            mEtDialogBookAuthor.setText(mBookCustomInfo.getAuthor().get(0));
        }
        if (mBookCustomInfo.getIsbn13() != null) {
            mTvDialogBookIsbn.setText(mBookCustomInfo.getIsbn13());
        }
        if (mBookCustomInfo.getPublisher() != null) {
            mEtDialogBookPublisher.setText(mBookCustomInfo.getPublisher());
        }
        if (mBookCustomInfo.getPublishDate() != null) {
            mEtDialogBookPublishDate.setText(mBookCustomInfo.getPublishDate());
        }
        if (mBookCustomInfo.getLanguage() != null) {
            mEtDialogBookLanguage.setText(mBookCustomInfo.getLanguage());
        }

        mLlayoutDialogReadingPage.setVisibility(View.GONE);
        Log.d(Constants.TAG_BOOK_DATA_EDIT_DIALOG, "mBookCustomInfo.getBookReadStatus(): " + mBookCustomInfo.getBookReadStatus());
        if (mBookCustomInfo.getBookReadStatus() != -1) {
            if (mBookCustomInfo.getBookReadStatus() == Constants.READING) {
                mRadioBtnReading.setChecked(true);
                // set reading page
                mLlayoutDialogReadingPage.setVisibility(View.VISIBLE);
                if (mBookCustomInfo.getReadingPage() != -1) {
                    mEtDialogBookReadingPage.setText(String.valueOf(mBookCustomInfo.getReadingPage()));
                }
            } else if (mBookCustomInfo.getBookReadStatus() == Constants.READ) {
                mRadioBtnRead.setChecked(true);
            } else {
                mRadioBtnUnread.setChecked(true);
            }
        } else {
            mRadioBtnUnread.setChecked(true);
        }

        // set listener
        mCheckBoxHaveBook.setOnCheckedChangeListener(this);
        if (mBookCustomInfo.isHaveBook()) {
            mCheckBoxHaveBook.setChecked(true);
            setPurchaseVisibility(true);

            if (mBookCustomInfo.getPurchaseDate() != null) {
                mEtDialogBookPurchaseDate.setText(mBookCustomInfo.getPurchaseDate());
            }
            if (mBookCustomInfo.getPurchasePrice() != null) {
                mEtDialogBookPurchasePrice.setText(mBookCustomInfo.getPurchasePrice());
            }
        } else {
            setPurchaseVisibility(false);
        }
    }


    @OnClick({R.id.btn_book_data_send, R.id.radioBtn_read, R.id.radioBtn_reading, R.id.radioBtn_unread})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_book_data_send:
                Log.d(Constants.TAG_BOOK_DATA_EDIT_DIALOG, "click btn_book_data_send: ");

                storeData();
                FirebaseApiHelper.newInstance().uploadMyBook(mBookCustomInfo.getIsbn13(), mBookCustomInfo);
                mPresenter.refreshMainFragment();
                mPresenter.refreshDetailFragment(mBookCustomInfo);
                dismiss();
                break;

            case R.id.radioBtn_reading:
                mLlayoutDialogReadingPage.setVisibility(View.VISIBLE);
                break;

            case R.id.radioBtn_unread:
                mLlayoutDialogReadingPage.setVisibility(View.GONE);
                break;

            case R.id.radioBtn_read:
                mLlayoutDialogReadingPage.setVisibility(View.GONE);
                break;
        }
    }


    private void storeData() {

        mBookCustomInfo.setTitle(mEtDialogBookTitle.getText().toString());
        mBookCustomInfo.setSubtitle(mEtDialogBookSubtitle.getText().toString());
//        mAuthor.add(mEtDialogBookTitle.getText().toString());
//        mBook.setAuthor(mAuthor);
        mBookCustomInfo.setPublisher(mEtDialogBookPublisher.getText().toString());
        mBookCustomInfo.setPublishDate(mEtDialogBookPublishDate.getText().toString());
        mBookCustomInfo.setLanguage(mEtDialogBookLanguage.getText().toString());

        mBookCustomInfo.setBookReadStatus(checkBookStatus());


        mBookCustomInfo.setHaveBook(mCheckBoxHaveBook.isChecked());

        if (mCheckBoxHaveBook.isChecked()) {
            mBookCustomInfo.setPurchaseDate(mEtDialogBookPurchaseDate.getText().toString());
            mBookCustomInfo.setPurchasePrice(mEtDialogBookPurchasePrice.getText().toString());
        }

        if (mBookCustomInfo.getCreateTime() == "") {
            mBookCustomInfo.setCreateTime(String.valueOf(System.currentTimeMillis() / 1000));
        } else {
            mBookCustomInfo.setUpdateTime(String.valueOf(System.currentTimeMillis() / 1000));
        }
    }


    private int checkBookStatus() {
        int bookStatus = -1;

        if (mRadioBtnRead.isChecked()) {
            bookStatus = Constants.READ;
            mBookCustomInfo.setReadingPage(-1);
        } else if (mRadioBtnReading.isChecked()) {
            bookStatus = Constants.READING;
            if (mEtDialogBookReadingPage.getText().toString().length() > 0
                    && Integer.valueOf(mEtDialogBookReadingPage.getText().toString()) > 0) {
                mBookCustomInfo.setReadingPage(Integer.valueOf(mEtDialogBookReadingPage.getText().toString()));
            }
        } else if (mRadioBtnUnread.isChecked()) {
            bookStatus = Constants.UNREAD;
            mBookCustomInfo.setReadingPage(-1);
        }
        return bookStatus;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        Log.d(Constants.TAG_BOOK_DATA_EDIT_DIALOG, "onCheckedChanged: " + isChecked);
        setPurchaseVisibility(isChecked);
    }

    private void setPurchaseVisibility(boolean isVisible) {
        Log.d(Constants.TAG_BOOK_DATA_EDIT_DIALOG, "setVisibility: " + isVisible);
        mLlayoutDialogPurchaseDate.setVisibility((isVisible) ? View.VISIBLE : View.GONE);
        mLlayoutDialogPurchasePrice.setVisibility((isVisible) ? View.VISIBLE : View.GONE);
    }
}
