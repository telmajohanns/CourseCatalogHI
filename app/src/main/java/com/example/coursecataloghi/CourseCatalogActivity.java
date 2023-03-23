package com.example.coursecataloghi;

import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class CourseCatalogActivity extends AppCompatActivity {
    ExpandableListView expandableListViewExample;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableTitleList;
    HashMap<String, List<String>> expandableDetailList;
    //ExpandableListView courseCatalogList;
    //private ExpandableListAdapter adapter;

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_catalog);
        //courseCatalogList = (ExpandableListView) findViewById(R.id.courseCatalogList);
        //Þurfum að gera adapter og eitthvað allskonar til að geta sett hluti í listviewið

    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_catalog);
        expandableListViewExample = (ExpandableListView) findViewById(R.id.courseCatalogList);
        expandableDetailList = ExpandableListDataItems.getData();
        expandableTitleList = new ArrayList<String>(expandableDetailList.keySet());
        expandableListAdapter = new CustomizedExpandableListAdapter(this, expandableTitleList, expandableDetailList);
        expandableListViewExample.setAdapter(expandableListAdapter);

        // This method is called when the group is expanded
        expandableListViewExample.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(), expandableTitleList.get(groupPosition) + " List Expanded.", Toast.LENGTH_SHORT).show();
            }
        });

        // This method is called when the group is collapsed
        expandableListViewExample.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(), expandableTitleList.get(groupPosition) + " List Collapsed.", Toast.LENGTH_SHORT).show();
            }
        });

        // This method is called when the child in any group is clicked
        // via a toast method, it is shown to display the selected child item as a sample
        // we may need to add further steps according to the requirements
        expandableListViewExample.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Toast.makeText(getApplicationContext(), expandableTitleList.get(groupPosition)
                        + " -> "
                        + expandableDetailList.get(
                        expandableTitleList.get(groupPosition)).get(
                        childPosition), Toast.LENGTH_SHORT
                ).show();
                return false;
            }
        });
    }

    /*private ArrayList<Course> createCourseCatalog() {
        ArrayList<Course> courses = new ArrayList<>();
        ArrayList<String> titles = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            titles.add("TÖL" + String.valueOf(i));
            Course course = new Course("TÖL" + String.valueOf(i), "Tölvunarfræði " + String.valueOf(i), 6.0, "Vor", "Grunnnám", "VON", "IVT", "IS", "Ebba Þóra", "Steinunn María", "2022-2023", true, "1234", "linkur");
            courses.add(course);
        }
        return courses;
    }*/
}
