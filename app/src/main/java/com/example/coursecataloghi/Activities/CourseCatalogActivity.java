package com.example.coursecataloghi.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
    private ExpandableListView expandableListViewExample;
    private ExpandableListAdapter expandableListAdapter;
    private List<String> expandableTitleList;
    private HashMap<String, List<String>> expandableDetailList;
    private Button filter;
    private Button logOutBtn;
    private SharedPreferences sharedPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_catalog);

        expandableListViewExample = (ExpandableListView) findViewById(R.id.courseCatalogList);
        filter = (Button) findViewById(R.id.filter_catalog);
        logOutBtn = (Button) findViewById(R.id.logout_button);

        sharedPref = this.getSharedPreferences(
                getString(R.string.sharedpreffile), Context.MODE_PRIVATE);

        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterCatalog();
            }
        });

        if (!CourseCatalogService.isCsInitiated()) {
            InputStream coursedata = getResources().openRawResource(R.raw.course_data);
            try {
                CourseCatalogService.getInstance(coursedata);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            expandableDetailList = CourseCatalogService.getExpandableDetailListAll();
        }
        else { expandableDetailList = CourseCatalogService.getFilteredData(); }

        expandableTitleList = new ArrayList<String>(expandableDetailList.keySet());
        expandableListAdapter = new CustomizedExpandableListAdapter(this, expandableTitleList, expandableDetailList);
        expandableListViewExample.setAdapter(expandableListAdapter);

    }
    private void filterCatalog() {
        Intent switchActivityIntent = new Intent(this, FilterActivity.class);
        startActivity(switchActivityIntent);
    }

    private void signOut(){
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove("Username").commit();
        Intent switchActivityIntent = new Intent(this, MainActivity.class);
        startActivity(switchActivityIntent);
    }
}
