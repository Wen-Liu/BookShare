package com.wenliu.bookshare.lent;

import com.wenliu.bookshare.base.BasePresenter;
import com.wenliu.bookshare.base.BaseView;
import com.wenliu.bookshare.object.LentBook;

import java.util.ArrayList;

public interface LentContract {

    interface View extends BaseView<Presenter> {

        void showLent(ArrayList<LentBook> lentBooks);

        void isShowLoading(boolean isShow);

        void isNoLentData(boolean isNoLentData);

        void showConfirmReject(LentBook lentBook);

        void showConfirmAccept(LentBook lentBook);
    }

    interface Presenter extends BasePresenter {
        void  getMyLentData();

        void confirmReject(LentBook lentBook);

        void confirmAccept(LentBook lentBook);

        void sendBorrowAccept(LentBook lentBook);

        void sendBorrowReject(LentBook lentBook);

    }
}
