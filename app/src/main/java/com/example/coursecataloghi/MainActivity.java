package com.example.coursecataloghi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    //Steinunn var hér
    //Hebbi líka
    EditText username, password;
    Button loginbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        loginbutton = (Button) findViewById(R.id.btnlogin);

        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Græja hvað gerist þegar ýtt er á login
                String uName = username.getText().toString();
                String pwd = password.getText().toString();
                boolean loggingIN = logIn(uName, pwd);
            }
        });
    }

    public boolean logIn(String userName, String pwd){
        boolean acceptance = false;


        return acceptance;
    }
}