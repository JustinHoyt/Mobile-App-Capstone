package com.goldteam.todolist.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by drews on 4/21/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper{
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "ToDo.db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DataDefinitions.SQL_CREATE_LIST_TABLE);
        db.execSQL(DataDefinitions.SQL_CREATE_TASK_TABLE);
        for (String sqlCommand : DataDefinitions.SQL_SEED_LIST_TABLE) {
            db.execSQL(sqlCommand);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DataDefinitions.SQL_DELETE_LIST_TABLE);
        db.execSQL(DataDefinitions.SQL_DELETE_TASK_TABLE);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void writeLists(String list) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        ContentValues values = new ContentValues();
        values.put(DataDefinitions.TableDefinitions.LIST_NAME, list);
        db.insert(DataDefinitions.TableDefinitions.LIST_TABLE_NAME, null, values);
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public void writeTask(String task, int listID) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        ContentValues values = new ContentValues();
        values.put(DataDefinitions.TableDefinitions.TASK_NAME, task);
        values.put(DataDefinitions.TableDefinitions.TASK_COMPLETED, 0);
        values.put(DataDefinitions.TableDefinitions.LIST_FK, listID);

        db.insert(DataDefinitions.TableDefinitions.TASK_TABLE_NAME, null, values);
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public void updateTask(String currentTaskName, Task task) {
        final String taskName = task.getName();
        final boolean completed = task.isCompleted();
        final int listId = task.getListId();
        final SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        final ContentValues values = new ContentValues();
        values.put(DataDefinitions.TableDefinitions.TASK_NAME, taskName);
        values.put(DataDefinitions.TableDefinitions.TASK_COMPLETED, /*if*/ completed ? 1 : 0);
        values.put(DataDefinitions.TableDefinitions.LIST_FK, listId);
        final int rows = db.update(
                DataDefinitions.TableDefinitions.TASK_TABLE_NAME,
                values,
                DataDefinitions.TableDefinitions.TASK_NAME + " = ? ",
                new String[]{currentTaskName}
        );
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public List<String> readLists() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<String> lists = new ArrayList<>();

        Cursor cursor = db.rawQuery("select * from " + DataDefinitions.TableDefinitions.LIST_TABLE_NAME, null);
        while(cursor.moveToNext()) {
            String list = cursor.getString(
                    cursor.getColumnIndexOrThrow(DataDefinitions.TableDefinitions.LIST_NAME));
            lists.add(list);
        }
        cursor.close();
        return lists;
    }

    public String readList(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        int listID = id+1;
        String list = "";

        Cursor cursor = db.rawQuery("select * from " +
                DataDefinitions.TableDefinitions.LIST_TABLE_NAME +
                " where " +
                DataDefinitions.TableDefinitions.ID +
                " = " +
                listID,
                null);

        while(cursor.moveToNext()) {
            list = cursor.getString(
                    cursor.getColumnIndexOrThrow(DataDefinitions.TableDefinitions.LIST_NAME));
        }
        cursor.close();
        return list;
    }

    public List<Task> readTasks(int listID) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Task> tasks = new ArrayList<>();

        Cursor cursor = db.rawQuery("select * from " +
                DataDefinitions.TableDefinitions.TASK_TABLE_NAME +
                " where " +
                DataDefinitions.TableDefinitions.LIST_FK +
                " = " +
                listID, null);
        while(cursor.moveToNext()) {
            String taskName = cursor.getString(
                    cursor.getColumnIndexOrThrow(DataDefinitions.TableDefinitions.TASK_NAME)
            );
            final boolean completed = cursor.getInt(
                    cursor.getColumnIndexOrThrow(DataDefinitions.TableDefinitions.TASK_COMPLETED)
            ) == 1;
            final Task task = new Task();
            task.setName(taskName);
            task.setCompleted(completed);
            task.setListId(listID);
            tasks.add(task);
        }
        cursor.close();
        return tasks;
    }

    public Task readTask(String name, int listId) {
        final List<Task> tasks = readTasks(listId);
        for (Task task : tasks) {
            if (task.getName().equals(name)) {
                return task;
            }
        }
        return null;
    }

    public void deleteLists(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "delete from " +
                DataDefinitions.TableDefinitions.LIST_TABLE_NAME +
                " where " +
                DataDefinitions.TableDefinitions.ID +
                " = " +
                id;
        db.rawQuery(query,
                null);
    }

    public void deleteTask(String task) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();

        String selection = DataDefinitions.TableDefinitions.TASK_NAME + " LIKE ?";
        String[] selectionArgs = { task };
        db.delete(DataDefinitions.TableDefinitions.TASK_TABLE_NAME, selection, selectionArgs);
        db.setTransactionSuccessful();
        db.endTransaction();
    }

}
