package com.example.a163363s.parkbuddy;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.a163363s.parkbuddy.Adapters.TasksAdapter;
import com.example.a163363s.parkbuddy.DataManagers.TaskDataManager;
import com.example.a163363s.parkbuddy.DataModels.Task;
import com.example.a163363s.parkbuddy.helpers.DatabaseHelper;
import com.example.a163363s.parkbuddy.helpers.DateHelper;

public class BookmarkActivity  extends AppCompatActivity {

    public static TaskDataManager taskDataManager = new TaskDataManager();

    // UI widgets
    //
    FloatingActionButton buttonAdd;
    GridView gridViewTasks;

    static int TASK_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);

        DatabaseHelper.initialize((getApplicationContext()));

        gridViewTasks = findViewById(R.id.gridViewTasks);
        gridViewTasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                //When the taps on one of the items in the GridView, show a popup dialog
                // that allows user to select one of the  following choices:
                // Edit Tasks / Complete Task
                //
                new AlertDialog.Builder(BookmarkActivity.this)
                        .setTitle("What do you want to do?")
                        .setItems(new String[]{"Edit Location", "Delete Location"}, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int itemSelected) {

                                final Task task = (Task) gridViewTasks.getItemAtPosition(position);

                                if(itemSelected == 0)
                                {
                                    // "Edit Task" was selected.
                                    //
                                    Intent intent= new Intent(BookmarkActivity.this, EditActivity.class);
                                    intent.putExtra("id",task.getId());
                                    startActivityForResult(intent,TASK_REQUEST_CODE);
                                }

                                else
                                {
                                    // "Complete Task" was selected
                                    // Lets show an alert to ask user for comfirmation before removing
                                    // the task from view.
                                    //
                                    new AlertDialog.Builder(BookmarkActivity.this)
                                            .setMessage("Delete this Location?")
                                            .setNegativeButton("No",null)
                                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    task.setStatus("DELETED");
                                                    BookmarkActivity.taskDataManager.updateTask(task);

                                                    refreshList();
                                                }
                                            })
                                            .show();
                                }
                            }
                        })
                        .show();
            }
        });

        buttonAdd = findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Show our EditActivity.
                //
                Intent intent = new Intent(BookmarkActivity.this, EditActivity.class);
                intent.putExtra("id", 0);
                startActivityForResult(intent, TASK_REQUEST_CODE);
            }
        });

        refreshList();

    }


    public void refreshList()
    {
        Task[] tasks = taskDataManager.getAllTasks();

        TasksAdapter adapter = new TasksAdapter(this, tasks);
        gridViewTasks.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == TASK_REQUEST_CODE)
        {
            refreshList();
        }
    }
}


