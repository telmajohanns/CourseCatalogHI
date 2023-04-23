package com.example.coursecataloghi.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

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

    // Viðmótshlutir
    private Spinner courses_drop_down;
    private Button add_favorites, rm_favorites, back_to_catalog;

    // Tilviksbreyta, listi af skammstöfunum uppáhalds áfanga notandans
    private ArrayList<String> favoritesList = new ArrayList<>();
    // SharedPreferences hlutur til að sækja innskráðan notanda
    private SharedPreferences sharedPref;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_favorites);

        courses_drop_down = (Spinner) findViewById(R.id.courses_drop_down);
        add_favorites = (Button) findViewById(R.id.add_favorites);
        rm_favorites = (Button) findViewById(R.id.rm_favorites);
        back_to_catalog = (Button) findViewById(R.id.back_to_catalog);

        // Kallar á backToCatalog() sem færir notandann aftur á kennsluskrár síðuna
        back_to_catalog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToCatalog();
            }
        });

        // Bæta við áfanga í eigin áfanga
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

        // Fjarlægja áfanga úr eigin áföngum, virkni ennþá í vinnslu
        rm_favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rmFavorites(courses_drop_down.getSelectedItem().toString());
            }
        });
    }

    /**
     * Fall sem færir notandann aftur á kennsluskrár síðuna
     */
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

    /**
     * Fall sem sækir innskráðan notanda í SharedPreferences
     * @return Notandanafn innskráðs notanda
     */
    public String getCurrentUser() {
        // Sækja notandann sem er skráður inn
        sharedPref = this.getSharedPreferences(
                getString(R.string.sharedpreffile), Context.MODE_PRIVATE);

        return (sharedPref.getString("Notandanafn", "Default_Value"));
    }

    /**
     * Fall sem bætir áfanga í uppáhalds lista notanda ef hann var ekki þegar til í listanum
     * og endurræsir AddFavorites síðunni
     * @param courseAcro Skammstöfun áfanga sem var valið að bæta við
     * @throws IOException
     */
    private void addFavorites(String courseAcro) throws IOException {
        UserService userService = new UserService();

        boolean addCourse = userService.addToFavorites(getCurrentUser(), courseAcro);
        if (addCourse) {
            Toast.makeText(AddFavoritesActivity.this, "Áfanga bætt við á lista", Toast.LENGTH_SHORT).show();
            Intent switchActivityIntent = new Intent(this, AddFavoritesActivity.class);
            startActivity(switchActivityIntent);
        }
        else {
            Toast.makeText(AddFavoritesActivity.this, "Áfangi þegar til á lista og var því ekki bætt við", Toast.LENGTH_SHORT).show();
            Intent switchActivityIntent = new Intent(this, AddFavoritesActivity.class);
            startActivity(switchActivityIntent);
        }

    }

    /**
     * Fall sem fjarlægjir áfanga af uppáhalds lista notanda ef hann var þegar til í listanum
     * og endurræsir AddFavorites síðunni
     * Virkni enn í vinnslu
     * @param courseAcro Skammstöfun áfanga sem á að fjarlægja
     */
    private void rmFavorites(String courseAcro) {

        UserService userService = new UserService();

        boolean rmCourse = false; //userService.rmFavorites(getCurrentUser(), courseAcro);
        if (rmCourse) {
            // Vantar að útfæra rmFavorites í userService, NetworkManager og bakenda
            // Skilaboð um að það gekk að fjarlægja áfanga úr lista
            //Toast.makeText(AddFavoritesActivity.this, "Áfangi fjarlægður af lista", Toast.LENGTH_SHORT).show();
            Intent switchActivityIntent = new Intent(this, AddFavoritesActivity.class);
            startActivity(switchActivityIntent);
        }
        else {
            //Skilaboð um að það gekk ekki því áfangi var ekki til í lista
            //Toast.makeText(AddFavoritesActivity.this, "Áfangi fannst ekki í lista og var því ekki fjarlægður", Toast.LENGTH_SHORT).show();
            Intent switchActivityIntent = new Intent(this, AddFavoritesActivity.class);
            startActivity(switchActivityIntent);
        }
    }
}
