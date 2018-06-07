package com.wenliu.bookshare;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.wenliu.bookshare.api.callbacks.SignInCallback;
import com.wenliu.bookshare.api.callbacks.SignUpCallback;
import com.wenliu.bookshare.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity implements LoginContract.View {

    //region "BindView"
    @BindView(R.id.progress_login)
    ProgressBar mProgressView;

    @BindView(R.id.llayout_login_sign_in)
    LinearLayout mLinearlayoutSignIn;
    @BindView(R.id.et_login_sign_in_email)
    EditText mEditTextSignInEmail;
    @BindView(R.id.et_login_sign_in_password)
    EditText mEditTextSignInPassword;
    @BindView(R.id.btn_login_sign_in_email)
    Button mEmailSignInButton;

    @BindView(R.id.linearlayout_sign_up)
    LinearLayout mLinearlayoutSignUp;
    @BindView(R.id.et_login_signUp_name)
    EditText mEditTextSignUpName;
    @BindView(R.id.et_login_sign_up_email)
    EditText mEditTextSignUpEmail;
    @BindView(R.id.et_login_sign_up_password)
    EditText mEditTextSignUpPassword;
    @BindView(R.id.et_login_sign_up_password_confirm)
    EditText mEditTextSignUpPasswordConfirm;
    @BindView(R.id.btn_login_register_email)
    Button mBtnSignUpEmail;
    @BindView(R.id.btn_login_to_register)
    Button mBtnRegister;
    @BindView(R.id.btn_login_register_cancel)
    Button mBtnRegisterCancel;
    @BindView(R.id.clayout_login)
    ConstraintLayout mConstraintLayout;
    //endregion
    private LoginContract.Presenter mPresenter;
    private FirebaseAuth mAuth;
    private View mFocusView;
    private String mEmail;
    private String mPassword;
    private boolean isProcessCancel = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(Constants.TAG_LOGIN_ACTIVITY, "onCreate: ");

        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        // check the user's login status. If true, pass the login page.
        if (UserManager.getInstance().isLoginStatus()) {
            showUserInfoLog();
            transToShareBookActivity();
        } else {
            init();
        }
    }

    private void init() {
        mPresenter = new LoginPresenter(this);
        mPresenter.start();
        mAuth = FirebaseAuth.getInstance();

        mConstraintLayout.getBackground().setAlpha(230);
        setSignUpVisibility(false);
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.d(Constants.TAG_LOGIN_ACTIVITY, "onStart: ");
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void login() {
        Log.d(Constants.TAG_LOGIN_ACTIVITY, "login: ");

        // Store values at the time of the login attempt.
        mEmail = mEditTextSignInEmail.getText().toString();
        mPassword = mEditTextSignInPassword.getText().toString();
        mFocusView = null;

        // Check for a valid password
        if (TextUtils.isEmpty(mPassword) || !mPresenter.isPasswordValid(mPassword)) {
            mEditTextSignInPassword.setError(getString(R.string.error_invalid_password));
            mFocusView = mEditTextSignInPassword;
            isProcessCancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(mEmail)) {
            mEditTextSignInEmail.setError(getString(R.string.error_field_required));
            mFocusView = mEditTextSignInEmail;
            isProcessCancel = true;
        } else if (!mPresenter.isEmailValid(mEmail)) {
            mEditTextSignInEmail.setError(getString(R.string.error_invalid_email));
            mFocusView = mEditTextSignInEmail;
            isProcessCancel = true;
        }

        if (isProcessCancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            mFocusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true, mLinearlayoutSignIn);
            mPresenter.loginTask(this, mAuth, mEmail, mPassword, new SignInCallback() {
                @Override
                public void onCompleted() {
                    showProgress(false, mLinearlayoutSignIn);
                    showUserInfoLog();
                    transToShareBookActivity();
                }

                @Override
                public void onError(String errorMessage) {
                    Log.d(Constants.TAG_LOGIN_ACTIVITY, "onError: " + errorMessage);

                    showProgress(false, mLinearlayoutSignIn);
                    mEditTextSignInEmail.setError(getString(R.string.error_login_fail));
                    mFocusView = mEditTextSignInEmail;
                    isProcessCancel = true;
                }
            });
        }
    }


    private void register() {
        Log.d(Constants.TAG_LOGIN_ACTIVITY, "register");

        String name = mEditTextSignUpName.getText().toString();
        String email = mEditTextSignUpEmail.getText().toString();
        String password = mEditTextSignUpPassword.getText().toString();
        String passwordConfirm = mEditTextSignUpPasswordConfirm.getText().toString();

        isProcessCancel = false;
        mFocusView = null;

        if (TextUtils.isEmpty(name)) {
            mEditTextSignUpName.setError(getString(R.string.error_field_required));
            mFocusView = mEditTextSignUpName;
            isProcessCancel = true;
        }

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password) || !mPresenter.isPasswordValid(password)) {
            mEditTextSignUpPassword.setError(getString(R.string.error_invalid_password));
            mFocusView = mEditTextSignUpPassword;
            isProcessCancel = true;
        }

        if (TextUtils.isEmpty(passwordConfirm) || !passwordConfirm.equals(password)) {
            mEditTextSignUpPasswordConfirm.setError(getString(R.string.error_incorrect_password_confirm));
            mFocusView = mEditTextSignUpPasswordConfirm;
            isProcessCancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEditTextSignUpEmail.setError(getString(R.string.error_field_required));
            mFocusView = mEditTextSignUpEmail;
            isProcessCancel = true;
        } else if (!mPresenter.isEmailValid(email)) {
            mEditTextSignUpEmail.setError(getString(R.string.error_invalid_email));
            mFocusView = mEditTextSignUpEmail;
            isProcessCancel = true;
        }

        if (isProcessCancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            mFocusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true, mLinearlayoutSignUp);
            mPresenter.register(this, mAuth, email, password, name, new SignUpCallback() {
                @Override
                public void onCompleted() {
                    showProgress(false, mLinearlayoutSignUp);
                    showUserInfoLog();
                    transToShareBookActivity();
                }

                @Override
                public void onError(String errorMessage) {
                    showProgress(false, mLinearlayoutSignUp);
                    mEditTextSignUpEmail.setError(errorMessage);
                    mFocusView = mEditTextSignUpEmail;
                    isProcessCancel = true;
                }
            });
        }
    }


    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show, final View view) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            view.setVisibility(show ? View.GONE : View.VISIBLE);
            view.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    view.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            view.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }


    @OnClick({R.id.btn_login_sign_in_email, R.id.btn_login_register_email, R.id.btn_login_register_cancel, R.id.btn_login_to_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login_sign_in_email:
                login();
                break;

            case R.id.btn_login_to_register:
                setSignUpForm();
                showSignUpForm(true);
                break;

            case R.id.btn_login_register_email:
                register();
                break;

            case R.id.btn_login_register_cancel:
                showSignUpForm(false);
                break;
        }
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        mPresenter = presenter;
    }


    private void setSignInVisibility(boolean visible) {
        mLinearlayoutSignIn.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    private void setSignUpVisibility(boolean visible) {
        mLinearlayoutSignUp.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    private void showSignUpForm(boolean isShow) {
        setSignInVisibility(isShow ? false : true);
        setSignUpVisibility(isShow ? true : false);
    }

    private void setSignUpForm() {
        mEmail = mEditTextSignInEmail.getText().toString();
        mPassword = mEditTextSignInPassword.getText().toString();

        if (mEmail != null) {
            mEditTextSignUpEmail.setText(mEmail);
        }
        if (mPassword != null) {
            mEditTextSignUpPassword.setText(mPassword);
        }
    }

    private void transToShareBookActivity() {
        Intent intent = new Intent(LoginActivity.this, ShareBookActivity.class);
        startActivity(intent);
        finish();
    }

}

