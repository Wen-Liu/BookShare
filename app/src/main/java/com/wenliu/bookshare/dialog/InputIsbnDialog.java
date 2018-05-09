package com.wenliu.bookshare.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.wenliu.bookshare.R;
import com.wenliu.bookshare.ShareBook;
import com.wenliu.bookshare.ShareBookActivity;
import com.wenliu.bookshare.ShareBookContract;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wen on 2018/5/9.
 */

public class InputIsbnDialog extends Dialog {

    @BindView(R.id.dialog_comment_edittext)
    EditText mDialogCommentEdittext;
    @BindView(R.id.imageView_scanner)
    ImageView mImageViewScanner;
    @BindView(R.id.btn_send)
    Button mBtnSend;

    private Context mContext;
    private IntentIntegrator scanIntegrator;
    private ShareBookActivity mShareBookActivity;
    private ShareBookContract.Presenter mPresenter;

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

                Toast.makeText(ShareBook.getAppContext(),"123",Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_send:
                Toast.makeText(ShareBook.getAppContext(),"send",Toast.LENGTH_SHORT).show();

                break;
        }
    }


}
