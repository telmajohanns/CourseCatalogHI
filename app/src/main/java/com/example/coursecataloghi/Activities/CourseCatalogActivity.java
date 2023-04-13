package com.example.coursecataloghi.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.coursecataloghi.Activities.Adapters.CustomizedExpandableListAdapter;
import com.example.coursecataloghi.R;
import com.example.coursecataloghi.Services.CourseCatalogService;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class CourseCatalogActivity extends AppCompatActivity {
    ExpandableListView expandableListViewExample;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableTitleList;
    HashMap<String, List<String>> expandableDetailList;
    Button filter;
    // búa til catalogservice7
    // inní oncreate sem kallar á getinstance, með r resource skránna sem inntak
    //CourseCatalogService courseCatalogService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_catalog);
        filter = (Button) findViewById(R.id.filter_catalog);
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                filterCatalog();
            }
        });
        expandableListViewExample = (ExpandableListView) findViewById(R.id.courseCatalogList);

        if (!CourseCatalogService.isCsInitiated()) {
            InputStream coursedata = getResources().openRawResource(R.raw.course_data);

            //CourseCatalogService courseCatalogService = CourseCatalogService.getInstance(coursedata);
            System.out.println("Sækja org data");
            try {
                CourseCatalogService.getInstance(coursedata);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            expandableDetailList = CourseCatalogService.getExpandableDetailListAll();
        }
        else {
            System.out.println("Sækja filtered data");

            //expandableDetailList.clear();
            expandableDetailList = CourseCatalogService.getFilteredData();
        }



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

        // Fall þegar áfangahóp er lokað/fellt saman
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
    private void filterCatalog() {
        Intent switchActivityIntent = new Intent(this, FilterActivity.class);
        startActivity(switchActivityIntent);
    }
}
