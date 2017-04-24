package com.goldteam.todolist;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;


public class DetailActivity extends Activity{
    public static final String LIST_ID = "id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PreferenceManager.setDefaultValues(this, R.xml.fragment_preference, false);
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);

        if (settings.getBoolean("checkBoxPref", true)) {
            setTheme(android.R.style.Theme_Holo);
        } else {
            setTheme(android.R.style.Widget_Holo_Light_ActionBar);
        }

        setContentView(R.layout.activity_detail);

        TasksFragment tasksFragment = (TasksFragment)
                getFragmentManager().findFragmentById(R.id.detail_frag);
        int professorId = (int) getIntent().getExtras().get(LIST_ID);
        tasksFragment.setProfessor(professorId);
    }
}
