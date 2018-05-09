package com.wenliu.bookshare;

import android.app.FragmentManager;
import android.app.FragmentTransaction;

import com.wenliu.bookshare.main.MainFragment;
import com.wenliu.bookshare.main.MainPresenter;

/**
 * Created by wen on 2018/5/4.
 */

public class ShareBookPresenter implements ShareBookContract.Presenter {

    private final ShareBookContract.View mShareBookView;
    private FragmentManager mFragmentManager;

    private MainFragment mMainFragment;
    private MainPresenter mMainPresenter;

    public static final String MAIN = "MAIN";

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
    public void checkIsbnValid(String isbn) {
        if (isbn.length() == 10 || isbn.length() == 13) {
            mShareBookView.setEditText(isbn);
        } else {
            mShareBookView.setEditTextError("不符合格式");
        }
    }
}
