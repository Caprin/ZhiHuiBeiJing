package com.example.caprin.zhihuibeijing.utils.bitmap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.example.caprin.zhihuibeijing.utils.MD5Encoder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.Format;

/**
 * Created by w4785 on 2016/11/27.
 */
public class LocalCacheUtils {

    public static final String CACHE_PATH = Environment.getExternalStorageDirectory().
            getAbsolutePath() + "/zhbj_cache";

    /**
     * 本地缓存
     *
     * @param url
     */
    public Bitmap getBitmapFormatLocal(String url) {
        try {
            String fileName = MD5Encoder.encode(url);
            File file = new File(CACHE_PATH, fileName);

            if (file.exists()) {
                Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
                return bitmap;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 向sdcard写图片
     */
    public void setBitmapToLocal(String url, Bitmap bitmap) {
        try {
            String fileName = MD5Encoder.encode(url);

            File file = new File(CACHE_PATH, fileName);

            File parentFile = file.getParentFile();
            if (!parentFile.exists()) {
                parentFile.mkdir();
            }
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(file));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
