package com.example.caprin.zhihuibeijing.Base.menudetail;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.caprin.zhihuibeijing.Base.BaseMenuDetailPager;
import com.example.caprin.zhihuibeijing.R;
import com.example.caprin.zhihuibeijing.domain.PhotoData;
import com.example.caprin.zhihuibeijing.global.GlobalConstants;
import com.example.caprin.zhihuibeijing.utils.CacheUtils;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;

/**
 * Created by WD on 2016/11/10.
 */
public class PhotoMenuDetailPager extends BaseMenuDetailPager {
    private ArrayList<PhotoData.dataNews> mPhotoList;
    private PhotoData data;
    private ListView lvPhoto;
    private GridView gvPhoto;
    private photoAdapter mAdapter;
    private ImageButton btnPhoto;

    public PhotoMenuDetailPager(Activity activity, ImageButton btnPhoto) {
        super(activity);

        this.btnPhoto = btnPhoto;

        btnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeDisplay();
            }
        });
    }

    @Override
    public View initViews() {
        View view = View.inflate(mActivity, R.layout.menu_photo_pager, null);
        lvPhoto = (ListView) view.findViewById(R.id.lv_photo);
        gvPhoto = (GridView) view.findViewById(R.id.gv_photo);

        return view;
    }

    @Override
    public void initData() {
        String cache = CacheUtils.getCache(mActivity, GlobalConstants.PHOTOS_URL);

        if (!TextUtils.isEmpty(cache)) {

        }
        getDataFromServer();
    }

    private void getDataFromServer() {
        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, GlobalConstants.PHOTOS_URL, new RequestCallBack<Object>() {
            @Override
            public void onSuccess(ResponseInfo<Object> responseInfo) {
                parseData((String) responseInfo.result);

                CacheUtils.setCache(mActivity, GlobalConstants.PHOTOS_URL, (String) responseInfo.result);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(mActivity, s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void parseData(String result) {
        Gson gson = new Gson();
        data = gson.fromJson(result, PhotoData.class);

        mPhotoList = data.data.news;

        if (mPhotoList != null) {
            mAdapter = new photoAdapter();
            lvPhoto.setAdapter(mAdapter);
            gvPhoto.setAdapter(mAdapter);
        }
    }

    class photoAdapter extends BaseAdapter {
        private BitmapUtils utils;

        public photoAdapter() {
            utils = new BitmapUtils(mActivity);
        }

        @Override
        public int getCount() {
            return mPhotoList.size();
        }

        @Override
        public PhotoData.dataNews getItem(int position) {
            return mPhotoList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = View.inflate(mActivity, R.layout.photo_list_item, null);
                holder.mTitle = (TextView) convertView.findViewById(R.id.photo_title);
                holder.mPic = (ImageView) convertView.findViewById(R.id.photo_pic);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            PhotoData.dataNews item = getItem(position);
            holder.mTitle.setText(item.title);

            utils.display(holder.mPic, item.listimage);
            return null;
        }
    }

    class ViewHolder {
        public ImageView mPic;
        public TextView mTitle;
    }

    private boolean isListDisplay = true;

    public void changeDisplay() {
        if (isListDisplay) {
            isListDisplay = false;
            lvPhoto.setVisibility(View.VISIBLE);
            gvPhoto.setVisibility(View.GONE);

            btnPhoto.setImageResource(R.drawable.icon_pic_list_type);
        } else {
            isListDisplay = true;
            lvPhoto.setVisibility(View.GONE);
            gvPhoto.setVisibility(View.VISIBLE);

            btnPhoto.setImageResource(R.drawable.icon_pic_grid_type);
        }
    }
}
