package com.wenliu.bookshare.friendprofile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wenliu.bookshare.Constants;
import com.wenliu.bookshare.ImageManager;
import com.wenliu.bookshare.R;
import com.wenliu.bookshare.api.FirebaseApiHelper;
import com.wenliu.bookshare.base.BaseActivity;
import com.wenliu.bookshare.object.BookCustomInfo;
import com.wenliu.bookshare.object.User;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FriendProfileActivity extends BaseActivity implements FriendProfileContract.View, View.OnClickListener {

    //region "BindView"
    @BindView(R.id.Recycview_friend_profile)
    RecyclerView mRecycviewFriendProfile;
    @BindView(R.id.tv_friend_profile_name)
    TextView mTvFriendProfileName;
    @BindView(R.id.tv_friend_profile_email)
    TextView mTvFriendProfileEmail;
    @BindView(R.id.iv_friend_profile_userimage)
    ImageView mIvFriendProfileUserimage;
    @BindView(R.id.toolbar_friend_profile)
    Toolbar mToolbarFriendProfile;
    @BindView(R.id.appbarlayout_friend_profile)
    AppBarLayout mAppbarlayoutFriendProfile;

    //endregion

    private User mUser;
    private FriendProfileAdapter mFriendProfileAdapter;
    private ImageManager mImageManager = new ImageManager(this);
    private FriendProfileContract.Presenter mPresenter;
    private ArrayList<BookCustomInfo> mBookCustomInfos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_profile);
        ButterKnife.bind(this);

        Log.d(Constants.TAG_FRIEND_PROFILE_ACTIVITY, "onCreate: ");
        initView();
        setToolbar();
        setRecyclerView();

    }

    private void initView() {
        mPresenter = new FriendProfilePresenter(this);
        Bundle bundle = this.getIntent().getExtras();
        mUser = (User) bundle.getSerializable(Constants.USER_DATA);

        if (mUser.getImage() != null) {
            mImageManager.loadCircleImage(mUser.getImage(), mIvFriendProfileUserimage);
        }
        if (mUser.getName() != null) {
            mTvFriendProfileName.setText(mUser.getName());
        }
        mTvFriendProfileEmail.setText(mUser.getEmail());

        mPresenter.getFriendBooks(mUser.getId());
    }

    private void setToolbar() {
        mAppbarlayoutFriendProfile = (AppBarLayout) findViewById(R.id.appbarlayout_friend_profile);
        // Set the padding to match the Status Bar height
        mToolbarFriendProfile = (Toolbar) findViewById(R.id.toolbar_friend_profile);
        mToolbarFriendProfile.setPadding(0, getStatusBarHeight(), 0, 0);
        setSupportActionBar(mToolbarFriendProfile);
        mToolbarFriendProfile.setNavigationOnClickListener(this);
    }

    private void setRecyclerView() {
        mFriendProfileAdapter = new FriendProfileAdapter(mBookCustomInfos, mPresenter);
        mRecycviewFriendProfile.setLayoutManager(new LinearLayoutManager(this));
        mRecycviewFriendProfile.setAdapter(mFriendProfileAdapter);
    }

    @Override
    public void setPresenter(FriendProfileContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showFriendBooks(ArrayList<BookCustomInfo> bookCustomInfos) {
        Log.d(Constants.TAG_FRIEND_PROFILE_ACTIVITY, "showFriendBooks: ");
        mFriendProfileAdapter.updateData(bookCustomInfos);
    }

    @Override
    public void showConfirmDialog(final BookCustomInfo bookCustomInfo) {
        Log.d(Constants.TAG_FRIEND_PROFILE_ACTIVITY, "showConfirmDialog: ");

        new AlertDialog.Builder(this)
                .setMessage("確定要向 " + mUser.getName() + " 借 「" + bookCustomInfo.getTitle() + "」 這本書嗎？")
                .setCancelable(false)
                .setPositiveButton(getString(R.string.alert_dialog_delete_positive), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPresenter.sendBorrowRequest(mUser, bookCustomInfo);
                    }
                })
                .setNegativeButton(getString(R.string.alert_dialog_delete_negative), null)
                .create()
                .show();
    }

    @Override
    public void onClick(View v) {
        onBackPressed();
    }
}
