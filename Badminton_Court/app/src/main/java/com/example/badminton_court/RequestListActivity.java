package com.example.badminton_court;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cursoradapter.widget.SimpleCursorAdapter;

public class RequestListActivity extends AppCompatActivity {
    private DBManager dbManager;

    private ListView listView;

    private SimpleCursorAdapter adapter;

    final String[] from = new String[] { DatabaseHelper._JOIN_REQUEST_ID, DatabaseHelper.JOIN_REQUEST_BOOKING_ID,
            DatabaseHelper.JOIN_REQUEST_USER_NAME, DatabaseHelper.JOIN_REQUEST_EMAIL, DatabaseHelper.JOIN_REQUEST_BRANCH,
            DatabaseHelper.JOIN_REQUEST_COURT, DatabaseHelper.JOIN_REQUEST_DATE, DatabaseHelper.JOIN_REQUEST_TIME, DatabaseHelper.JOIN_REQUEST_HOST_NAME };

    final int[] to = new int[] { R.id.id, R.id.bookingId, R.id.username, R.id.email ,R.id.branch, R.id.court, R.id.date, R.id.time, R.id.host};

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_emp_list);
        dbManager = new DBManager(this);
        dbManager.open();

        SharedPreferences sharedPreferences = getSharedPreferences("user_data", Context.MODE_PRIVATE);
        String userEmail = sharedPreferences.getString("user_email", "");

        // Query the database to retrieve user details based on the email
        User user = dbManager.getUserByEmail(userEmail);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");

        Cursor cursor = dbManager.fetchJoinRequest(id);

        listView = (ListView) findViewById(R.id.list_view);
        listView.setEmptyView(findViewById(R.id.empty));

        adapter = new SimpleCursorAdapter(this, R.layout.activity_view_record, cursor, from, to, 0);
        adapter.notifyDataSetChanged();

        listView.setAdapter(adapter);

        // OnCLickListener For List Items
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long viewId) {
                TextView idTextView = (TextView) view.findViewById(R.id.id);
                TextView bookingIdTextView = (TextView) view.findViewById(R.id.bookingId);
                TextView usernameTextView = (TextView) view.findViewById(R.id.username);
                TextView emailTextView = (TextView) view.findViewById(R.id.email);
                TextView branchTextView = (TextView) view.findViewById(R.id.branch);
                TextView courtTextView = (TextView) view.findViewById(R.id.court);
                TextView dateTextView = (TextView) view.findViewById(R.id.date);
                TextView timeTextView = (TextView) view.findViewById(R.id.time);
                TextView hostTextView = (TextView) view.findViewById(R.id.host);

                String id = idTextView.getText().toString();
                String bookingId = bookingIdTextView.getText().toString();
                String username = usernameTextView.getText().toString();
                String email = emailTextView.getText().toString();
                String branch = branchTextView.getText().toString();
                String court = courtTextView.getText().toString();
                String date = dateTextView.getText().toString();
                String time = timeTextView.getText().toString();
                String host = hostTextView.getText().toString();


                Intent modify_intent = new Intent(getApplicationContext(), RequestActionActivity.class);
                modify_intent.putExtra("id", id);
                modify_intent.putExtra("bookingId", bookingId);
                modify_intent.putExtra("username", username);
                modify_intent.putExtra("email", email);
                modify_intent.putExtra("branch", branch);
                modify_intent.putExtra("court", court);
                modify_intent.putExtra("date", date);
                modify_intent.putExtra("time", time);
                modify_intent.putExtra("host", host);


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
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//      getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }

}
