package com.goldteam.todolist;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.goldteam.todolist.Database.DataDefinitions;
import com.goldteam.todolist.Database.DatabaseHelper;

import java.io.File;

public class MainActivity extends Activity implements ListsFragment.ListsListener {
    public DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void itemClicked(long id) {
        View fragmentContainer = findViewById(R.id.fragment_container);
        if (fragmentContainer != null) {
            TasksFragment details = new TasksFragment();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            details.setProfessor(id);
            ft.replace(R.id.fragment_container, details, "TASK");
            ft.addToBackStack(null);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();
        } else {
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra(DetailActivity.LIST_ID, (int)id);
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.preferences:
            {
                Intent intent = new Intent();
                intent.setClassName(this, "com.goldteam.todolist.MyPreferenceActivity");
                startActivity(intent);
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
