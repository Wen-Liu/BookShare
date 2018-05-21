package com.wenliu.bookshare.profile;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wenliu.bookshare.Constants;
import com.wenliu.bookshare.ImageManager;
import com.wenliu.bookshare.R;
import com.wenliu.bookshare.ShareBook;
import com.wenliu.bookshare.UserManager;
import com.wenliu.bookshare.api.callbacks.GetUserInfoCallback;
import com.wenliu.bookshare.base.BaseActivity;
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

    private Toolbar mToolbar;
    private ProfileContract.Presenter mPresenter;
    private ProfileAdapter mProfileAdapter;
    private ArrayList<User> mFriends = new ArrayList<>();
    private ImageManager mImageManager;


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

        mTvProfileUserName.setText(UserManager.getInstance().getUserName());
        mTvProfileUserEmail.setText(UserManager.getInstance().getUserEmail());

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
    }


    private void setToolbar() {
        // Set the padding to match the Status Bar height
        mToolbar = (Toolbar) findViewById(R.id.toolbar_profile);
        mToolbar.setPadding(0, getStatusBarHeight(), 0, 0);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.manu_profile, menu);
        return true;
    }

    @Override
    public void setPresenter(ProfileContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
