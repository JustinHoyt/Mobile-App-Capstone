package com.goldteam.todolist;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.goldteam.todolist.Database.DatabaseHelper;

import java.util.ArrayList;


public class ListsFragment extends ListFragment {
    static interface ListsListener {
        void itemClicked(long id);
    }

    private ListsListener listener;
    private DatabaseHelper db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        db = new DatabaseHelper(inflater.getContext());

        java.util.List<String> lists = new ArrayList<>();
        lists = db.readLists();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                inflater.getContext(),
                android.R.layout.simple_expandable_list_item_1,
                lists);

//        String[] names = new String[List.LISTs.length];
//        for (int i = 0; i < names.length; i++) {
//            names[i] = List.LISTs[i].getName();
//        }
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
//                inflater.getContext(),
//                android.R.layout.simple_list_item_1,
//                names);
        setListAdapter(adapter);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.listener = (ListsListener)activity;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        if (listener != null) {
            listener.itemClicked(id);
        }
    }

}
