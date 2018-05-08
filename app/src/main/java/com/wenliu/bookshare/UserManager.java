package com.wenliu.bookshare;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.wenliu.bookshare.api.FirebaseApiHelper;
import com.wenliu.bookshare.api.callbacks.SignInCallback;
import com.wenliu.bookshare.api.callbacks.SignUpCallback;
import com.wenliu.bookshare.object.User;

/**
 * Created by wen on 2018/5/7.
 */

public class UserManager {

    private static final UserManager instance = new UserManager();
    private static String mUserId;
    private static String mUserEmail;
    private static String mUserName;
    private static String mUserImage;
    private static String mFbToken;

    SharedPreferences mUserData = ShareBook.getAppContext().getSharedPreferences(Constants.USER_DATA, Context.MODE_PRIVATE);

    // private constructor，這樣其他物件就沒辦法直接用new來取得新的實體
    private UserManager() {
    }

    // 因為constructor已經private，所以需要另外提供方法讓其他程式調用這個類別
    public static UserManager getInstance() {
        return instance;
    }

    public String getUserId() {
        return mUserData.getString(Constants.USER_ID, null);
    }

    public String getUserEmail() {
        return mUserData.getString(Constants.USER_EMAIL, null);
    }

    public String getUserName() {
        return mUserData.getString(Constants.USER_NAME, null);
    }

    public String getUserImage() {
        return mUserData.getString(Constants.USER_IMAGE, null);
    }

    public boolean isLoginStatus() {
        Log.d(Constants.TAG_USERMANAGER, "isLoginStatus(): " + ((getUserId() == null) ? false : true));
        return (getUserId() == null) ? false : true;
    }


    public void signUpByEmail(final LoginActivity activity, final FirebaseAuth auth, String email, String password, final String name, final SignUpCallback callback) {
        Log.d(Constants.TAG_USERMANAGER, "signUpByEmail ");

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(Constants.TAG_USERMANAGER, "createUserWithEmail success!");

                            FirebaseUser firebaseUser = auth.getCurrentUser();

                            mUserData.edit()
                                    .putString(Constants.USER_EMAIL, firebaseUser.getEmail())
                                    .putString(Constants.USER_ID, firebaseUser.getUid())
                                    .putString(Constants.USER_NAME, name)
                                    .commit();

                            User user = new User();
                            user.setEmail(firebaseUser.getEmail());
                            user.setId(firebaseUser.getUid());
                            user.setName(name);

                            new FirebaseApiHelper().uploadUser(user, callback);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.e(Constants.TAG_USERMANAGER, "createUserWithEmail fail! Error message: " + task.getException().getLocalizedMessage());
                            callback.onError(task.getException().getLocalizedMessage());
                        }
                    }
                });
    }


    public void signInByEmail(LoginActivity activity, final FirebaseAuth auth, String email, String password, final SignInCallback callback) {

        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(Constants.TAG_USERMANAGER, "signInWithEmailAndPassword success!");
                            FirebaseUser user = auth.getCurrentUser();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.e(Constants.TAG_USERMANAGER, "signInWithEmailAndPassword fail! Error message: " + task.getException().getLocalizedMessage());
                            callback.onError(task.getException().getLocalizedMessage());
                        }
                    }
                });
    }


}
