package com.example.bt2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.bt2.R;
import com.example.bt2.Student;

import java.util.List;

class StudentArrayAdapter extends ArrayAdapter<Student> {
    int resource;
    private List<Student> students;

    public StudentArrayAdapter(Context context, int resource, List<Student> students) {
        super(context, resource, students);
        this.students = students;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(this.getContext());
            v = vi.inflate(this.resource, null);
        }
        Student s = getItem(position);

        if (s != null) {
            TextView studentID = (TextView) v.findViewById(R.id.student_ID);
            TextView studentName = (TextView) v.findViewById(R.id.student_name);
            TextView studentDob = (TextView) v.findViewById(R.id.student_dob);
            CheckBox checkBox = (CheckBox) v.findViewById(R.id.checkBox);

            if (studentID != null) {
                studentID.setText(s.getID());
            }
            if (studentName != null) {
                studentName.setText(s.getName());
            }
            if (studentDob != null) {
                studentDob.setText(s.getDob());
            }

            checkBox.setChecked(s.isChecked());
            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                s.setChecked(isChecked);
            });

        }
        return v;
    }

}
