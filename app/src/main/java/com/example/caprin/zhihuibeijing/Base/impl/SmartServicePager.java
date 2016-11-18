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
public class SmartServicePager extends BasePager {
    public SmartServicePager(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {
        tvTitle.setText("生活");

        setSlidingMenuEnable(true);
        btnMenu.setVisibility(View.VISIBLE);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleSlidingMenu(true);
            }
        });

        TextView text = new TextView(mActivity);
        text.setText("智慧服务");
        text.setTextSize(25);
        text.setTextColor(Color.RED);
        text.setGravity(Gravity.CENTER);

        flContent.addView(text);
    }
}
