package com.example.caprin.zhihuibeijing.Base.menudetail;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.caprin.zhihuibeijing.Base.BaseMenuDetailPager;
import com.example.caprin.zhihuibeijing.Base.TabDetailPager;
import com.example.caprin.zhihuibeijing.MainActivity;
import com.example.caprin.zhihuibeijing.R;
import com.example.caprin.zhihuibeijing.domain.NewsData;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;

/**
 * Created by WD on 2016/11/10.
 */
public class NewsMenuDetailPager extends BaseMenuDetailPager {
    private ViewPager mViewPager;
    private ArrayList<TabDetailPager> mPagerList;
    private ArrayList<NewsData.NewsTabData> mNewsTabData;

    private TabPageIndicator mIndicator;
    private ImageButton mBtnNext;

    public NewsMenuDetailPager(Activity activity, ArrayList<NewsData.NewsTabData> children) {
        super(activity);
        mNewsTabData = children;
    }

    @Override
    public View initViews() {
        View view = View.inflate(mActivity, R.layout.news_menu_detail, null);
        mViewPager = (ViewPager) view.findViewById(R.id.vp_menu_detail);

        mIndicator = (TabPageIndicator) view.findViewById(R.id.indicator);

        mBtnNext = (ImageButton) view.findViewById(R.id.btn_next);

        mBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextPage(v);
            }
        });

        mIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                MainActivity mainUI = (MainActivity) mActivity;
                SlidingMenu slidingMenu = mainUI.getSlidingMenu();
                if (position == 0) {
                    slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
                } else {
                    slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        return view;
    }

    @Override
    public void initData() {
        mPagerList = new ArrayList<TabDetailPager>();

        for (int i = 0; i < mNewsTabData.size(); i++) {
            TabDetailPager pager = new TabDetailPager(mActivity, mNewsTabData.get(i));
            mPagerList.add(pager);
        }

        mViewPager.setAdapter(new MenuDetailAdapter());

        mIndicator.setViewPager(mViewPager);

    }

    public void nextPage(View view) {
        int currentItem = mViewPager.getCurrentItem();
        mViewPager.setCurrentItem(++currentItem);
    }

    class MenuDetailAdapter extends PagerAdapter {


        @Override
        public CharSequence getPageTitle(int position) {
            return mNewsTabData.get(position).title;
        }

        @Override
        public int getCount() {
            return mPagerList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            TabDetailPager pager = mPagerList.get(position);
            container.addView(pager.mRootView);

            pager.initData();
            return pager.mRootView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
