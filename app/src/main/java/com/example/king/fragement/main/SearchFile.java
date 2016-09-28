package com.example.king.fragement.main;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.king.fragement.R;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Kings on 2016/1/21.
 */
public class SearchFile extends AppCompatActivity {

    static private ArrayList<String> search_result = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_file);

        ListView list = (ListView) findViewById(android.R.id.list);
        list.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,search_result));

        Button searchBn = (Button) findViewById(R.id.search);
        searchBn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (search_result != null) {
                    search_result.clear();
                }

                EditText et = (EditText) findViewById(R.id.file_name);
//                SearchFile.this.searchFile(et.getText().toString().trim(), "/sdcard");
                SearchFile.this.searchFile(et.getText().toString().trim(), Environment.getExternalStorageDirectory().getPath());
                ((ArrayAdapter)((ListView)SearchFile.this.findViewById(android.R.id.list)).getAdapter()).notifyDataSetChanged();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private void searchFile(String filename, String path) {
        File[] files = null;
        try {
            files = new File(path).listFiles();
        } catch (Exception e) {
            files = null;
            Toast.makeText(this, getString(R.string.open_file_err), Toast.LENGTH_SHORT).show();
            return;
        }

        for (File file : files) {
            if (file.isDirectory() && file.listFiles() != null) {
                searchFile(filename, file.getPath());
            } else if (file.isFile()) {
                if (filename == null || filename.isEmpty()) {
                    search_result.add(file.getPath());
                } else {
                    String name = file.getName();
                    if (name.indexOf(filename) != -1) {
                        search_result.add(file.getPath());
                    }
                }
            }
        }
    }
}
