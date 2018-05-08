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

    @BindView(R.id.login_progress)
    ProgressBar mProgressView;
    @BindView(R.id.linearlayout_sign_in)
    LinearLayout mLinearlayoutSignIn;
    @BindView(R.id.editText_signIn_email)
    EditText mEditTextSignInEmail;
    @BindView(R.id.editText_signIn_password)
    EditText mEditTextSignInPassword;
    @BindView(R.id.btn_signIn_email)
    Button mEmailSignInButton;

    @BindView(R.id.linearlayout_sign_up)
    LinearLayout mLinearlayoutSignUp;
    @BindView(R.id.editText_signUp_name)
    EditText mEditTextSignUpName;
    @BindView(R.id.editText_signUp_email)
    EditText mEditTextSignUpEmail;
    @BindView(R.id.editText_signUp_password)
    EditText mEditTextSignUpPassword;
    @BindView(R.id.editText_signUp_password_confirm)
    EditText mEditTextSignUpPasswordConfirm;
    @BindView(R.id.btn_register_email)
    Button mBtnSignUpEmail;
    @BindView(R.id.btn_go_to_register)
    Button mBtnRegister;
    @BindView(R.id.btn_register_cancel)
    Button mBtnRegisterCancel;
    @BindView(R.id.constraint_layout)
    ConstraintLayout mConstraintLayout;

    private LoginContract.Presenter mPresenter;
    private FirebaseAuth mAuth;
    private View focusView;
    private boolean cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(Constants.TAG_LOGIN_ACTIVITY, "onCreate");
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        initView();

        mAuth = FirebaseAuth.getInstance();
        mPresenter = new LoginPresenter(this);
        mPresenter.start();
    }


    private void initView() {
        mConstraintLayout.getBackground().setAlpha(230);
        setSignUpVisibility(false);
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.d(Constants.TAG_LOGIN_ACTIVITY, "onStart");
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void login() {
        Log.d(Constants.TAG_LOGIN_ACTIVITY, "login");
        // Store values at the time of the login attempt.
        String email = mEditTextSignInEmail.getText().toString();
        String password = mEditTextSignInPassword.getText().toString();

        cancel = false;
        focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password) || !mPresenter.isPasswordValid(password)) {
            mEditTextSignInPassword.setError(getString(R.string.error_invalid_password));
            focusView = mEditTextSignInPassword;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEditTextSignInEmail.setError(getString(R.string.error_field_required));
            focusView = mEditTextSignInEmail;
            cancel = true;
        } else if (!mPresenter.isEmailValid(email)) {
            mEditTextSignInEmail.setError(getString(R.string.error_invalid_email));
            focusView = mEditTextSignInEmail;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true,mLinearlayoutSignIn);
            mPresenter.loginTask(this, mAuth, email, password, new SignInCallback() {
                @Override
                public void onCompleted() {
                    showProgress(false,mLinearlayoutSignIn);
                    transToShareBookActivity();
                }

                @Override
                public void onError(String errorMessage) {
                    showProgress(false,mLinearlayoutSignIn);
                    mEditTextSignInEmail.setError(errorMessage);
                    focusView = mEditTextSignInEmail;
                    cancel = true;
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

        cancel = false;
        focusView = null;

        if (TextUtils.isEmpty(name)) {
            mEditTextSignUpName.setError(getString(R.string.error_field_required));
            focusView = mEditTextSignUpName;
            cancel = true;
        }

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password) || !mPresenter.isPasswordValid(password)) {
            mEditTextSignUpPassword.setError(getString(R.string.error_invalid_password));
            focusView = mEditTextSignUpPassword;
            cancel = true;
        }

        if (TextUtils.isEmpty(passwordConfirm) || !passwordConfirm.equals(password)) {
            mEditTextSignUpPasswordConfirm.setError(getString(R.string.error_incorrect_password_confirm));
            focusView = mEditTextSignUpPasswordConfirm;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEditTextSignUpEmail.setError(getString(R.string.error_field_required));
            focusView = mEditTextSignUpEmail;
            cancel = true;
        } else if (!mPresenter.isEmailValid(email)) {
            mEditTextSignUpEmail.setError(getString(R.string.error_invalid_email));
            focusView = mEditTextSignUpEmail;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true,mLinearlayoutSignUp);
            mPresenter.register(this, mAuth, email, password, name, new SignUpCallback() {
                @Override
                public void onCompleted() {
                    showProgress(false,mLinearlayoutSignUp);
                    transToShareBookActivity();
                }

                @Override
                public void onError(String errorMessage) {
                    showProgress(false, mLinearlayoutSignUp);
                    mEditTextSignUpEmail.setError(errorMessage);
                    focusView = mEditTextSignUpEmail;
                    cancel = true;
                }
            });
        }
    }


    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show, final View view ) {
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


    @OnClick({R.id.btn_signIn_email, R.id.btn_register_email, R.id.btn_register_cancel, R.id.btn_go_to_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_signIn_email:
                login();
                break;

            case R.id.btn_go_to_register:
                showSignUpForm(true);
                break;

            case R.id.btn_register_email:
                register();
                break;

            case R.id.btn_register_cancel:
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

    private void transToShareBookActivity() {
        Intent intent = new Intent(LoginActivity.this, ShareBookActivity.class);
        startActivity(intent);
        finish();
    }


    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
//    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {
//
//        private String mEmail;
//        private String mPassword;
//
//        UserLoginTask(String email, String password) {
//            mEmail = email;
//            mPassword = password;
//        }
//
//        @Override
//        protected Boolean doInBackground(Void... params) {
//            Log.d(Constants.TAG_LOGIN_ACTIVITY, "UserLoginTask doInBackground");
//            Log.d(Constants.TAG_FIREBASE_API_HELPER, "loginByEmail ");
//            mAuth.createUserWithEmailAndPassword(mEmail, mPassword)
//                    .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//                            if (task.isSuccessful()) {
//                                // Sign in success, update UI with the signed-in user's information
//                                Log.d(Constants.TAG_FIREBASE_API_HELPER, "createUserWithEmail success!");
//                                FirebaseUser user = mAuth.getCurrentUser();
//                            } else {
//                                // If sign in fails, display a message to the user.
//                                Log.e(Constants.TAG_FIREBASE_API_HELPER, "createUserWithEmail fail!");
//                            }
//                        }
//                    });
//
//            return true;
//        }
//
//        @Override
//        protected void onPostExecute(final Boolean success) {
//            mAuthTask = null;
//            showProgress(false);
//
//            if (success) {
//                Intent intent = new Intent(LoginActivity.this, ShareBookActivity.class);
//                startActivity(intent);
//                LoginActivity.this.finish();
//
//            } else {
//                mEditTextSignInPassword.setError(getString(R.string.error_incorrect_password));
//                mEditTextSignInPassword.requestFocus();
//            }
//        }
//
//        @Override
//        protected void onCancelled() {
//            mAuthTask = null;
//            showProgress(false);
//        }
//    }
}
