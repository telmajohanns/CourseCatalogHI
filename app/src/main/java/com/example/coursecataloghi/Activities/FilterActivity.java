package com.example.coursecataloghi.Activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

    // Viðmótshlutir
    private EditText filter_teacher_course;
    private Button filter_favorites, filter_confirm_button, filter_cancel_button;
    private CheckBox filter_EN, filter_IS, filter_ISEN, filter_fall, filter_summer, filter_spring;
    private Spinner filter_eduLevel, filter_dept, filter_field, sortByECTS;

    private SharedPreferences sharedPref;
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
        sortByECTS = (Spinner) findViewById(R.id.sortByECTS);

        // Staðfest síu
        filter_confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmFilter();
            }
        });

        // Hætt við síu
        filter_cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelFilter();
            }
        });

        // Valið að sía eftir eigin áföngum
        filter_favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    favorites();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }

    /**
     * Fall sem kallar á doFiltering í CourseCatalogService sem sækir
     * uppáhalds áfanga notandans og skilar þeim til að birta þá.
     */
    private void favorites() throws IOException {
        UserService userService = new UserService();
        String userName = getCurrentUser();
        // Þurfum að kalla hérna á userService.getFavorites einhvern veginn til að fá listann af favorites áföngum
        ArrayList<String> favoritesList = userService.getFavorites(userName); // og setja það í breytuna favoritesList, þá ætti allt að vera rétt
        //Tímabundnir favorites áfangar

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

    public String getCurrentUser() {
        // Sækja notandann sem er skráður inn
        sharedPref = this.getSharedPreferences(
                getString(R.string.sharedpreffile), Context.MODE_PRIVATE);
        String userName = (sharedPref.getString("Notandanafn", "Default_Value"));

        return userName;
    }

    /**
     * Fall sem sækir allar síur sem notandinn valdi, setur þær í HashMap, þar sem lykillinn
     * er nafnið á fallinu sem þarf að kalla á til að sía rétt, og gildið er ArrayList af
     * strengjum sem á að sía eftir í lykilfallinu. Í lokin kallar fallið á doFiltering
     * sem gerir alla þá virkni
     */
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
            if (!sortByECTS.getSelectedItem().toString().equals("Röðun lista eftir fjölda ECTS")) {
                ArrayList<String> sortList = new ArrayList<>();
                sortList.add(sortByECTS.getSelectedItem().toString());
                filterMap.put("sortByECTS", sortList);
            }


            InputStream coursedata = getResources().openRawResource(R.raw.course_data);
            CourseCatalogService.doFiltering(filterMap, coursedata);
            Intent switchActivityIntent = new Intent(this, CourseCatalogActivity.class);
            startActivity(switchActivityIntent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Fall sem færir notandann aftur á CourseCatalogActivity síðuna án þess að sía neitt.
     */
    private void cancelFilter(){
        HashMap<String, ArrayList<String>> filterMap = new HashMap<>();
        InputStream coursedata = getResources().openRawResource(R.raw.course_data);
        try {
            CourseCatalogService.doFiltering(filterMap, coursedata);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        Intent switchActivityIntent = new Intent(this, CourseCatalogActivity.class);
        startActivity(switchActivityIntent);
    }
}
