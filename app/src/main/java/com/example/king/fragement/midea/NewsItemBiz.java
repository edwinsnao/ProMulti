package com.example.king.fragement.midea;


import android.util.Log;

import com.edwinsnao.midea.CommonException;
import com.example.king.fragement.main.LogWrap;
import com.example.king.fragement.midea.detail.NewsDto;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by test on 15-11-10.
 */
public class NewsItemBiz
{
    static int responseCode;
    public NewsDto getNews(String urlStr) throws CommonException {
        NewsDto newsDto = new NewsDto();
        List<NewsItem> newses = new ArrayList<>();
        String htmlStr = DataUtil.doGet(urlStr);
        Document doc = Jsoup.parse(htmlStr);
//        Elements units1 = doc.getElementsByClass("news_con");
//        Elements units2 = doc.getElementsByClass("news_date");
//        Elements units3 = doc.getElementsByClass("news_pic");
//        Elements units = doc.getElementsByClass("news_row");
        Elements units4 = doc.getElementsByClass("nr").get(0).getElementsByTag("p");
//        for (int i = 0; i < units.size(); i++) {
//            TraceItem newsItem = new TraceItem();
////            newsItem.setNewsType(newsType);
//            //pic
//            Element unit_ele = units.get(i);
//
////            Element unit_ele1 = units1.get(i);
////            Element unit_ele2 = units2.get(i);
////            Element unit_ele1 = units1.get(0);
////            Element unit_ele2 = units2.get(0);
////            Element dl_ele = unit_ele.getElementsByTag("a").get(0);// dl
////            Element dt_ele = dl_ele.child(0);// dt
////            try {// 鍙兘娌℃湁鍥剧墖
////                Element img_ele = unit_ele.getElementsByClass("news_pic").get(0);
//////                Element img_ele1 = img_ele.getelementbyt
////                String imgLink = img_ele.child(0).child(0).attr("src");
////                newsItem.setImgLink(imgLink);
////            } catch (IndexOutOfBoundsException e) {
////
////            }
////            Element content_ele = dl_ele.child(1);// dd
////            String content = content_ele.text();
////            newsItem.setContent(content);
////            Elements units1 = doc.getElementsByClass("news_con");
////            Element h1_ele = unit_ele.getElementsByTag("h1").get(0);
//            //con
////            unit_ele = units.get(1);
//            Element unit_ele1 = unit_ele.getElementsByClass("news_con").get(0);
////            Element unit_ele1 = unit_ele.getElementsByTag("h2").get(0);
////            Element h1_ele = unit_ele1.get(0);
////            Element h1_a_ele = h1_ele.child(0);
//
//            String title = unit_ele1.getElementsByTag("h2").get(0).child(0).text();
////            String href = unit_ele1.child(0).attr("href");
//            String href = unit_ele1.getElementsByTag("h2").get(0).child(0).attr("href");
////            String content = unit_ele1.child(0).text();
//            try {
//                String content = unit_ele1.text();
////            System.out.println(""+h1_ele+"\n");
////            System.out.println(""+h1_a_ele+"\n");
//                newsItem.setContent(content);
//            } catch (Exception e) {
//                // TODO: handle exception
//                e.printStackTrace();
//            }
//            newsItem.setLink(href);
//            newsItem.setTitle(title);
//            newses.add(newsItem);
//        }
//        // 获得文章中的第一个detail


//        news.setContent(child.outerHtml());
        NewsItem newsItem = new NewsItem();
        StringBuilder content_tmp = new StringBuilder();
//        System.out.println(units4.size());
//      return 10
        for(int i = 0 ; i<units4.size();i++) {
            String content = units4.get(i).text();
            content_tmp.append(content+"\n");
        }
        newsItem.setTitle(String.valueOf(content_tmp));
        newses.add(newsItem);

        newsDto.setNewses(newses);
        return newsDto;

    }

    /**
     * 涓氱晫銆佺Щ鍔ㄣ�浜戣绠�     *
     * @return
     * @throws CommonException
     */
    public List<NewsItem> getNewsItems(int newsType, int currentPage) throws CommonException, UnsupportedEncodingException {
        String urlStr = URLUtil.generateUrl(newsType, currentPage);
//        String urlStr = "http://www.midea.com/cn/news_center/Product_News/index.shtml";
        urlStr = URLDecoder.decode(urlStr);
//        urlStr = URLEncoder.encode(urlStr);
        LogWrap.d("newsItemBiz"+ urlStr);
        String htmlStr = DataUtil.doGet(urlStr);
        LogWrap.d("newsItemBiz"+ htmlStr);

        ArrayList newsItems = new ArrayList();
        NewsItem newsItem = null;

        Document doc = Jsoup.parse(htmlStr);
//        Elements units = doc.getElementsByClass("unit");
        Elements units1 = doc.getElementsByClass("news_con");
        Elements units2 = doc.getElementsByClass("news_date");
        Elements units3 = doc.getElementsByClass("news_pic");
        Elements units = doc.getElementsByClass("news_row");
        for (int i = 0; i < units.size(); i++)
        {
            newsItem = new NewsItem();
            newsItem.setNewsType(newsType);
            newsItem.setCurrentPage(currentPage);
            //pic
            Element unit_ele = units.get(i);

//            Element unit_ele1 = units1.get(i);
//            Element unit_ele2 = units2.get(i);
//            Element unit_ele1 = units1.get(0);
//            Element unit_ele2 = units2.get(0);
//            Element dl_ele = unit_ele.getElementsByTag("a").get(0);// dl
//            Element dt_ele = dl_ele.child(0);// dt
            try
            {// 鍙兘娌℃湁鍥剧墖
                Element img_ele = unit_ele.getElementsByClass("news_pic").get(0);
//                Element img_ele1 = img_ele.getelementbyt
                String imgLink = img_ele.child(0).child(0).attr("src");
                newsItem.setImgLink(imgLink);
            } catch (IndexOutOfBoundsException e)
            {

            }
//            Element content_ele = dl_ele.child(1);// dd
//            String content = content_ele.text();
//            newsItem.setContent(content);
//            Elements units1 = doc.getElementsByClass("news_con");
//            Element h1_ele = unit_ele.getElementsByTag("h1").get(0);
        //con
//            unit_ele = units.get(1);
            Element unit_ele1 = unit_ele.getElementsByClass("news_con").get(0);
//            Element unit_ele1 = unit_ele.getElementsByTag("h2").get(0);
//            Element h1_ele = unit_ele1.get(0);
//            Element h1_a_ele = h1_ele.child(0);
            String title = unit_ele1.getElementsByTag("h2").get(0).child(0).text();
//            String href = unit_ele1.child(0).attr("href");
            String href = unit_ele1.getElementsByTag("h2").get(0).child(0).attr("href");
//            String content = unit_ele1.child(0).text();
            try{
            String content = unit_ele1.text();
//            System.out.println(""+h1_ele+"\n");
//            System.out.println(""+h1_a_ele+"\n");
            newsItem.setContent(content);
            }
            catch (Exception e) {
				// TODO: handle exception
            	e.printStackTrace();
			}
            newsItem.setLink(href);
            newsItem.setTitle(title);
//            newsItem.setContent(content);

//            Element h4_ele = unit_ele.getElementsByTag("h4").get(0);
//            Element h4_ele = unit_ele.getElementsByTag("h2").get(0);
//            Element h4_ele = unit_ele.getElementsByTag("div").get(0);
//            Element ago_ele = h4_ele.getElementsByClass("ago").get(0);
        //date
//        unit_ele = units.get(2);
            Element unit_ele2 = unit_ele.getElementsByClass("news_date").get(0);
//            Element ago_ele = unit_ele2.child(0);
//            String date = ago_ele.text();
            String date = unit_ele2.text();

            newsItem.setDate(date);

//            Element dl_ele = unit_ele.getElementsByTag("dl").get(0);// dl
//            Element dl_ele = unit_ele.getElementsByTag("div").get(0);// dl
//            Element dt_ele = dl_ele.child(0);// dt
//            try
//            {// 鍙兘娌℃湁鍥剧墖
//                Element img_ele = dt_ele.child(0);
//                String imgLink = img_ele.child(0).attr("src");
//                newsItem.setImgLink(imgLink);
//            } catch (IndexOutOfBoundsException e)
//            {
//
//            }
//            Element content_ele = dl_ele.child(1);// dd
//            String content = content_ele.text();
//            newsItem.setContent(content);
            newsItems.add(newsItem);
        }

        return newsItems;

    }
    public static int getResponseCode(){
        return responseCode;
    }

}
