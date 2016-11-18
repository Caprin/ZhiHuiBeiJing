package com.example.caprin.zhihuibeijing.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 11个页签的viewpager
 * <p>
 * Created by WD on 2016/11/15.
 */
public class HorizontalViewPager extends ViewPager {
    public HorizontalViewPager(Context context) {
        super(context);
    }

    public HorizontalViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        if (getCurrentItem() != 0) {
//            getParent().requestDisallowInterceptTouchEvent(true);
//        } else {
//            getParent().requestDisallowInterceptTouchEvent(false);
//        }
//        return super.dispatchTouchEvent(ev);
//    }
}
