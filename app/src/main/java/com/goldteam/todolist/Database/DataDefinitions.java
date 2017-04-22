package com.goldteam.todolist.Database;

import android.provider.BaseColumns;

/**
 * Created by drews on 4/21/2017.
 */

public class DataDefinitions {
    public DataDefinitions() {}

    public static abstract class TableDefinitions implements BaseColumns {
        public static final String LIST_TABLE_NAME = "lists";
        public static final String TASK_TABLE_NAME = "tasks";

        public static final String ID = "id";
        public static final String LIST_NAME = "list_name";
        public static final String TASK = "task";
        public static final String IS_CHECKED = "is_checked";

    }

    public static final String SQL_CREATE_LIST_TABLE = "CREATE TABLE " + TableDefinitions.LIST_TABLE_NAME + " (" +
            TableDefinitions.ID + " INTEGER PRIMARY KEY," +
            TableDefinitions.LIST_NAME + " TEXT)";

    public static final String SQL_CREATE_TASK_TABLE = "CREATE TABLE " + TableDefinitions.TASK_TABLE_NAME + " (" +
            TableDefinitions.ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            TableDefinitions.TASK + " TEXT NOT NULL," +
            TableDefinitions.IS_CHECKED + " INTEGER DEFAULT 0 NOT NULL)";

    public static final String SQL_DELETE_LIST_TABLE =
            "DROP TABLE IF EXISTS " + TableDefinitions.LIST_TABLE_NAME;

    public static final String SQL_DELETE_TASK_TABLE =
            "DROP TABLE IF EXISTS " + TableDefinitions.TASK_TABLE_NAME;

}
