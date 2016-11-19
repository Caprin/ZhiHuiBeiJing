package com.example.caprin.zhihuibeijing.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by WD on 2016/11/15.
 */
public class TopNewsViewPager extends ViewPager {
    int startX;
    int startY;

    public TopNewsViewPager(Context context) {
        super(context);
    }

    public TopNewsViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    /**
     * 左滑
     * 右滑
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                getParent().requestDisallowInterceptTouchEvent(true);

                startX = (int) ev.getRawX();
                startY = (int) ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int endX = (int) ev.getRawX();
                int endY = (int) ev.getRawY();

                if (endX - startX > endY - startY) { //左右滑动
                    if (endX > startX) { //右滑
                        if (getCurrentItem() == 0) { //第一页
                            getParent().requestDisallowInterceptTouchEvent(false);
                        }
                    } else { //左滑
                        if (getAdapter().getCount() - 1 == getCurrentItem()) {
                            getParent().requestDisallowInterceptTouchEvent(false);
                        }
                    }
                } else { //上下滑动
                    getParent().requestDisallowInterceptTouchEvent(false);
                }

                break;

            default:
                break;
        }

        return super.dispatchTouchEvent(ev);
    }
}
