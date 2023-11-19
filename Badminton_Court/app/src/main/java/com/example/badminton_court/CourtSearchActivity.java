package com.example.badminton_court;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class CourtSearchActivity extends AppCompatActivity {
    Button beginner, intermediate, expert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.courtsearch);
        loadFragment(new BeginnerCourtActivity());

        beginner = (Button) findViewById(R.id.beginner);
        intermediate = (Button) findViewById(R.id.intermediate);
        expert = (Button) findViewById(R.id.expert);

        beginner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            // load First Fragment
                loadFragment(new BeginnerCourtActivity());
            }
        });
        intermediate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // load Second Fragment
                loadFragment(new IntermediateCourtActivity());
            }
        });
        expert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // load Third Fragment
                loadFragment(new ExpertCourtActivity());
            }
        });
    }

    private void loadFragment(Fragment fragment) {
// create a FragmentManager
        FragmentManager fm = getSupportFragmentManager();
// create a FragmentTransaction to begin the transaction and replace the Fragment
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
// replace the FrameLayout with new Fragment
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        //fragmentTransaction.commit(); // save the changes
        fragmentTransaction.commit();
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
