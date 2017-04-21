package com.goldteam.todolist;

import android.app.Activity;
import android.os.Bundle;


public class DetailActivity extends Activity{
    public static final String LIST_ID = "id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TasksFragment tasksFragment = (TasksFragment)
                getFragmentManager().findFragmentById(R.id.detail_frag);
        int professorId = (int) getIntent().getExtras().get(LIST_ID);
        tasksFragment.setProfessor(professorId);
    }
}
