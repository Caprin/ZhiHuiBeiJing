package com.example.caprin.zhihuibeijing.Base.impl;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.caprin.zhihuibeijing.Base.BaseMenuDetailPager;
import com.example.caprin.zhihuibeijing.Base.BasePager;
import com.example.caprin.zhihuibeijing.Base.menudetail.InteractMenuDetailPager;
import com.example.caprin.zhihuibeijing.Base.menudetail.NewsMenuDetailPager;
import com.example.caprin.zhihuibeijing.Base.menudetail.PhotoMenuDetailPager;
import com.example.caprin.zhihuibeijing.Base.menudetail.TopicMenuDetailPager;
import com.example.caprin.zhihuibeijing.Fragment.LeftMenuFragment;
import com.example.caprin.zhihuibeijing.MainActivity;
import com.example.caprin.zhihuibeijing.domain.NewsData;
import com.example.caprin.zhihuibeijing.global.GlobalConstants;
import com.example.caprin.zhihuibeijing.utils.CacheUtils;
import com.example.caprin.zhihuibeijing.utils.PrefUtils;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;

/**
 * Created by WD on 2016/10/17.
 */
public class NewsCenterPager extends BasePager {
    private ArrayList<BaseMenuDetailPager> mPageList;

    private NewsData mNewsData;

    public NewsCenterPager(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {
//        tvTitle.setText("新闻");

        setSlidingMenuEnable(true);
        btnMenu.setVisibility(View.VISIBLE);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleSlidingMenu(true);
            }
        });

        String cache = CacheUtils.getCache(mActivity, GlobalConstants.CATEGORIES_URL);

        if (!TextUtils.isEmpty(cache)) {
            parseData(cache);
        }
        getDataFromServer();
    }


//    private void getDataFromServer() {
//        String result = null;
//        URL url = null;
//        try {
//            url = new URL(GlobalConstants.CATEGORIES_URL);
//            HttpURLConnection con = (HttpURLConnection) url.openConnection();
//            con.setRequestMethod("GET");
//            if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
//                result = con.getResponseMessage();
//            } else {
//                Toast.makeText(mActivity, "服务器没有响应", Toast.LENGTH_SHORT).show();
//            }
//            parseData(result);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    private void getDataFromServer() {
        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, GlobalConstants.CATEGORIES_URL, new
                RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        String result = responseInfo.result;
                        parseData(result);

                        //设置缓存
                        CacheUtils.setCache(mActivity, GlobalConstants.CATEGORIES_URL, result);
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show();
//                Toast.makeText(mActivity, "链接失败！", Toast.LENGTH_SHORT);
                        error.getStackTrace();
                    }
                });
    }

    private void parseData(String result) {
        Gson gson = new Gson();

        mNewsData = gson.fromJson(result, NewsData.class);

        MainActivity mainUI = (MainActivity) mActivity;
        LeftMenuFragment leftMenuFragment = mainUI.getLeftMenuFragment();
        leftMenuFragment.setMenuData(mNewsData);

        mPageList = new ArrayList<BaseMenuDetailPager>();

        mPageList.add(new NewsMenuDetailPager(mActivity, mNewsData.data.get(0).children));
        mPageList.add(new TopicMenuDetailPager(mActivity));
        mPageList.add(new PhotoMenuDetailPager(mActivity));
        mPageList.add(new InteractMenuDetailPager(mActivity));

        setCurrentMenuDetailPager(leftMenuFragment.mCurrentPos);

    }

    public void setCurrentMenuDetailPager(int position) {
        BaseMenuDetailPager pager = mPageList.get(position);
        flContent.removeAllViews();
        flContent.addView(pager.mRootView);

        NewsData.NewsMenuData menuData = mNewsData.data.get(position);
        tvTitle.setText(menuData.title);

        pager.initData();
    }
}

