package com.goldteam.todolist;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.goldteam.todolist.Database.DatabaseHelper;

import java.util.List;
import java.util.concurrent.ExecutionException;


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
    public void onCreate(Bundle savedInstanceState) {
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

    public void refreshTasks() {
        tasks = db.readTasks((int) listId);

        adapter = new ArrayAdapter<String>(
                getView().getContext(),
                android.R.layout.simple_expandable_list_item_1,
                tasks);
        TaskList.setAdapter(adapter);

        String listName = db.readList((int) listId);
        getActivity().setTitle(listName);
    }


    @Override
    public void onStart() {
        super.onStart();
        View view = getView();

        ListName = (TextView) getView().findViewById(R.id.list_name);
        TaskList = (ListView) getView().findViewById(R.id.task_list);
        refreshTasks();
        newTask = (EditText) view.findViewById(R.id.new_task);
        addTask = (Button) view.findViewById(R.id.add_task);
        TaskList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                new deleteTaskThread().execute(((TextView) view).getText().toString());

                for(int i=0; i<tasks.size();i++) {
                    if (tasks.get(i).equals(((TextView) view).getText().toString())) {
                        tasks.remove(i);
                    }
                }
                adapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_expandable_list_item_1, tasks);
                TaskList.setAdapter(adapter);
            }
        });

        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newTaskText = newTask.getText().toString();
                new writeTaskThread().execute(newTaskText);

                refreshTasks();
            }
        });

//        if (view != null) {
//            String listName = db.readList((int) listId);
//
//            ListName.setText(listName);
//        }
    }


    public void setProfessor(long professorId) {
        this.listId = professorId;
    }

    private class writeTaskThread extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] params) {
            db.writeTask((String) params[0], (int) listId);
            return null;
        }
    }

    private class deleteTaskThread extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] params) {
            db.deleteTask((String) params[0]);
            return null;
        }
    }
}
