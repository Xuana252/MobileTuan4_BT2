package com.example.bt2;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;



import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityResultLauncher<Intent> addClassLauncher;

    private ActivityResultLauncher<Intent> classDetailLauncher;

    private List<MyClass> classList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        List<Student> lopA = new ArrayList<>();
        lopA.add(new Student("1", "Nguyen Van A","1/1/2024"));
        lopA.add(new Student("2", "Nguyen Van B","1/1/2024"));
        lopA.add(new Student("3", "Nguyen Van C","1/1/2024"));

        List<Student> lopB = new ArrayList<>();
        lopB.add(new Student("1", "Tran Van A","1/1/2024"));
        lopB.add(new Student("2", "Tran Van B","1/1/2024"));
        lopB.add(new Student("3", "Tran Van C","1/1/2024"));

        List<Student> lopC = new ArrayList<>();
        lopC.add(new Student("1", "Le Van A","1/1/2024"));
        lopC.add(new Student("2", "Le Van B","1/1/2024"));
        lopC.add(new Student("3", "Le Van C","1/1/2024"));


        classList.add(new MyClass("1","Lop A",lopA));
        classList.add(new MyClass("2","Lop B",lopB));
        classList.add(new MyClass("3","Lop C",lopC));

        ListView classListview = (ListView) findViewById(R.id.classListView);

        ClassArrayAdapter arrayAdapter = new ClassArrayAdapter(MainActivity.this, R.layout.class_item, classList);


        classListview.setAdapter(arrayAdapter);


        classListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MyClass selectedClass = classList.get(position); // Get the selected class


                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra("CLASS", selectedClass); // Pass the selected class object
                classDetailLauncher.launch(intent);
            }
        });

        addClassLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            String className = data.getStringExtra("CLASS_NAME");

                            MyClass newClass = new MyClass(String.valueOf(classList.size() + 1), className, new ArrayList<>());
                            classList.add(newClass); // Add the new student to the list

                            // Notify adapter to refresh the ListView
                            ((ClassArrayAdapter) ((ListView) findViewById(R.id.classListView)).getAdapter()).notifyDataSetChanged();
                        }
                    }
                });

        classDetailLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            MyClass returnedClass = (MyClass) data.getSerializableExtra("CLASS");

                            if (returnedClass != null) {
                                for (int i = 0; i < classList.size(); i++) {
                                    MyClass existingClass = classList.get(i);
                                    if (existingClass.getClassID().equals(returnedClass.getClassID())) {
                                        classList.set(i, returnedClass); // Replace with updated class
                                        break;
                                    }
                                }
                                // Notify adapter to refresh the ListView
                                ((ClassArrayAdapter) ((ListView) findViewById(R.id.classListView)).getAdapter()).notifyDataSetChanged();
                            }
                        }
                    }
                });


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
            Intent intent = new Intent(MainActivity.this, AddClassActivity.class);// Pass the class ID
            addClassLauncher.launch(intent);
            return true;
        } else if (itemId == R.id.Delete) {
            List<MyClass> classesToRemove = new ArrayList<>();
            for (MyClass myClass : classList) {
                if (myClass.isChecked()) {
                    classesToRemove.add(myClass);
                }
            }
            classList.removeAll(classesToRemove);

            // Update the ListView and student count
            ((ClassArrayAdapter) ((ListView) findViewById(R.id.classListView)).getAdapter()).notifyDataSetChanged();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

