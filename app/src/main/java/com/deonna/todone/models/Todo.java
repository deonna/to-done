package com.deonna.todone.models;

import android.util.Log;

import com.deonna.todone.constants.Priority;
import com.deonna.todone.database.TodoDataSource;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Todo implements Serializable {

    private static final String TAG = Todo.class.getSimpleName() ;
    private static final String FORMAT_PATTERN = "MMM d, yyyy";

    private long id;
    private String name;
    private Priority priority;
    private boolean isCompleted;
    private Date dueDate;
    private String dueDateText;
    String note;

    private static final long serialVersionUID = 1L;

    private static TodoDataSource todosDataSource;

    public Todo(String name) {

        this.name = name;
        priority = Priority.LOW;
        isCompleted = false;
        dueDate = null;
        dueDateText = "";
        note = "";

        todosDataSource = Todos.getDataSource();
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public void setId(long newId) {

        id = newId;
    }

    public long getId() {

        return id;
    }

    public Priority getPriority() {

        return priority;
    }

    public void setPriority(Priority priority) {

        this.priority = priority;
    }

    public void setPriorityFromInt(int priority) {

        this.priority = Priority.fromInt(priority);
    }

    public boolean getIsCompleted() {

        return isCompleted;
    }

    public void setIsCompleted(boolean isCompleted) {

        this.isCompleted = isCompleted;
    }

    public int getIsCompletedAsInt() {

        if (isCompleted) {
            return 1;
        } else {
            return 0;
        }
    }

    public void setIsCompleted(int completed) {

        if (completed == 0) {
            isCompleted = false;
        } else {
            isCompleted = true;
        }
    }

    public Date getDueDate() {

        return dueDate;
    }

    public void setDueDate(Date dueDate) {

        this.dueDate = dueDate;
        setDueDateText(getDueDateText());
    }

    public static void addToDataSource(Todo todo) {

        todosDataSource.create(todo);
    }

    public static void updateInDataSource(Todo todo) {

        todosDataSource.update(todo);
    }

    public static void removeFromDataSource(long id) {

        todosDataSource.delete(id);
    }

    public String getDueDateText() {

        if (dueDate == null) {
            return "";
        } else {
            dueDateText = getFormattedDate(dueDate);
            return dueDateText;
        }
    }

    private String getFormattedDate(Date date) {

        SimpleDateFormat formatter = new SimpleDateFormat(FORMAT_PATTERN);
        String formattedDate = formatter.format(date);

        return formattedDate;
    }

    public void setDueDateText(String dueDateText) {

        if (!dueDateText.isEmpty()) {
            this.dueDateText = dueDateText;

            SimpleDateFormat formatter = new SimpleDateFormat(FORMAT_PATTERN);

            try {
                dueDate = formatter.parse(dueDateText);
            } catch (ParseException e) {
                Log.e(TAG, "Error parsing due date: " + e);
            }
        }
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getNote() {
        return note;
    }
}
