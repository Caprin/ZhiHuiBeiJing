package com.example.caprin.zhihuibeijing.utils.bitmap;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.example.caprin.zhihuibeijing.R;

/**
 * Created by w4785 on 2016/11/26.
 */
public class MyBitmapUtils {

    LocalCacheUtils mLocalCacheUtils;
    NetCacheUtils mNetCacheUtils;
    MemoryCacheUtils mMemoryCacheUtils;

    public MyBitmapUtils() {
        mNetCacheUtils = new NetCacheUtils(mLocalCacheUtils, mMemoryCacheUtils);
        mLocalCacheUtils = new LocalCacheUtils();
        mMemoryCacheUtils = new MemoryCacheUtils();
    }

    public void display(ImageView ivPic, String listImage) {
//        ivPic.setImageBitmap(R.drawable.);

        //从本地读

        //从内存读

        //从网络读
        mNetCacheUtils.getBitmapFromNet(ivPic, listImage);
    }

}
