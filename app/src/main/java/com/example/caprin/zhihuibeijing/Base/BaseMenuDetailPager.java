package com.example.caprin.zhihuibeijing.Base;

import android.app.Activity;
import android.view.View;

/**
 * Created by WD on 2016/11/10.
 */
public abstract class BaseMenuDetailPager {

    public Activity mActivity;
    public View mRootView;

    public BaseMenuDetailPager(Activity activity) {
        mActivity = activity;
        mRootView = initViews();
    }

    public abstract View initViews();

    public void initData() {

    }
}
