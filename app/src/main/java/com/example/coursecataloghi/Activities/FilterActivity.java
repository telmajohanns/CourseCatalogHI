package com.example.coursecataloghi.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.coursecataloghi.R;

public class FilterActivity extends AppCompatActivity {

    EditText filter_teacher_course;
    Button filter_favorites, filter_confirm_button, filter_cancel_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_catalog);
        filter_teacher_course = (EditText) findViewById(R.id.filter_teacher_course);
        filter_favorites = (Button) findViewById(R.id.filter_favorites);
        filter_confirm_button = (Button) findViewById(R.id.filter_confirm_button);
        filter_cancel_button = (Button) findViewById(R.id.filter_cancel_button);
        filter_confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmFilter();
            }
        });
        filter_cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelFilter();
            }
        });
    }
    private void confirmFilter(){
        Intent switchActivityIntent = new Intent(this, CourseCatalogActivity.class);
        startActivity(switchActivityIntent);
    }
    private void cancelFilter(){
        Intent switchActivityIntent = new Intent(this, CourseCatalogActivity.class);
        startActivity(switchActivityIntent);
    }
}
