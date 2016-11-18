package com.example.caprin.zhihuibeijing.Fragment;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.caprin.zhihuibeijing.Base.impl.NewsCenterPager;
import com.example.caprin.zhihuibeijing.MainActivity;
import com.example.caprin.zhihuibeijing.R;
import com.example.caprin.zhihuibeijing.domain.NewsData;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;

/**
 * Created by WD on 2016/10/14.
 */
public class LeftMenuFragment extends BaseFragment {

    private ListView lvlist;
    private ArrayList<NewsData.NewsMenuData> menuList;
    public int mCurrentPos;
    private menuAdapter mAdapter;

    @Override
    public View initViews() {
        View view = View.inflate(mActivity, R.layout.fragmnet_left_menu, null);

        lvlist = (ListView) view.findViewById(R.id.lf_list);

        return view;
    }

    @Override
    public void initData() {
        lvlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                mCurrentPos = position;
                mAdapter.notifyDataSetChanged();

                setCurrentDetailPager(position);

                toggleSlidingMenu();
            }
        });
    }

    public void toggleSlidingMenu() {
        MainActivity mainUI = (MainActivity) mActivity;
        SlidingMenu slidingMenu = mainUI.getSlidingMenu();
        slidingMenu.toggle();
    }


    public void setCurrentDetailPager(int position) {
        MainActivity mainUI = (MainActivity) mActivity;
        ContentFragment fragment = mainUI.getContentFragment();
        NewsCenterPager pager = fragment.getNewsCenterPager();
        pager.setCurrentMenuDetailPager(position);
    }

    public void setMenuData(NewsData data) {
        menuList = data.data;
        mAdapter = new menuAdapter();
        lvlist.setAdapter(mAdapter);
    }

    class menuAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return menuList.size();
        }

        @Override
        public NewsData.NewsMenuData getItem(int i) {
            return menuList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View itemView = View.inflate(mActivity, R.layout.list_menu_item, null);
            TextView tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            NewsData.NewsMenuData newsDataMenu = getItem(i);
            tvTitle.setText(newsDataMenu.title);

            if (mCurrentPos == i) {
                tvTitle.setEnabled(true);
            } else {
                tvTitle.setEnabled(false);
            }

            return itemView;
        }
    }
}
