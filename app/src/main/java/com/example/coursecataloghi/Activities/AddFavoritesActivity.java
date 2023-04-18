package com.example.coursecataloghi.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.coursecataloghi.R;

import java.util.ArrayList;

public class AddFavoritesActivity extends AppCompatActivity {
    ArrayList<String> favoritesList = new ArrayList<>();
    private Spinner courses_drop_down;
    private Button add_favorites, rm_favorites, back_to_catalog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_favorites);

        courses_drop_down = (Spinner) findViewById(R.id.courses_drop_down);
        add_favorites = (Button) findViewById(R.id.add_favorites);
        rm_favorites = (Button) findViewById(R.id.rm_favorites);
        back_to_catalog = (Button) findViewById(R.id.back_to_catalog);

        back_to_catalog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToCatalog();
            }
        });
        add_favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFavorites(courses_drop_down.getSelectedItem().toString());
            }
        });
        rm_favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rmFavorites(courses_drop_down.getSelectedItem().toString());
            }
        });
    }

    public void backToCatalog() {
        Intent switchActivityIntent = new Intent(this, CourseCatalogActivity.class);
        startActivity(switchActivityIntent);
    }

    public boolean addFavorites(String courseAcro) {
        // Hér þurfum við að bæta courseAcro strengnum við í
        // favorites listann hjá notandanum
        if (favoritesList.isEmpty()) {
            favoritesList.add(courseAcro);
            Intent switchActivityIntent = new Intent(this, AddFavoritesActivity.class);
            startActivity(switchActivityIntent);
            return true;
        }

        // Hér ætti að vera notað listann frá notandanum
        for (String string: favoritesList) {
            if (string.equals(courseAcro)) {
                // Skilaboð um að það gekk ekki því áfanginn er í favorites nú þegar
                return false;
            }
        }
        favoritesList.add(courseAcro);
        Intent switchActivityIntent = new Intent(this, AddFavoritesActivity.class);
        startActivity(switchActivityIntent);
        return true;
    }

    public boolean rmFavorites(String courseAcro) {
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
