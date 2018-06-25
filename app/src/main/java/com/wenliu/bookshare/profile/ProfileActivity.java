package com.wenliu.bookshare.profile;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.wenliu.bookshare.Constants;
import com.wenliu.bookshare.ImageManager;
import com.wenliu.bookshare.R;
import com.wenliu.bookshare.UserManager;
import com.wenliu.bookshare.api.callbacks.GetUserInfoCallback;
import com.wenliu.bookshare.base.BaseActivity;
import com.wenliu.bookshare.friend.FriendFragment;
import com.wenliu.bookshare.friend.FriendPresenter;
import com.wenliu.bookshare.friendprofile.FriendProfileActivity;
import com.wenliu.bookshare.lent.LentFragment;
import com.wenliu.bookshare.lent.LentPresenter;
import com.wenliu.bookshare.object.User;

import java.util.ArrayList;
import java.util.List;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class ProfileActivity extends BaseActivity implements ProfileContract.View {

    //region "BindView"
    @BindView(R.id.appbar_layout_profile)
    AppBarLayout mAppbarLayoutProfile;
    @BindView(R.id.collape_toolbar_profile)
    CollapsingToolbarLayout mCollapeToolbarProfile;
    @BindView(R.id.toolbar_profile)
    Toolbar mToolbarProfile;
    @BindView(R.id.iv_profile_user_image)
    ImageView mIvProfileUserImage;
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
    @BindView(R.id.tabs_profile)
    TabLayout mTabsProfile;
    @BindView(R.id.viewpager_profile)
    ViewPager mViewpagerProfile;

    //endregion

    private ProfileContract.Presenter mPresenter;
    private ImageManager mImageManager;
    private int[] mBookStatusInfo;
    private boolean isAddDialogShow = false;

    private ProfileViewPagerAdapter mProfileViewPagerAdapter;
    private FriendFragment mFriendFragment;
    private FriendPresenter mFriendPresenter;
    private LentFragment mLentFragment;
    private LentPresenter mLentPresenter;
    private List<Fragment> mFragments;
    private List<String> mTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        Log.d(Constants.TAG_PROFILE_ACTIVITY, "onCreate: ");

        UserManager.getInstance().getUserInfo(new GetUserInfoCallback() {
            @Override
            public void onCompleted(User user) {
                Log.d(Constants.TAG_PROFILE_ACTIVITY, "getUserInfo onCompleted: ");
                UserManager.getInstance().storeUserData(user);
                showUserInfoLog();
                initView();
            }

            @Override
            public void onError(String error) {
                Log.d(Constants.TAG_PROFILE_ACTIVITY, "getUserInfo onError: ");
            }
        });
    }

    private void initView() {
        mPresenter = new ProfilePresenter(this);
        mPresenter.start();

        Bundle bundle = this.getIntent().getExtras();
        mBookStatusInfo = bundle.getIntArray(Constants.BUNDLE_BOOK_STATUS);

        setToolbar();
        setProfileView();
        setViewPager();
    }

    private void setToolbar() {
        // Set the padding to match the Status Bar height
        mToolbarProfile.setPadding(0, getStatusBarHeight(), 0, 0);
        setSupportActionBar(mToolbarProfile);
        mCollapeToolbarProfile.setTitle(UserManager.getInstance().getUserName());
    }

    private void setViewPager() {

        if (mFriendFragment == null) mFriendFragment = FriendFragment.newInstance();
        if (mFriendPresenter == null) {
            mFriendPresenter = new FriendPresenter(mFriendFragment, this);
        }

        if (mLentFragment == null) mLentFragment = LentFragment.newInstance();
        if (mLentPresenter == null) {
            mLentPresenter = new LentPresenter(mLentFragment);
        }

        mFragments = new ArrayList<>();
        mFragments.add(mFriendFragment);
        mFragments.add(mLentFragment);

        mTitles = new ArrayList<>();
        mTitles.add(getString(R.string.profile_tab_layout_friend));
        mTitles.add(getString(R.string.profile_tab_layout_lent));

        mProfileViewPagerAdapter = new ProfileViewPagerAdapter(getSupportFragmentManager(), mPresenter, mFragments, mTitles);
        mViewpagerProfile.setAdapter(mProfileViewPagerAdapter);
        mTabsProfile.setupWithViewPager(mViewpagerProfile);
    }

    private void setProfileView() {

        mImageManager = new ImageManager(this);
        if (UserManager.getInstance().getUserImage() != null) {
            mImageManager.loadCircleImage(UserManager.getInstance().getUserImage(), mIvProfileUserImage);
        }
        mTvProfileUserName.setText(UserManager.getInstance().getUserName());
        mTvProfileUserEmail.setText(UserManager.getInstance().getUserEmail());
        mTvProfileBookBook.setText(String.valueOf(mBookStatusInfo[Constants.MY_BOOK]));
        mTvProfileBookUnread.setText(String.valueOf(mBookStatusInfo[Constants.UNREAD]));
        mTvProfileBookRead.setText(String.valueOf(mBookStatusInfo[Constants.READ]));
        mTvProfileBookLent.setText(String.valueOf(mBookStatusInfo[Constants.LENT]));
//        getSupportActionBar().setTitle(UserManager.getInstance().getUserName());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(Constants.TAG_PROFILE_ACTIVITY, "onActivityResult: ");

        if (resultCode == RESULT_OK) {
            mPresenter.result(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onDestroy() {
        Log.d(Constants.TAG_PROFILE_ACTIVITY, "onDestroy: ");
        try {
            //釋放內存
            super.onDestroy();
            //釋放整個介面與圖片
            mIvProfileUserImage.setImageBitmap(null);
            mIvProfileUserImage = null;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("text", "New_DISS_Main.onDestroy()崩潰=" + e.toString());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.manu_profile, menu);
        return true;
    }

    @OnClick({R.id.iv_profile_user_image, R.id.iv_profile_change_image, R.id.fab_profile})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_profile_user_image:
            case R.id.iv_profile_change_image:
                ProfileActivityPermissionsDispatcher.getPhotoFromGalleryWithPermissionCheck(this);
                break;

            case R.id.fab_profile:
                if (!isAddDialogShow) {
                    isAddDialogShow(true);
                    showAddFriendDialog(false);
                }
                break;

            default:
                break;
        }
    }

    //region Profile Contract
    @Override
    public void setPresenter(ProfileContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showImageOnView(Bitmap bitmap) {
        Log.d(Constants.TAG_PROFILE_ACTIVITY, "showImageOnView: ");

        mImageManager.loadCircleImage(bitmap, mIvProfileUserImage);
    }

    @Override
    public void showAddFriendDialog(boolean showAlert) {

        final View addFriendView = View.inflate(this, R.layout.dialog_add_friend, null);
        final EditText mEtInputEmail = ((EditText) addFriendView.findViewById(R.id.et_dialog_add_friend_email));
        ((TextView) addFriendView.findViewById(R.id.tv_add_friend_alert)).setVisibility(showAlert ? View.VISIBLE : View.GONE);

        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.alert_dialog_add_friend))
                .setCancelable(false)
                .setView(addFriendView)
                .setPositiveButton(getString(R.string.alert_dialog_delete_positive), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String email = mEtInputEmail.getText().toString();

                        if (email != null && email.length() >= 5) {
                            mFriendPresenter.checkUserByEmail(email);
                        } else {
                            showAddFriendDialog(true);
                        }

                        Log.d(Constants.TAG_PROFILE_ACTIVITY, "onClick: " + email);
                    }
                })
                .setNegativeButton(getString(R.string.alert_dialog_delete_negative), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        isAddDialogShow(false);
                    }
                })
                .create()
                .show();
    }

    @Override
    public void isAddDialogShow(boolean isShow) {
        isAddDialogShow = isShow;
        showKeybroad(false);
    }

    @Override
    public void setUserImage(Uri userImageUri) {
        mImageManager.loadCircleImage(userImageUri, mIvProfileUserImage);
    }

    @Override
    public void showFriendProfileActivity(User friend) {
        Intent intent = new Intent(this, FriendProfileActivity.class);

        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.USER_DATA, friend);

        intent.putExtras(bundle);
        startActivity(intent);
    }
    //endregion

    private void showKeybroad(boolean show) {
        Log.d(Constants.TAG_PROFILE_ACTIVITY, "showKeybroad: ");
        InputMethodManager mInputMethodManager = (InputMethodManager) this.getSystemService(this.INPUT_METHOD_SERVICE);

        if (show) {
            mInputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        } else {
            mInputMethodManager.toggleSoftInput(InputMethodManager.HIDE_NOT_ALWAYS, 0);
        }
    }

    @NeedsPermission({Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void getPhotoFromGallery() {
        Log.d(Constants.TAG_PROFILE_ACTIVITY, "getPhotoFromGallery: ");

        mPresenter.getPhotoFromGallery();
    }

    //region Permission
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        ProfileActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnShowRationale({Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void showRationale(final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setMessage(getString(R.string.permission_request))
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.proceed();
                    }
                })
//                .setNegativeButton("deny", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        request.cancel();
//                    }
//                })
                .create()
                .show();
    }

    // Annotate a method which is invoked if the user doesn't grant the permissions
    @OnPermissionDenied({Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void showDenied() {
        Toast.makeText(this, "denied", Toast.LENGTH_SHORT).show();
    }

    // Annotates a method which is invoked if the user
    // chose to have the device "never ask again" about a permission
    @OnNeverAskAgain({Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void showNeverAsk() {
        Toast.makeText(this, "never ask", Toast.LENGTH_SHORT).show();
    }
    //endregion
}
