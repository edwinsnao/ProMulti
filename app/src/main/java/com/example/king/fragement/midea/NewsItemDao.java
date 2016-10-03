package com.example.king.fragement.midea;

/**
 * Created by test on 15-11-14.
 */
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.example.king.fragement.main.BaseApplication;


public class NewsItemDao
{

    private DBHelper dbHelper;
//    private List<NewsItem> newsItemsGroup ;
//    private List<NewsItem> newsItemsProduct ;
//    private List<NewsItem> newsItemsGetGroup ;
//    private List<NewsItem> newsItemsGetProduct;
    private List<NewsItem> newsItemsGroup = new ArrayList<>();
    private List<NewsItem> newsItemsProduct = new ArrayList<>();
    private List<NewsItem> newsItemsGetGroup;
    private List<NewsItem> newsItemsGetProduct;

    public NewsItemDao(Context context)
    {
        dbHelper = new DBHelper(context);
//        dbHelper = application.getDBHelper();
    }

    public List<NewsItem> listAll(int newsType){
        List<NewsItem> newsItems = new ArrayList<NewsItem>();
        try
        {
//            int offset = 10 * (currentPage - 1);
//            String sql = "select title,link,date,imgLink,content,newstype from tb_newsItem where newstype = ? limit ?,? ";
//            String sql = "select title,link,date,imgLink,content,newstype from tb_newsItem where newstype = ? and currentpage = ?";
            String sql = "select distinct date,title,link,imgLink,content,newstype from tb_newsItem where newstype = ? group by date order by  date desc";
            SQLiteDatabase db = dbHelper.getReadableDatabase();
//            SQLiteStatement stmt = db.compileStatement(sql);
//            stmt.execute();
//            Cursor c = db.rawQuery(sql, new String[] { newsType + "", offset + "", "" + (offset + 10) });
            Cursor c = db.rawQuery(sql, new String[] { newsType + "" });

            NewsItem newsItem = null;

            while (c.moveToNext())
            {
                newsItem = new NewsItem();

//                String title = c.getString(0);
//                String link = c.getString(1);
//                String date = c.getString(2);
//                String imgLink = c.getString(3);
//                String content = c.getString(4);
//                Integer newstype = c.getInt(5);
                String title = c.getString(1);
                String link = c.getString(2);
                String date = c.getString(0);
                String imgLink = c.getString(3);
                String content = c.getString(4);
                Integer newstype = c.getInt(5);

                newsItem.setTitle(title);
                newsItem.setLink(link);
                newsItem.setImgLink(imgLink);
                newsItem.setDate(date);
                newsItem.setNewsType(newstype);
                newsItem.setContent(content);
//                这里也要+？

                newsItems.add(newsItem);

            }
            c.close();
            db.close();
//            Log.e("DBHelper",newsItems.size() + "  newsItems.size()");
        } catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        /**GroupNews*/
        if(newsType == 3){
            newsItemsGroup.clear();
            newsItemsGroup.addAll(newsItems);
            return newsItemsGroup;
        }else{
            newsItemsProduct.clear();
            newsItemsProduct.addAll(newsItems);
            return newsItemsProduct;
        }
//        Log.e("groupsizeALL",String.valueOf(newsItemsGroup.size()));
//        Log.e("productsizeALL",String.valueOf(newsItemsProduct.size()));

    }

    public void add(NewsItem newsItem)
    {
//        Log.e("DBHelper","add news newstype " + newsItem.getNewsType());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.beginTransaction();
        String sql = "insert into tb_newsItem (title,link,date,imgLink,content,newstype) values(?,?,?,?,?,?) ;";
        SQLiteStatement stmt = db.compileStatement(sql);
        stmt.bindString(1,newsItem.getTitle());
        stmt.bindString(2,newsItem.getLink());
        stmt.bindString(3,newsItem.getDate());
        stmt.bindString(4,newsItem.getImgLink());
        stmt.bindString(5,newsItem.getContent());
        stmt.bindLong(6,(long)newsItem.getNewsType());
//        db.execSQL(sql,
//                new Object[] { newsItem.getTitle(), newsItem.getLink(), newsItem.getDate(), newsItem.getImgLink(),
//                        newsItem.getContent(), newsItem.getNewsType() ,newsItem.getCurrentPage()});
        stmt.executeInsert();
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
    }
    /**
     * 从数据库中查询用户包含的关键字数据
     *
     * @return
     */
    public List<NewsItem> searchnews(String query,int start , int end)
    {
        List<NewsItem> newsItems = new ArrayList<NewsItem>();
        try
        {
            /*
            *在？那里不需要加'和%
            * %是在String那里加
            * */
            String sql = "select title,link,date,imgLink,content,newstype from tb_newsItem where content like ? order by date desc limit ?,?";
            SQLiteDatabase db = dbHelper.getReadableDatabase();
//            Cursor c = db.rawQuery(sql, new String[] { newsType + "", offset + "", "" + (offset + 10) });
            Cursor c = db.rawQuery(sql, new String[] { "%"+query + "%", start + "", end + "" });

            NewsItem newsItem = null;

            while (c.moveToNext())
            {
                newsItem = new NewsItem();

                String title = c.getString(0);
                String link = c.getString(1);
                String date = c.getString(2);
                String imgLink = c.getString(3);
                String content = c.getString(4);
                Integer newstype = c.getInt(5);

                newsItem.setTitle(title);
                newsItem.setLink(link);
                newsItem.setImgLink(imgLink);
                newsItem.setDate(date);
                newsItem.setNewsType(newstype);
                newsItem.setContent(content);
//                这里也要+？

                newsItems.add(newsItem);

            }
            c.close();
            db.close();
//            Log.i("SearchNews:",newsItems.size() + "  newsItems.size()");
        } catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return newsItems;

    }
    public List<NewsItem> searchnews_type(String query,int type,int start , int end)
    {
        List<NewsItem> newsItems = new ArrayList<NewsItem>();
        try
        {
            /*
            *在？那里不需要加'和%
            * %是在String那里加
            * */
//            String sql = "select title,link,date,imgLink,content,newstype from tb_newsItem where content like ? and newstype = ? order by date desc limit ?,?";
            String sql = "select distinct date,title,link,imgLink,content,newstype from tb_newsItem where content like ? and newstype = ? group by date order by date desc limit ?,?";
            SQLiteDatabase db = dbHelper.getReadableDatabase();
//            Cursor c = db.rawQuery(sql, new String[] { newsType + "", offset + "", "" + (offset + 10) });
            Cursor c = db.rawQuery(sql, new String[] { "%"+query + "%", type + "",start + "", end + "" });

            NewsItem newsItem = null;

            while (c.moveToNext())
            {
                newsItem = new NewsItem();

                String title = c.getString(1);
                String link = c.getString(2);
                String date = c.getString(0);
                String imgLink = c.getString(3);
                String content = c.getString(4);
                Integer newstype = c.getInt(5);

                newsItem.setTitle(title);
                newsItem.setLink(link);
                newsItem.setImgLink(imgLink);
                newsItem.setDate(date);
                newsItem.setNewsType(newstype);
                newsItem.setContent(content);
//                这里也要+？

                newsItems.add(newsItem);

            }
            c.close();
            db.close();
//            Log.i("SearchNews:",newsItems.size() + "  newsItems.size()");
        } catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return newsItems;

    }

    public void deleteAll(int newsType)
    {
        String sql = "delete from tb_newsItem where newstype = ?";
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL(sql, new Object[] { newsType });
        db.close();
    }

    /*
    * 查询
    * 比下面那的速度慢
    * */
//    public Cursor query(){
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        Cursor c = db.query("tb_newsItem",null,null,null,null,null,null);
//        return c;
//    }

    public void add(List<NewsItem> newsItems)
    {
        for (NewsItem newsItem : newsItems)
        {
            add(newsItem);
        }
    }

    /**
     * 根据newsType和currentPage从数据库中取最新数据
     *
     * @param newsType
     *
     * @return
     */
    public NewsItem listLatest(int newsType)
    {

//        Log.e("DBHelper",newsType + "  newsType");
//        Log.e("DBHelper",currentPage + "  currentPage");
        // 0 -9 , 10 - 19 ,
        NewsItem newsItems = new NewsItem();
        try
        {
//            int offset = 10 * (currentPage - 1);
//            String sql = "select title,link,date,imgLink,content,newstype from tb_newsItem where newstype = ? limit 0,1 ";
            /**只拿需要的字段，加快速度*/
            String sql = "select distinct date,title,newstype from tb_newsItem where newstype = ? group by date order by  date desc limit 0,1 ";
//            String sql = "select title,link,date,imgLink,content,newstype from tb_newsItem where newstype = ? and currentpage = ?";
            SQLiteDatabase db = dbHelper.getReadableDatabase();
//            SQLiteStatement stmt = db.compileStatement(sql);
//            stmt.execute();
//            Cursor c = db.rawQuery(sql, new String[] { newsType + "", offset + "", "" + (offset + 10) });
            Cursor c = db.rawQuery(sql, new String[] { newsType + "" });


            while (c.moveToNext())
            {

                String title = c.getString(1);
                String date = c.getString(0);
                Integer newstype = c.getInt(2);
                newsItems.setTitle(title);
                newsItems.setDate(date);
                newsItems.setNewsType(newstype);
            }
            c.close();
            db.close();
        } catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return newsItems;

    }

    /**
     * 根据newsType和currentPage从数据库中取数据
     *
     * @param newsType
     * @param currentPage
     * @return
     */
    public List<NewsItem> list(int newsType, int currentPage)
    {
        if(newsType == 3) {
            newsItemsGetGroup = new ArrayList<>();
            newsItemsGroup = listAll(newsType);
            /**第一次进来还没有加载数据*/
            if(newsItemsGroup.size() == 0) {
                return null;
            }
            for(int i = (currentPage - 1) * 8 ; i < currentPage * 8 ;i++) {
                /**当本地的数据加载完时结束，否则崩溃*/
                if(i >= newsItemsGroup.size())
                    return newsItemsGetGroup;
                else
                    newsItemsGetGroup.add(newsItemsGroup.get(i));
            }
            return newsItemsGetGroup;
        }else{
            newsItemsGetProduct = new ArrayList<>();
            newsItemsProduct = listAll(newsType);
            /**第一次进来还没有加载数据*/
            if(newsItemsProduct.size() == 0)
                return null;
            for(int i = (currentPage - 1) * 8 ; i < currentPage * 8 ;i++) {
                Log.e("currentPage",String.valueOf(currentPage));
                /**当本地的数据加载完时结束，否则崩溃*/
                if(i >= newsItemsProduct.size())
                    return newsItemsGetProduct;
                else
                    newsItemsGetProduct.add(newsItemsProduct.get(i));
            }
            return newsItemsGetProduct;
        }

    }

}
