package com.example.coursecataloghi.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import com.example.coursecataloghi.R;
import com.example.coursecataloghi.Services.CourseCatalogService;
import com.example.coursecataloghi.Services.UserService;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class FilterActivity extends AppCompatActivity {

    private EditText filter_teacher_course;
    private Button filter_favorites, filter_confirm_button, filter_cancel_button;
    private CheckBox filter_EN, filter_IS, filter_ISEN, filter_fall, filter_summer, filter_spring;
    private Spinner filter_eduLevel, filter_dept, filter_field;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_catalog);
        filter_teacher_course = (EditText) findViewById(R.id.filter_teacher_course);
        filter_favorites = (Button) findViewById(R.id.filter_favorites);
        filter_confirm_button = (Button) findViewById(R.id.filter_confirm_button);
        filter_cancel_button = (Button) findViewById(R.id.filter_cancel_button);
        filter_EN = (CheckBox) findViewById(R.id.filter_EN);
        filter_IS = (CheckBox) findViewById(R.id.filter_IS);
        filter_ISEN = (CheckBox) findViewById(R.id.filter_ISEN);
        filter_fall = (CheckBox) findViewById(R.id.filter_fall);
        filter_summer = (CheckBox) findViewById(R.id.filter_summer);
        filter_spring = (CheckBox) findViewById(R.id.filter_spring);
        filter_eduLevel = (Spinner) findViewById(R.id.filter_eduLevel);
        filter_dept = (Spinner) findViewById(R.id.filter_dept);
        filter_field = (Spinner) findViewById(R.id.filter_field);


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
        filter_favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                favorites();
            }
        });

    }

    private void favorites() {
        UserService userService = new UserService();
        // Þurfum að kalla hérna á userService.getFavorites einhvern veginn til að fá listann af favorites áföngum
        ArrayList<String> favoritesList = new ArrayList<>(); // og setja það í breytuna favoritesList, þá ætti allt að vera rétt
        //Tímabundnir favorites áfangar
        favoritesList.add("TÖL101G");
        favoritesList.add("ASK032M");
        HashMap<String, ArrayList<String>> filterMap = new HashMap<>();
        filterMap.put("filterByFavorites", favoritesList);
        InputStream coursedata = getResources().openRawResource(R.raw.course_data);
        try {
            CourseCatalogService.doFiltering(filterMap, coursedata);
        } catch (IOException e) {
            throw new RuntimeException();
        }

        Intent switchActivityIntent = new Intent(this, CourseCatalogActivity.class);
        startActivity(switchActivityIntent);
    }
    private void confirmFilter(){
        try {
            HashMap<String, ArrayList<String>> filterMap = new HashMap<>();

            // check á öllum gildunum
            if(!filter_teacher_course.getText().toString().isEmpty()) {
                ArrayList<String> filterList = new ArrayList<>();
                filterList.add(filter_teacher_course.getText().toString());
                filterMap.put("filterByText", filterList);
            }
            if (filter_EN.isChecked() || filter_IS.isChecked() || filter_ISEN.isChecked()) {
                ArrayList<String> filterList = new ArrayList<>();
                if (filter_EN.isChecked()) {filterList.add("Enska");}
                if (filter_IS.isChecked()) {filterList.add("Íslenska");}
                if (filter_ISEN.isChecked()) {filterList.add("Íslenska/enska");}
                filterMap.put("filterByLanguage", filterList);
            }
            if (filter_fall.isChecked() || filter_summer.isChecked() || filter_spring.isChecked()) {
                ArrayList<String> filterList = new ArrayList<>();
                if (filter_fall.isChecked()) {filterList.add("Haust");}
                if (filter_summer.isChecked()) {filterList.add("Sumar");}
                if (filter_spring.isChecked()) {filterList.add("Vor");}
                filterMap.put("filterBySemester", filterList);
            }
            if (!filter_eduLevel.getSelectedItem().toString().equals("Öll námstig")) {
                ArrayList<String> filterList = new ArrayList<>();
                filterList.add(filter_eduLevel.getSelectedItem().toString());
                filterMap.put("filterByEduLevel", filterList);
            }
            if (!filter_dept.getSelectedItem().toString().equals("Allar deildir")) {
                ArrayList<String> filterList = new ArrayList<>();
                filterList.add(filter_dept.getSelectedItem().toString());
                filterMap.put("filterByDept", filterList);
            }
            if (!filter_field.getSelectedItem().toString().equals("Öll svið")) {
                ArrayList<String> filterList = new ArrayList<>();
                filterList.add(filter_field.getSelectedItem().toString());
                filterMap.put("filterByField", filterList);
            }


            InputStream coursedata = getResources().openRawResource(R.raw.course_data);
            CourseCatalogService.doFiltering(filterMap, coursedata);
            Intent switchActivityIntent = new Intent(this, CourseCatalogActivity.class);
            startActivity(switchActivityIntent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    private void cancelFilter(){
        Intent switchActivityIntent = new Intent(this, CourseCatalogActivity.class);
        startActivity(switchActivityIntent);
    }
}
