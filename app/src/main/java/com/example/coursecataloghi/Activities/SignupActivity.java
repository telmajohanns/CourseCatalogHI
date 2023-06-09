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

public class SignupActivity extends AppCompatActivity {

    //Viðmótshlutir
    private EditText newUsername, newPassword, newRetypePassword;
    private Button signupButton;

    private TextView goBackButton;

    //Tilviksbreyta fyrir userservice
    private UserService userServ;

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

        //Onclick listener fyrir signup takkann
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uName = newUsername.getText().toString();
                String pwd = newPassword.getText().toString();
                String pwdAgain = newRetypePassword.getText().toString();
                boolean userExists = false;
                if (!userExists){
                    if (pwd.equals(pwdAgain)) {
                        userServ.createUser(uName, pwd);
                        Toast.makeText(SignupActivity.this, "Nýskráning gekk, velkomin/nn.", Toast.LENGTH_SHORT).show();
                        goBack();
                    }else{
                        Toast.makeText(SignupActivity.this, "Lykilorð passa ekki, reyndu aftur.", Toast.LENGTH_SHORT).show();
                        newPassword.getText().clear();
                        newRetypePassword.getText().clear();
                    }
                }else{
                    Toast.makeText(SignupActivity.this, "Notandanafn þegar í notkun, reyndu aftur.", Toast.LENGTH_SHORT).show();
                    newUsername.getText().clear();
                    newPassword.getText().clear();
                    newRetypePassword.getText().clear();
                }
            }
        });

        //Onclick listener fyrir til baka takkann
        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBack();
            }
        });
    }

    /**
     * Fall sem sér um að færa notenda aftur yfir á innskráningar síðu eftir að hann hefur
     * búið til nýjann aðgang eða ýtt á til baka takkann.
     */
    private void goBack(){
        Intent switchActivityIntent = new Intent(this, MainActivity.class);
        startActivity(switchActivityIntent);
    }


}