/*
 * Assignment #: HW02
 * File Name: HW02 --- DisplayTaskActivity.java
 * Full Name: Kristin Pflug
 */

package com.example.hw02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayTaskActivity extends AppCompatActivity {

    Task task;
    TextView displayName;
    TextView displayDate;
    TextView displayPriority;
    final static public String DELETE_TASK_KEY = "DELETE_TASK_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_task);
        setTitle(R.string.display_task_label);

        displayName = findViewById(R.id.display_task_name_label);
        displayDate = findViewById(R.id.display_task_date_label);
        displayPriority = findViewById(R.id.display_task_priority_label);

        if(getIntent() != null && getIntent().getExtras() != null && getIntent().hasExtra(MainActivity.TASK_KEY)){
            task = (Task) getIntent().getSerializableExtra(MainActivity.TASK_KEY);

            displayName.setText(task.name);
            displayDate.setText(task.date);
            displayPriority.setText(task.priority);
        }

        findViewById(R.id.display_task_cancel_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        findViewById(R.id.display_task_delete_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplayTaskActivity.this, MainActivity.class);
                intent.putExtra(DELETE_TASK_KEY, task);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}