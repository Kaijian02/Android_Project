package com.example.badminton_court;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.mindrot.jbcrypt.BCrypt;

public class ModifyProfileActivity  extends Activity implements View.OnClickListener {
    private EditText titleText;
    private EditText ageText;
    private EditText passwordText;
    private long _id;
    private Button updateBtn, cancelBtn;
    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Modify Record");

        setContentView(R.layout.activitymodifyprofile);

        dbManager = new DBManager(this);
        dbManager.open();

        titleText = (EditText) findViewById(R.id.name_edittext);
        ageText = (EditText) findViewById(R.id.age_edittext);
        passwordText = (EditText) findViewById(R.id.password_edittext);

        updateBtn = (Button) findViewById(R.id.btn_update);
        cancelBtn = (Button) findViewById(R.id.btn_cancel);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String name = intent.getStringExtra("title");
        String age = intent.getStringExtra("age");

        _id = Long.parseLong(id);

        titleText.setText(name);
        ageText.setText(age);

        updateBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.btn_update) {

            String title = titleText.getText().toString();
            String age = ageText.getText().toString();
            String password = passwordText.getText().toString();
            String hashedPass = hashPassword(password);//store the hashed password

            if (title.equals("") || age.equals("")) {
                Toast.makeText(ModifyProfileActivity.this,"Failed to Update profile, Null value detected!",Toast.LENGTH_SHORT).show();
            }
            else {
                if (password.equals("")) {
                    dbManager.updateWithoutPassword(_id, title, age);
                } else {
                    dbManager.update(_id, title, age, hashedPass);
                }
                Toast.makeText(ModifyProfileActivity.this,"Profile Updated!",Toast.LENGTH_SHORT).show();
            }

            this.returnHome();
        }

        else if (v.getId()==R.id.btn_cancel)
        {
            this.returnHome();
        }
    }

    public String hashPassword(String password) {
        // Generate a salt
        String salt = BCrypt.gensalt(12); // 12 is the "work factor," the number of rounds of hashing

        // Hash the password with the salt
        String hashedPassword = BCrypt.hashpw(password, salt);

        return hashedPassword;
    }
    public void returnHome() {
        Intent home_intent = new Intent(getApplicationContext(), EditProfileActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(home_intent);
    }
}
