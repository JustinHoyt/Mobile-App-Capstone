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
        public static final String LIST_FK = "list_id";
        public static final String TASK_NAME = "task";
        public static final String TASK_COMPLETED = "task_completed";

    }

    public static final String SQL_CREATE_LIST_TABLE =
            "CREATE TABLE IF NOT EXISTS " +
            TableDefinitions.LIST_TABLE_NAME +
            " (" +
            TableDefinitions.ID +
            " INTEGER PRIMARY KEY, " +
            TableDefinitions.LIST_NAME +
            " TEXT)";

    public static final String SQL_CREATE_TASK_TABLE = String.format(
            "CREATE TABLE IF NOT EXISTS %s (" +
                    "%s INTEGER PRIMARY KEY, " +
                    "%s INTEGER NOT NULL, " +
                    "%s TEXT NOT NULL, " +
                    "%s INTEGER NOT NULL"+
                    ")",
            TableDefinitions.TASK_TABLE_NAME,
            TableDefinitions.ID,
            TableDefinitions.LIST_FK,
            TableDefinitions.TASK_NAME,
            TableDefinitions.TASK_COMPLETED
    );

    public static final String[] SQL_SEED_LIST_TABLE = {
            "insert into " +
            TableDefinitions.LIST_TABLE_NAME +
            " values (null, 'To Do List')",

            "insert into " +
            TableDefinitions.LIST_TABLE_NAME +
            " values (null, 'Shopping List')",

            "insert into " +
            TableDefinitions.LIST_TABLE_NAME +
            " values (null, 'Wish List')",

            "insert into " +
            TableDefinitions.LIST_TABLE_NAME +
            " values (null, 'Honey-Do List')",

            "insert into " +
            TableDefinitions.LIST_TABLE_NAME +
            " values (null, 'School Work List')"
    };


    public static final String SQL_DELETE_LIST_TABLE =
            "DROP TABLE IF EXISTS " + TableDefinitions.LIST_TABLE_NAME;

    public static final String SQL_DELETE_TASK_TABLE =
            "DROP TABLE IF EXISTS " + TableDefinitions.TASK_TABLE_NAME;

}
