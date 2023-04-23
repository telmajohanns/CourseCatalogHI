package com.example.coursecataloghi.Activities;

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

import com.example.coursecataloghi.Networking.NetworkManager;
import com.example.coursecataloghi.R;
import com.example.coursecataloghi.Services.UserService;

import Entities.Data;
import Entities.User;

public class MainActivity extends AppCompatActivity {

    private EditText username, password;
    private Button loginButton;

    private TextView signUpButton;
    private SharedPreferences sharedPref;
    private UserService userServ;

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
        userServ = new UserService();

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


                int loggingIN = userServ.login(uName, pwd);

                if(loggingIN == 200){
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString(getString(R.string.username), uName);
                    editor.apply();
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




    private void loggedIn(){
        Intent switchActivityIntent = new Intent(this, CourseCatalogActivity.class);
        startActivity(switchActivityIntent);
    }

    private void goToSignUp() {
        Intent switchActivityIntent = new Intent(this, SignupActivity.class);
        startActivity(switchActivityIntent);
    }
}