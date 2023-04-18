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
    // Viðmótshlutir
    private Button filter, logOutBtn, set_my_courses;
    private SharedPreferences sharedPref;
    private ExpandableListView expandableListViewExample;

    // Adapter til að birta listann og undirgögn í listanum rétt
    private ExpandableListAdapter expandableListAdapter;
    // Listi af strengjum sem eru titillinn á hverjum hlut/áfanga í listanum
    private List<String> expandableTitleList;
    // Hashmap þar sem lykillinn er titillinn á hlutnum/áfanganum og gildið er listi af strengjum
    // sem eru nánari upplýsingar um hlutinn/áfangann og birtast þegar hann er opnaður í listanum
    private HashMap<String, List<String>> expandableDetailList;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_catalog);

        expandableListViewExample = (ExpandableListView) findViewById(R.id.courseCatalogList);
        filter = (Button) findViewById(R.id.filter_catalog);
        logOutBtn = (Button) findViewById(R.id.logout_button);
        set_my_courses = (Button) findViewById(R.id.set_my_courses);

        sharedPref = this.getSharedPreferences(
                getString(R.string.sharedpreffile), Context.MODE_PRIVATE);

        // Skráir notandann út
        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });

        // Kallar á filterCatalog() sem færir notandann á síusíðuna
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterCatalog();
            }
        });

        set_my_courses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToFavorites();
            }
        });

        // CourseCatalogService er Singleton, svo ef það er til þá viljum við gera annað
        if (!CourseCatalogService.isCsInitiated()) {
            InputStream coursedata = getResources().openRawResource(R.raw.course_data);
            try {
                CourseCatalogService.getInstance(coursedata);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            expandableDetailList = CourseCatalogService.getExpandableDetailListAll();
        }
        // Ef CourseCatalogService er til, þá sækja síuð gögn en ekki endurhlaða öllum listanum
        else { expandableDetailList = CourseCatalogService.getFilteredData(); }

        expandableTitleList = new ArrayList<String>(expandableDetailList.keySet());
        expandableListAdapter = new CustomizedExpandableListAdapter(this, expandableTitleList, expandableDetailList);
        expandableListViewExample.setAdapter(expandableListAdapter);

    }

    /**
     * Fall sem færir notandann yfir á síusíðuna, FilterActivity
     */
    private void filterCatalog() {
        Intent switchActivityIntent = new Intent(this, FilterActivity.class);
        startActivity(switchActivityIntent);
    }
    private void addToFavorites() {
        Intent switchActivityIntent = new Intent(this, AddFavoritesActivity.class);
        startActivity(switchActivityIntent);
    }

    /**
     * Fall sem skráir notandann út og færir hann aftur á upphafs innskráningarsíðuna, MainActivity
     */
    private void signOut(){
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove("Username").commit();
        Intent switchActivityIntent = new Intent(this, MainActivity.class);
        startActivity(switchActivityIntent);
    }
}
