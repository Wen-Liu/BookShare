package com.wenliu.bookshare;

import com.google.firebase.auth.FirebaseAuth;
import com.wenliu.bookshare.api.FirebaseApiHelper;
import com.wenliu.bookshare.api.SignUpCallback;

/**
 * Created by wen on 2018/5/7.
 */

public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.View mLoginView;

    public LoginPresenter(LoginContract.View loginView) {
        mLoginView = loginView;
        mLoginView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public boolean isEmailValid(String email) {
        return email.contains("@");
    }

    @Override
    public boolean isPasswordValid(String password) {
        return password.length() > 6;
    }

    @Override
    public void loginTask(LoginActivity activity, FirebaseAuth auth, String email, String password, SignUpCallback callback) {
        UserManager.getInstance().signUpByEmail(activity, auth, email, password, callback);
    }

}
