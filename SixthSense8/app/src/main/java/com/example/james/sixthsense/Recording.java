
// Name: James Kennedy
// Course: CSC 415
// Semester: Fall 2017
// Instructor: Dr. Pulimood
// Project name: SixthSense
// Description: Android app that can apply different vibration patterns to notifications.
// Filename: Recording.java
// Description: This file is the main activity file and handles the bottom navigation bar and fragment management
// Last modified on: 4/18/18



package com.example.james.sixthsense;

import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.Vector;


public class Recording extends AppCompatActivity {



//-----------------------------------------------------------------------------------------
//
//  Function: onNavigationItemSelected()
//
//    Parameters:
//    onNavigationItemSelectedListener:listens for input in bottom nav bar
//
//    Pre-condition: One of the bottom nav buttons is pressed
//    Post-condition: Associated fragment is displayed on screen
//-----------------------------------------------------------------------------------------
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    setTitle("SixthSense");
                    RecordingFragment fragmentR = new RecordingFragment();
                    FragmentTransaction fragmentTransactionR = getSupportFragmentManager().beginTransaction();
                    fragmentTransactionR.replace(R.id.frame, fragmentR, "RecordingFragment");
                    fragmentTransactionR.commit();
                    return true;
                case R.id.navigation_dashboard:
                    setTitle("SixthSense");
                    PatternsFragment fragmentP = new PatternsFragment();
                    FragmentTransaction fragmentTransactionP = getSupportFragmentManager().beginTransaction();
                    fragmentTransactionP.replace(R.id.frame, fragmentP, "PatternsFragment");
                    fragmentTransactionP.commit();
                    return true;
                case R.id.navigation_notifications:
                    setTitle("SixthSense");
                    NotificationsFragment fragmentN = new NotificationsFragment();
                    FragmentTransaction fragmentTransactionN = getSupportFragmentManager().beginTransaction();
                    fragmentTransactionN.replace(R.id.frame, fragmentN, "NotificationsFragment");
                    fragmentTransactionN.commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording);
        startService(new Intent(this, NotificationListener.class));


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        //uses code from bottom nav bar to display recording fragment as main page when activity is created
        setTitle("SixthSense");
        RecordingFragment fragmentR = new RecordingFragment();
        FragmentTransaction fragmentTransactionR = getSupportFragmentManager().beginTransaction();
        fragmentTransactionR.replace(R.id.frame, fragmentR, "RecordingFragment");
        fragmentTransactionR.commit();




    }


    public void onSaveClick(View view) {
    }
}
