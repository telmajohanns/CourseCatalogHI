package com.example.coursecataloghi.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.coursecataloghi.R;
import com.example.coursecataloghi.Services.CourseCatalogService;
import com.example.coursecataloghi.Services.UserService;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class AddFavoritesActivity extends AppCompatActivity {
    private ArrayList<String> favoritesList = new ArrayList<>();
    private Spinner courses_drop_down;
    private Button add_favorites, rm_favorites, back_to_catalog;
    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_favorites);


        courses_drop_down = (Spinner) findViewById(R.id.courses_drop_down);
        add_favorites = (Button) findViewById(R.id.add_favorites);
        rm_favorites = (Button) findViewById(R.id.rm_favorites);
        back_to_catalog = (Button) findViewById(R.id.back_to_catalog);
        //rm_favorites.setVisibility(View.VISIBLE);

        back_to_catalog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToCatalog();
            }
        });
        add_favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    addFavorites(courses_drop_down.getSelectedItem().toString());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        rm_favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rmFavorites(courses_drop_down.getSelectedItem().toString());
            }
        });
    }

    private void backToCatalog() {
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

    public String getCurrentUser() {
        // Sækja notandann sem er skráður inn
        sharedPref = this.getSharedPreferences(
                getString(R.string.sharedpreffile), Context.MODE_PRIVATE);
        String userName = (sharedPref.getString("Notandanafn", "Default_Value"));

        return userName;
    }

    private boolean addFavorites(String courseAcro) throws IOException {
        UserService userService = new UserService();
        String userName = getCurrentUser();
        System.out.println(userName);
        boolean addCourse = userService.addToFavorites(userName, courseAcro);
        if (addCourse) {
            Intent switchActivityIntent = new Intent(this, AddFavoritesActivity.class);
            startActivity(switchActivityIntent);
            return true;
        }
        else {
            Intent switchActivityIntent = new Intent(this, AddFavoritesActivity.class);
            startActivity(switchActivityIntent);
            return false;
        }

    }

    private boolean rmFavorites(String courseAcro) {
        String userName = getCurrentUser();
        System.out.println(userName);
        if (favoritesList.isEmpty()) {
            //Skilaboð um að það gekk ekki því listinn er tómur
            return false;
        }
        // Hér þurfum við að fjarlægja courseAcro strenginn úr
        // favorites listanum hjá notandanum

        // Hér ætti að vera notað listann frá notandanum

        for (String string: favoritesList) {
            if (string.equals(courseAcro)) {
                favoritesList.remove(string);
                Intent switchActivityIntent = new Intent(this, AddFavoritesActivity.class);
                startActivity(switchActivityIntent);
                return true;
            }
        }
        // Skilaboð um villu
        return false;
    }
}
