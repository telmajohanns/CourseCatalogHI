package com.example.coursecataloghi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

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

    }
}