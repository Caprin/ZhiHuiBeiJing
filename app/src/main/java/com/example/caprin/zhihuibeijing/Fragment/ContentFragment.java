package com.example.caprin.zhihuibeijing.Fragment;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.caprin.zhihuibeijing.Base.BasePager;
import com.example.caprin.zhihuibeijing.Base.impl.GovAffairsPager;
import com.example.caprin.zhihuibeijing.Base.impl.HomePager;
import com.example.caprin.zhihuibeijing.Base.impl.NewsCenterPager;
import com.example.caprin.zhihuibeijing.Base.impl.SettingPager;
import com.example.caprin.zhihuibeijing.Base.impl.SmartServicePager;
import com.example.caprin.zhihuibeijing.R;

import java.util.ArrayList;

/**
 * Created by WD on 2016/10/14.
 */
public class ContentFragment extends BaseFragment {

    private ViewPager mViewPager;

    private RadioGroup rgGroup;

    private ArrayList<BasePager> mPagerList;

    @Override
    public View initViews() {
        View view = View.inflate(mActivity, R.layout.fragmnet_content, null);

        rgGroup = (RadioGroup) view.findViewById(R.id.rg_group);

        mViewPager = (ViewPager) view.findViewById(R.id.vp_content);

        return view;
    }

    @Override
    public void initData() {
        rgGroup.check(R.id.homePage);

        mPagerList = new ArrayList<BasePager>();

        mPagerList.add(new HomePager(mActivity));
        mPagerList.add(new NewsCenterPager(mActivity));
        mPagerList.add(new SmartServicePager(mActivity));
        mPagerList.add(new GovAffairsPager(mActivity));
        mPagerList.add(new SettingPager(mActivity));

        mViewPager.setAdapter(new contentAdapter());

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mPagerList.get(position).initData();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        rgGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.homePage:
                        mViewPager.setCurrentItem(0, false);
                        break;
                    case R.id.news:
                        mViewPager.setCurrentItem(1, false);
                        break;
                    case R.id.smartService:
                        mViewPager.setCurrentItem(2, false);
                        break;
                    case R.id.govAffairs:
                        mViewPager.setCurrentItem(3, false);
                        break;
                    case R.id.setting:
                        mViewPager.setCurrentItem(4, false);
                        break;
                    default:
                        break;
                }
            }
        });
        mPagerList.get(0).initData();
    }

    class contentAdapter extends PagerAdapter {

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
            BasePager pager = mPagerList.get(position);
            container.addView(pager.mRootView);

            return pager.mRootView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    public NewsCenterPager getNewsCenterPager() {
        return (NewsCenterPager) mPagerList.get(1);
    }
}
