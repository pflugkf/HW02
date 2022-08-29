/*
 * Assignment #: HW02
 * File Name: HW02 --- MainActivity.java
 * Full Name: Kristin Pflug
 */

package com.example.hw02;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {

    ArrayList<Task> taskArrayList;
    TextView taskCountText;
    TextView taskName;
    TextView taskDate;
    TextView taskPriority;
    final static public String TASK_KEY = "TASK_KEY";

    ActivityResultLauncher<Intent> startForResultCreate = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result != null && result.getResultCode() == RESULT_OK){
                if(result.getData() != null && result.getData().getSerializableExtra(CreateTaskActivity.NEW_TASK_KEY) != null){
                    Intent data = result.getData();
                    Task task = (Task) data.getSerializableExtra(CreateTaskActivity.NEW_TASK_KEY);

                    taskArrayList.add(task);
                    sortByDate();
                    updateTaskList();
                }
            }
        }
    });

    ActivityResultLauncher<Intent> startForResultDelete = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result != null && result.getResultCode() == RESULT_OK){
                if(result.getData() != null && result.getData().getSerializableExtra(DisplayTaskActivity.DELETE_TASK_KEY) != null){
                    Intent data = result.getData();
                    Task task = (Task) data.getSerializableExtra(DisplayTaskActivity.DELETE_TASK_KEY);

                    for (int i = 0; i < taskArrayList.size(); i++) {
                        if (taskArrayList.get(i).name.equals(task.name)) {
                            taskArrayList.remove(i);
                            sortByDate();
                            updateTaskList();
                        }
                    }
                }
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.main_activity_title);

        taskArrayList = new ArrayList<>();
        updateTaskList();

        findViewById(R.id.view_task_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (taskArrayList.size() == 0) {
                    Toast.makeText(MainActivity.this, getString(R.string.no_tasks_toast), Toast.LENGTH_SHORT).show();
                } else {
                    ArrayList<String> taskNameList = new ArrayList<>();
                    for (Task task : taskArrayList) {
                        taskNameList.add(task.name);
                    }

                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle(R.string.alertdialog_title)
                            .setItems(taskNameList.toArray(new String[taskNameList.size()]), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent = new Intent(MainActivity.this, DisplayTaskActivity.class);
                                    intent.putExtra(TASK_KEY, taskArrayList.get(i));
                                    startForResultDelete.launch(intent);
                                }
                            })
                            .setNegativeButton(R.string.alertdialog_cancel_button, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });


        findViewById(R.id.create_task_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CreateTaskActivity.class);
                startForResultCreate.launch(intent);
            }
        });
    }

    private void updateTaskList(){
        taskCountText = findViewById(R.id.task_count_label);
        taskCountText.setText("You have " + taskArrayList.size() + " tasks");

        taskName = findViewById(R.id.task_name_label);
        taskDate = findViewById(R.id.task_date_label);
        taskPriority = findViewById(R.id.task_priority_label);

        if(taskArrayList.size() == 0){
            taskName.setText(getString(R.string.no_tasks_label));
            taskDate.setText("");
            taskPriority.setText("");
        } else {
            Task firstTask = taskArrayList.get(0);
            taskName.setText(firstTask.name);
            taskDate.setText(firstTask.date);
            taskPriority.setText(firstTask.priority);
        }
    }

    private void sortByDate() {
        Collections.sort(taskArrayList, new Comparator<Task>() {
            @Override
            public int compare(Task t1, Task t2) {
                DateFormat f = new SimpleDateFormat("MM/dd/yyyy");
                try {
                    return f.parse(t1.date).compareTo(f.parse(t2.date));
                } catch (ParseException e) {
                    throw new IllegalArgumentException(e);
                }

            }

        });
    }


}