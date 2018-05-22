package com.wenliu.bookshare;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.security.MessageDigest;

/**
 * Created by wen on 2018/5/18.
 */

public class ImageManager {

    private Context mContext;
    public static final String ANDROID_RESOURCE = "android.resource://";
    public static final String FOREWARD_SLASH = "/";

    public ImageManager(Context context) {
        this.mContext = context;
    }


    /**
     * 加載網絡圖片
     *
     * @param url
     * @param imageView
     */
    public void loadUrlImage(String url, ImageView imageView) {
        Glide.with(mContext)
                .load(url)
                .into(imageView);
    }

    /**
     * 加載網絡圓型圖片
     * https://blog.csdn.net/zhangyiminsunshine/article/details/78051435
     *
     * @param url
     * @param imageView
     */
    public void loadCircleImage(String url, ImageView imageView) {
        Glide.with(mContext)
                .load(url)
                .apply(RequestOptions
                        .circleCropTransform()
                        .placeholder(R.drawable.all_placeholder_avatar)
                        .error(R.drawable.all_placeholder_avatar)
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(imageView);
    }

    /**
     * 加載 bitmap 圓型圖片
     * https://blog.csdn.net/zhangyiminsunshine/article/details/78051435
     *
     * @param bitmap
     * @param imageView
     */
    public void loadCircleImageBitmap(Bitmap bitmap, ImageView imageView) {
        Glide.with(mContext)
                .load(bitmap)
                .apply(RequestOptions
                        .circleCropTransform()
                        .placeholder(R.drawable.all_placeholder_avatar)
                        .error(R.drawable.all_placeholder_avatar)
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(imageView);
    }


    /**
     * 加載 uri 圓型圖片
     * https://blog.csdn.net/zhangyiminsunshine/article/details/78051435
     *
     * @param uri
     * @param imageView
     */
    public void loadCircleImageUri(Uri uri, ImageView imageView) {
        Glide.with(mContext)
                .load(uri)
                .apply(RequestOptions
                        .circleCropTransform()
                        .placeholder(R.drawable.all_placeholder_avatar)
                        .error(R.drawable.all_placeholder_avatar)
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(imageView);
    }


    /**
     * 加載 drawable 圓型圖片
     * https://blog.csdn.net/zhangyiminsunshine/article/details/78051435
     *
     * @param resourceId
     * @param imageView
     */
    public void loadCircleImage(int resourceId, ImageView imageView) {
        Glide.with(mContext)
                .load(resourceId)
                .apply(RequestOptions
                        .circleCropTransform())
                .into(imageView);
    }


    public static void loadRoundCircleImage(Context context, String url, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .circleCrop()//设置圆形
                .placeholder(R.color.White)
                .error(R.color.White)
                //.priority(Priority.HIGH)
                .bitmapTransform(new RoundedCorners(45))
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context).load(url).apply(options).into(imageView);

    }

}
