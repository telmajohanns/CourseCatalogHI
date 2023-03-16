package com.example.coursecataloghi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.FileWriter;
import java.io.IOException;

import Entities.User;

public class SignupActivity extends AppCompatActivity {

    EditText newUsername, newPassword, newRetypePassword;
    Button signupButton, goBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        newUsername = (EditText) findViewById(R.id.newUsername);
        newPassword = (EditText) findViewById(R.id.newPassword);
        newRetypePassword = (EditText) findViewById(R.id.newRetypePassword);
        signupButton = (Button) findViewById(R.id.btnSignUp);
        goBackButton = (Button) findViewById(R.id.btnGoBack);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Tékka hvort user sé nú þegar til, ef svo er gefa viðvörun og alls ekki búa hann til
                User user = new User(newUsername.getText().toString(), newPassword.getText().toString());
                /*try {
                    //Reyna að finna leið til að búa til user í txt skrá meðan bakenda og db ruglið er ves
                    FileWriter fileWriter = new FileWriter("users.txt");
                    fileWriter.write(user.getUsername() + "," + user.getPassword());
                    fileWriter.write(System.lineSeparator());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }*/


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
}