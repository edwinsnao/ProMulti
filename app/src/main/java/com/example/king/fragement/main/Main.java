package com.example.king.fragement.main;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.example.king.fragement.AboutUs;
import com.example.king.fragement.BroadcastTest;
import com.example.king.fragement.FileTest;
import com.example.king.fragement.MapsActivity;
import com.example.king.fragement.MapsActivity1;
import com.example.king.fragement.NewsFragement;
import com.example.king.fragement.OsLogin;
import com.example.king.fragement.QueryProcess;
import com.example.king.fragement.R;
import com.example.king.fragement.SettingsActivity;
import com.example.king.fragement.midea.ItemListActivity;
import com.example.king.fragement.midea.ItemListActivity1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Kings on 2016/1/20.
 */
public class Main extends AppCompatActivity implements  MideaFragment.Callbacks,MideaFragment1.Callbacks{
    private DrawerLayout mDrawerLayout;
    Adapter adapter;
    long exitTime = 0;
    ViewPager viewPager;
    static Toolbar toolbar;
    static FloatingActionButton fab;
    int lastVisibleItemPosition = 0;
    //    static ListView list;
    static View mFooterView;
    //    TextView network;
    static boolean scrollFlag = false;
    //    public static List<Class> activities = new ArrayList<>();
    public static List<Map<String,Class>> activities_list = new ArrayList<>();
    public static List<String> time = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initViews();
//        fab.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                         /*
//             * 和list.setselection(0)一样
//             * */
//                        if (!list.isStackFromBottom()) {
//                            list.setStackFromBottom(true);
//                        }
//                        list.setStackFromBottom(false);
//                        Snackbar.make(v,"Demo",Snackbar.LENGTH_SHORT)
//                                .setAction("Action",null).show();
//                    }
//                });
//        mFooterView = LayoutInflater.from(MainActivity.this).inflate(R.layout.loading_view, null);
//        getListView().addFooterView(mFooterView, null, false);

    }

    private void initViews() {
        initData();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        list = (ListView) findViewById(android.R.id.list);
        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView nav = (NavigationView) findViewById(R.id.nav);
        if (nav != null) {
            setupDrawerContent(nav);
        }
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        if (viewPager != null) {
            setupViewPager(viewPager);
        }
        fab = (FloatingActionButton) findViewById(R.id.fab);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initData() {
        Map<String, Class> name_activities = new HashMap<>();
        name_activities.put("DialogPra", DialogPra.class);
        activities_list.add(name_activities);
//        activities.add(DialogPra.class);
        time.add("2016-1-15");
        name_activities = new HashMap<>();
        name_activities.put("QueryProcess", QueryProcess.class);
        activities_list.add(name_activities);
//        activities.add(QueryProcess.class);
        time.add("2015-10-10");
        name_activities = new HashMap<>();
        name_activities.put("Download", com.example.king.fragement.MainActivity.class);
        activities_list.add(name_activities);
//        activities.add(MainActivity.class);
        time.add("2015-10-10");
        name_activities = new HashMap<>();
        name_activities.put("AboutUs", AboutUs.class);
        activities_list.add(name_activities);
//        activities.add(AboutUs.class);
        time.add("2015-10-10");
        name_activities = new HashMap<>();
        name_activities.put("CSDN_News", NewsFragement.class);
        activities_list.add(name_activities);
//        activities.add(NewsFragement.class);
        time.add("2015-10-10");
        name_activities = new HashMap<>();
        name_activities.put("News", Main.class);
        activities_list.add(name_activities);
//        activities.add(Main.class);
        time.add("2015-10-10");
        name_activities = new HashMap<>();
        name_activities.put("Maps", MapsActivity.class);
        activities_list.add(name_activities);
//        activities.add(MapsActivity.class);
        time.add("2015-10-10");
        name_activities = new HashMap<>();
        name_activities.put("Maps1", MapsActivity1.class);
        activities_list.add(name_activities);
//        activities.add(MapsActivity1.class);
        time.add("2015-10-10");
        name_activities = new HashMap<>();
        name_activities.put("Setting", SettingsActivity.class);
        activities_list.add(name_activities);
//        activities.add(SettingsActivity.class);
        time.add("2015-10-10");
        name_activities = new HashMap<>();
        name_activities.put("Midea_News", ItemListActivity.class);
        activities_list.add(name_activities);
//        activities.add(ItemListActivity.class);
        time.add("2015-10-10");
        name_activities = new HashMap<>();
        name_activities.put("OSLogin", OsLogin.class);
        activities_list.add(name_activities);
//        activities.add(OsLogin.class);
        time.add("2015-10-10");
        name_activities = new HashMap<>();
        name_activities.put("Midea_News1", ItemListActivity1.class);
        activities_list.add(name_activities);
//        activities.add(ItemListActivity1.class);
        time.add("2015-10-10");
        name_activities = new HashMap<>();
        name_activities.put("FileTest", FileTest.class);
        activities_list.add(name_activities);
//        activities.add(FileTest.class);
        time.add("2015-10-10");
        name_activities = new HashMap<>();
        name_activities.put("Broadcast", BroadcastTest.class);
        activities_list.add(name_activities);
//        activities.add(BroadcastTest.class);
        time.add("2015-10-10");
    }

    @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            switch(item.getItemId()){
                case android.R.id.home:
                    mDrawerLayout.openDrawer(GravityCompat.START);
                    return true;
            }
            return super.onOptionsItemSelected(item);
        }
    private void setupViewPager(ViewPager viewPager){
        adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new CheeseListFragment(),"Category 1");
//        adapter.addFragment(new CheeseListFragment(),"Category 2");
        adapter.addFragment(new MideaFragment(),"Midea_News1");
//        adapter.addFragment(new CheeseListFragment(),"Category 3");
        adapter.addFragment(new MideaFragment1(),"Midea_News2");
        viewPager.setAdapter(adapter);
    }
    private void setupDrawerContent(NavigationView nav){
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                item.setChecked(true);
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
    }

    @Override
    public void onItemSelected(String id) {
//        if (mTwoPane) {
        // In two-pane mode, show the detail view in this activity by
        // adding or replacing the detail fragment using a
        // fragment transaction.
        int index = viewPager.getCurrentItem();
        if(adapter.getPageTitle(index) == "Midea_News") {
//        adapter getFragmentManager().findFragmentById(R.id.list);
            Bundle arguments = new Bundle();
            arguments.putString(com.example.king.fragement.midea.ItemDetailFragment.ARG_ITEM_ID, String.valueOf(id));
            com.example.king.fragement.midea.ItemDetailFragment fragment = new com.example.king.fragement.midea.ItemDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.item_detail_container, fragment)
                    .commit();
        }
        else if(adapter.getPageTitle(index) == "Midea_News1"){
            Bundle arguments = new Bundle();
            arguments.putString(com.example.king.fragement.midea.ItemDetailFragment.ARG_ITEM_ID, String.valueOf(id));
            com.example.king.fragement.midea.ItemDetailFragment fragment = new com.example.king.fragement.midea.ItemDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.item_detail_container, fragment)
                    .commit();
        }

    }
//        else {
//     In single-pane mode, simply start the detail activity
//     for the selected item ID.
//            Intent detailIntent = new Intent(this, NewsContentActivity.class);
//            detailIntent.putExtra(com.example.king.fragement.midea.ItemDetailFragment.ARG_ITEM_ID, id);
//            startActivity(detailIntent);
//        }
//    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment,String title){
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }
    }

