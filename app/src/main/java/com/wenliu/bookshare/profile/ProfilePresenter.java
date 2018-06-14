package com.wenliu.bookshare.profile;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.wenliu.bookshare.Constants;
import com.wenliu.bookshare.ShareBook;
import com.wenliu.bookshare.api.FirebaseApiHelper;
import com.wenliu.bookshare.friend.FriendFragment;
import com.wenliu.bookshare.friend.FriendPresenter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by wen on 2018/5/17.
 */

public class ProfilePresenter implements ProfileContract.Presenter {

    private FirebaseApiHelper mFirebaseApiHelper = FirebaseApiHelper.getInstance();
    private ProfileContract.View mProfileView;
    private String mCurrentPhotoPath;
    private ProfileActivity mProfileActivity;
    private Uri mImageUri;

    public static final String FRIEND = "FRIEND";

    public ProfilePresenter(ProfileContract.View profileView) {
        Log.d(Constants.TAG_PROFILE_PRESENTER, "ProfilePresenter: ");
        mProfileView = profileView;
        mProfileView.setPresenter(this);
        mProfileActivity = (ProfileActivity) profileView;
    }

    @Override
    public void start() {
    }

    @Override
    public void getPhotoFromGallery() {
        Log.d(Constants.TAG_PROFILE_PRESENTER, "getPhotoFromGallery: ");
        //讀取圖片，使用 Intent.ACTION_GET_CONTENT 這個 Action
        Intent intentPhotoGallery = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        //開啟 Pictures 畫面 Type 設定為 image
        intentPhotoGallery.setType("image/*");

        if (intentPhotoGallery.resolveActivity(mProfileActivity.getPackageManager()) != null) {
            File imageFile = null;
            try {
                imageFile = createImageFile();
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (imageFile != null) {
                mImageUri = FileProvider.getUriForFile(mProfileActivity, "com.wenliu.bookshare.fileprovider", imageFile); //mImageCameraTempUri
                intentPhotoGallery.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
                mProfileActivity.startActivityForResult(intentPhotoGallery, Constants.GET_PHOTO_FROM_GALLERY);
            }
        }
    }

    private File createImageFile() throws Exception {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = mProfileActivity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        File imageFile = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */);
        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = imageFile.getAbsolutePath();
        Log.d(Constants.TAG_PROFILE_PRESENTER, "mCurrentPhotoPath: " + mCurrentPhotoPath);
        return imageFile;
    }


    @Override
    public void result(int requestCode, int resultCode, Intent intent) {
        switch (requestCode) {
            case Constants.GET_PHOTO_FROM_GALLERY:  //取得圖片後進行裁剪
                Log.d(Constants.TAG_PROFILE_PRESENTER, "result: GET_PHOTO_FROM_GALLERY: ");
                String path = getRealPathFromURI(intent.getData());
                File imageFile = new File(path);
                doCropPhoto(mImageUri, FileProvider.getUriForFile(mProfileActivity, "com.wenliu.bookshare.fileprovider", imageFile));
                break;

            case Constants.GET_PHOTO_CROP:  //裁剪完的圖片更新到ImageView
                Log.d(Constants.TAG_PROFILE_PRESENTER, "result: GET_PHOTO_CROP ");

                mProfileView.setUserImage(mImageUri);
                uploadProfileImage(mImageUri);
                break;
        }
    }

    private String getRealPathFromURI(Uri uri) {
        Log.d(Constants.TAG_PROFILE_PRESENTER, "getRealPathFromURI: ");

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
        } else if ("primary".equalsIgnoreCase(type)) {
            return Environment.getExternalStorageDirectory() + "/" + split[1];
        }

        final String selection = "_id=?";
        final String[] selectionArgs = new String[]{
                split[1]
        };

        return getDataColumn(mProfileActivity, contentUri, selection, selectionArgs);
    }

    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {
        Log.d(Constants.TAG_PROFILE_PRESENTER, "getDataColumn: ");

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

    private void doCropPhoto(Uri uri, Uri myGalleryUri) {
        Log.d(Constants.TAG_PROFILE_PRESENTER, "doCropPhoto: ");

        try {
            Intent intent = new Intent("com.android.camera.action.CROP");
            intent.setDataAndType(myGalleryUri, "image/*");
            intent.putExtra("crop", "true");// crop=true 有這句才能叫出裁剪頁面.
            intent.putExtra("scale", true); //讓裁剪框支援縮放
            intent.putExtra("aspectX", 1);// 这兩項為裁剪框的比例.
            intent.putExtra("aspectY", 1);// x:y=1:1
            intent.putExtra("outputX", 200);//回傳照片比例X
            intent.putExtra("outputY", 200);//回傳照片比例Y
            intent.putExtra("return-data", false);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);

            List<ResolveInfo> resInfoList = mProfileActivity.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
            for (ResolveInfo resolveInfo : resInfoList) {
                String packageName = resolveInfo.activityInfo.packageName;
                mProfileActivity.grantUriPermission(packageName, uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            }

            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION); //For android 8.0 and after
            mProfileActivity.startActivityForResult(intent, Constants.GET_PHOTO_CROP);

        } catch (ActivityNotFoundException e) {
            Log.d(Constants.TAG_PROFILE_PRESENTER, "doCropPhoto: ActivityNotFoundException ");
//            Toast.makeText(((ProfileActivity) mProfileView), "This device doesn't support the crop action!", Toast.LENGTH_SHORT).show();
        }
    }

    public Intent getCropImageIntent(Uri uri, Uri myGalleryUri) {
        //裁剪圖片的Intent設定
        Log.d(Constants.TAG_PROFILE_PRESENTER, "getCropImageIntent: ");

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

    @Override
    public void getPhotoUri(Uri uri) {
        Log.d(Constants.TAG_PROFILE_PRESENTER, "getPhotoUri: ");
        InputStream inputStream = null;
        try {
            inputStream = ShareBook.getAppContext().getContentResolver().openInputStream(uri);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        mProfileView.showImageOnView(bitmap);
    }

    public void uploadProfileImage(Uri imageUri) {
        mFirebaseApiHelper.uploadProfileImage(imageUri);
    }
}



