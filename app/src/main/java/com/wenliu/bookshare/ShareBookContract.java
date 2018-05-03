package com.wenliu.bookshare;

/**
 * Created by wen on 2018/5/2.
 */

public interface ShareBookContract {

    interface View extends BaseView<Presenter> {
        void setText(String text);


    }


    interface Presenter extends BasePresenter {


    }

}
