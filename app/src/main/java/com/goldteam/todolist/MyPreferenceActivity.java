package com.goldteam.todolist;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

import java.util.List;

/**
 * Created by justinhoyt on 4/23/17.
 */

public class MyPreferenceActivity extends PreferenceActivity
{
    @Override
    public void onBuildHeaders(List<Header> target)
    {
        loadHeadersFromResource(R.xml.headers_preference, target);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        PreferenceManager.setDefaultValues(this, R.xml.fragment_preference, false);
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);

        if (settings.getBoolean("checkBoxPref", true)) {
            setTheme(android.R.style.Theme_Holo);
        } else {
            setTheme(android.R.style.Theme_Holo_Light);
        }

        super.onCreate(savedInstanceState);


    }

    @Override
    protected boolean isValidFragment(String fragmentName)
    {
        return MyPreferenceFragment.class.getName().equals(fragmentName);
    }
}