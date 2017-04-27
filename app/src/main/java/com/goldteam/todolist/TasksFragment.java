package com.goldteam.todolist;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.goldteam.todolist.Database.DatabaseHelper;
import com.goldteam.todolist.Database.Task;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class TasksFragment extends Fragment {
    private long listId;
    private TextView ListName;
    private List<Task> tasks;
    private ArrayAdapter<String> adapter;
    private ListView TaskList;
    private EditText newTask;
    private Button addTask;

    private DatabaseHelper db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Iconify.with(new FontAwesomeModule());
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

        final List<String> taskList = new ArrayList<>();

        for (Task task : tasks) {
            taskList.add(task.getName());
        }


        adapter = new ListItemAdapter(
                getView().getContext(),
                android.R.layout.simple_list_item_1,
                taskList,
                db,
                (int) listId,
                new Runnable() {
                    @Override
                    public void run() {
                        refreshTasks();
                    }
                }
        );
        TaskList.setAdapter(adapter);
    }


    @Override
    public void onStart() {
        super.onStart();
        View view = getView();

        ListName = (TextView) getView().findViewById(R.id.list_name);
        TaskList = (ListView) getView().findViewById(R.id.task_list);
        refreshTasks();
        String listName = db.readList((int) listId);
        getActivity().setTitle(listName);
        newTask = (EditText) view.findViewById(R.id.new_task);
        newTask.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    String newTaskText = newTask.getText().toString();
                    new writeTaskThread().execute(newTaskText);
                    newTask.setText("");
                    return true;
                }
                return false;
            }
        });
        addTask = (Button) view.findViewById(R.id.add_task);

        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newTaskText = newTask.getText().toString();
                new writeTaskThread().execute(newTaskText);
                refreshTasks();
            }
        });
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

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            refreshTasks();
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
