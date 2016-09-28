package com.example.king.fragement.main.preference;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.example.king.fragement.R;

/**
 * Created by Kings on 2016/3/5.
 */
public class SettingFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference);
    }
}
