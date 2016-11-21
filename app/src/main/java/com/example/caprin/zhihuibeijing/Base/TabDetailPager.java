package com.example.caprin.zhihuibeijing.Base;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.caprin.zhihuibeijing.R;
import com.example.caprin.zhihuibeijing.domain.NewsData;
import com.example.caprin.zhihuibeijing.domain.TabData;
import com.example.caprin.zhihuibeijing.global.GlobalConstants;
import com.example.caprin.zhihuibeijing.utils.PrefUtils;
import com.example.caprin.zhihuibeijing.view.RefreshListView;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;

/**
 * Created by WD on 2016/11/13.
 */
public class TabDetailPager extends BaseMenuDetailPager {
    private NewsData.NewsTabData mTabData;

    private String mURL;

    private TabData mTabDetailData;

    //    @ViewInject(R.id.vp_news)
    private ViewPager mViewpager;

    private BitmapUtils utils;

    //    @ViewInject(R.id.topnews_title)
    private TextView tvTitle;

    //    @ViewInject(R.id.indicator)
    private CirclePageIndicator indicator;

    //    @ViewInject(R.id.lv_list)
    private RefreshListView lvList;

    private ArrayList<TabData.TabNewsData> mNewsList;
    private String mMoreUrl;
    private NewsAdapter mNewsAdapter;

    private static final String TAG = "onitemclik";

    public TabDetailPager(Activity activity, NewsData.NewsTabData newsTabData) {
        super(activity);
        mTabData = newsTabData;

        mURL = GlobalConstants.SERVER_URL + mTabData.url;
    }

    @Override
    public View initViews() {
        View view = View.inflate(mActivity, R.layout.tab_detail_pager, null);

        View headerView = View.inflate(mActivity, R.layout.list_header_topnews, null);

//        ViewUtils.inject(this, view);
//        ViewUtils.inject(this, headerView);

        mViewpager = (ViewPager) headerView.findViewById(R.id.vp_news);

        tvTitle = (TextView) headerView.findViewById(R.id.topnews_title);

        indicator = (CirclePageIndicator) headerView.findViewById(R.id.indicator);

        lvList = (RefreshListView) view.findViewById(R.id.lv_list);

        lvList.addHeaderView(headerView);

        lvList.setOnRefreshListener(new RefreshListView.onRefreshListener() {
            @Override
            public void onRefresh() {
                getDataFromServer();
            }

            @Override
            public void onLoadMore() {
                if (mMoreUrl != null) {
                    getMoreDataFromServer();
                } else {
                    Toast.makeText(mActivity, "最后一页了", Toast.LENGTH_SHORT).show();
                    lvList.onRefreshComplete(false);
                }
            }
        });

        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String ids = PrefUtils.getString(mActivity, "read_ids", "");
                String readId = mNewsList.get(position).id;
                if (!ids.contains(readId)) {
                    ids = ids + readId + ",";
                    PrefUtils.setString(mActivity, "read_ids", ids);
                }

                Log.d(TAG, "read_ids = " + ids);
                changeReadState(view);

            }
        });

        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int
                    positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tvTitle.setText(mTabDetailData.data.topnews.get(position).title);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        return view;
    }

    private void changeReadState(View view) {
        TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
        tvTitle.setTextColor(Color.GRAY);
    }

    @Override
    public void initData() {
        getDataFromServer();
    }

    private void getDataFromServer() {
        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, mURL, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                parseData(result, false);

                lvList.onRefreshComplete(true);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(mActivity, s, Toast.LENGTH_SHORT).show();
                e.getStackTrace();

                lvList.onRefreshComplete(false);
            }
        });
    }

    private void getMoreDataFromServer() {
        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, mMoreUrl, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                parseData(result, true);

                lvList.onRefreshComplete(true);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(mActivity, s, Toast.LENGTH_SHORT).show();
                e.getStackTrace();

                lvList.onRefreshComplete(false);
            }
        });
    }

    private void parseData(String result, boolean isMore) {
        Gson gson = new Gson();
        mTabDetailData = gson.fromJson(result, TabData.class);

        String more = mTabDetailData.data.more;
        if (!TextUtils.isEmpty(more)) {
            mMoreUrl = GlobalConstants.SERVER_URL + more;
        } else {
            mMoreUrl = null;
        }

        if (!isMore) {
            mViewpager.setAdapter(new TopNewsAdapter());

            mNewsList = mTabDetailData.data.news;

            mNewsAdapter = new NewsAdapter();

            lvList.setAdapter(mNewsAdapter);

            tvTitle.setText(mTabDetailData.data.topnews.get(0).title);

            indicator.setViewPager(mViewpager);
            indicator.setSnap(true);

            indicator.onPageSelected(0);
        } else {
            ArrayList<TabData.TabNewsData> news = mTabDetailData.data.news;
            mNewsList.addAll(news);
            mNewsAdapter.notifyDataSetChanged();
        }
    }

    class TopNewsAdapter extends PagerAdapter {

        public TopNewsAdapter() {
            utils = new BitmapUtils(mActivity);
            utils.configDefaultLoadingImage(R.drawable.topnews_item_default);
        }

        @Override
        public int getCount() {
            return mTabDetailData.data.topnews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView image = new ImageView(mActivity);
            image.setScaleType(ImageView.ScaleType.FIT_XY);

            utils.display(image, mTabDetailData.data.topnews.get(position).topimage);

            container.addView(image);
            return image;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    class NewsAdapter extends BaseAdapter {

        private BitmapUtils utils;

        public NewsAdapter() {
            utils = new BitmapUtils(mActivity);
            utils.configDefaultLoadingImage(R.drawable.pic_item_list_default);
        }

        @Override
        public int getCount() {
            return mNewsList.size();
        }

        @Override
        public Object getItem(int i) {
            return mNewsList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder holder;
            if (view == null) {
                view = View.inflate(mActivity, R.layout.news_list_item, null);
                holder = new ViewHolder();

                holder.tvPic = (ImageView) view.findViewById(R.id.tv_pic);
                holder.tvTitle = (TextView) view.findViewById(R.id.news_title);
                holder.tvDate = (TextView) view.findViewById(R.id.tv_date);

                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }

            TabData.TabNewsData item = mNewsList.get(i);
            holder.tvDate.setText(item.pubdate);
            holder.tvTitle.setText(item.title);

            utils.display(holder.tvPic, item.listimage);
            String ids = PrefUtils.getString(mActivity, "read_ids", "");
            if (ids.contains(mNewsList.get(i).id)) {
                tvTitle.setTextColor(Color.GRAY);
            } else {
                tvTitle.setTextColor(Color.BLACK);
            }

            return view;
        }
    }

    class ViewHolder {
        public TextView tvTitle;
        public TextView tvDate;
        public ImageView tvPic;
    }
}
