package com.example.badminton_court;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.mindrot.jbcrypt.BCrypt;


public class MainActivity extends AppCompatActivity {

    private EditText emailInput, passwordInput;

    private Button loginbtn;

    private DBManager dbManager;

    SharedPreferences sharedpreferences;
    public static final String mypreference = "user_data";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbManager = new DBManager(this);

        emailInput = (EditText) findViewById(R.id.emailInputLogin);
        passwordInput = (EditText) findViewById(R.id.passwordInputLogin);
        loginbtn = (Button) findViewById(R.id.btn_login);
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailInput.getText().toString();
                String password = passwordInput.getText().toString();

                if(email.equals("") || password.equals("")){
                    Toast.makeText(MainActivity.this, "Please filled in email and password for login.", Toast.LENGTH_SHORT).show();
                }
                else{

                    Boolean checkuserpass = dbManager.checkemailpassword(email, password);
                    if(checkuserpass==true){
                        sharedpreferences = getSharedPreferences(mypreference,
                                Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString("user_email", email);
                        editor.apply();

                        Toast.makeText(MainActivity.this, " Login successfully.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(MainActivity.this, " Login failed. Invalid credentials.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    public void signUpPage(View view){
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    public void homePage(View view){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}