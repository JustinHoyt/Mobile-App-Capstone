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

import org.w3c.dom.Text;

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
        db = new DatabaseHelper(inflater.getContext());
        return inflater.inflate(R.layout.tasks, container, false);
    }


    @Override
    public void onStart() {
        super.onStart();
        View view = getView();

        db.getWritableDatabase();
        tasks = db.readTasks((int) listId);

        ListName = (TextView) view.findViewById(R.id.list_name);

        TaskList = (ListView) view.findViewById(R.id.task_list);
        adapter = new ArrayAdapter<String>(
                view.getContext(),
                android.R.layout.simple_expandable_list_item_1,
                tasks);
        TaskList.setAdapter(adapter);
        TaskList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                db.deleteTask(((TextView) view).getText().toString());

                for(int i=0; i<tasks.size();i++) {
                    if (tasks.get(i).equals(((TextView) view).getText().toString())) {
                        tasks.remove(i);
                    }
                }
                adapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_expandable_list_item_1, tasks);
                TaskList.setAdapter(adapter);
            }
        });

        newTask = (EditText) view.findViewById(R.id.new_task);
        addTask = (Button) view.findViewById(R.id.add_task);
        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newTaskText = newTask.getText().toString();
                db.writeTask(newTaskText, (int) listId);
            }
        });

        if (view != null) {
            String listName = db.readList((int) listId);

            ListName.setText(listName);
        }
    }


    public void setProfessor(long professorId) {
        this.listId = professorId;
    }
}
