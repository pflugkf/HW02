/*
 * Assignment #: HW02
 * File Name: HW02 --- CreateTaskActivity.java
 * Full Name: Kristin Pflug
 */

package com.example.hw02;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CreateTaskActivity extends AppCompatActivity {

    EditText nameEntered;
    TextView displayDate;
    RadioGroup priorityList;
    final static public String NEW_TASK_KEY = "NEW_TASK_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);
        setTitle(R.string.create_task_activity_title);

        displayDate = findViewById(R.id.create_task_date_text);

        findViewById(R.id.create_task_set_date_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dpd = new DatePickerDialog(CreateTaskActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                displayDate.setText((monthOfYear + 1) + "/" + dayOfMonth + "/" + year);
                            }
                        }, year, month, day);

                dpd.show();

            }
        });

        findViewById(R.id.create_task_cancel_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        findViewById(R.id.create_task_submit_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String taskName = "";
                String taskDate = "";
                String taskPriority = "";

                nameEntered = findViewById(R.id.create_task_name_textbox);
                if(!nameEntered.getText().toString().equals("")) {
                    taskName = nameEntered.getText().toString();
                } else {
                    Toast.makeText(CreateTaskActivity.this, getString(R.string.no_name_entered_toast), Toast.LENGTH_SHORT).show();
                }

                if(!displayDate.getText().toString().equals("")) {
                    taskDate = displayDate.getText().toString();
                } else {
                    Toast.makeText(CreateTaskActivity.this, getString(R.string.no_date_picked_toast), Toast.LENGTH_SHORT).show();
                }

                priorityList = findViewById(R.id.create_task_priority_radiogroup);
                int chosenPriority = priorityList.getCheckedRadioButtonId();

                if(chosenPriority != -1) {
                    if (chosenPriority == R.id.create_task_priority_high_radiobutton) {
                        taskPriority = "High";
                    } else if (chosenPriority == R.id.create_task_priority_medium_radiobutton) {
                        taskPriority = "Medium";
                    } else if (chosenPriority == R.id.create_task_priority_low_radiobutton) {
                        taskPriority = "Low";
                    }
                } else {
                    Toast.makeText(CreateTaskActivity.this, getString(R.string.no_priority_chosen_toast), Toast.LENGTH_SHORT).show();
                }

                if(!taskName.equals("") && !taskDate.equals("") && !taskPriority.equals("")) {
                    Task task = null;
                    task = new Task(taskName, taskDate, taskPriority);


                    Intent intent = new Intent(CreateTaskActivity.this, MainActivity.class);
                    intent.putExtra(NEW_TASK_KEY, task);
                    setResult(RESULT_OK, intent);
                    finish();
                } else if (taskName.equals("") && taskDate.equals("") && taskPriority.equals("")) {
                    Toast.makeText(CreateTaskActivity.this, getString(R.string.empty_form_toast), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}