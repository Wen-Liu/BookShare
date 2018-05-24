package com.wenliu.bookshare;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.util.Log;
import android.widget.ImageView;

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
    private FragmentManager mFragmentManager;
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
    public void transToMain() {
        Log.d(Constants.TAG_SHAREBOOK_PRESENTER, "transToMain: ");

        FragmentTransaction transaction = mFragmentManager.beginTransaction();

        if (mMainFragment == null) mMainFragment = MainFragment.newInstance();
        if (!mMainFragment.isAdded()) {
            transaction.add(R.id.frame_container, mMainFragment, MAIN);
        } else {
            transaction.show(mMainFragment);
        }
        transaction.commit();

        if (mMainPresenter == null) {
            mMainPresenter = new MainPresenter(mMainFragment);
        }

    }

    @Override
    public void transToDetail(BookCustomInfo bookCustomInfo, ImageView imageView) {
        Log.d(Constants.TAG_SHAREBOOK_PRESENTER, "transToDetail: ");

        FragmentTransaction transaction = mFragmentManager.beginTransaction();

        if (mMainFragment != null && !mMainFragment.isHidden()) {
            transaction.hide(mMainFragment);
            transaction.addToBackStack(MAIN);
        }

        if (mDetailFragment== null) {
            mDetailFragment = DetailFragment.newInstance();
            Log.d(Constants.TAG_SHAREBOOK_PRESENTER, "transToDetail newInstance: ");
        }

        transaction.add(R.id.frame_container, mDetailFragment, DETAIL);
        transaction.addSharedElement(imageView, ShareBook.getAppContext().getString(R.string.transitionName_detail));
        transaction.commit();

        mDetailPresenter = new DetailPresenter(mDetailFragment,this, bookCustomInfo);
    }

    @Override
    public void refreshMainFragment() {
        mMainPresenter.loadBooks();
    }

    @Override
    public void refreshDetailFragment(BookCustomInfo bookCustomInfo) {
        if(mDetailFragment!= null && mDetailFragment.isVisible()) {
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



}
