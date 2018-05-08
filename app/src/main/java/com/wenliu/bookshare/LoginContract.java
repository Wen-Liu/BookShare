package com.wenliu.bookshare;

import com.google.firebase.auth.FirebaseAuth;
import com.wenliu.bookshare.api.callbacks.SignInCallback;
import com.wenliu.bookshare.api.callbacks.SignUpCallback;
import com.wenliu.bookshare.base.BasePresenter;
import com.wenliu.bookshare.base.BaseView;

/**
 * Created by wen on 2018/5/7.
 */

public interface LoginContract {

    interface View extends BaseView<Presenter> {

    }


    interface Presenter extends BasePresenter {

        boolean isEmailValid(String email);

        boolean isPasswordValid(String password);

        void loginTask(LoginActivity activity, FirebaseAuth auth, String email, String password, SignInCallback callback);

        void register(LoginActivity activity, FirebaseAuth auth, String email, String password,String name, SignUpCallback callback);

    }


}
