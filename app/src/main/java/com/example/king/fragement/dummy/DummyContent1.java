package com.example.king.fragement.dummy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.king.fragement.FileTest;
import com.example.king.fragement.R;
import com.example.king.fragement.com.example.king.sorting.SortingActivity;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

//import com.example.king.aidl.AidlCilent;
//import com.example.king.fragement.DayDreamService;
//import com.example.king.fragement.Download;
//import com.example.king.fragement.HttpClientTest;
//import com.example.king.fragement.MainActivity1;
//import com.example.king.fragement.MapsActivity2;
//import com.example.king.fragement.R;
//import com.example.king.fragement.TestActivity;
//import com.example.king.net.SocketTest;
//import com.example.king.net.SocketTest;

public class DummyContent1
        extends BaseAdapter
{
    public static LinkedList<DummyItem> ITEMS = new LinkedList();
    int COUNT = 20;
    int i = COUNT;
    Map<String, DummyItem> ITEM_MAP = new HashMap();
    Map<View, Map<Integer, View>> cache = new HashMap();
    final Context context;

    public DummyContent1(Context paramContext, LinkedList<DummyItem> paramLinkedList)
    {
//        ITEMS.add(new DummyItem(1L, "TestActivity.class", TestActivity.class, "A demo", "10-4"));
//        ITEMS.add(new DummyItem(2L, "Download.class", Download.class, "A demo", "10-4"));
//        ITEMS.add(new DummyItem(3L, "MainActivity1.class", MainActivity1.class, "A demo", "10-4"));
//        ITEMS.add(new DummyItem(4L, "HttpClientTest.class", HttpClientTest.class, "A demo", "10-4"));
//        ITEMS.add(new DummyItem(5L, "OsLogin.class", OsLogin.class, "A demo", "10-5"));
//        ITEMS.add(new DummyItem(6L, "FileTest.class", FileTest.class, "A demo", "10-8"));
//        ITEMS.add(new DummyItem(7L, "Broadcasr.class", BroadcastTest.class, "A demo", "10-9"));
////        ITEMS.add(new DummyItem(8L, "DayDream.class", DayDreamService.class, "A demo", "10-9"));
////        ITEMS.add(new DummyItem(9L, "Map.class", MapsActivity2.class, "A demo", "10-9"));
////        ITEMS.add(new DummyItem(10L, "JniTest.class", JniTest.class, "A demo", "10-9"));
////        ITEMS.add(new DummyItem(11L, "Aidl.class", AidlCilent.class, "A demo", "10-9"));
//        ITEMS.add(new DummyItem(11L, "Process.class", QueryProcess.class, "A demo", "10-10"));
//        ITEMS.add(new DummyItem(11L, "MainActivity.class",MainActivity.class, "A demo", "10-10"));
//        ITEMS.add(new DummyItem(11L, "AboutUs.class",AboutUs.class, "A demo", "10-10"));
//        ITEMS.add(new DummyItem(11L, "SocketTest.class", SocketTest.class, "A demo", "10-10"));
        ITEMS.add(new DummyItem(1,"Sorting.class", SortingActivity.class,"A demo","10-25"));
        int i = 0;
//        while (i < 100)
        while (i < COUNT)
        {
            ITEMS.add(new DummyItem(30L, "Random.class", FileTest.class, "A demo", "10-10"));
            i += 1;
        }
        this.context = paramContext;
        ITEMS = paramLinkedList;
    }

    public void addMoreItems(int count){
//        COUNT += count;
//        notifyDataSetChanged();
        int j = 0;
//        while (i < 100)
        while (j++ < count)
        {
            ITEMS.add(new DummyItem(i, "Random.class", FileTest.class, "A demo", "10-10"));
            i ++;
        }
//        notifyDataSetInvalidated();
        /*
        * 需要notifydatasetchang（）才可以生效
        * */
        notifyDataSetChanged();
    }

    public static LinkedList<DummyItem> getItems()
    {
        return ITEMS;
    }

    public int getCount()
    {
        return ITEMS.size();
    }

    public Object getItem(int paramInt)
    {
        return ITEMS.get(paramInt);
    }

    public long getItemId(int paramInt)
    {
        return ((DummyItem)getItem(paramInt)).getId();
    }

    public View getView(int paramInt, View convertView, ViewGroup paramViewGroup)
    {
        /*
        * ViewHolder优化
        *
        * */
        final ViewHolder viewHolder;
//        Object localObject1 = paramView;
        View v = convertView;
        TextView name;
        TextView time;
        TextView id;
//        paramView = (View)localObject1;
        if (v == null) {
//            v = ((LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.my_layout, paramViewGroup, false);
            LayoutInflater inflater  = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.my_layout,paramViewGroup,false);
//            viewHolder = new ViewHolder();
        }
//        Object localObject2 = (Map)this.cache.get(paramView);
//        Object localObject3;
        Map<Integer,View> itemMap = cache.get(v);
        if (itemMap == null)
        {
            viewHolder = new ViewHolder();
            itemMap = new HashMap<Integer,View>();
            viewHolder.image = (ImageView) v.findViewById(R.id.image);
            viewHolder.activity = (TextView)v.findViewById(R.id.activity);
            viewHolder.time = (TextView)v.findViewById(R.id.time);
            viewHolder.id = (TextView)v.findViewById(R.id.id);
//            ((Map)localObject3).put(Integer.valueOf(2131689694), localObject1);
//            ((Map)localObject3).put(Integer.valueOf(2131689696), localObject2);
//            ((Map)localObject3).put(Integer.valueOf(2131689695), paramViewGroup);
            itemMap.put(R.id.activity,viewHolder.activity);
            itemMap.put(R.id.id,viewHolder.id);
            itemMap.put(R.id.time,viewHolder.time);

            this.cache.put(v, itemMap);
            v.setTag(viewHolder);

        } else {
//            name = (TextView) itemMap.get(R.id.activity);
//            id = (TextView) itemMap.get(R.id.id);
//            time = (TextView) itemMap.get(R.id.time);
            viewHolder = (ViewHolder) v.getTag();
        }
//        for (;;)
//        {
//            localObject3 = (DummyItem)getItem(paramInt);
//            ((TextView)localObject1).setText(((DummyItem)localObject3).getName());
//            paramViewGroup.setText(String.valueOf(((DummyItem)localObject3).getId()));
//            ((TextView)localObject2).setText(((DummyItem)localObject3).getTime());
//            localObject1 = (TextView)((Map)localObject2).get(Integer.valueOf(2131689694));
//            paramViewGroup = (TextView)((Map)localObject2).get(Integer.valueOf(2131689695));
//            localObject2 = (TextView)((Map)localObject2).get(Integer.valueOf(2131689696));
//        }

        DummyItem item = (DummyItem) getItem(paramInt);
        viewHolder.activity.setText(item.getName());
        viewHolder.time.setText(item.getTime());
        viewHolder.id.setText(String.valueOf(item.getId()));


        return v;
    }

    public void setItems(LinkedList<DummyItem> paramLinkedList)
    {
        ITEMS = paramLinkedList;
    }

    public class DummyItem
    {
        Class activity;
        public long id;
        String name;
        String time;
        String title;

        public DummyItem(long paramLong, String paramString1, Class paramClass, String paramString2, String paramString3)
        {
            this.name = paramString1;
            this.id = paramLong;
            this.time = paramString3;
            this.title = paramString2;
            this.activity = paramClass;
        }

        public Class getActivity()
        {
            return this.activity;
        }

        public long getId()
        {
            return this.id;
        }

        public String getName()
        {
            return this.name;
        }

        public String getTime()
        {
            return this.time;
        }

        public String getTitle()
        {
            return this.title;
        }

        public void setActivity(Class paramClass)
        {
            this.activity = paramClass;
        }

        public void setId(long paramLong)
        {
            this.id = paramLong;
        }

        public void setName(String paramString)
        {
            this.name = paramString;
        }

        public void setTime(String paramString)
        {
            this.time = paramString;
        }

        public void setTitle(String paramString)
        {
            this.title = paramString;
        }
    }
    static class ViewHolder{
        public ImageView image;
        public TextView time;
        public TextView id;
        public TextView activity;
    }
}


/* Location:              D:\迅雷下载\dex2jar-2.0\classes-dex2jar.jar!\com\example\test\fragement\dummy\DummyContent_1.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */