package com.wenliu.bookshare;

import com.google.firebase.auth.FirebaseAuth;
import com.wenliu.bookshare.api.callbacks.SignInCallback;
import com.wenliu.bookshare.api.callbacks.SignUpCallback;

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
        return password.length() > 5;
    }

    @Override
    public void loginTask(LoginActivity activity, FirebaseAuth auth, String email, String password, SignInCallback callback) {
        UserManager.getInstance().signInByEmail(activity, auth, email, password, callback);
    }

    @Override
    public void register(LoginActivity activity, FirebaseAuth auth, String email, String password, String name, SignUpCallback callback) {
        UserManager.getInstance().signUpByEmail(activity, auth, email, password, name, callback);
    }


}
