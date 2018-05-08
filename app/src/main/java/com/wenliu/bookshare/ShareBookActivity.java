package com.wenliu.bookshare;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.zxing.integration.android.IntentIntegrator;

import butterknife.ButterKnife;


public class ShareBookActivity extends AppCompatActivity implements ShareBookContract.View {
//    @BindView(R.id.editText_isbn)
//    EditText mEditTextIsbn;
//    @BindView(R.id.btn_sendISBN)
//    Button mBtnSendISBN;
//    @BindView(R.id.imageView)
//    ImageView mImageView;
//    @BindView(R.id.btn_barcode_scanner)
//    Button mBtnBarcodeScanner;
//    @BindView(R.id.tv_book_title)
//    TextView mTvBookTitle;

    private String mIsbn;
    private final String mUrl = "http://api.findbook.tw/book/cover/";
    private String mBookTitle;
    private String mBookAuthor;
    private IntentIntegrator scanIntegrator;
    private ShareBookContract.Presenter mPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        mImageView = (ImageView) findViewById(R.id.imageView);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }


    private void init() {

        Log.d(Constants.TAG_MAIN_ACTIVITY, "ShareBookActivity.init");
        setContentView(R.layout.activity_share_book);
        ButterKnife.bind(this);

        mPresenter = new ShareBookPresenter(this, getFragmentManager());
        mPresenter.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


//    @OnClick({R.id.btn_barcode_scanner, R.id.btn_sendISBN})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.btn_barcode_scanner:
//
//                scanIntegrator = new IntentIntegrator(ShareBookActivity.this);
//                scanIntegrator.setPrompt("請掃描");
//                scanIntegrator.setTimeout(300000);
//                scanIntegrator.setOrientationLocked(false);
//                scanIntegrator.initiateScan();
//
//                break;
//
//            case R.id.btn_sendISBN:
//                mIsbn = String.valueOf(mEditTextIsbn.getText());
//                String bookCoverUrl = GetBookCoverUrl.GetUrl(mIsbn);
//
//                new GetBookIdTask(mIsbn, new GetBookIdCallback() {
//                    @Override
//                    public void onCompleted(String id) {
//                        Log.d(Constants.TAG_MAIN_ACTIVITY, "========== GetBookIdTask onCompleted ==========");
//
//                        new GetBookDataTask(id, new GetBookDataCallback() {
//                            @Override
//                            public void onCompleted(Book book) {
//                                Log.d(Constants.TAG_MAIN_ACTIVITY, "========== GetBookDataTask onCompleted ==========");
//
//                                new FirebaseApiHelper().uploadBooks(mIsbn,book);
//                                setText(book.getTitle());
//                                setImage(book.getImage());
//                            }
//
//                            @Override
//                            public void onError(String errorMessage) {
//
//                            }
//                        }).execute();
//                    }
//
//                    @Override
//                    public void onError(String errorMessage) {
//                        Log.d(Constants.TAG_MAIN_ACTIVITY, "GetBookIdTask onError");
//                    }
//                }).execute();
//                break;
//        }
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
//
//        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
//        if (scanningResult != null) {
//            if (scanningResult.getContents() != null) {
//                String scanContent = scanningResult.getContents();
//                if (!scanContent.equals("")) {
//                    Toast.makeText(getApplicationContext(), "掃描內容: " + scanContent.toString(), Toast.LENGTH_LONG).show();
//                    mEditTextIsbn.setText(scanContent.toString());
//                }
//            }
//        } else {
//            super.onActivityResult(requestCode, resultCode, intent);
//            Toast.makeText(getApplicationContext(), "發生錯誤", Toast.LENGTH_LONG).show();
//        }
//    }


    @Override
    public void setText(String text) {
//        mTvBookTitle.setText(text);
    }

    @Override
    public void setImage(String image) {
//        Picasso.get()
//                .load(image)
//                .into(mImageView);
    }

    @Override
    public void setPresenter(ShareBookContract.Presenter presenter) {

    }
}
