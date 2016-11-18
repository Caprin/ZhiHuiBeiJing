package com.example.caprin.zhihuibeijing.Base.impl;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.caprin.zhihuibeijing.Base.BasePager;
import com.example.caprin.zhihuibeijing.MainActivity;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

/**
 * Created by WD on 2016/10/16.
 */
public class HomePager extends BasePager {

    public HomePager(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {
        setSlidingMenuEnable(false);

        tvTitle.setText("首页");
        btnMenu.setVisibility(View.GONE);

        TextView text = new TextView(mActivity);
        text.setText("首页");
        text.setTextSize(25);
        text.setTextColor(Color.RED);
        text.setGravity(Gravity.CENTER);

        flContent.addView(text);
    }

}
