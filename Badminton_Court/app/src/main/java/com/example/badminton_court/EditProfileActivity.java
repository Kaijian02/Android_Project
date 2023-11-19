package com.example.badminton_court;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class EditProfileActivity extends AppCompatActivity {
    private DBManager dbManager;
    private TextView name;
    private TextView email;
    private TextView age;
    Button editProfile;
    String value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editprofile);

        dbManager = new DBManager(this);

        name = (TextView) findViewById(R.id.name);
        email = (TextView) findViewById(R.id.email);
        age = (TextView) findViewById(R.id.age);

        editProfile = (Button) findViewById(R.id.editProfile);

        SharedPreferences sharedPreferences = this.getSharedPreferences("user_data", Context.MODE_PRIVATE);
        String userEmail = sharedPreferences.getString("user_email", "");
        User user = dbManager.getUserByEmail(userEmail);
        String userName = String.valueOf(user.getName());
        String userAge = String.valueOf(user.getAge());
        String _id = String.valueOf(user.getId());

        name.setText(userName);
        email.setText(userEmail);
        age.setText(userAge);

        //set onclicklistener for edit name buttons
        editProfile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent modify_intent = new Intent(getApplicationContext(), ModifyProfileActivity.class);
                modify_intent.putExtra("id", _id);
                modify_intent.putExtra("title", userName);
                modify_intent.putExtra("email", userEmail);
                modify_intent.putExtra("age", userAge);

                startActivity(modify_intent);
            }
        });
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

    public void signInPage(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}