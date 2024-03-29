package com.example.a163363s.parkbuddy.DataManagers;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;

import com.example.a163363s.parkbuddy.DataModels.Task;
import com.example.a163363s.parkbuddy.helpers.DatabaseHelper;

public class TaskDataManager {

    // This method gets a list of all tasks from the database, whose status are ACTIVE.
    // It will also order the tasks by the due date in descending order.
    //
    public Task[] getAllTasks()
    {
        SQLiteDatabase db = DatabaseHelper.getInstance();
        Cursor c = db.rawQuery("SELECT id, description, status, datedue, colour FROM Task WHERE status = 'ACTIVE' ORDER BY datedue DESC" , null );

        ArrayList<Task> tasks = new ArrayList<>();

        if (c.moveToFirst())
        {
            do{
                int id = c.getInt(0);
                String description = c.getString(1);
                String status = c.getString(2);
                Date dateDue = new Date(c.getLong(3));
                String colour = c.getString(4);

                Task task = new Task(id, description, colour, status, dateDue);
                tasks.add(task);

            } while (c.moveToNext());
        }
        db.close();
        return tasks.toArray(new Task[0]);
    }


    // This method gets a single Task object given its ID
    //
    public Task getTasksById(int id)
    {
        SQLiteDatabase db = DatabaseHelper.getInstance();
        Cursor c = db.rawQuery("SELECT id, description, status, datedue, colour FROM Task WHERE id = ?",
                new String[] {Integer.toString(id)});

        ArrayList<Task> tasks = new ArrayList<>();

        if (c.moveToFirst())
        {
            id = c.getInt(0);
            String description = c.getString(1);
            String status = c.getString(2);
            Date dateDue = new Date(c.getLong(3));
            String colour = c.getString(4);

            Task task = new Task(id, description, colour, status, dateDue);
            db.close();
            return task;
        }
        return null;
    }


    // This method inserts a new Task into the Task table in our
    // SQLite database.
    //
    public void addTasks(Task task)
    {
        SQLiteDatabase db = DatabaseHelper.getInstance();

        ContentValues values = new ContentValues();
        values.put("description", task.getDescription());
        values.put("status", task.getStatus());
        values.put("colour", task.getColour());
        values.put("datedue" ,task.getDatedue().getTime());

        db.insert("Task", null, values);
        db.close();
    }


    // This method inserts a new Task (with the same ID) into the Task table in our
    // SQLite database.
    //
    public void updateTask(Task task)
    {
        SQLiteDatabase db = DatabaseHelper.getInstance();

        ContentValues values = new ContentValues();
        values.put("id", task.getId());
        values.put("description", task.getDescription());
        values.put("status", task.getStatus());
        values.put("colour", task.getColour());
        values.put("datedue" ,task.getDatedue().getTime());

        db.replace("Task", null, values);
        db.close();
    }


    public void removeTask(Task task)
    {
        SQLiteDatabase db = DatabaseHelper.getInstance();

        String idString = Integer.toString(task.getId());

        db.delete("Tasks", "id = ?",new String[] {idString});
        db.close();
    }
}
