package com.wenliu.bookshare.profile;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wenliu.bookshare.Constants;
import com.wenliu.bookshare.ImageManager;
import com.wenliu.bookshare.R;
import com.wenliu.bookshare.ShareBook;
import com.wenliu.bookshare.UserManager;
import com.wenliu.bookshare.api.callbacks.GetUserInfoCallback;
import com.wenliu.bookshare.base.BaseActivity;
import com.wenliu.bookshare.dialog.ProgressBarDialog;
import com.wenliu.bookshare.object.User;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProfileActivity extends BaseActivity implements ProfileContract.View {

    @BindView(R.id.appbarlayout_profile)
    AppBarLayout mAppbarlayoutProfile;
    @BindView(R.id.collape_toolbar_profile)
    CollapsingToolbarLayout mCollapeToolbarProfile;
    @BindView(R.id.toolbar_profile)
    Toolbar mToolbarProfile;
    @BindView(R.id.rv_profile)
    RecyclerView mRvProfile;
    @BindView(R.id.iv_profile_userimage)
    ImageView mIvProfileUserimage;
    @BindView(R.id.iv_profile_change_image)
    ImageView mIvProfileChangeImage;
    @BindView(R.id.tv_profile_user_name)
    TextView mTvProfileUserName;
    @BindView(R.id.tv_profile_user_email)
    TextView mTvProfileUserEmail;
    @BindView(R.id.tv_profile_book_book)
    TextView mTvProfileBookBook;
    @BindView(R.id.tv_profile_book_unread)
    TextView mTvProfileBookUnread;
    @BindView(R.id.tv_profile_book_read)
    TextView mTvProfileBookRead;
    @BindView(R.id.tv_profile_book_lent)
    TextView mTvProfileBookLent;
    @BindView(R.id.fab_profile)
    FloatingActionButton mFabProfile;

    private Toolbar mToolbar;
    private ProfileContract.Presenter mPresenter;
    private ProfileAdapter mProfileAdapter;
    private ArrayList<User> mFriends = new ArrayList<>();
    private ImageManager mImageManager;
    private int[] mBookStatusInfo;
    private ProgressBarDialog mProgressBarDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);


        Log.d(Constants.TAG_PROFILE_ACTIVITY, "onCreate: ");

        if (UserManager.getInstance().getUserName() == null) {
            UserManager.getInstance().getUserInfo(new GetUserInfoCallback() {
                @Override
                public void onCompleted(User user) {
                    Log.d(Constants.TAG_PROFILE_ACTIVITY, "getUserInfo onCompleted: ");
                    UserManager.getInstance().storeUserData(user);
                    showUserInfoLog();
                    init();
                }

                @Override
                public void onError(String error) {
                }
            });
        } else {
            init();
        }
    }

    private void init() {
        setToolbar();
        setRecyclerView();

        String url = "http://img0.pconline.com.cn/pconline/1308/28/3446062_13451641160b60-4w4921.jpg";
        mImageManager = new ImageManager(this);
        mImageManager.loadCircleImage(url, mIvProfileUserimage);

        Bundle bundle = this.getIntent().getExtras();
        mBookStatusInfo = bundle.getIntArray(Constants.BOOKSTATUS);

        mToolbar.setTitle(UserManager.getInstance().getUserName());
//        mTvProfileUserName.setText(UserManager.getInstance().getUserName());
        mTvProfileUserEmail.setText(UserManager.getInstance().getUserEmail());
        mTvProfileBookBook.setText(String.valueOf(mBookStatusInfo[Constants.MY_BOOK_UNREAD] + mBookStatusInfo[Constants.MY_BOOK_READ]));
        mTvProfileBookUnread.setText(String.valueOf(mBookStatusInfo[Constants.MY_BOOK_UNREAD]));
        mTvProfileBookRead.setText(String.valueOf(mBookStatusInfo[Constants.MY_BOOK_READ] + mBookStatusInfo[Constants.READ]));
        mTvProfileBookLent.setText(String.valueOf(mBookStatusInfo[Constants.MY_BOOK_LENT]));
    }

    private void setToolbar() {
        // Set the padding to match the Status Bar height
        mToolbar = (Toolbar) findViewById(R.id.toolbar_profile);
        mToolbar.setPadding(0, getStatusBarHeight(), 0, 0);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.White));

        setSupportActionBar(mToolbar);
    }

    /**
     * @return height of status bar
     */
    private int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources()
                .getIdentifier("status_bar_height", "dimen", "android");

        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
            Log.d(Constants.TAG_PROFILE_ACTIVITY, "getStatusBarHeight: " + result);

        }
        return result;
    }

    private void setRecyclerView() {
        mProfileAdapter = new ProfileAdapter(mFriends);
        mRvProfile.setLayoutManager(new LinearLayoutManager(this));
        mRvProfile.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                int space = ShareBook.getAppContext().getResources().getDimensionPixelSize(R.dimen.space_item);
                outRect.top = space;
            }
        });
        mRvProfile.setAdapter(mProfileAdapter);

    }

    @OnClick(R.id.iv_profile_change_image)
    public void onViewClicked() {

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){

            Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();

        } else {
            //讀取圖片
            Intent intent = new Intent();
            //開啟Pictures畫面Type設定為image
            intent.setType("image/*");
            //使用Intent.ACTION_GET_CONTENT這個Action
            intent.setAction(Intent.ACTION_GET_CONTENT);
            //取得照片後返回此畫面
            startActivityForResult(intent, 1);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.manu_profile, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {



        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 0:  //取得圖片後進行裁剪
                    Uri uri = data.getData();
                    if (uri != null) {
                        doCropPhoto(uri);
                    }
                    break;
                case 1:  //裁剪完的圖片更新到ImageView
                    //先釋放ImageView上的圖片
                    if (mIvProfileUserimage.getDrawable() != null) {
                        mIvProfileUserimage.setImageBitmap(null);
                        System.gc();
                    }
                    //更新ImageView
                    Bitmap bitmap = data.getParcelableExtra("data");
                    mIvProfileUserimage.setImageBitmap(bitmap);
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    protected void doCropPhoto(Uri uri) {
        //進行照片裁剪
        Intent intent = getCropImageIntent(uri);
        startActivityForResult(intent, 1);
    }

    //裁剪圖片的Intent設定
    public static Intent getCropImageIntent(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");// crop=true 有這句才能叫出裁剪頁面.
        intent.putExtra("scale", true); //讓裁剪框支援縮放
        intent.putExtra("aspectX", 1);// 这兩項為裁剪框的比例.
        intent.putExtra("aspectY", 1);// x:y=1:1
        intent.putExtra("outputX", 500);//回傳照片比例X
        intent.putExtra("outputY", 500);//回傳照片比例Y
        intent.putExtra("return-data", true);
        return intent;
    }

    @Override
    protected void onDestroy() {
        try {
            //釋放內存
            super.onDestroy();
            //釋放整個介面與圖片
            mIvProfileUserimage.setImageBitmap(null);
            mIvProfileUserimage = null;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("text", "New_DISS_Main.onDestroy()崩潰=" + e.toString());
        }
    }

    @Override
    public void setPresenter(ProfileContract.Presenter presenter) {
        mPresenter = presenter;
    }

}
