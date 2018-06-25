package com.wenliu.bookshare;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import com.wenliu.bookshare.base.BaseActivity;
import com.wenliu.bookshare.dialog.BookDataEditDialog;
import com.wenliu.bookshare.dialog.InputIsbnDialog;
import com.wenliu.bookshare.object.BookCustomInfo;
import com.wenliu.bookshare.profile.ProfileActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ShareBookActivity extends BaseActivity implements ShareBookContract.View {
    //region "BindView"
    @BindView(R.id.appbar_sharebook)
    AppBarLayout mAppbarSharebook;
    @BindView(R.id.fab_sharebook)
    FloatingActionButton mFabSharebook;
    @BindView(R.id.toolbar_sharebook)
    Toolbar mToolbarSharebook;
    //endregion

    private ShareBookContract.Presenter mPresenter;
    private InputIsbnDialog mInputIsbnDialog;
    private BookDataEditDialog mBookDataEditDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();
    }

    private void init() {
        Log.d(Constants.TAG_SHAREBOOK_ACTIVITY, "ShareBookActivity.init: ");
        setContentView(R.layout.activity_share_book);
        ButterKnife.bind(this);

        mPresenter = new ShareBookPresenter(this, getSupportFragmentManager());
        mPresenter.start();
        setToolbar();
    }

    private void setToolbar() {
        // Set the padding to match the Status Bar height
        mToolbarSharebook.setPadding(0, getStatusBarHeight(), 0, 0);
        setSupportActionBar(mToolbarSharebook);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        // Handle action bar item clicks here. The action bar will automatically handle clicks on
        // the Home/Up button, so long as you specify a parent activity in AndroidManifest.xml.
        if (menuItem.getItemId() == R.id.action_to_profile) {
            startProfileActivity();
            return true;
        }

        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        Log.d(Constants.TAG_SHAREBOOK_ACTIVITY, "onActivityResult: ");
        mPresenter.result(requestCode, resultCode, intent);
    }

    @OnClick(R.id.fab_sharebook)
    public void onViewClicked() {
//        Snackbar.make(mFab, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show();
        showInputIsbnDialog();
    }

    //region "Contract"
    @Override
    public void setPresenter(ShareBookContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setEditText(String isbn) {
        mInputIsbnDialog.setEditTextIsbn(isbn);
    }

    @Override
    public void setEditTextError(String error) {
        mInputIsbnDialog.setEditTextError(error);
    }

    @Override
    public void transToDetail(BookCustomInfo bookCustomInfo) {
        mPresenter.transToDetail(bookCustomInfo);
    }

    @Override
    public void showEditDialog(BookCustomInfo bookCustomInfo) {
        mBookDataEditDialog = new BookDataEditDialog(this, this, mPresenter, bookCustomInfo);

        Window win = mBookDataEditDialog.getWindow();
//        win.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        win.setAttributes(lp);

        mBookDataEditDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mBookDataEditDialog.getWindow().getAttributes().windowAnimations = R.style.Animation_slide_right; //style id
        mBookDataEditDialog.show();
    }

    //endregion

    private void startProfileActivity() {
        Intent intent = new Intent(ShareBookActivity.this, ProfileActivity.class);
        Bundle bundle = new Bundle();
        bundle.putIntArray(Constants.BUNDLE_BOOK_STATUS, mPresenter.getMyBookStatus());
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void showInputIsbnDialog() {
        mInputIsbnDialog = new InputIsbnDialog(this, this, mPresenter);
        mInputIsbnDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mInputIsbnDialog.getWindow().getAttributes().windowAnimations = R.style.Animation_fade; //style id
        mInputIsbnDialog.show();
    }

    public void setToolbarVisibility(boolean isVisible) {
        Log.d(Constants.TAG_SHAREBOOK_ACTIVITY, "setToolbarVisibility: ");
        if (mToolbarSharebook != null) {
            mToolbarSharebook.setVisibility((isVisible) ? View.VISIBLE : View.GONE);
        }
    }

    public void setFabVisibility(boolean isVisible) {
        Log.d(Constants.TAG_SHAREBOOK_ACTIVITY, "setFabVisibility: ");
        if (mFabSharebook != null) {
            mFabSharebook.setVisibility((isVisible) ? View.VISIBLE : View.GONE);
        }
    }


}
