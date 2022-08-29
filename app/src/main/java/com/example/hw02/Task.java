/*
 * Assignment #: HW02
 * File Name: HW02 --- Task.java
 * Full Name: Kristin Pflug
 */

package com.example.hw02;

import java.io.Serializable;
import java.util.Date;

public class Task implements Serializable {

    String name;
    String date;
    String priority;

    public Task(String name, String date, String priority) {
        this.name = name;
        this.date = date;
        this.priority = priority;
    }
}
