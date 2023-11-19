package com.example.badminton_court;

import static android.graphics.Color.GREEN;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class BookingActivity extends AppCompatActivity {

    EditText date;

    private EditText dateEditText, currentPlayersEditText, joinPlayersEditText;
    private Spinner branchSpinner;
    private Spinner courtSpinner;
    private Button time10Button;
    private Button time12Button;
    private Button time14Button;
    private Button time16Button;
    private Button time18Button;
    private Button time20Button;
    private Spinner levelSpinner;
    private Spinner statusSpinner;
    private Button bookButton;
    private String selectedTime = ""; // Variable to store the selected time

    private String selectedDate;


    private DBManager dbManager;

    DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        dbManager = new DBManager(this);

        SharedPreferences sharedPreferences = getSharedPreferences("user_data", Context.MODE_PRIVATE);
        String userEmail = sharedPreferences.getString("user_email", "");
        User user = dbManager.getUserByEmail(userEmail);

        date = (EditText) findViewById(R.id.date);

        Button time10 = (Button) findViewById(R.id.time10);
        Button time12 = (Button) findViewById(R.id.time12);
        Button time14 = (Button) findViewById(R.id.time14);
        Button time16 = (Button) findViewById(R.id.time16);
        Button time18 = (Button) findViewById(R.id.time18);
        Button time20 = (Button) findViewById(R.id.time20);

        dateEditText = findViewById(R.id.date);
        branchSpinner = findViewById(R.id.dropdown_branch);
        courtSpinner = findViewById(R.id.dropdown_court);
        time10Button = findViewById(R.id.time10);
        time12Button = findViewById(R.id.time12);
        time14Button = findViewById(R.id.time14);
        time16Button = findViewById(R.id.time16);
        time18Button = findViewById(R.id.time18);
        time20Button = findViewById(R.id.time20);
        levelSpinner = findViewById(R.id.dropdown_level);
        statusSpinner = findViewById(R.id.dropdown_status);
        currentPlayersEditText = findViewById(R.id.currentPlayers);
        joinPlayersEditText = findViewById(R.id.joinPlayers);
        bookButton = findViewById(R.id.bookButton);
        TextView timeError = findViewById(R.id.timeError);

        int purpleButtonBackground = ContextCompat.getColor(this, R.color.purple);
        int greenButtonBackground = ContextCompat.getColor(this, R.color.green);
        int greyButtonBackground =  ContextCompat.getColor(this, R.color.grey);


        // Set click listeners for time buttons
        time10Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedTime = time10Button.getText().toString();
                time10Button.setBackgroundColor(greenButtonBackground);
                if(time12Button.isEnabled()==true) {
                    time12Button.setBackgroundColor(purpleButtonBackground);
                }
                else{
                    time12Button.setBackgroundColor(greyButtonBackground);
                }
                if(time14Button.isEnabled()==true) {
                    time14Button.setBackgroundColor(purpleButtonBackground);
                }
                else{
                    time14Button.setBackgroundColor(greyButtonBackground);
                }
                if(time16Button.isEnabled()==true) {
                    time16Button.setBackgroundColor(purpleButtonBackground);
                }
                else{
                    time16Button.setBackgroundColor(greyButtonBackground);
                }
                if(time18Button.isEnabled()==true) {
                    time18Button.setBackgroundColor(purpleButtonBackground);
                }
                else{
                    time18Button.setBackgroundColor(greyButtonBackground);
                }
                if(time20Button.isEnabled()==true){
                    time20Button.setBackgroundColor(purpleButtonBackground);
                }
                else{
                    time20Button.setBackgroundColor(greyButtonBackground);
                }
            }
        });

        time12Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedTime = time12Button.getText().toString();
                time12Button.setBackgroundColor(greenButtonBackground);
                if(time10Button.isEnabled()==true) {
                    time10Button.setBackgroundColor(purpleButtonBackground);
                }
                else{
                    time10Button.setBackgroundColor(greyButtonBackground);
                }
                if(time14Button.isEnabled()==true) {
                    time14Button.setBackgroundColor(purpleButtonBackground);
                }
                else{
                    time14Button.setBackgroundColor(greyButtonBackground);
                }
                if(time16Button.isEnabled()==true) {
                    time16Button.setBackgroundColor(purpleButtonBackground);
                }
                else{
                    time16Button.setBackgroundColor(greyButtonBackground);
                }
                if(time18Button.isEnabled()==true) {
                    time18Button.setBackgroundColor(purpleButtonBackground);
                }
                else{
                    time18Button.setBackgroundColor(greyButtonBackground);
                }
                if(time20Button.isEnabled()==true){
                    time20Button.setBackgroundColor(purpleButtonBackground);
                }
                else{
                    time20Button.setBackgroundColor(greyButtonBackground);
                }
            }
        });

        time14Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedTime = time14Button.getText().toString();
                time14Button.setBackgroundColor(greenButtonBackground);
                if(time10Button.isEnabled()==true) {
                    time10Button.setBackgroundColor(purpleButtonBackground);
                }
                else{
                    time10Button.setBackgroundColor(greyButtonBackground);
                }
                if(time12Button.isEnabled()==true) {
                    time12Button.setBackgroundColor(purpleButtonBackground);
                }
                else{
                    time12Button.setBackgroundColor(greyButtonBackground);
                }
                if(time16Button.isEnabled()==true) {
                    time16Button.setBackgroundColor(purpleButtonBackground);
                }
                else{
                    time16Button.setBackgroundColor(greyButtonBackground);
                }
                if(time18Button.isEnabled()==true) {
                    time18Button.setBackgroundColor(purpleButtonBackground);
                }
                else{
                    time18Button.setBackgroundColor(greyButtonBackground);
                }
                if(time20Button.isEnabled()==true){
                    time20Button.setBackgroundColor(purpleButtonBackground);
                }
                else{
                    time20Button.setBackgroundColor(greyButtonBackground);
                }
            }
        });

        time16Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedTime = time16Button.getText().toString();
                time16Button.setBackgroundColor(greenButtonBackground);
                if(time10Button.isEnabled()==true) {
                    time10Button.setBackgroundColor(purpleButtonBackground);
                }
                else{
                    time10Button.setBackgroundColor(greyButtonBackground);
                }
                if(time12Button.isEnabled()==true) {
                    time12Button.setBackgroundColor(purpleButtonBackground);
                }
                else{
                    time12Button.setBackgroundColor(greyButtonBackground);
                }
                if(time14Button.isEnabled()==true) {
                    time14Button.setBackgroundColor(purpleButtonBackground);
                }
                else{
                    time14Button.setBackgroundColor(greyButtonBackground);
                }
                if(time18Button.isEnabled()==true) {
                    time18Button.setBackgroundColor(purpleButtonBackground);
                }
                else{
                    time18Button.setBackgroundColor(greyButtonBackground);
                }
                if(time20Button.isEnabled()==true){
                    time20Button.setBackgroundColor(purpleButtonBackground);
                }
                else{
                    time20Button.setBackgroundColor(greyButtonBackground);
                }
            }
        });

        time18Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedTime = time18Button.getText().toString();
                time18Button.setBackgroundColor(greenButtonBackground);
                if(time10Button.isEnabled()==true) {
                    time10Button.setBackgroundColor(purpleButtonBackground);
                }
                else{
                    time10Button.setBackgroundColor(greyButtonBackground);
                }
                if(time12Button.isEnabled()==true) {
                    time12Button.setBackgroundColor(purpleButtonBackground);
                }
                else{
                    time12Button.setBackgroundColor(greyButtonBackground);
                }
                if(time14Button.isEnabled()==true) {
                    time14Button.setBackgroundColor(purpleButtonBackground);
                }
                else{
                    time14Button.setBackgroundColor(greyButtonBackground);
                }
                if(time16Button.isEnabled()==true) {
                    time16Button.setBackgroundColor(purpleButtonBackground);
                }
                else{
                    time16Button.setBackgroundColor(greyButtonBackground);
                }
                if(time20Button.isEnabled()==true){
                    time20Button.setBackgroundColor(purpleButtonBackground);
                }
                else{
                    time20Button.setBackgroundColor(greyButtonBackground);
                }
            }
        });

        time20Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedTime = time20Button.getText().toString();
                time20Button.setBackgroundColor(greenButtonBackground);
                if(time10Button.isEnabled()==true) {
                    time10Button.setBackgroundColor(purpleButtonBackground);
                }
                else{
                    time10Button.setBackgroundColor(greyButtonBackground);
                }
                if(time12Button.isEnabled()==true) {
                    time12Button.setBackgroundColor(purpleButtonBackground);
                }
                else{
                    time12Button.setBackgroundColor(greyButtonBackground);
                }
                if(time14Button.isEnabled()==true) {
                    time14Button.setBackgroundColor(purpleButtonBackground);
                }
                else{
                    time14Button.setBackgroundColor(greyButtonBackground);
                }
                if(time16Button.isEnabled()==true) {
                    time16Button.setBackgroundColor(purpleButtonBackground);
                }
                else{
                    time16Button.setBackgroundColor(greyButtonBackground);
                }
                if(time18Button.isEnabled()==true){
                    time18Button.setBackgroundColor(purpleButtonBackground);
                }
                else{
                    time18Button.setBackgroundColor(greyButtonBackground);
                }
            }
        });

        // Set a click listener for the bookButton
        bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedDate = dateEditText.getText().toString();
                String selectedBranch = branchSpinner.getSelectedItem().toString();
                String selectedCourt = courtSpinner.getSelectedItem().toString();
                String selectedLevel = levelSpinner.getSelectedItem().toString();
                String selectedStatus = statusSpinner.getSelectedItem().toString();
                String currentPlayers = currentPlayersEditText.getText().toString();
                String joinPlayers = joinPlayersEditText.getText().toString();

                if (selectedDate.isEmpty()) {
                    dateEditText.setError("Please select a date");
                    return;
                }

                // Validate date format
                if (!isValidDateFormat(selectedDate, "dd/MM/yyyy")) {
                    dateEditText.setError("Invalid date format. Please use dd/MM/yyyy");
                    return;
                }
                else{
                    dateEditText.setError(null);
                }

                if (selectedTime.isEmpty()) {
                    timeError.setError("Please select a time");
                    return;
                }
                else{
                    timeError.setError(null);
                }

                // Validate currentPlayers
                if (currentPlayers.isEmpty()) {
                    currentPlayersEditText.setError("Number of players is required");
                    return;
                }

                if (!TextUtils.isDigitsOnly(currentPlayers)) {
                    currentPlayersEditText.setError("Please enter a valid number");
                    return;
                }

                int currentPlayersValue = Integer.parseInt(currentPlayers);
                if (currentPlayersValue < 1) {
                    currentPlayersEditText.setError("Number of players cannot be less than 1");
                    return;
                }

                // Validate joinPlayers
                if (joinPlayers.isEmpty()) {
                    joinPlayersEditText.setError("Number of join players is required");
                    return;
                }

                if (!TextUtils.isDigitsOnly(joinPlayers)) {
                    joinPlayersEditText.setError("Please enter a valid number");
                    return;
                }

                int joinPlayersValue = Integer.parseInt(joinPlayers);
                if (joinPlayersValue < 0) {
                    joinPlayersEditText.setError("Number of join players cannot be less than 0");
                    return;
                }

                Intent intent = new Intent(BookingActivity.this, PaymentActivity.class);
                intent.putExtra("selectedDate", selectedDate);
                intent.putExtra("selectedBranch", selectedBranch);
                intent.putExtra("selectedCourt", selectedCourt);
                intent.putExtra("selectedTime", selectedTime);
                intent.putExtra("selectedLevel", selectedLevel);
                intent.putExtra("selectedStatus", selectedStatus);
                intent.putExtra("currentPlayers", currentPlayers);
                intent.putExtra("joinPlayers", joinPlayers);

                startActivity(intent);
            }
        });

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day

                // date picker dialog
                datePickerDialog = new DatePickerDialog(BookingActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                date.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                Calendar minDate = Calendar.getInstance();
                minDate.add(Calendar.DAY_OF_MONTH, 1);
                datePickerDialog.getDatePicker().setMinDate(minDate.getTimeInMillis());
                datePickerDialog.show();
            }
        });

        // Find the Spinners by their IDs
        branchSpinner = findViewById(R.id.dropdown_branch);
        courtSpinner = findViewById(R.id.dropdown_court);

        // Set up the OnItemSelectedListener for the branchSpinner
        branchSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(selectedDate !=null) {
                    Boolean checkExist10 = dbManager.checkBookingAvailability(selectedDate, branchSpinner.getSelectedItem().toString(), courtSpinner.getSelectedItem().toString(), "10:00");
                    Boolean checkExist12 = dbManager.checkBookingAvailability(selectedDate, branchSpinner.getSelectedItem().toString(), courtSpinner.getSelectedItem().toString(), "12:00");
                    Boolean checkExist14 = dbManager.checkBookingAvailability(selectedDate, branchSpinner.getSelectedItem().toString(), courtSpinner.getSelectedItem().toString(), "14:00");
                    Boolean checkExist16 = dbManager.checkBookingAvailability(selectedDate, branchSpinner.getSelectedItem().toString(), courtSpinner.getSelectedItem().toString(), "16:00");
                    Boolean checkExist18 = dbManager.checkBookingAvailability(selectedDate, branchSpinner.getSelectedItem().toString(), courtSpinner.getSelectedItem().toString(), "18:00");
                    Boolean checkExist20 = dbManager.checkBookingAvailability(selectedDate, branchSpinner.getSelectedItem().toString(), courtSpinner.getSelectedItem().toString(), "20:00");

                if(checkExist10 == true){
                    time10.setEnabled(false);
                    time10Button.setBackgroundColor(greyButtonBackground);
                }
                else{
                    time10.setEnabled(true);
                    time10Button.setBackgroundColor(purpleButtonBackground);
                }
                if(checkExist12 == true){
                    time12.setEnabled(false);
                    time12Button.setBackgroundColor(greyButtonBackground);
                }
                else{
                    time12.setEnabled(true);
                    time12Button.setBackgroundColor(purpleButtonBackground);
                }
                if(checkExist14 == true){
                    time14.setEnabled(false);
                    time14Button.setBackgroundColor(greyButtonBackground);
                }
                else{
                    time14.setEnabled(true);
                    time14Button.setBackgroundColor(purpleButtonBackground);
                }
                if(checkExist16 == true){
                    time16.setEnabled(false);
                    time16Button.setBackgroundColor(greyButtonBackground);
                }
                else{
                    time16.setEnabled(true);
                    time16Button.setBackgroundColor(purpleButtonBackground);
                }
                if(checkExist18 == true){
                    time18.setEnabled(false);
                    time18Button.setBackgroundColor(greyButtonBackground);
                }
                else{
                    time18.setEnabled(true);
                    time18Button.setBackgroundColor(purpleButtonBackground);
                }
                if(checkExist20 == true){
                    time20.setEnabled(false);
                    time20Button.setBackgroundColor(greyButtonBackground);
                }
                else{
                    time20.setEnabled(true);
                    time20Button.setBackgroundColor(purpleButtonBackground);
                }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Handle case when nothing is selected (if needed)
            }
        });

        // Set up the OnItemSelectedListener for the courtSpinner
        courtSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (selectedDate != null) {
                    Boolean checkExist10 = dbManager.checkBookingAvailability(selectedDate, branchSpinner.getSelectedItem().toString(), courtSpinner.getSelectedItem().toString(), "10:00");
                    Boolean checkExist12 = dbManager.checkBookingAvailability(selectedDate, branchSpinner.getSelectedItem().toString(), courtSpinner.getSelectedItem().toString(), "12:00");
                    Boolean checkExist14 = dbManager.checkBookingAvailability(selectedDate, branchSpinner.getSelectedItem().toString(), courtSpinner.getSelectedItem().toString(), "14:00");
                    Boolean checkExist16 = dbManager.checkBookingAvailability(selectedDate, branchSpinner.getSelectedItem().toString(), courtSpinner.getSelectedItem().toString(), "16:00");
                    Boolean checkExist18 = dbManager.checkBookingAvailability(selectedDate, branchSpinner.getSelectedItem().toString(), courtSpinner.getSelectedItem().toString(), "18:00");
                    Boolean checkExist20 = dbManager.checkBookingAvailability(selectedDate, branchSpinner.getSelectedItem().toString(), courtSpinner.getSelectedItem().toString(), "20:00");

                    if (checkExist10 == true) {
                        time10.setEnabled(false);
                        time10Button.setBackgroundColor(greyButtonBackground);
                    } else {
                        time10.setEnabled(true);
                        time10Button.setBackgroundColor(purpleButtonBackground);
                    }
                    if (checkExist12 == true) {
                        time12.setEnabled(false);
                        time12Button.setBackgroundColor(greyButtonBackground);
                    } else {
                        time12.setEnabled(true);
                        time12Button.setBackgroundColor(purpleButtonBackground);
                    }
                    if (checkExist14 == true) {
                        time14.setEnabled(false);
                        time14Button.setBackgroundColor(greyButtonBackground);
                    } else {
                        time14.setEnabled(true);
                        time14Button.setBackgroundColor(purpleButtonBackground);
                    }
                    if (checkExist16 == true) {
                        time16.setEnabled(false);
                        time16Button.setBackgroundColor(greyButtonBackground);
                    } else {
                        time16.setEnabled(true);
                        time16Button.setBackgroundColor(purpleButtonBackground);
                    }
                    if (checkExist18 == true) {
                        time18.setEnabled(false);
                        time18Button.setBackgroundColor(greyButtonBackground);
                    } else {
                        time18.setEnabled(true);
                        time18Button.setBackgroundColor(purpleButtonBackground);
                    }
                    if (checkExist20 == true) {
                        time20.setEnabled(false);
                        time20Button.setBackgroundColor(greyButtonBackground);
                    } else {
                        time20.setEnabled(true);
                        time20Button.setBackgroundColor(purpleButtonBackground);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Handle case when nothing is selected (if needed)
            }
        });

        // Set up the TextWatcher for the date input
        EditText dateInput = findViewById(R.id.date);
        dateInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // This method is deprecated and may not be called on some devices
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // This method is called before the text is changed
            }

            @Override
            public void afterTextChanged(Editable s) {
                // This method is called after the text is changed
                selectedDate = s.toString();
                Boolean checkExist10 = dbManager.checkBookingAvailability(selectedDate, branchSpinner.getSelectedItem().toString(), courtSpinner.getSelectedItem().toString(), "10:00");
                Boolean checkExist12 = dbManager.checkBookingAvailability(selectedDate, branchSpinner.getSelectedItem().toString(), courtSpinner.getSelectedItem().toString(), "12:00");
                Boolean checkExist14 = dbManager.checkBookingAvailability(selectedDate, branchSpinner.getSelectedItem().toString(), courtSpinner.getSelectedItem().toString(), "14:00");
                Boolean checkExist16 = dbManager.checkBookingAvailability(selectedDate, branchSpinner.getSelectedItem().toString(), courtSpinner.getSelectedItem().toString(), "16:00");
                Boolean checkExist18 = dbManager.checkBookingAvailability(selectedDate, branchSpinner.getSelectedItem().toString(), courtSpinner.getSelectedItem().toString(), "18:00");
                Boolean checkExist20 = dbManager.checkBookingAvailability(selectedDate, branchSpinner.getSelectedItem().toString(), courtSpinner.getSelectedItem().toString(), "20:00");

                if(checkExist10 == true){
                    time10.setEnabled(false);
                    time10Button.setBackgroundColor(greyButtonBackground);
                }
                else{
                    time10.setEnabled(true);
                    time10Button.setBackgroundColor(purpleButtonBackground);
                }
                if(checkExist12 == true){
                    time12.setEnabled(false);
                    time12Button.setBackgroundColor(greyButtonBackground);
                }
                else{
                    time12.setEnabled(true);
                    time12Button.setBackgroundColor(purpleButtonBackground);
                }
                if(checkExist14 == true){
                    time14.setEnabled(false);
                    time14Button.setBackgroundColor(greyButtonBackground);
                }
                else{
                    time14.setEnabled(true);
                    time14Button.setBackgroundColor(purpleButtonBackground);
                }
                if(checkExist16 == true){
                    time16.setEnabled(false);
                    time16Button.setBackgroundColor(greyButtonBackground);
                }
                else{
                    time16.setEnabled(true);
                    time16Button.setBackgroundColor(purpleButtonBackground);
                }
                if(checkExist18 == true){
                    time18.setEnabled(false);
                    time18Button.setBackgroundColor(greyButtonBackground);
                }
                else{
                    time18.setEnabled(true);
                    time18Button.setBackgroundColor(purpleButtonBackground);
                }
                if(checkExist20 == true){
                    time20.setEnabled(false);
                    time20Button.setBackgroundColor(greyButtonBackground);
                }
                else{
                    time20.setEnabled(true);
                    time20Button.setBackgroundColor(purpleButtonBackground);
                }
            }
        });

        //Get the court dropdown
        Spinner spinner = findViewById(R.id.dropdown_court);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.dropdown_courts, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        //Get the level dropdown
        Spinner spinner2 = findViewById(R.id.dropdown_level);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.dropdown_levels, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);

        //Get the status dropdown
        Spinner spinner3 = findViewById(R.id.dropdown_status);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this, R.array.dropdown_status, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(adapter3);

        //Get the branch dropdown
        Spinner spinner4 = findViewById(R.id.dropdown_branch);
        ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(this, R.array.dropdown_branch, android.R.layout.simple_spinner_item);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner4.setAdapter(adapter4);
    }

    // Function to validate date format
    private boolean isValidDateFormat(String dateStr, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        sdf.setLenient(false);

        try {
            // Parse the date string strictly according to the defined format
            sdf.parse(dateStr);
            return true;
        } catch (ParseException e) {
            return false;
        }
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbManager.close();
    }
}