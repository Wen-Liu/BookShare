package com.wenliu.bookshare;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.wenliu.bookshare.detial.DetailFragment;
import com.wenliu.bookshare.detial.DetailPresenter;
import com.wenliu.bookshare.main.MainFragment;
import com.wenliu.bookshare.main.MainPresenter;
import com.wenliu.bookshare.object.BookCustomInfo;

/**
 * Created by wen on 2018/5/4.
 */

public class ShareBookPresenter implements ShareBookContract.Presenter {
    private final ShareBookContract.View mShareBookView;
    private final FragmentManager mFragmentManager;
    private MainFragment mMainFragment;
    private MainPresenter mMainPresenter;
    private DetailFragment mDetailFragment;
    private DetailPresenter mDetailPresenter;

    public static final String MAIN = "MAIN";
    public static final String DETAIL = "DETAIL";

    public ShareBookPresenter(ShareBookContract.View shareBookView, FragmentManager fragmentManager) {
        mShareBookView = shareBookView;
        mFragmentManager = fragmentManager;
    }

    @Override
    public void start() {
        transToMain();
    }

    @Override
    public void result(int requestCode, int resultCode, Intent intent) {

        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
            if (scanningResult.getContents() != null) {
                String scanContent = scanningResult.getContents();
                if (!scanContent.equals("")) {
                    String scanResult = scanContent.toString();
                    Log.d(Constants.TAG_SHAREBOOK_PRESENTER, "掃描內容: " + scanResult);

                    checkIsbnValid(isIsbnValid(scanResult), scanResult);
                }
            }
        } else {
            Toast.makeText(ShareBook.getAppContext(), "發生錯誤", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean isIsbnValid(String isbn) {
        return isbn.length() == 10 || isbn.length() == 13;
    }

    @Override
    public void checkIsbnValid(boolean isIsbnValid, String isbn) {
        if (isIsbnValid) {
            mShareBookView.setEditText(isbn);
        } else {
            mShareBookView.setEditTextError(ShareBook.getAppContext().getString(R.string.error_invalid_isbn));
        }
    }

    @Override
    public void transToMain() {
        Log.d(Constants.TAG_SHAREBOOK_PRESENTER, "transToMain: ");

        FragmentTransaction transaction = mFragmentManager.beginTransaction();

        if (mMainFragment == null) mMainFragment = MainFragment.newInstance();

        if (mMainPresenter == null) {
            mMainPresenter = new MainPresenter(mMainFragment);
        }

        if (!mMainFragment.isAdded()) {
            transaction.add(R.id.frame_container_sharebook, mMainFragment, MAIN);
        } else {
            transaction.show(mMainFragment);
        }

        transaction.commit();
    }

    @Override
    public void transToDetail(BookCustomInfo bookCustomInfo) {
        Log.d(Constants.TAG_SHAREBOOK_PRESENTER, "transToDetail: ");

        FragmentTransaction transaction = mFragmentManager.beginTransaction();

        if (mMainFragment != null && !mMainFragment.isHidden()) {
            transaction.hide(mMainFragment);
            transaction.addToBackStack(MAIN);
        }

        if (mDetailFragment == null) {
            mDetailFragment = DetailFragment.newInstance();
            Log.d(Constants.TAG_SHAREBOOK_PRESENTER, "transToDetail newInstance: ");
        }

        if (mDetailPresenter == null) {
            mDetailPresenter = new DetailPresenter(mDetailFragment, this, bookCustomInfo);
        } else {
            mDetailPresenter.setBookData(bookCustomInfo);
        }

        if (!mDetailFragment.isAdded()) {
            Log.d(Constants.TAG_SHAREBOOK_PRESENTER, "transToDetail Add: ");
            transaction.add(R.id.frame_container_sharebook, mDetailFragment, DETAIL);
        }

        transaction.commit();
    }

    @Override
    public void refreshMainFragment() {
        mMainPresenter.loadBooks();
    }

    @Override
    public void refreshDetailFragment(BookCustomInfo bookCustomInfo) {
        if (mDetailFragment != null && mDetailFragment.isVisible()) {
            mDetailFragment.showBook(bookCustomInfo);
        }
    }

    @Override
    public int[] getMyBookStatus() {
        return mMainFragment.getMyBookStatus();
    }

    @Override
    public void goToEditDialog(BookCustomInfo bookCustomInfo) {
        mShareBookView.showEditDialog(bookCustomInfo);
    }


}
