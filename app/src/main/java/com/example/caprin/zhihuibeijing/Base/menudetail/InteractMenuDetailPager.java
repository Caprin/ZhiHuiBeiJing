package com.example.caprin.zhihuibeijing.Base.menudetail;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.caprin.zhihuibeijing.Base.BaseMenuDetailPager;

/**
 * Created by WD on 2016/11/10.
 */
public class InteractMenuDetailPager extends BaseMenuDetailPager {
    public InteractMenuDetailPager(Activity activity) {
        super(activity);
    }

    @Override
    public View initViews() {
        TextView text = new TextView(mActivity);
        text.setText("菜单详情页 互动");
        text.setTextColor(Color.RED);
        text.setTextSize(25);
        text.setGravity(Gravity.CENTER);

        return text;
    }
}
