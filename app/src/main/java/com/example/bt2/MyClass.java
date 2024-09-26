package com.example.bt2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MyClass implements Serializable {
    private String className;
    private String classID;
    private List<Student> studentList;
    private boolean isChecked = false;

    public MyClass(String id,String name,List<Student> list) {
        this.classID = id;
        this.className = name;
        this.studentList =  list;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public  boolean isChecked() {
        return this.isChecked;
    }

    public String getClassName(){
        return this.className;
    }

    public String getClassID(){
        return this.classID;
    }

    public int getStudentCount(){
        return this.studentList.size();
    }

    public List<Student> getStudentList(){
        return this.studentList;
    }

}
