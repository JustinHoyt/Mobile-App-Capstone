package com.goldteam.todolist;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.goldteam.todolist.Database.DatabaseHelper;


public class TasksFragment extends Fragment {
    private long listId;
    private TextView ListName;

    public SharedPreferences preferences;

    private DatabaseHelper db;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            listId = savedInstanceState.getLong("listId");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //View view = inflater.inflate(R.layout.tasks, container, false);
        db = new DatabaseHelper(inflater.getContext());
        //List list = List.LISTs[(int) listId];

        //LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.professorLayout);
//        String[] degrees = list.getDegrees().split("; ");
//        for( String degree : degrees){
//            TextView textView = new TextView(getActivity());
//            textView.setPadding(8, 16, 8, 16);
//            textView.setTextSize(20f);
//            textView.setText(degree);
//            linearLayout.addView(textView);
//        }
        //return view;
        return inflater.inflate(R.layout.tasks, container, false);
    }


    @Override
    public void onStart() {
        super.onStart();
        View view = getView();
        db.getWritableDatabase();

        preferences = PreferenceManager.getDefaultSharedPreferences(view.getContext());
        ListName = (TextView) view.findViewById(R.id.list_name);

        if (view != null) {
            //List list = List.LISTs[(int) listId];
            String listName = db.readList((int) listId);

            ListName.setText(listName);
        }
    }


    public void setProfessor(long professorId) {
        this.listId = professorId;
    }
}
