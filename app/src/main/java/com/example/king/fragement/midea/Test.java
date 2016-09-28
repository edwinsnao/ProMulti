package com.example.king.fragement.midea;



import com.edwinsnao.midea.CommonException;
import com.edwinsnao.midea.Constaint;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class Test
{

    public static void main(String[] args)
    {
        NewsItemBiz biz = new NewsItemBiz();
        int currentPage = 1;
        try
        {
            /**
             * 涓氱晫
             */
//            List<TraceItem> newsItems = biz.getNewsItems(Constaint.NEWS_TYPE_YEJIE, currentPage);
//            for (TraceItem item : newsItems)
//            {
//                System.out.println(item);
//            }
//
//            System.out.println("----------------------");
            /**
             * 绋嬪簭鍛樻潅蹇�             */
//            newsItems = biz.getNewsItems(Constaint.NEWS_TYPE_CHENGXUYUAN, currentPage);
//            for (TraceItem item : newsItems)
//            {
//                System.out.println(item);
//            }
//            System.out.println("----------------------");
            /**
             * 鐮斿彂
             */
            List<NewsItem>newsItems = biz.getNewsItems(Constaint.NEWS_TYPE_YANFA, currentPage);
            for (NewsItem item : newsItems)
            {
//                System.out.println(item);
            }
//            System.out.println("----------------------");
            /**
             * 绉诲姩
             */
            newsItems = biz.getNewsItems(Constaint.NEWS_TYPE_YIDONG, currentPage);
            for (NewsItem item : newsItems)
            {
//                System.out.println(item);
            }
//            System.out.println("----------------------");

        } catch (CommonException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

}
