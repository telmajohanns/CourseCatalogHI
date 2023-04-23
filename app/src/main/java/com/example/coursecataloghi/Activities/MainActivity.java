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
import com.example.coursecataloghi.R;
import com.example.coursecataloghi.Services.UserService;

public class MainActivity extends AppCompatActivity {

    //Viðmótshlutir
    private EditText username, password;
    private Button loginButton;
    private TextView signUpButton;

    //Tilviksbreyta af sharedpreference til þess að halda utan um innskráðan notenda
    private SharedPreferences sharedPref;

    //Tilviksbreyta af userservice
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

        //Onclick listener fyrir nýskráningar takka
        signUpButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                goToSignUp();
            }
        });

        //Onclick listener fyrir innskráningar takka
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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


    /**
     * Fall til þess að færa notenda af innskráningar síðu og yfir á aðalsíðuna í appinu
     */
    private void loggedIn(){
        Intent switchActivityIntent = new Intent(this, CourseCatalogActivity.class);
        startActivity(switchActivityIntent);
    }

    /**
     * Fall sem færir notenda af innskráningar síðu og yfir á síðu til þess að stofna aðgang
     */
    private void goToSignUp() {
        Intent switchActivityIntent = new Intent(this, SignupActivity.class);
        startActivity(switchActivityIntent);
    }
}