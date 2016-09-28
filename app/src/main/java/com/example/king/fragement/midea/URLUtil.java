package com.example.king.fragement.midea;


import android.util.Log;

import com.edwinsnao.midea.Constaint;
import com.example.king.fragement.main.LogWrap;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class URLUtil
{


//    public static final String NEWS_LIST_URL = "http://www.csdn.net/headlines.html";
    public static final String NEWS_LIST_URL = "http://www.midea.com/cn/news_center/";
//    public static final String NEWS_LIST_URL_YIDONG = "http://mobile.csdn.net/mobile";
//    public static final String NEWS_LIST_URL_YIDONG = "http://www.midea.com/cn/news_center/Product_News/";
    public static final String NEWS_LIST_URL_YIDONG = "http://www.midea.com/cn/news_center/Product_News/index.shtml";
//    public static final String NEWS_LIST_URL_YIDONG = "http://www.galanz.com.cn/";
//    public static final String NEWS_LIST_URL_YANFA = "http://sd.csdn.net/sd";
    public static final String NEWS_LIST_URL_YANFA = "http://www.midea.com/cn/news_center/Group_News/index.shtml";
//    public static final String NEWS_LIST_URL_YANFA = "http://www.baidu.com";
    public static final String NEWS_LIST_URL_YUNJISUAN = "http://cloud.csdn.net/cloud";
//    public static final String NEWS_LIST_URL_ZAZHI = "http://programmer.csdn.net/programmer";
//    public static final String NEWS_LIST_URL_YEJIE = "http://news.csdn.net/news";

    /**
     * 根据文章类型，和当前页码生成url
     * @param newsType
     * @param currentPage
     * @return
     */
    public static String generateUrl(int newsType, int currentPage) throws UnsupportedEncodingException {
//        currentPage = currentPage > 0 ? currentPage : 1;
        currentPage = currentPage > 0 ? currentPage : 1;
//        �����ǵ�һҳ�����
        int page = currentPage -1;
        String urlStr = "";
        switch (newsType)
        {
//            case Constaint.NEWS_TYPE_YEJIE:
//                urlStr = NEWS_LIST_URL_YEJIE;
//                urlStr += "/" + currentPage;
//                break;
            case Constaint.NEWS_TYPE_YANFA:
                if(currentPage == 1)
                    urlStr = NEWS_LIST_URL_YANFA;
                else
                    urlStr = "http://www.midea.com/cn/news_center/Group_News/index_"+page +".shtml";
                break;
//            case Constaint.NEWS_TYPE_CHENGXUYUAN:
//                urlStr = NEWS_LIST_URL_ZAZHI;
//                urlStr += "/" + currentPage;
//                break;
            case Constaint.NEWS_TYPE_YUNJISUAN:
                urlStr = NEWS_LIST_URL_YUNJISUAN;
                urlStr += "/" + currentPage;
                break;
            default:
                if(currentPage == 1)
                    urlStr = NEWS_LIST_URL_YIDONG;
                else
                    urlStr = "http://www.midea.com/cn/news_center/Product_News/index_"+page+".shtml";
                break;
        }


//        urlStr += "/" + currentPage;
//        try {
//            urlStr = URLEncoder.encode(urlStr,"UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
        LogWrap.d("urlutil"+urlStr);

            String str = new String(urlStr.getBytes(), "UTF-8");
            str = URLEncoder.encode(str, "UTF-8");

//        return urlStr;
        return str;


    }


}
