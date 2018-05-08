package com.wenliu.bookshare;

import com.wenliu.bookshare.base.BasePresenter;
import com.wenliu.bookshare.base.BaseView;

/**
 * Created by wen on 2018/5/2.
 */

public interface ShareBookContract {

    interface View extends BaseView<Presenter> {
        void setText(String text);

        void setImage(String image);
    }


    interface Presenter extends BasePresenter {
        void transToMain();

    }

}