package com.wenliu.bookshare.base;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.afollestad.materialdialogs.MaterialDialog;
import com.wenliu.bookshare.Constants;
import com.wenliu.bookshare.R;
import com.wenliu.bookshare.UserManager;

public class BaseActivity extends AppCompatActivity {

    protected Context mContext;
    private MaterialDialog mMaterialDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(Constants.TAG_BASE_ACTIVITY, "onCreate");
        mContext = this;
        setStatusBar();
    }

    /**
     * To change status bar to transparent.
     * @notice this method have to be used before setContentView.
     */
    private void setStatusBar() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){//4.4
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT); //calculateStatusColor(Color.WHITE, (int) alphaValue)
        }
    }


    /**
     * @return height of status bar
     */
    public int getStatusBarHeight() {

        int result = 0;
        int resourceId = getResources()
                .getIdentifier("status_bar_height", "dimen", "android");

        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
            Log.d(Constants.TAG_BASE_ACTIVITY, "getStatusBarHeight: " + result);
        }
        return result;
    }


    public void isShowLoading(boolean isShow) {
        if (isShow) {
            if (mMaterialDialog == null) {
                mMaterialDialog = new MaterialDialog.Builder(this)
                        .content(R.string.please_wait)
                        .progress(true, 0)
                        .show();
            } else {
                mMaterialDialog.show();
            }
        } else {
            mMaterialDialog.dismiss();
        }
    }


    public void showUserInfoLog() {

        Log.i(Constants.TAG_BASE_ACTIVITY, "---------------------User Info-------------------------");
        Log.i(Constants.TAG_BASE_ACTIVITY, "User id: " + UserManager.getInstance().getUserId());
        Log.i(Constants.TAG_BASE_ACTIVITY, "User name: " + UserManager.getInstance().getUserName());
        Log.i(Constants.TAG_BASE_ACTIVITY, "User email: " + UserManager.getInstance().getUserEmail());
        Log.i(Constants.TAG_BASE_ACTIVITY, "User image: " + UserManager.getInstance().getUserImage());
        Log.i(Constants.TAG_BASE_ACTIVITY, "-------------------------------------------------------");
    }
}
