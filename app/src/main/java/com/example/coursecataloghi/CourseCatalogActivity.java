package com.example.coursecataloghi;

import android.os.Bundle;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import Entities.Course;

public class CourseCatalogActivity extends AppCompatActivity {
    ExpandableListView courseCatalogList;
    private ExpandableListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_catalog);
        courseCatalogList = (ExpandableListView) findViewById(R.id.courseCatalogList);
        //Þurfum að gera adapter og eitthvað allskonar til að geta sett hluti í listviewið

    }

    private ArrayList<Course> createCourseCatalog() {
        ArrayList<Course> courses = new ArrayList<>();
        ArrayList<String> titles = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            titles.add("TÖL" + String.valueOf(i));
            Course course = new Course("TÖL" + String.valueOf(i), "Tölvunarfræði " + String.valueOf(i), 6.0, "Vor", "Grunnnám", "VON", "IVT", "IS", "Ebba Þóra", "Steinunn María", "2022-2023", true, "1234", "linkur");
            courses.add(course);
        }
        return courses;
    }
}
