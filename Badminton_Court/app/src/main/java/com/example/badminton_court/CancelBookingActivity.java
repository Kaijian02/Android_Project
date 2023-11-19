package com.example.badminton_court;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class CancelBookingActivity extends Activity implements OnClickListener {
    private Button requestBtn, cancelBtn;
    private TextView dateText;
    private TextView timeText;
    private TextView branchText, courtText, skillText, statusText, currentText, joinText;
    private long _id;
    private String bookingId;
    private DBManager dbManager;
    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_booking);
        dbManager = new DBManager(this);
        dbManager.open();


        dateText = (TextView) findViewById(R.id.date_textview);
        timeText = (TextView) findViewById(R.id.time_textview);
        branchText = (TextView) findViewById(R.id.branch_textview);
        courtText = (TextView) findViewById(R.id.court_textview);
        skillText = (TextView) findViewById(R.id.skill_textview);
        statusText = (TextView) findViewById(R.id.status_textview);
        currentText = (TextView) findViewById(R.id.current_textview);
        joinText = (TextView) findViewById(R.id.join_textview);

        requestBtn = (Button) findViewById(R.id.btn_viewRequest);
        cancelBtn = (Button) findViewById(R.id.btn_cancel);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String date = intent.getStringExtra("date");
        String time = intent.getStringExtra("time");
        String branch = intent.getStringExtra("branch");

        bookingId = id;
        //Get details
        Cursor cursor = dbManager.fetchOneBooking(id);
        _id = Long.parseLong(id);


        dateText.setText(date);
        timeText.setText(time);
        branchText.setText(branch);

        if(cursor!=null && cursor.moveToFirst()){
            courtText.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.BOOKING_COURT)));
            skillText.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.BOOKING_SKILL)));
            statusText.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.BOOKING_STATUS)));
            currentText.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.BOOKING_CURRENT_PLAYERS)));
            joinText.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.BOOKING_JOIN_PLAYERS)));
        }

        requestBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.btn_viewRequest) {

            Intent modify_intent = new Intent(getApplicationContext(), RequestListActivity.class);
                modify_intent.putExtra("id", bookingId);

                startActivity(modify_intent);
        }

        else if (v.getId()==R.id.btn_cancel)
        {
            dbManager.cancelBooking(_id);
            Toast.makeText(CancelBookingActivity.this, "Booking cancelled", Toast.LENGTH_SHORT).show();
            this.returnHome();

        }
    }

    public void returnHome() {
        Intent home_intent = new Intent(getApplicationContext(), HistoryListActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(home_intent);
    }

    public void back(View view){
        Intent intent = new Intent(this, HistoryListActivity.class);
        startActivity(intent);
    }
}