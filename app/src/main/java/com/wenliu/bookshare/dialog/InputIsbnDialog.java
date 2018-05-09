package com.wenliu.bookshare.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.wenliu.bookshare.Constants;
import com.wenliu.bookshare.GetBookCoverUrl;
import com.wenliu.bookshare.R;
import com.wenliu.bookshare.ShareBook;
import com.wenliu.bookshare.ShareBookActivity;
import com.wenliu.bookshare.ShareBookContract;
import com.wenliu.bookshare.api.FirebaseApiHelper;
import com.wenliu.bookshare.api.GetBookDataTask;
import com.wenliu.bookshare.api.GetBookIdTask;
import com.wenliu.bookshare.api.callbacks.GetBookDataCallback;
import com.wenliu.bookshare.api.callbacks.GetBookIdCallback;
import com.wenliu.bookshare.object.Book;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wen on 2018/5/9.
 */

public class InputIsbnDialog extends Dialog {


    @BindView(R.id.edittext_isbn)
    EditText mEdittextIsbn;
    @BindView(R.id.imageView_scanner)
    ImageView mImageViewScanner;
    @BindView(R.id.btn_send)
    Button mBtnSend;

    private Context mContext;
    private IntentIntegrator scanIntegrator;
    private ShareBookActivity mShareBookActivity;
    private ShareBookContract.Presenter mPresenter;
    private String mIsbn;


    public InputIsbnDialog(@NonNull Context context, ShareBookActivity activity, ShareBookContract.Presenter presenter) {
        super(context);
        setContentView(R.layout.dialog_input_isbn);
        ButterKnife.bind(this);

        mContext = context;
        mShareBookActivity = activity;
        mPresenter = presenter;
    }


    @OnClick({R.id.imageView_scanner, R.id.btn_send})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.imageView_scanner:
                scanIntegrator = new IntentIntegrator(mShareBookActivity);
                scanIntegrator.setPrompt("請掃描");
                scanIntegrator.setTimeout(300000);
                scanIntegrator.setOrientationLocked(false);
                scanIntegrator.initiateScan();

                break;


            case R.id.btn_send:

                mIsbn = String.valueOf(mEdittextIsbn.getText());
                String bookCoverUrl = GetBookCoverUrl.GetUrl(mIsbn);

                new GetBookIdTask(mIsbn, new GetBookIdCallback() {
                    @Override
                    public void onCompleted(String id) {
                        Log.d(Constants.TAG_INPUT_ISBN_DIALOG, "========== GetBookIdTask onCompleted ==========");

                        new GetBookDataTask(id, new GetBookDataCallback() {
                            @Override
                            public void onCompleted(Book book) {
                                Log.d(Constants.TAG_INPUT_ISBN_DIALOG, "========== GetBookDataTask onCompleted ==========");

                                new FirebaseApiHelper().uploadBooks(mIsbn, book);
                            }

                            @Override
                            public void onError(String errorMessage) {

                            }
                        }).execute();
                    }

                    @Override
                    public void onError(String errorMessage) {
                        Log.d(Constants.TAG_INPUT_ISBN_DIALOG, "GetBookIdTask onError");
                    }
                }).execute();

                break;
        }
    }

    public void setEditTextIsbn(String isbn){
        mEdittextIsbn.setText(isbn);
    }

    public void setEditTextError(String error){
        mEdittextIsbn.setError(error);
        mEdittextIsbn.requestFocus();
    }

}
