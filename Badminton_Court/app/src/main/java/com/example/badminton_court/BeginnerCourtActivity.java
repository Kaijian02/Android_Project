package com.example.badminton_court;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.cursoradapter.widget.SimpleCursorAdapter;
import androidx.fragment.app.Fragment;


public class BeginnerCourtActivity extends Fragment {
    View view;
    private DBManager dbManager;

    private ListView listView;
    private SimpleCursorAdapter adapter;
    AlertDialog.Builder builder;
    final String[] from = new String[] { DatabaseHelper.BOOKING_COURT,
            DatabaseHelper._BOOKING_ID, DatabaseHelper.BOOKING_DATE,
            DatabaseHelper.BOOKING_TIME, DatabaseHelper.BOOKING_USER_NAME,
            DatabaseHelper.BOOKING_CURRENT_PLAYERS, DatabaseHelper.BOOKING_BRANCH};

    final int[] to = new int[] { R.id.court, R.id.courtId, R.id.courtDate, R.id.courtTime, R.id.courtHost, R.id.courtPlayersAmount, R.id.branch };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.beginnercourtfragment, container, false);

        dbManager = new DBManager(getContext());
        dbManager.open();
        Cursor cursor = dbManager.fetchBeginnerCourtList();

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("user_data", Context.MODE_PRIVATE);
        String userEmail = sharedPreferences.getString("user_email", "");
        User user = dbManager.getUserByEmail(userEmail);

        listView = (ListView) view.findViewById(R.id.list_view);
        listView.setEmptyView(view.findViewById(R.id.empty));

        adapter = new SimpleCursorAdapter(getContext(), R.layout.beginnercourtsearchbutton, cursor, from, to, 0);
        adapter.notifyDataSetChanged();

        listView.setAdapter(adapter);

        builder = new AlertDialog.Builder(getContext());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long viewId) {
                TextView courtIdTextView = (TextView) view.findViewById(R.id.courtId);
                TextView courtHostTextView = (TextView) view.findViewById(R.id.courtHost);
                TextView courtDateTextView = (TextView) view.findViewById(R.id.courtDate);
                TextView courtTimeTextView = (TextView) view.findViewById(R.id.courtTime);
                TextView courtBranchTextView = (TextView) view.findViewById(R.id.branch);
                TextView courtTextView = (TextView) view.findViewById(R.id.court);

                String courtId = courtIdTextView.getText().toString();
                String courtHost = courtHostTextView.getText().toString();
                String courtDate = courtDateTextView.getText().toString();
                String courtTime = courtTimeTextView.getText().toString();
                String branch = courtBranchTextView.getText().toString();
                String court = courtTextView.getText().toString();
                String userName = String.valueOf(user.getName());

                builder.setTitle("Are you sure you want to join the selected court?")
                        .setMessage("Court: " + court + "\nDate: " + courtDate + "\nTime: " + courtTime)
                        .setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                boolean checkSameUser;

                                checkSameUser = dbManager.checkSameUser(String.valueOf(user.getId()), courtId);

                                if(checkSameUser == true){
                                    Toast.makeText(getActivity(), "You cannot join your court", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    long result = dbManager.insertJoinCourt(courtId, courtHost, courtDate, courtTime, branch, court, userName, userEmail);
                                    if (result != -1) {
                                        Toast.makeText(getActivity(), "Request sent!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getActivity(), "Failed to send request", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        })
                        .show();
            }
        });
        return view;
    }

}
