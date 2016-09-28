package com.example.king.fragement.main.hightlight;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.king.fragement.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kings on 2016/1/22.
 */
public class HightLight extends AppCompatActivity {

    private ListView mListView;
    private EditText et_input;
    private String userInput = "";
    private List<Data> dataList = new ArrayList<Data>();
    private char[] chs = { 'A', 'B', 'C', 'D', 'E', 'M', 'L', 'F', 'X', 'W',
            'Z' };
    private HightKeywordsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hightlight);

        findViewById();
        initListViewData();
        setListener();
    }

    private void setListener() {

        et_input.addTextChangedListener(watcher);

        mAdapter = new HightKeywordsAdapter();

        mListView.setAdapter(mAdapter);
    }

    private TextWatcher watcher = new TextWatcher() {

        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {

            userInput = String.valueOf(s);

            mAdapter.notifyDataSetChanged();

        }

        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {

        }

        public void afterTextChanged(Editable s) {

        }
    };

    private void initListViewData() {

        for (char ch : chs) {

            for (int i = 0; i < 5; i++) {

                Data data = new Data(String.valueOf(ch) + "Chinese",
                        String.valueOf(ch) + i, String.valueOf(ch));

                dataList.add(data);
            }
        }

    }

    private void findViewById() {
        et_input = (EditText) findViewById(R.id.et_input);
        mListView = (ListView) findViewById(R.id.mListView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * Customize Adapter, so that realize highlighted keywords
     */
    private class HightKeywordsAdapter extends BaseAdapter {

        @Override
        public int getCount() {

            return dataList.size();
        }

        @Override
        public Object getItem(int position) {

            return dataList.get(position);
        }

        @Override
        public long getItemId(int position) {

            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View view;
            ViewHolder holder;

            if (convertView == null) {

                holder = new ViewHolder();

                view = View.inflate(getApplicationContext(),
                        R.layout.list_item_hightlight, null);

                holder.tv_name = (TextView) view.findViewById(R.id.tv_name);
                holder.tv_en = (TextView) view.findViewById(R.id.tv_en);
                holder.tv_sen = (TextView) view.findViewById(R.id.tv_sen);

                view.setTag(holder);
            } else {
                view = convertView;
                holder = (ViewHolder) view.getTag();
            }

            Data data = dataList.get(position);

            String name = data.getName();

            if (name != null && name.contains(userInput)) {

                int index = name.indexOf(userInput);

                int len = userInput.length();

                Spanned temp = Html.fromHtml(name.substring(0, index)
                        + "<u><font color=#FF0000>"
                        + name.substring(index, index + len) + "</font></u>"
                        + name.substring(index + len, name.length()));

                holder.tv_name.setText(temp);
            } else {
                holder.tv_name.setText(data.getName());
            }

            holder.tv_en.setText(data.getEn());
            holder.tv_sen.setText(data.getSen());

            return view;
        }
    }

    static class ViewHolder {
        public TextView tv_name;
        public TextView tv_en;
        public TextView tv_sen;

    }

}
