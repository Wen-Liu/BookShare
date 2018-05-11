package com.wenliu.bookshare;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.wenliu.bookshare.base.BaseActivity;
import com.wenliu.bookshare.dialog.InputIsbnDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ShareBookActivity extends BaseActivity implements ShareBookContract.View {

    @BindView(R.id.fab)
    FloatingActionButton mFab;
    private ShareBookContract.Presenter mPresenter;
    private InputIsbnDialog mInputIsbnDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();
    }


    private void init() {

        Log.d(Constants.TAG_MAIN_ACTIVITY, "ShareBookActivity.init");
        setContentView(R.layout.activity_share_book);
        ButterKnife.bind(this);

        mPresenter = new ShareBookPresenter(this, getFragmentManager());
        mPresenter.start();

        setToolbar();
    }

    private void setToolbar() {
        // Set the padding to match the Status Bar height
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setPadding(0, getStatusBarHeight(), 0, 0);
        setSupportActionBar(toolbar);
    }


    /**
     *
     * @return height of status bar
     */
    private int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources()
                .getIdentifier("status_bar_height", "dimen", "android");

        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @OnClick(R.id.fab)
    public void onViewClicked() {
//        Snackbar.make(mFab, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show();

        mInputIsbnDialog = new InputIsbnDialog(this, this, mPresenter);
        mInputIsbnDialog.show();
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        Log.d(Constants.TAG_MAIN_ACTIVITY, "onActivityResult");

        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
            if (scanningResult.getContents() != null) {
                String scanContent = scanningResult.getContents();
                if (!scanContent.equals("")) {
                    String scanResult = scanContent.toString();
//                    Toast.makeText(getApplicationContext(), "掃描內容: " + scanResult, Toast.LENGTH_LONG).show();
                    Log.d(Constants.TAG_MAIN_ACTIVITY, "掃描內容: " + scanResult);
                    mPresenter.checkIsbnValid(mPresenter.isIsbnValid(scanResult), scanResult);
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, intent);
            Toast.makeText(getApplicationContext(), "發生錯誤", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void setPresenter(ShareBookContract.Presenter presenter) {

    }


    @Override
    public void setEditText(String isbn) {
        mInputIsbnDialog.setEditTextIsbn(isbn);
    }


    @Override
    public void setEditTextError(String error) {
        mInputIsbnDialog.setEditTextError(error);
    }
}
