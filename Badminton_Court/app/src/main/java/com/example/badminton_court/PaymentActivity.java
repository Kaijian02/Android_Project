package com.example.badminton_court;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class PaymentActivity extends AppCompatActivity {

    private Button confirmPayment;
    private Button cancel;
    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment);
        dbManager = new DBManager(this);

        SharedPreferences sharedPreferences = getSharedPreferences("user_data", Context.MODE_PRIVATE);
        String userEmail = sharedPreferences.getString("user_email", "");
        User user = dbManager.getUserByEmail(userEmail);

        Intent intent = getIntent();
        String selectedDate = intent.getStringExtra("selectedDate");
        String selectedBranch = intent.getStringExtra("selectedBranch");
        String selectedCourt = intent.getStringExtra("selectedCourt");
        String selectedTime = intent.getStringExtra("selectedTime");
        String selectedLevel = intent.getStringExtra("selectedLevel");
        String selectedStatus = intent.getStringExtra("selectedStatus");
        String currentPlayers = intent.getStringExtra("currentPlayers");
        String joinPlayers = intent.getStringExtra("joinPlayers");
        String userId = String.valueOf(user.getId());
        String userName = String.valueOf(user.getName());
        String courtPrice = "RM 20.00";

        TextView date = findViewById(R.id.date);
        TextView time = findViewById(R.id.time);
        TextView court = findViewById(R.id.court);
        TextView level = findViewById(R.id.level);
        TextView price = findViewById(R.id.price);

        date.setText("Date: " + selectedDate);
        time.setText("Time: " +selectedTime);
        court.setText("Court: " +selectedCourt);
        level.setText("Skill: " +selectedLevel);
        price.setText("Price: " +courtPrice);

        EditText cardNumberEditText = findViewById(R.id.cardNumber);
        EditText monthEditText = findViewById(R.id.month);
        EditText yearEditText = findViewById(R.id.year);
        EditText ccvEditText = findViewById(R.id.ccv);
        confirmPayment = findViewById(R.id.confirmPayment);
        cancel = findViewById(R.id.cancel);

        // click listener for confirm payment
        confirmPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String cardNumber = cardNumberEditText.getText().toString().trim();
                String month = monthEditText.getText().toString().trim();
                String year = yearEditText.getText().toString().trim();
                String ccv = ccvEditText.getText().toString().trim();

                if (cardNumber.isEmpty()) {
                    cardNumberEditText.setError("Card number is required");
                    return;
                }

                // Check if the input contains only digits and is exactly 16 digits
                if (!TextUtils.isDigitsOnly(cardNumber) || cardNumber.length() != 16) {
                    cardNumberEditText.setError("Invalid card number. Must be 16 digits.");
                    return;
                }

                // Validate month
                if (month.isEmpty()) {
                    monthEditText.setError("Month is required");
                    return;
                }

                int monthValue;
                try {
                    monthValue = Integer.parseInt(month);
                } catch (NumberFormatException e) {
                    monthEditText.setError("Invalid month");
                    return;
                }

                if (monthValue < 1 || monthValue > 12) {
                    monthEditText.setError("Invalid month");
                    return;
                }

                // Validate year
                if (year.isEmpty()) {
                    yearEditText.setError("Year is required");
                    return;
                }

                if (!TextUtils.isDigitsOnly(year)) {
                    yearEditText.setError("Invalid year");
                    return;
                }

                int currentYear = Calendar.getInstance().get(Calendar.YEAR);
                int currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1; // Adding 1 as Calendar.MONTH is zero-based
                int yearValue = Integer.parseInt(year); // Convert to integer for validation

                if (yearValue < currentYear || (yearValue == currentYear && monthValue < currentMonth)) {
                    yearEditText.setError("Card is expired");
                    return;
                }

                // Validate CCV
                if (ccv.isEmpty()) {
                    ccvEditText.setError("CCV is required");
                    return;
                }

                if (ccv.length() != 3 || !TextUtils.isDigitsOnly(ccv)) {
                    ccvEditText.setError("Invalid CCV");
                    return;
                }

                dbManager.open();
                long result = dbManager.booking(selectedDate, selectedBranch, selectedCourt, selectedTime, selectedLevel, selectedStatus, currentPlayers, joinPlayers, userId, userName);
                if (result != -1) {
                    Toast.makeText(PaymentActivity.this, "Booking successful", Toast.LENGTH_SHORT).show();
                    // Go back to the homepage
                    Intent intent = new Intent(PaymentActivity.this, HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();

                } else {
                    Toast.makeText(PaymentActivity.this, "Booking failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // click listener for cancel button
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Finish
                finish();
            }
        });

    }

    public void homePage(View view){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    public void signInPage(View view) {
        Intent intent = new Intent(this, MainActivity.class);
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
}