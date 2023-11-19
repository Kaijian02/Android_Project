package com.example.badminton_court;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class RequestActionActivity extends Activity implements OnClickListener {

    private Button approveBtn, rejectBtn;


    private TextView requestIdText;
    private TextView usernameText;
    private TextView emailText;
    private TextView branchText;
    private TextView courtText;
    private TextView dateText;
    private TextView timeText;
    private TextView hostText;
    private String requestId;
    private String bookingId;
    private String username;
    private String email;
    private String branch;
    private String court;
    private String date;
    private String time;
    private String host;


    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Join Your Court?");
        setContentView(R.layout.activity_request_action);

        dbManager = new DBManager(this);
        dbManager.open();


        requestIdText = (TextView) findViewById(R.id.requestId_textview);
        usernameText = (TextView) findViewById(R.id.username_textview);
        emailText = (TextView) findViewById(R.id.email_textview);

        approveBtn = (Button) findViewById(R.id.btn_approve);
        rejectBtn = (Button) findViewById(R.id.btn_reject);

        Intent intent = getIntent();
        requestId = intent.getStringExtra("id");
        bookingId = intent.getStringExtra("bookingId");
        username = intent.getStringExtra("username");
        email = intent.getStringExtra("email");
        branch = intent.getStringExtra("branch");
        court = intent.getStringExtra("court");
        date = intent.getStringExtra("date");
        time = intent.getStringExtra("time");
        host = intent.getStringExtra("host");



        requestIdText.setText(requestId);
        usernameText.setText(username);
        emailText.setText(email);

        approveBtn.setOnClickListener(this);
        rejectBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.btn_approve) {

            boolean checkJoinIsZero = dbManager.checkJoinPlayers(bookingId);
            if(checkJoinIsZero == true){
                Toast.makeText(RequestActionActivity.this, "The players you wish to join is full", Toast.LENGTH_SHORT).show();
            }
            else {
                long result = dbManager.approveRequest(bookingId, username, email, branch, court, date, time, host);
                if (result != -1) {
                    Toast.makeText(RequestActionActivity.this, "Request Approved", Toast.LENGTH_SHORT).show();
                    dbManager.updateAfterApproved(bookingId);
                    dbManager.deleteRequest(requestId);
                    // Go back to the homepage
                    this.returnHome();

                } else {
                    Toast.makeText(RequestActionActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        }

        else if (v.getId()==R.id.btn_reject)
        {
            dbManager.deleteRequest(requestId);
            Toast.makeText(RequestActionActivity.this, "Request Rejected", Toast.LENGTH_SHORT).show();
            this.returnHome();
        }
    }

    public void returnHome() {
        Intent home_intent = new Intent(getApplicationContext(), RequestListActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        home_intent.putExtra("id", bookingId);
        startActivity(home_intent);
    }
}