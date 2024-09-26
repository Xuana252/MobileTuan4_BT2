package com.example.bt2;

import java.io.Serializable;
import java.util.Date;

public class Student implements Serializable {
    private String ID;
    private String name;
    private String dob;
    private boolean isChecked = false;

    public Student(String id,String name,String date) {
        this.ID= id;
        this.name = name;
        this.dob = date;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public  boolean isChecked() {
        return this.isChecked;
    }
    public String getName() {
        return this.name;
    }
    public String getID() {
        return this.ID;
    }
    public String getDob() {
        return this.dob;
    }

}
