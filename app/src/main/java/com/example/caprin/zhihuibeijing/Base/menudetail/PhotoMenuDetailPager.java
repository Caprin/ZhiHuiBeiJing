package com.example.caprin.zhihuibeijing.Base.menudetail;

import android.app.Activity;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListView;

import com.example.caprin.zhihuibeijing.Base.BaseMenuDetailPager;
import com.example.caprin.zhihuibeijing.R;
import com.example.caprin.zhihuibeijing.domain.PhotoData;
import com.example.caprin.zhihuibeijing.global.GlobalConstants;
import com.example.caprin.zhihuibeijing.utils.CacheUtils;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.apache.http.protocol.HTTP;

/**
 * Created by WD on 2016/11/10.
 */
public class PhotoMenuDetailPager extends BaseMenuDetailPager {
    public PhotoMenuDetailPager(Activity activity) {
        super(activity);
    }

    @Override
    public View initViews() {
        View view = View.inflate(mActivity, R.layout.menu_photo_pager, null);
        ListView lvPhoto = (ListView) view.findViewById(R.id.lv_photo);
        GridView gvPhoto = (GridView) view.findViewById(R.id.gv_photo);

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
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }

    private void parseData(String result) {
        Gson gson = new Gson();
        gson.fromJson(result, PhotoData.class);
    }

    class phtotAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
        }
    }
}
