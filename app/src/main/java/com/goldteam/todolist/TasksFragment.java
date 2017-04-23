package com.goldteam.todolist;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.goldteam.todolist.Database.DatabaseHelper;

import java.util.*;
import java.util.List;


public class TasksFragment extends Fragment {
    private long listId;
    private TextView ListName;
    private List<String> tasks;
    private ArrayAdapter<String> adapter;
    private ListView TaskList;
    private EditText newTask;
    private Button addTask;
    //private ListsListener listener;


    //public SharedPreferences preferences;

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
        tasks = db.readTasks((int) listId);

        //preferences = PreferenceManager.getDefaultSharedPreferences(view.getContext());
        ListName = (TextView) view.findViewById(R.id.list_name);

        TaskList = (ListView) view.findViewById(R.id.task_list);
        TaskList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        //String[] test = {"test1", "test2", "test3"};
        adapter = new ArrayAdapter<String>(
                view.getContext(),
                android.R.layout.simple_expandable_list_item_1,
                tasks);
        TaskList.setAdapter(adapter);
        TaskList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                int currentPaintFlags = ((TextView)view).getPaintFlags();
                if((((TextView)view).getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG) > currentPaintFlags )
                    ((TextView)view).setPaintFlags(((TextView)view).getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                else {
                    ((TextView)view).setPaintFlags(0);
                }
            }
        });

        newTask = (EditText) view.findViewById(R.id.new_task);
        addTask = (Button) view.findViewById(R.id.add_task);
        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newTaskText = newTask.getText().toString();
                db.writeTask(newTaskText, (int) listId);

//                Fragment currentFragment = getFragmentManager().findFragmentByTag("TASK");
//                FragmentTransaction ft = getFragmentManager().beginTransaction();
//                ft.detach(getFragmentManager()
//                    .findFragmentByTag("TASK"))
//                    .attach(getFragmentManager()
//                    .findFragmentByTag("TASK"))
//                    .commit();
            }
        });

        //adapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1, professor.getDegreeList());
        //TaskList.setAdapter(adapter);

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
