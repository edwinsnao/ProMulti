package com.example.king.fragement.main.maps;

/**
 * Created by fazhao on 2016/12/5.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
* 暂时不用加入，到时候还是用trace的来查询
* */
public class StepDBHelper extends SQLiteOpenHelper
{
    private static final String DB_NAME = "step";

    public StepDBHelper(Context context)
    {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        /**
         * id,title,link,date,imgLink,content,newstype,currentpage
         * 把title作为主键防止出现重复
         */
        String sql = "create table step_item( _id integer primary key autoincrement , "
                + " name text , address text , date text , latitude real , longitude real ,tag integer , step integer );";
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
