package com.wenliu.bookshare.detial;

import com.wenliu.bookshare.base.BasePresenter;
import com.wenliu.bookshare.base.BaseView;
import com.wenliu.bookshare.object.BookCustomInfo;

/**
 * Created by wen on 2018/5/14.
 */

public interface DetailContract {

    interface View extends BaseView<Presenter> {

        void showBook(BookCustomInfo bookCustomInfo);
    }

    interface Presenter extends BasePresenter {

        void showBookDataEditDialog(BookCustomInfo bookCustomInfo);

        void setBookData(BookCustomInfo bookCustomInfo);
    }
}
