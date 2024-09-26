package com.example.bt2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddClassActivity extends AppCompatActivity {

    Button cancelButton;
    Button createClassButton;

    EditText classNameInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class);

        cancelButton = findViewById(R.id.cancelButton);

        createClassButton = findViewById(R.id.addClassButton);

        classNameInput = findViewById(R.id.classNameInput);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        createClassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String className = classNameInput.getText().toString();
                if (className.isEmpty()) {
                    Toast.makeText(AddClassActivity.this,"please fill all field",Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(AddClassActivity.this,"add class successfully",Toast.LENGTH_LONG).show();

                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("CLASS_NAME",className );
                    setResult(RESULT_OK, resultIntent); // Set the result
                    finish(); // Close the activity
                }

            }
        });
    }
}
