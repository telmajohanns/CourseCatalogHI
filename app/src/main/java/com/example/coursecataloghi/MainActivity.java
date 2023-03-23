package com.example.coursecataloghi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import Entities.Data;
import Entities.User;

public class MainActivity extends AppCompatActivity {
    //Steinunn var hér
    //Hebbi líka
    EditText username, password;
    Button loginButton;

    TextView signUpButton;
    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        loginButton = (Button) findViewById(R.id.btnlogin);
        signUpButton = (TextView) findViewById(R.id.btnsignup);
        sharedPref = this.getSharedPreferences(
                getString(R.string.sharedpreffile), Context.MODE_PRIVATE);

        signUpButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                goToSignUp();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Græja hvað gerist þegar ýtt er á login
                String uName = username.getText().toString();
                String pwd = password.getText().toString();
                boolean loggingIN = logIn(uName, pwd);
                if(loggingIN){
                    loggedIn();
                }
                else {
                    Toast.makeText(MainActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                    username.getText().clear();
                    password.getText().clear();
                }
            }
        });
    }

    private boolean logIn(String userName, String pwd){
        Data data = Data.getInstance();
        for (User u: data.getUsers()) {
            if (userName.equals(u.getUsername())) {
                if (pwd.equals(u.getPassword())) {
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString(getString(R.string.username), userName);
                    editor.apply();
                    return true;
                }
            }
        }

        return false;
    }

    private void loggedIn(){
        Intent switchActivityIntent = new Intent(this, CourseCatalogActivity.class);
        startActivity(switchActivityIntent);
    }

    private void goToSignUp() {
        Intent switchActivityIntent = new Intent(this, SignupActivity.class);
        startActivity(switchActivityIntent);
    }
}