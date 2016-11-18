package com.example.caprin.zhihuibeijing.domain;

import java.util.ArrayList;

/**
 * Created by WD on 2016/11/8.
 */
public class NewsData {
    public int retcode;
    public ArrayList<NewsMenuData> data;

    public class NewsMenuData {
        public String id;
        public String title;
        public int type;
        public String url;

        public ArrayList<NewsTabData> children;
    }

    public class NewsTabData {
        public String id;
        public String title;
        public int type;
        public String url;
    }
}
