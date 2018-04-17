package com.example.james.sixthsense;

import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.Vector;

public class Recording extends AppCompatActivity {
    //This is where my local variables are defined
    private TextView mTextMessage;
    private Button vibrateButton;
    private ToggleButton recordToggle;
    long lastDown = 0;
    long lastDuration = 0;
    boolean recording = false;
    Vibrator vibrator;
    Vector<Long> currPattern = new Vector<Long>();







    //onCreate is also a method provided by android studio templates
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording);



        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation_recording);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_recording:

                        break;
                    case R.id.navigation_patterns:

                        break;
                    case R.id.navigation_notifications:

                        break;
                }
                return false;
            }
        });


        vibrateButton = findViewById(R.id.buttonVibrate);
        recordToggle = findViewById(R.id.toggleRecord);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        vibrateButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    vibrator.vibrate(3000);
                    if(recording == true)
                    {
                        lastDuration = System.currentTimeMillis() - lastDown;
                        currPattern.add(lastDuration);
                        lastDown = System.currentTimeMillis();
                    }
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    vibrator.cancel();
                    if(recording == true)
                    {
                        lastDuration = System.currentTimeMillis() - lastDown;
                        currPattern.add(lastDuration);
                        lastDown = System.currentTimeMillis();
                    }
                }

                return true;
            }
        });


    }

//    BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
//        @Override
//        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//            switch (item.getItemId()) {
//                case R.id.navigation_recording:
//
//                    return true;
//                case R.id.navigation_patterns:
//
//                    return true;
//                case R.id.navigation_notifications:
//
//                    return true;
//            }
//            return false;
//        }
//    };
    //This method comes with the android studio bottom navigation bar activity template


    public void recordToggle(View view)
    {
        boolean checked = ((ToggleButton)view).isChecked();
        if(checked)
        {
            recording = true;
        }
        else
        {
            recording = false;
            currPattern.clear();
        }
    }
}
