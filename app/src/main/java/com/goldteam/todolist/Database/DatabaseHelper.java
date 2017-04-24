package com.goldteam.todolist.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by drews on 4/21/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper{
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "ToDo.db";
    public static DataDefinitions dd;

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
        ContentValues values = new ContentValues();
        values.put(DataDefinitions.TableDefinitions.LIST_NAME, list);
        db.insert(DataDefinitions.TableDefinitions.LIST_TABLE_NAME, null, values);
    }

    public void writeTask(String task, int listID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DataDefinitions.TableDefinitions.TASK_NAME, task);
        values.put(DataDefinitions.TableDefinitions.LIST_FK, listID);
        db.insert(DataDefinitions.TableDefinitions.TASK_TABLE_NAME, null, values);
    }

    public void writeChecked(String task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DataDefinitions.TableDefinitions.IS_CHECKED, 1);

        String selection = DataDefinitions.TableDefinitions.TASK_NAME + " LIKE ?";
        String[] selectionArgs = { task };

        int count = db.update(
                DataDefinitions.TableDefinitions.TASK_TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }

    public void writeUnchecked(String task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DataDefinitions.TableDefinitions.IS_CHECKED, 0);

        String selection = DataDefinitions.TableDefinitions.TASK_NAME + " LIKE ?";
        String[] selectionArgs = { task };

        int count = db.update(
                DataDefinitions.TableDefinitions.TASK_TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }

    public List<String> readLists() {
        SQLiteDatabase db = this.getReadableDatabase();
        List lists = new ArrayList<>();

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

    public List<String> readTasks(int listID) {
        SQLiteDatabase db = this.getReadableDatabase();
        List tasks = new ArrayList<>();

        Cursor cursor = db.rawQuery("select * from " +
                DataDefinitions.TableDefinitions.TASK_TABLE_NAME +
                " where " +
                DataDefinitions.TableDefinitions.LIST_FK +
                " = " +
                listID, null);
        while(cursor.moveToNext()) {
            String list = cursor.getString(
                    cursor.getColumnIndexOrThrow(DataDefinitions.TableDefinitions.TASK_NAME));
            tasks.add(list);
        }
        cursor.close();
        return tasks;
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

        String selection = DataDefinitions.TableDefinitions.TASK_NAME + " LIKE ?";
        String[] selectionArgs = { task };
        db.delete(DataDefinitions.TableDefinitions.TASK_TABLE_NAME, selection, selectionArgs);
    }
}
