package com.example.badminton_court;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import org.mindrot.jbcrypt.BCrypt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


public class SignUpActivity extends AppCompatActivity {

    private EditText nameInput, emailInput, passwordInput, ageInput, cpasswordInput;

    private Button registerbtn;

    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);


        nameInput = (EditText) findViewById(R.id.nameInputRegister);
        emailInput = (EditText) findViewById(R.id.emailInputRegister);
        passwordInput = (EditText) findViewById(R.id.passwordInputRegister);
        cpasswordInput = (EditText) findViewById(R.id.confirmPasswordInputRegister);
        ageInput = (EditText) findViewById(R.id.ageInputRegister);
        registerbtn = (Button) findViewById(R.id.btn_register);

        dbManager = new DBManager(this);
        dbManager.open();

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameInput.getText().toString();
                String email = emailInput.getText().toString();
                String password = passwordInput.getText().toString();
                String cpassword = cpasswordInput.getText().toString();
                String age = ageInput.getText().toString();

                Boolean checkDbExistEmail = dbManager.checkExistEmail(email);//check if email is already registered

                //check password and confirm password are matched
                if(name.equals("") || email.equals("") || password.equals("") || cpassword.equals("") || age.equals("")){
                    Toast.makeText(SignUpActivity.this, "Please fill in all the details required.", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(password.equals(cpassword)){
                        if(checkAge(age)){
                            if(checkDbExistEmail == true){ //if the email is exist
                                Toast.makeText(SignUpActivity.this, "Email is used by someone. Please use another email.", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                String hashedPass = hashPassword(password);//store the hashed password
                                long result = dbManager.addUser(name, email, hashedPass, age);
                                if (result != -1) {
                                    Toast.makeText(SignUpActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(SignUpActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                        else{
                            Toast.makeText(SignUpActivity.this, "Please enter a valid age.", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(SignUpActivity.this, "Password and confirm password are not matched.", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbManager.close();
    }

    public void signInPage(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    //check valid age
    public boolean checkAge(String age){
        int isValidAge = Integer.valueOf(age);
        if(isValidAge <= 0 || isValidAge >= 100){
            return false;
        }
        else{
            return true;
        }
    }

    //password hashing
    public String hashPassword(String password) {
        // Generate a salt
        String salt = BCrypt.gensalt(12); // 12 is the "work factor," the number of rounds of hashing

        // Hash the password with the salt
        String hashedPassword = BCrypt.hashpw(password, salt);

        return hashedPassword;
    }
}
