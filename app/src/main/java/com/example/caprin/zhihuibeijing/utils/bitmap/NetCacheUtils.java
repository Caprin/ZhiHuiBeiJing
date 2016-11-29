package com.example.caprin.zhihuibeijing.utils.bitmap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by w4785 on 2016/11/26.
 */
public class NetCacheUtils {
    LocalCacheUtils mLocalCacheUtils;
    MemoryCacheUtils mMemoryUtils;

    public NetCacheUtils(LocalCacheUtils localCacheUtils, MemoryCacheUtils memoryCacheUtils) {
        mLocalCacheUtils = localCacheUtils;
        mMemoryUtils = memoryCacheUtils;
    }

    private static final String TAG = "NetCacheUtils";

    public void getBitmapFromNet(ImageView ivPic, String url) {
        new BitmapTask().execute(ivPic, url);

    }

    /**
     * Handler和线程池的封装
     */
    class BitmapTask extends AsyncTask<Object, Void, Bitmap> {

        private ImageView ivPic;
        private String url;

        /**
         * 后台耗时方法在此执行，子线程
         *
         * @param params
         * @return
         */
        @Override
        protected Bitmap doInBackground(Object[] params) {
            ivPic = (ImageView) params[0];
            url = (String) params[1];

            ivPic.setTag(url);
            Bitmap bitmmap = downloadBitmap(url);
            return bitmmap;
        }

        /**
         * 更新进度，主线程
         *
         * @param values
         */
        @Override
        protected void onProgressUpdate(Void[] values) {
            super.onProgressUpdate(values);
        }

        /**
         * 耗时任务结束后执行，主线程
         *
         * @param result
         */
        @Override
        protected void onPostExecute(Bitmap result) {
            if (result == null) {
                String bindUrl = (String) ivPic.getTag();

                if (url.equals(bindUrl)) {
                    ivPic.setImageBitmap(result);
                    Log.d(TAG, "成功读取图片");
                }
            }
        }
    }

    private Bitmap downloadBitmap(String url) {
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) new URL(url).openConnection();

            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setRequestMethod("GET");

            conn.connect();

            int responCode = conn.getResponseCode();
            if (responCode == 200) {
                InputStream inputStream = conn.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                return bitmap;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return null;
    }

}
