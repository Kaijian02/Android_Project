package com.example.badminton_court;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cursoradapter.widget.SimpleCursorAdapter;

public class HistoryListActivity extends AppCompatActivity {

    private DBManager dbManager;

    private ListView listView;

    private SimpleCursorAdapter adapter;

    final String[] from = new String[] { DatabaseHelper._BOOKING_ID,
            DatabaseHelper.BOOKING_DATE, DatabaseHelper.BOOKING_TIME, DatabaseHelper.BOOKING_BRANCH };

    final int[] to = new int[] { R.id.id, R.id.date, R.id.time, R.id.branch };
//    public static final String mypreference = "user_data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_history_list);
        dbManager = new DBManager(this);
        dbManager.open();
        SharedPreferences sharedPreferences = getSharedPreferences("user_data", Context.MODE_PRIVATE);
        String userEmail = sharedPreferences.getString("user_email", "");

        // Query the database to retrieve user details based on the email
        User user = dbManager.getUserByEmail(userEmail);
        String name = user.getName();
        int id = user.getId();
        String idString = String.valueOf(id);

        Cursor cursor = dbManager.fetch(idString, name);

        listView = (ListView) findViewById(R.id.list_view);
        listView.setEmptyView(findViewById(R.id.empty));

        adapter = new SimpleCursorAdapter(this, R.layout.activity_view_booking, cursor, from, to, 0);
        adapter.notifyDataSetChanged();

        listView.setAdapter(adapter);

        // OnCLickListener For List Items
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long viewId) {
                TextView idTextView = (TextView) view.findViewById(R.id.id);
                TextView dateTextView = (TextView) view.findViewById(R.id.date);
                TextView timeTextView = (TextView) view.findViewById(R.id.time);
                TextView branchTextView = (TextView) view.findViewById(R.id.branch);

                String id = idTextView.getText().toString();
                String date = dateTextView.getText().toString();
                String time = timeTextView.getText().toString();
                String branch = branchTextView.getText().toString();

                Intent modify_intent = new Intent(getApplicationContext(), CancelBookingActivity.class);
                modify_intent.putExtra("date", date);
                modify_intent.putExtra("time", time);
                modify_intent.putExtra("branch", branch);
                modify_intent.putExtra("id", id);

                startActivity(modify_intent);
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
