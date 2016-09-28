package com.example.king.fragement.main.parcel_serial;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.king.fragement.R;

/**
 * Created by Kings on 2016/2/10.
 */
public class PagerActivity extends FragmentActivity {
    static Learn learn = null;
    static Book mBook = null;
    static ControlFragment controlFragment;
    static BookFragment bookFragment;
    static LearnFragment learnFragment;
    private MyAdapter adapter;
    private ViewPager pager;
    private static final String PAR_KEY = "Book";
    private static final String SER_KEY = "Learn";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parcel_serial_inpager);
        adapter = new MyAdapter(getSupportFragmentManager());

        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);
        pager.requestTransparentRegion(pager);
    }

    public static class BookFragment extends Fragment {
        TextView bookName;
        TextView publishTime;
        TextView authorName;

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Bundle bundle = getArguments();
            if(bundle != null){
                mBook = bundle.getParcelable(PAR_KEY);
            }
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//            return super.onCreateView(inflater, container, savedInstanceState);
            View v = inflater.inflate(R.layout.book_parcelable, container, false);
            bookName = (TextView) v.findViewById(R.id.name);
            authorName = (TextView) v.findViewById(R.id.authorName);
            publishTime = (TextView) v.findViewById(R.id.publishTime);
//            Bundle bundle = getArguments();
//            Book mBook = bundle.getParcelable(PAR_KEY);
            if (mBook != null) {
                bookName.setText(mBook.getName());
                publishTime.setText(mBook.getPublishTime());
                authorName.setText(mBook.getAuthor());
            }
            return v;
        }
    }

    public static class ControlFragment extends Fragment implements View.OnClickListener {
        Button putBook;
        Button clearBook;
        Button putLearn;
        Button clearLearn;

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//            return super.onCreateView(inflater, container, savedInstanceState);
            View v = inflater.inflate(R.layout.control_fragment, container, false);
            putBook = (Button) v.findViewById(R.id.putParcel);
            clearBook = (Button) v.findViewById(R.id.clearParcel);
            putLearn = (Button) v.findViewById(R.id.putSerial);
            clearLearn = (Button) v.findViewById(R.id.clearSerial);
            putBook.setOnClickListener(this);
            clearBook.setOnClickListener(this);
            putLearn.setOnClickListener(this);
            clearLearn.setOnClickListener(this);
            return v;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.putParcel:
                    if (bookFragment == null)
                        bookFragment = new BookFragment();
                    if (mBook == null) {
                        mBook = new Book();
                    }
                    mBook.setAuthor("fazhao");
                    mBook.setName("Action in Android");
                    mBook.setPublishTime("2016-2-10");
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(PAR_KEY, mBook);
                    bookFragment = null;
                    bookFragment = new BookFragment();
                    bookFragment.setArguments(bundle);
                    break;
                case R.id.clearParcel:
                    mBook = null;
                    break;
                case R.id.putSerial:
                    if (learnFragment == null)
                        learnFragment = new LearnFragment();
                    if (learn == null)
                        learn = new Learn();
                    learn.setName("Serializable & Parcelable");
                    learn.setDate("2016-2-10");
                    learn.setNeedToLearn("Many");
                    learn.setLast("18");
                    Bundle bundle1 = new Bundle();
                    bundle1.putSerializable(SER_KEY, learn);
                    /*
                    * 报错
                    * java.lang.IllegalStateException: Fragment already active
                    * 所以new一个
                    * */
                    learnFragment = null;
                    learnFragment = new LearnFragment();
                    learnFragment.setArguments(bundle1);
                    break;
                case R.id.clearSerial:
                    learn = null;
                    break;
            }
        }
    }

    public static class LearnFragment extends Fragment {
        TextView learnName;
        TextView needToLearn;
        TextView last;
        TextView time;

        /*
        * 加入了oncreate后，调用fragment.setArguments();
        * 就生效了，可以收到传递的数值和清除的响应。不加入oncreate之前是不行的
        * */
        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Bundle bundle = getArguments();
            if(bundle != null){
                learn = (Learn)bundle.getSerializable(SER_KEY);
            }
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//            return super.onCreateView(inflater, container, savedInstanceState);
//            return inflater.inflate(R.layout.learn_serial, container, false);
            View v = inflater.inflate(R.layout.learn_serial, container, false);
            learnName = (TextView) v.findViewById(R.id.learnName);
            needToLearn = (TextView) v.findViewById(R.id.needToLearn);
            last = (TextView) v.findViewById(R.id.last);
            time = (TextView) v.findViewById(R.id.time);
//            Bundle bundle = getArguments();
//            learn = bundle.getParcelable(SER_KEY);
            if (learn != null) {
                learnName.setText(learn.getName());
                needToLearn.setText(learn.getNeedToLearn());
                last.setText(learn.getLast());
                time.setText(learn.getDate());
            }
            return v;
        }
    }

    public static class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            /*
            * 因为这里有return，所以并不需要break;
            * */
            switch (position) {
                case 0:
//                    return new ControlFragment();
                    if (controlFragment == null)
                        controlFragment = new ControlFragment();
                    return controlFragment;
                case 1:
//                    return new LearnFragment();
                    if (learnFragment == null)
                        learnFragment = new LearnFragment();
                    return learnFragment;
                case 2:
//                    return new BookFragment();
                    if (bookFragment == null)
                        bookFragment = new BookFragment();
                    return bookFragment;
//                default:
//                    return null;
            }
            /*
            * 与default同效果
            * */
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}
