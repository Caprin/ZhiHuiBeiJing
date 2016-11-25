package com.example.caprin.zhihuibeijing.domain;

import java.util.ArrayList;

/**
 * Created by w4785 on 11/24/2016.
 */

public class PhotoData {
    public PhotoDetailData data;
    public int retcode;

    public class PhotoDetailData {
        public String countcommenturl;
        public String more;
        public ArrayList<dataNews> news;
        public String title;
        public String topic;
    }

    public class dataNews {
        public boolean comment;
        public String commentlist;
        public String commenurl;
        public int id;
        public String largeimage;
        public String listimage;
        public String pubdate;
        public String smallimage;
        public String title;
        public String type;
        public String url;
    }

}
