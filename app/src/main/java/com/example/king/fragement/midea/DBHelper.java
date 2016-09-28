package com.example.king.fragement.midea;

/**
 * Created by test on 15-11-14.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper
{
    private static final String DB_NAME = "midea_app";

    public DBHelper(Context context)
    {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        /**
         * id,title,link,date,imgLink,content,newstype
         * 把title作为主键防止出现重复
         */
        String sql = "create table tb_newsItem( _id integer primary key autoincrement , "
                + " title text , link text , date text , imgLink text , content text , newstype integer );";
//        String sql = "create table tb_newsItem( _id integer primary key autoincrement , "
//                + " title text , link text , date text , imgLink text , content text , newstype integer ,currentpage integer );";
//        String sql = "create table tb_newsItem( _id integer primary key autoincrement , "
//                + " title text , link text , date text , imgLink text , content text , newstype integer  );";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        // TODO Auto-generated method stub

    }

}