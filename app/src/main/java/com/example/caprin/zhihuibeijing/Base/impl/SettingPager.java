package com.example.caprin.zhihuibeijing.Base.impl;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.caprin.zhihuibeijing.Base.BasePager;

/**
 * Created by WD on 2016/10/17.
 */
public class SettingPager extends BasePager {
    public SettingPager(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {
        setSlidingMenuEnable(false);

        tvTitle.setText("设置");
        btnMenu.setVisibility(View.GONE);

        TextView text = new TextView(mActivity);
        text.setText("设置");
        text.setTextSize(25);
        text.setTextColor(Color.RED);
        text.setGravity(Gravity.CENTER);

        flContent.addView(text);
    }
}
