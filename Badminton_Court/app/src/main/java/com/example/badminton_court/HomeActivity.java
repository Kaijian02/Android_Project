package com.example.badminton_court;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

public class HomeActivity extends AppCompatActivity {

    TextView nameText;

    Button bookbtn;

    private DBManager dbManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        bookbtn = (Button) findViewById(R.id.bookButton);

        dbManager = new DBManager(this);

        nameText =(TextView)findViewById(R.id.name);

        SharedPreferences sharedPreferences = getSharedPreferences("user_data", Context.MODE_PRIVATE);
        String userEmail = sharedPreferences.getString("user_email", "");
//        nameText.setText(userEmail);

        // Query the database to retrieve user details based on the email
        User user = dbManager.getUserByEmail(userEmail);

        if (user != null) {
            nameText.setText("Hello, " + user.getName());
//            Toast.makeText(HomeActivity.this, "User found", Toast.LENGTH_SHORT).show();
        } else {
            // Handle the case when the user is not found
            Toast.makeText(HomeActivity.this, "No user found", Toast.LENGTH_SHORT).show();
        }

        bookbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, BookingActivity.class);
                startActivity(intent);
            }
        });
    }


    public void signInPage(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

    public void homePage(View view){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    public void historyPage(View view){
        Intent intent = new Intent(this, HistoryListActivity.class);
        startActivity(intent);
    }

    public void editProfilePage(View view){
        Intent intent = new Intent(this, EditProfileActivity.class);
        startActivity(intent);
    }

    public void courtSearchPage(View view){
        Intent intent = new Intent(this, CourtSearchActivity.class);
        startActivity(intent);
    }

    public void checkRequest(View view){
        Intent intent = new Intent(this, RequestListActivity.class);
        startActivity(intent);
    }

    public void checkRequestStatus(View view){
        Intent intent = new Intent(this, ApprovedListActivity.class);
        startActivity(intent);
    }


}
