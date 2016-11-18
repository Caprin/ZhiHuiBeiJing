package com.example.caprin.zhihuibeijing;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.caprin.zhihuibeijing.utils.PrefUtils;

import java.util.ArrayList;

/**
 * Created by WD on 2016/10/13.
 */
public class GuidePagerActivity extends Activity {

    private ViewPager vpGuider;
    private ArrayList<ImageView> imageViewArrayList;
    private LinearLayout llPointGroup;
    private int mPointWidth;
    private View redPoint;
    private Button start;

    private static final int[] mImageIds = new int[]{R.drawable.guide_1,
            R.drawable.guide_2, R.drawable.guide_3};

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // 鍘绘帀鏍囬鍜岀姸鎬佹爮
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.viewpager);
        vpGuider = (ViewPager) findViewById(R.id.vp_guider);

        llPointGroup = (LinearLayout) findViewById(R.id.ll_point_group);

        start = (Button) findViewById(R.id.start);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 灞曠ず杩囦簡鏂版墜寮曞锛屾敼鍙榗ongfig鍊?
                PrefUtils.setBoolean(GuidePagerActivity.this, "is_user_guide_showed", true);

                startActivity(new Intent(GuidePagerActivity.this, MainActivity.class));
                finish();
            }
        });

        redPoint = findViewById(R.id.view_red_point);

        initViews();
        vpGuider.setAdapter(new GuiderAdapter());

        vpGuider.setOnPageChangeListener(new GuidePageListener());
    }


    // init images of guidePage
    private void initViews() {
        imageViewArrayList = new ArrayList<ImageView>();

        for (int i = 0; i < mImageIds.length; i++) {
            ImageView image = new ImageView(this);
            image.setBackgroundResource(mImageIds[i]);
            imageViewArrayList.add(image);
        }

        // init points of guidePage
        for (int i = 0; i < mImageIds.length; i++) {
            View point = new View(this);
            point.setBackgroundResource(R.drawable.shape_point_gray);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(30, 30);

            if (i > 0) {
                params.leftMargin = 30;
            }

            point.setLayoutParams(params);

            llPointGroup.addView(point);
        }

//        // Draw red point to cover the gray point
//        redPoint = new View(GuidePagerActivity.this);
//        redPoint.setBackgroundResource(R.drawable.shape_point_red);
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(30, 30);
//        params.leftMargin = -150;
//        redPoint.setLayoutParams(params);
//        llPointGroup.addView(redPoint);


        // Get view tree to listen layout end event
        llPointGroup.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onGlobalLayout() {
                        System.out.println("Layout End");
                        llPointGroup.getViewTreeObserver().
                                removeOnGlobalLayoutListener(this);
                        mPointWidth = llPointGroup.getChildAt(1).getLeft()
                                - llPointGroup.getChildAt(0).getLeft();
                        System.out.println("point distance:" + mPointWidth);
                    }
                });
    }


    //viewPager Adapter
    class GuiderAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mImageIds.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            container.addView(imageViewArrayList.get(position));
            return imageViewArrayList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    class GuidePageListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            int len = (int) (mPointWidth * positionOffset) + position * mPointWidth;

            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)
                    redPoint.getLayoutParams();
            params.leftMargin = len;
            redPoint.setLayoutParams(params);
        }

        @Override
        public void onPageSelected(int position) {
            if (position == mImageIds.length - 1) {
                start.setVisibility(View.VISIBLE);
            } else {
                start.setVisibility(View.INVISIBLE);
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
