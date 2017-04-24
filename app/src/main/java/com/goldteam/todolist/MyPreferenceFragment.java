package com.goldteam.todolist;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by justinhoyt on 4/23/17.
 */

public class MyPreferenceFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the fragment_preference from an XML resource
        addPreferencesFromResource(R.xml.fragment_preference);
    }
}
