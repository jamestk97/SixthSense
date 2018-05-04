// Name: James Kennedy
// Course: CSC 415
// Semester: Fall 2017
// Instructor: Dr. Pulimood
// Project name: SixthSense
// Description: Android app that can apply different vibration patterns to notifications.
// Filename: RecordingFragment.java
// Description: This fragment handles all of the functionality seen on the recording tab. Patterns and their names are input, generated and written onto internal files here
// Last modified on: 4/18/18


package com.example.james.sixthsense;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Vector;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.VIBRATOR_SERVICE;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecordingFragment extends Fragment{


    public RecordingFragment() {
        // Required empty public constructor
    }


    //Define file names
    private static final String NAMES_FILE = "names3.txt";
    private static final String PATTERNS_FILE = "patterns3.txt";
    //Define button objects
    private Button vibrateButton;
    private ToggleButton recordToggle;
    //Variables used for pattern timing recording
    long lastDown = 0;
    long lastDuration = 0;
    //Boolean flag for recording state
    boolean recording = false;
    //Vibrator object to perform vibrations
    Vibrator vibrator;
    //Some temp files for pattern saving
    String currName;
    Vector<Long> currPattern = new Vector<>();
    String currPatternStr;
    //Output streams for internal file writing
    FileOutputStream fNames = null;
    FileOutputStream fPatterns = null;


    


    //Auto-generated onCreateView to display associated fragment by using and returning View view
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_recording, container, false);

        //Give the buttons and vibrator appropriate associations to their respective buttons or services
        vibrateButton = (Button) view.findViewById(R.id.buttonVibrate);
        recordToggle = (ToggleButton) view.findViewById(R.id.toggleRecord);
        vibrator = (Vibrator) getActivity().getSystemService(VIBRATOR_SERVICE);


        //Define the vibrateButton touch listener
        vibrateButton.setOnTouchListener(new View.OnTouchListener() {
            //-----------------------------------------------------------------------------------------
//
//  Function: onTouch ()
//
//    Parameters:
//    View v: current view of fragment
//    MotionEvent event: specifies whether button is pressed or released
//
//    Pre-condition: Button is pressed, held or released
//    Post-condition: Vibration is played when button is pressed and continues to play until button is released. If recording is true, then the inputs will be added to currPattern
//-----------------------------------------------------------------------------------------
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    if(recording == true)
                    {
                        lastDuration = System.currentTimeMillis() - lastDown;
                        currPattern.add(lastDuration);
                        lastDown = System.currentTimeMillis();



                    }
                    vibrator.vibrate(3000);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {

                    if(recording == true)
                    {
                        lastDuration = System.currentTimeMillis() - lastDown;
                        currPattern.add(lastDuration);
                        lastDown = System.currentTimeMillis();
                    }
                    vibrator.cancel();
                }

                return true;
            }

        });

        //Defines alert that pops up when user ends recording session
        final AlertDialog.Builder alert = new AlertDialog.Builder(this.getContext());

        alert.setTitle("Save this pattern");
        alert.setMessage("Please enter a name for this pattern");

        // Set an EditText popup view to get user input
        final EditText input = new EditText(this.getContext());
        alert.setView(input);
//-----------------------------------------------------------------------------------------
//
//  Function: onClick ()
//
//    Parameters:
//    DialogInterface dialog: current dialog
//    int whichButton: defines which button was pressed
//
//    Pre-condition: One of the buttons is pressed. If save is pressed, the text must be input
//    Post-condition: Pattern is saved or operation is cancelled. If saved, the toast message is displayed confirming it
//-----------------------------------------------------------------------------------------
        alert.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                currName = input.getText().toString();
                save(view);
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                //empty because cancel does nothing
            }
        });

//Define ToggleButton touch listener
        recordToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            //-----------------------------------------------------------------------------------------
//
//  Function: onCheckedChange ()
//
//    Parameters:
//    CompoundButton buttonView: current view of button
//    boolean isChecked: specifies whether button is enabled or disabled
//
//    Pre-condition: Button is pressed
//    Post-condition: Recording is either disable or enabled depending on new state of button. If recording is disabled, generate the save alert
//-----------------------------------------------------------------------------------------
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if(isChecked){
                    recording = true;
                }else{
                    recording = false;

                    long[] pattArr = new long[currPattern.size()];
                    pattArr[0] = 0;
                    currPatternStr = "0 ";
                    for(int i = 1; i < currPattern.size(); i++)
                    {
                        pattArr[i] = currPattern.get(i);
                        currPatternStr = currPatternStr + pattArr[i] + " ";
                    }

                    vibrator.vibrate(pattArr, -1);
                    alert.show();



                    currPattern.clear();

                }
            }
        });

        return view;

    }
    //-----------------------------------------------------------------------------------------
//
//  Function: save ()
//
//    Parameters:
//    View v: current view of fragment
//
//    Pre-condition: Save button was pressed on save dialog so that this method can be called
//    Post-condition: The pattern and name are written into their respective internal files
//-----------------------------------------------------------------------------------------
    public void save(View v) {
        try {
            fNames = getActivity().openFileOutput(NAMES_FILE, Context.MODE_APPEND);
            fNames.write((currName + "\n").getBytes());
            Toast.makeText(this.getContext(), currName + " was saved", Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(fNames != null){
                try {
                    fNames.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        try {

            fPatterns = getActivity().openFileOutput(PATTERNS_FILE, Context.MODE_APPEND);

            fPatterns.write((currPatternStr + "\n").getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(fPatterns != null){
                try {
                    fPatterns.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }



}
