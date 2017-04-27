package com.goldteam.todolist.Database;

/**
 * Created by ricokahler on 4/26/17.
 */

public class Task {
    private String name;
    private boolean completed;
    private int listId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public int getListId() {
        return listId;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }
}
