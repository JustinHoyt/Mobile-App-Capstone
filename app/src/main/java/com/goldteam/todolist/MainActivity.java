package com.goldteam.todolist;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.goldteam.todolist.Database.DatabaseHelper;

public class MainActivity extends Activity implements ListsFragment.ListsListener {
    public DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DatabaseHelper(this);
        db.writeLists("To Do");
        db.writeLists("Shopping");
    }

    @Override
    public void itemClicked(long id) {
        View fragmentContainer = findViewById(R.id.fragment_container);
        if (fragmentContainer != null) {
            TasksFragment details = new TasksFragment();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            details.setProfessor(id);
            ft.replace(R.id.fragment_container, details);
            ft.addToBackStack(null);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();
        } else {
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra(DetailActivity.LIST_ID, (int)id);
            startActivity(intent);
        }
    }
}
