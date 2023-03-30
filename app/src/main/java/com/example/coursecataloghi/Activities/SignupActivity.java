package com.example.coursecataloghi.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coursecataloghi.R;
import com.example.coursecataloghi.Services.UserService;

import Entities.Data;
import Entities.User;

public class SignupActivity extends AppCompatActivity {

    EditText newUsername, newPassword, newRetypePassword;
    Button signupButton;

    TextView goBackButton;
    UserService userServ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        newUsername = (EditText) findViewById(R.id.newUsername);
        newPassword = (EditText) findViewById(R.id.newPassword);
        newRetypePassword = (EditText) findViewById(R.id.newRetypePassword);
        signupButton = (Button) findViewById(R.id.btnSignUp);
        goBackButton = (TextView) findViewById(R.id.btnGoBack);
        userServ = new UserService();

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uName = newUsername.getText().toString();
                String pwd = newPassword.getText().toString();
                String pwdrt = newRetypePassword.getText().toString();
                boolean userExists = userServ.doesUeserExsist(uName);
                if (!userExists){
                    if (pwd.equals(pwdrt)) {
                        userServ.createUser(uName, pwd);
                        Toast.makeText(SignupActivity.this, "Success, welcome", Toast.LENGTH_SHORT).show();
                        goBack();
                    }else{
                        Toast.makeText(SignupActivity.this, "Passwords don't match", Toast.LENGTH_SHORT).show();
                        newPassword.getText().clear();
                        newRetypePassword.getText().clear();
                    }
                }else{
                    Toast.makeText(SignupActivity.this, "Username already taken, try again", Toast.LENGTH_SHORT).show();
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


}