package com.example.caprin.zhihuibeijing.domain;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by WD on 2016/11/15.
 */
public class TabData {
    public int retcode;

    public TabDetail data;

    public class TabDetail {
        public String title;
        public String more;
        public ArrayList<TabNewsData> news;
        public ArrayList<TopNewsData> topnews;
    }

    /**
     * 新闻列表对象
     */
    public class TabNewsData {
        public String id;
        public String listimage;
        public String pubdate;
        public String title;
        public String type;
        public String url;
    }

    /**
     * 头条新闻对象
     */
    public class TopNewsData {
        public String id;
        public String topimage;
        public String pubdate;
        public String title;
        public String type;
        public String url;
    }
}
