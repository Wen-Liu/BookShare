package com.wenliu.bookshare.profile;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.wenliu.bookshare.Constants;
import com.wenliu.bookshare.ImageManager;
import com.wenliu.bookshare.R;
import com.wenliu.bookshare.UserManager;
import com.wenliu.bookshare.api.callbacks.GetUserInfoCallback;
import com.wenliu.bookshare.base.BaseActivity;
import com.wenliu.bookshare.object.User;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class ProfileActivity extends BaseActivity implements ProfileContract.View, View.OnClickListener {

    //region "BindView"
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
    @BindView(R.id.ll_profile_no_data)
    LinearLayout mLlProfileNoData;
    //endregion

    private ProfileContract.Presenter mPresenter;
    private Toolbar mToolbar;
    private ProfileAdapter mProfileAdapter;
    private ImageManager mImageManager;
    private ArrayList<User> mFriends = new ArrayList<>();
    private int[] mBookStatusInfo;
    private Uri mImageUri;
    private Uri mNewPhotoUri;
    private String mCurrentPhotoPath;
    private MaterialDialog mMaterialDialog;
    private boolean isAddDialogShow = false;


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
                init();
            }

            @Override
            public void onError(String error) {
            }
        });

    }

    private void init() {
        mPresenter = new ProfilePresenter(this);
        mPresenter.start();

        getMyFriends();
        setToolbar();
        setRecyclerView();

        Bundle bundle = this.getIntent().getExtras();
        mBookStatusInfo = bundle.getIntArray(Constants.BOOKSTATUS);
        mImageManager = new ImageManager(this);

        if (UserManager.getInstance().getUserImage() != null) {
            mImageManager.loadCircleImage(UserManager.getInstance().getUserImage(), mIvProfileUserimage);
        }
//        mTvProfileUserName.setText(UserManager.getInstance().getUserName());
        mTvProfileUserEmail.setText(UserManager.getInstance().getUserEmail());
        mTvProfileBookBook.setText(String.valueOf(mBookStatusInfo[Constants.MY_BOOK]));
        mTvProfileBookUnread.setText(String.valueOf(mBookStatusInfo[Constants.UNREAD]));
        mTvProfileBookRead.setText(String.valueOf(mBookStatusInfo[Constants.READ]));
        mTvProfileBookLent.setText(String.valueOf(mBookStatusInfo[Constants.LENT]));
        getSupportActionBar().setTitle(UserManager.getInstance().getUserName());

    }

    private void setToolbar() {
        // Set the padding to match the Status Bar height
        mToolbar = (Toolbar) findViewById(R.id.toolbar_profile);
        mToolbar.setPadding(0, getStatusBarHeight(), 0, 0);
        setSupportActionBar(mToolbar);
        mCollapeToolbarProfile.setTitle(UserManager.getInstance().getUserName());
        mToolbar.setNavigationOnClickListener(this);
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
        mProfileAdapter = new ProfileAdapter(this, mFriends);
        mRvProfile.setLayoutManager(new LinearLayoutManager(this));
//        mRvProfile.addItemDecoration(new RecyclerView.ItemDecoration() {
//            @Override
//            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//                super.getItemOffsets(outRect, view, parent, state);
//                int space = ShareBook.getAppContext().getResources().getDimensionPixelSize(R.dimen.gap_recycler_item);
//                outRect.top = space;
//            }
//        });
        mRvProfile.setAdapter(mProfileAdapter);
    }

    @OnClick({R.id.iv_profile_userimage, R.id.iv_profile_change_image, R.id.fab_profile})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_profile_userimage:
            case R.id.iv_profile_change_image:
                ProfileActivityPermissionsDispatcher.getPhotoFromGalleryWithPermissionCheck(this);
                break;
            case R.id.fab_profile:
                if (!isAddDialogShow) {
                    isAddDialogShow(true);
                    showAddFriendDialog(false);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case Constants.GET_PHOTO_FROM_GALLERY:  //取得圖片後進行裁剪
                if (resultCode == RESULT_OK) {
                    String path = getRealPathFromURI(data.getData());
                    File imageFile = new File(path);
                    Log.d(Constants.TAG_PROFILE_ACTIVITY, "onActivityResult: GET_PHOTO_FROM_GALLERY: ");
                    doCropPhoto(mImageUri, FileProvider.getUriForFile(this, "com.wenliu.bookshare.fileprovider", imageFile));
                }
                break;

            case Constants.GET_PHOTO_CROP:  //裁剪完的圖片更新到ImageView
                Log.d(Constants.TAG_PROFILE_ACTIVITY, "onActivityResult: GET_PHOTO_CROP ");

                if (resultCode == RESULT_OK) {
                    mImageManager.loadCircleImageUri(mImageUri, mIvProfileUserimage);
                    mPresenter.uploadProfileImage(mImageUri);
                }

                break;
        }


    }

    protected void doCropPhoto(Uri uri, Uri galleryUri) {
        Log.d(Constants.TAG_PROFILE_ACTIVITY, "doCropPhoto: ");

        try {
            Intent intent = new Intent("com.android.camera.action.CROP");
            intent.setDataAndType(galleryUri, "image/*");
            intent.putExtra("crop", "true");// crop=true 有這句才能叫出裁剪頁面.
            intent.putExtra("scale", true); //讓裁剪框支援縮放
            intent.putExtra("aspectX", 1);// 这兩項為裁剪框的比例.
            intent.putExtra("aspectY", 1);// x:y=1:1
            intent.putExtra("outputX", 200);//回傳照片比例X
            intent.putExtra("outputY", 200);//回傳照片比例Y
            intent.putExtra("return-data", false);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);

            List<ResolveInfo> resInfoList = this.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
            for (ResolveInfo resolveInfo : resInfoList) {
                String packageName = resolveInfo.activityInfo.packageName;
                this.grantUriPermission(packageName, uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            }

            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION); //For android 8.0 and after
            startActivityForResult(intent, Constants.GET_PHOTO_CROP);

        } catch (ActivityNotFoundException e) {
            Log.d(Constants.TAG_PROFILE_ACTIVITY, "doCropPhoto: ActivityNotFoundException ");
            Toast.makeText(this, "This device doesn't support the crop action!", Toast.LENGTH_SHORT).show();
        }
    }


    //裁剪圖片的Intent設定
    public Intent getCropImageIntent(Uri uri) {
        Log.d(Constants.TAG_PROFILE_ACTIVITY, "getCropImageIntent: ");

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra("crop", "true");// crop=true 有這句才能叫出裁剪頁面.
        intent.putExtra("scale", true); //讓裁剪框支援縮放
        intent.putExtra("aspectX", 1);// 这兩項為裁剪框的比例.
        intent.putExtra("aspectY", 1);// x:y=1:1
        intent.putExtra("outputX", 200);//回傳照片比例X
        intent.putExtra("outputY", 200);//回傳照片比例Y
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        return intent;
    }

    public String getRealPathFromURI(Uri uri) {
        Log.d(Constants.TAG_PROFILE_ACTIVITY, "getRealPathFromURI: ");

        final String docId = DocumentsContract.getDocumentId(uri);
        final String[] split = docId.split(":");
        final String type = split[0];

        Uri contentUri = null;
        if ("image".equals(type)) {
            contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        } else if ("video".equals(type)) {
            contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        } else if ("audio".equals(type)) {
            contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        }

        final String selection = "_id=?";
        final String[] selectionArgs = new String[]{
                split[1]
        };

        return getDataColumn(this, contentUri, selection, selectionArgs);
    }

    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {
        Log.d(Constants.TAG_PROFILE_ACTIVITY, "getDataColumn: ");

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
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
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.manu_profile, menu);
        return true;
    }

    @Override
    public void setPresenter(ProfileContract.Presenter presenter) {
        mPresenter = presenter;
    }

    private void getMyFriends() {
        mPresenter.getMyFriends();
    }

    @Override
    public void showImageOnView(Bitmap bitmap) {
        Log.d(Constants.TAG_PROFILE_ACTIVITY, "showImageOnView: ");

        mImageManager.loadCircleImageBitmap(bitmap, mIvProfileUserimage);
//        mIvProfileUserimage.setImageBitmap(bitmap);
    }

    @Override
    public void showProgressDialog(boolean show) {
        if (show) {
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

    @Override
    public void showAddFriendDialog(boolean showAlert) {

        final View addFriendView = View.inflate(this, R.layout.dialog_add_friend, null);
        final EditText mEtInputEmail = ((EditText) addFriendView.findViewById(R.id.et_dialog_add_friend_email));
        ((TextView) addFriendView.findViewById(R.id.tv_add_friend_alert)).setVisibility(showAlert ? View.VISIBLE : View.GONE);
//        showKeybroad(true);


        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.alert_dialog_add_friend))
                .setCancelable(false)
                .setView(addFriendView)
                .setPositiveButton(getString(R.string.alert_dialog_delete_positive), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String email = mEtInputEmail.getText().toString();
                        mPresenter.checkUserByEmail(email);
                        Log.d(Constants.TAG_PROFILE_ACTIVITY, "onClick: " + email);
                    }
                })
                .setNegativeButton(getString(R.string.alert_dialog_delete_negative), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        showKeybroad(false);
                        isAddDialogShow(false);
                    }
                })
                .create()
                .show();


    }

    @Override
    public void showFriend(ArrayList<User> friends) {
        mProfileAdapter.updateData(friends);
    }

    @Override
    public void isNoFriendData(boolean isNoFriendData) {
        Log.d(Constants.TAG_PROFILE_ACTIVITY, "isNoFriendData: ");
        mLlProfileNoData.setVisibility(isNoFriendData ? View.VISIBLE : View.GONE);
        mRvProfile.setVisibility(isNoFriendData ? View.GONE : View.VISIBLE);
    }


    private void showKeybroad(boolean show) {
        Log.d(Constants.TAG_PROFILE_ACTIVITY, "showKeybroad: ");
        InputMethodManager mInputMethodManager = (InputMethodManager) this.getSystemService(this.INPUT_METHOD_SERVICE);

        if (show) {
            mInputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        } else {
            mInputMethodManager.toggleSoftInput(InputMethodManager.HIDE_NOT_ALWAYS, 0);
        }
    }

    @Override
    public void isAddDialogShow(boolean isShow) {
        isAddDialogShow = isShow;
        showKeybroad(false);
    }

    @NeedsPermission({Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void getPhotoFromGallery() {
        Log.d(Constants.TAG_PROFILE_ACTIVITY, "getPhotoFromGallery: ");

        //讀取圖片，使用 Intent.ACTION_GET_CONTENT 這個 Action
        Intent intentPhotoGallery = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        //開啟 Pictures 畫面 Type 設定為 image
        intentPhotoGallery.setType("image/*");

        if (intentPhotoGallery.resolveActivity(this.getPackageManager()) != null) {
            File imageFile = null;
            try {
                imageFile = createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (imageFile != null) {
                mImageUri = FileProvider.getUriForFile(this, "com.wenliu.bookshare.fileprovider", imageFile); //mImageCameraTempUri
                intentPhotoGallery.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
                startActivityForResult(intentPhotoGallery, Constants.GET_PHOTO_FROM_GALLERY);
            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */);
        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        Log.d(Constants.TAG_PROFILE_ACTIVITY, "mCurrentPhotoPath: " + mCurrentPhotoPath);
        return image;
    }

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

    @Override
    public void onClick(View v) {
        onBackPressed();
    }
}
