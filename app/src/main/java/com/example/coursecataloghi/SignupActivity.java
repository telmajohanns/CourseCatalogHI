package com.example.coursecataloghi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileWriter;
import java.io.IOException;

import Entities.Data;
import Entities.User;

public class SignupActivity extends AppCompatActivity {

    EditText newUsername, newPassword, newRetypePassword;
    Button signupButton, goBackButton;
    Data data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        newUsername = (EditText) findViewById(R.id.newUsername);
        newPassword = (EditText) findViewById(R.id.newPassword);
        newRetypePassword = (EditText) findViewById(R.id.newRetypePassword);
        signupButton = (Button) findViewById(R.id.btnSignUp);
        goBackButton = (Button) findViewById(R.id.btnGoBack);
        data = Data.getInstance();

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uName = newUsername.getText().toString();
                String pwd = newPassword.getText().toString();
                String pwdrt = newRetypePassword.getText().toString();
                boolean userExists = doesUeserExsist(uName);
                if (!userExists){
                    if (pwd.equals(pwdrt)) {
                        data.createUser(uName, pwd);
                        Toast.makeText(SignupActivity.this, "Success, welcome", Toast.LENGTH_SHORT).show();
                        userCreated();
                    }else{
                        Toast.makeText(SignupActivity.this, "Passwords don't match", Toast.LENGTH_SHORT).show();
                        newPassword.getText().clear();
                        newRetypePassword.getText().clear();
                    }
                }else{
                    Toast.makeText(SignupActivity.this, "Failed, try again", Toast.LENGTH_SHORT).show();
                    newUsername.getText().clear();
                    newPassword.getText().clear();
                    newRetypePassword.getText().clear();
                }
            }
        });

        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBack();
            }
        });


    }
    private void goBack(){
        Intent switchActivityIntent = new Intent(this, MainActivity.class);
        startActivity(switchActivityIntent);
    }

    boolean doesUeserExsist(String uName){
        for (User u: data.getUsers())
            if(uName.equals(u.getUsername())){
                return true;
            }
        return false;
    }

    private void userCreated(){
        Intent switchActivityIntent = new Intent(this, MainActivity.class);
        startActivity(switchActivityIntent);
    }
}