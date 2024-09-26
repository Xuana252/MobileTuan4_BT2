

package com.example.bt2;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends AppCompatActivity {

    TextView classStudentCount;
    TextView className;

    MyClass selectedClass;

    private ActivityResultLauncher<Intent> addStudentLauncher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);



        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Intent intent = getIntent();
        selectedClass = (MyClass) intent.getSerializableExtra("CLASS");



        if (selectedClass != null) {


            ListView studentListView = (ListView) findViewById(R.id.studentListView);
            classStudentCount = findViewById(R.id.classStudentCount);
            className = findViewById(R.id.className);

            classStudentCount.setText("Sỉ số: " + selectedClass.getStudentCount());
            className.setText("Lớp :" + selectedClass.getClassName());

            StudentArrayAdapter arrayAdapter;
            arrayAdapter = new StudentArrayAdapter(getApplicationContext(), R.layout.list_item,selectedClass.getStudentList());


            studentListView.setAdapter(arrayAdapter);
        }

        addStudentLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            String studentName = data.getStringExtra("STUDENT_NAME");
                            String studentDob = data.getStringExtra("STUDENT_DOB");

                            Student newStudent = new Student(String.valueOf(selectedClass.getStudentList().size() + 1), studentName, studentDob);
                            selectedClass.getStudentList().add(newStudent); // Add the new student to the list

                            // Notify adapter to refresh the ListView
                            ((StudentArrayAdapter) ((ListView) findViewById(R.id.studentListView)).getAdapter()).notifyDataSetChanged();
                            classStudentCount.setText("Sỉ số: " + selectedClass.getStudentList().size());
                        }
                    }
                });
        // Set up the back press callback
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Create an updated MyClass object with the current data

                Intent resultIntent = new Intent();
                resultIntent.putExtra("CLASS", selectedClass);
                setResult(RESULT_OK, resultIntent); // Set the result

                finish(); // Close the activity
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);

    }



    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.Add) {
            Intent intent = new Intent(SecondActivity.this, AddStudentActivity.class);
            addStudentLauncher.launch(intent);
            return  true;
        } else if (itemId == R.id.Delete) {
            List<Student> studentsToRemove = new ArrayList<>();
            for (Student student : selectedClass.getStudentList()) {
                if (student.isChecked()) {
                    studentsToRemove.add(student);
                }
            }
            selectedClass.getStudentList().removeAll(studentsToRemove);

            // Update the ListView and student count
            ((StudentArrayAdapter) ((ListView) findViewById(R.id.studentListView)).getAdapter()).notifyDataSetChanged();
            classStudentCount.setText("Sỉ số: " + selectedClass.getStudentList().size());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }




}




