package com.goldteam.todolist;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.goldteam.todolist.Database.DatabaseHelper;
import com.goldteam.todolist.Database.Task;
import com.joanzapata.iconify.widget.IconButton;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.List;
import java.util.Random;

/**
 * Created by ricokahler on 4/24/17.
 */

public class ListItemAdapter extends ArrayAdapter<String> {
    private DatabaseHelper db;
    private LinearLayout selectedItem;
    private int selectedPosition;
    private int listId;
    private Runnable runnable;


    private static final int deleteButtonId = View.generateViewId();
    private static final int editButtonId = View.generateViewId();
    private static final int textViewId = View.generateViewId();
    private static final int editTextId = View.generateViewId();
    private static final int checkBoxId = View.generateViewId();


    public ListItemAdapter(Context context, int resource, List<String> items, DatabaseHelper db, int listId, Runnable runnable) {
        super(context, resource, items);
        selectedPosition = 0;
        this.db = db;
        this.listId = listId;
        this.runnable = runnable;
    }

    private void changeToEditing(final LinearLayout item, final String text) {
        if (item.findViewById(editTextId) == null) {
            final EditText editText = new EditText(getContext());
            editText.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    1f
            ));
            editText.setId(editTextId);
            editText.setMaxLines(1);
            editText.setSingleLine(true);
            editText.setLines(1);
            editText.setImeOptions(EditorInfo.IME_ACTION_DONE);
            editText.setSelectAllOnFocus(true);
            editText.setText(text);
            editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        final Task task = new Task();
                        task.setName(v.getText().toString());
                        final CheckBox checkBox = (CheckBox) item.findViewById(checkBoxId);
                        task.setCompleted(checkBox.isChecked());
                        db.updateTask(text, task);
                        changeToNonEditingSelected(item, text);
                    }
                    return false;
                }
            });
            if (item.findViewById(textViewId) != null) {
                item.removeView(item.findViewById(textViewId));
            }
            item.addView(editText, 1);
            editText.requestFocus();
        }
    }

    private void changeToNonEditingUnselected(final LinearLayout item, final String text) {
        changeToNonEditingSelected(item, text);
        item.setBackgroundColor(Color.TRANSPARENT);
        item.removeView(item.findViewById(deleteButtonId));
        item.removeView(item.findViewById(editButtonId));
    }

    private void changeToNonEditingSelected(final LinearLayout item, final String text) {
        if (item.findViewById(editButtonId) == null) {
            final IconButton editButton = new IconButton(getContext());
            editButton.setId(editButtonId);
            editButton.setText("{fa-pencil}");
            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (item.findViewById(editTextId) == null) {
                        // if there isn't an EditText then we can go into an editing state
                        changeToEditing(item, text);
                    } else {
                        // if there is an EditText then we can save the changes and
                        // go back to a non-editing state
                        final EditText editText = (EditText) item.findViewById(editTextId);
                        final Task task = new Task();
                        task.setName(editText.getText().toString());
                        task.setListId(listId);
                        final CheckBox checkBox = (CheckBox) item.findViewById(checkBoxId);
                        task.setCompleted(checkBox.isChecked());
                        db.updateTask(text, task);
                        changeToNonEditingSelected(item, text);
                    }
                }
            });
            item.addView(editButton);
        }

        if (item.findViewById(deleteButtonId) == null) {
            IconButton buttonDelete = new IconButton(getContext());
            buttonDelete.setId(deleteButtonId);
            buttonDelete.setText("{fa-remove}");
            buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    db.deleteTask(text);
                    runnable.run();
                }
            });
            item.addView(buttonDelete);
        }

        if (item.findViewById(editTextId) != null) {
            item.removeView(item.findViewById(editTextId));
        }

        if (item.findViewById(textViewId) == null) {
            final TextView textView = new TextView(getContext());
            textView.setText(text);
            textView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1f
            ));
            textView.setId(textViewId);
            item.addView(textView, 1);
        }

        item.setBackgroundColor(Color.BLUE);
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        if (view == null) {
            view = new LinearLayout(getContext());
            final LinearLayout item = (LinearLayout) view;
            item.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
            ));
            item.setGravity(Gravity.CENTER);

            final CheckBox checkBox = new CheckBox(getContext());
            checkBox.setId(checkBoxId);
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // bubble event
                    item.performClick();
                    final Task task = new Task();
                    task.setName(getItem(position));
                    task.setCompleted(checkBox.isChecked());
                    task.setListId(listId);
                    db.updateTask(getItem(position), task);
                }
            });
            final Task task = db.readTask(getItem(position), (int) listId);
            checkBox.setChecked(task.isCompleted());

            item.addView(checkBox);
            changeToNonEditingUnselected(item, getItem(position));

            final ListItemAdapter that = this;
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (that.selectedItem != item) {
                        if (that.selectedItem != null) {
                            changeToNonEditingUnselected(that.selectedItem, getItem(that.selectedPosition));
                        }
                        changeToNonEditingSelected(item, getItem(position));
                        that.selectedItem = item;
                        that.selectedPosition = position;
                    }
                }
            });
        }

        return view;
    }
}
