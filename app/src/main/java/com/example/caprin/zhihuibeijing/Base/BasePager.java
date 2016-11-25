package com.example.caprin.zhihuibeijing.Base;

import android.app.Activity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.caprin.zhihuibeijing.MainActivity;
import com.example.caprin.zhihuibeijing.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

/**
 * Created by WD on 2016/10/16.
 */
public class BasePager {

    public Activity mActivity;
    public View mRootView;

    public TextView tvTitle;

    public FrameLayout flContent;

    public ImageButton btnMenu;

    public ImageButton btnPhoto;

    public BasePager(Activity activity) {
        mActivity = activity;
        initViews();
    }

    public void initViews() {
        mRootView = View.inflate(mActivity, R.layout.base_pager, null);

        tvTitle = (TextView) mRootView.findViewById(R.id.title_text);

        flContent = (FrameLayout) mRootView.findViewById(R.id.fl_content);

        btnMenu = (ImageButton) mRootView.findViewById(R.id.btn_menu);

        btnMenu.setVisibility(View.GONE);

        btnPhoto = mRootView.findViewById(R.id.btn_photo);
    }

    public void initData() {

    }

    public void setSlidingMenuEnable(boolean enable) {
        MainActivity mainUI = (MainActivity) mActivity;

        SlidingMenu slidingMenu = mainUI.getSlidingMenu();

        if (enable) {
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        } else {
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        }
    }

    public void toggleSlidingMenu(boolean enable) {
        MainActivity mainUI = (MainActivity) mActivity;
        SlidingMenu slidingMenu = mainUI.getSlidingMenu();

        if (enable) {
            slidingMenu.toggle(enable);
        }
    }
}
