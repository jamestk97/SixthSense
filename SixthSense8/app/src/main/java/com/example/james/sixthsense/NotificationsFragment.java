// Name: James Kennedy
// Course: CSC 415
// Semester: Fall 2017
// Instructor: Dr. Pulimood
// Project name: SixthSense
// Description: Android app that can apply different vibration patterns to notifications.
// Filename: NotificationsFragment.java
// Description: This fragment handles all notifications functionality. Internal files are read here to access saved patterns and a background process will use them to apply them to incoming notifications
// Last modified on: 4/18/18

package com.example.james.sixthsense;


import android.content.Intent;
import android.content.pm.PackageManager;

import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

import static android.content.Context.VIBRATOR_SERVICE;


/**
 * A simple {@link Fragment} subclass.
 */


public class NotificationsFragment extends Fragment {



    public NotificationsFragment() {
        // Required empty public constructor
    }

    //This defines the two file names used to save patterns internally
    private static final String NAMES_FILE = "names3.txt";
    private static final String PATTERNS_FILE = "patterns3.txt";
    private static final String NOTES_FILE = "notes3.txt";
    private static final String N_PATTERNS_FILE = "nPatterns3.txt";
    //This defines the output streams for the two files
    FileInputStream fNames = null;
    FileInputStream fPatterns = null;
    FileInputStream fNotes = null;
    FileInputStream fNPatterns = null;
    //Define vibrator object to perform vibrations
    Vibrator vibrator;
    //define global variables that are used to store data read from the internal files
    int patternPos;
    Vector<String> names = new Vector<>();
    Vector<String> apps = new Vector<>();
    Vector<long[]> patterns = new Vector<>();
    Vector<Integer> nPatterns = new Vector<>();
    String[] namesArr;
    String[] appsArr;
    String currName;
    ListView listView;


    //Auto-generated onCreateView that generates the associated layout
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_notifications, container, false);

        load(view);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.addNotification);
        fab.setOnClickListener(new View.OnClickListener() {
            //----------------------------------------------------------------------------------------
//  Function: onClick ()
//
//    Parameters:
//    parameters: none
//    Pre-condition: addNotification button was pressed
//    Post-condition: Pulls up the addNotification fragment in the frame view
//-----------------------------------------------------------------------------------------
            @Override
            public void onClick(View view) {
                getActivity().setTitle("SixthSense");
                AddNotification fragmentN = new AddNotification();
                FragmentTransaction fragmentTransactionR = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransactionR.replace(R.id.frame, fragmentN, "AddNotification");
                fragmentTransactionR.commit();
            }
        });
        //Vibrator needs system information and service to work
        vibrator = (Vibrator) getActivity().getSystemService(VIBRATOR_SERVICE);
        if(appsArr != null)//This if statement handles the exception of when the file is empty thus producing an empty appsArr which would cause the ArraryAdapter and the app to crash
        {
            //This is where the ArrayAdapter is defined that handles populating the ListView with pattern names.
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, appsArr);
            listView = (ListView) view.findViewById(R.id.notificationsList);
            listView.setAdapter(adapter);
            //assign onClickListener to the list so that they will play their vibration when pressed
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                //-----------------------------------------------------------------------------------------
//
//  Function: onItemClick ()
//
//    Parameters:
//    int position: index of element pressed. Is the only parameter that matters, the other three are just placeholders for the method
//
//    Pre-condition: Element in ListView is pressed
//    Post-condition: Plays the appropriate vibration pattern
//-----------------------------------------------------------------------------------------
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {
                    vibrator.vibrate(patterns.get(nPatterns.get(position)), -1);
                }
            });
        }



        return view;
    }



    //-----------------------------------------------------------------------------------------
//
//  Function: load()
//
//    Parameters:
//    View v: current view of the fragment
//    Pre-condition: There is no precondition. This method is always executed when this fragment view is created
//    Post-condition: names and patterns structures will be populated with data from internal files
//-----------------------------------------------------------------------------------------
    public void load(View v) {

        //Load data from Names file
        try {
            fNames = getActivity().openFileInput(NAMES_FILE);
            InputStreamReader isr = new InputStreamReader(fNames);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String name;


            while ((name = br.readLine()) != null) {
                names.add(name);
            }
            namesArr = new String[names.size()];
            for (int i = 0; i < names.size(); i++) {
                namesArr[i] = names.get(i);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Load data from patterns file
        try {
            fPatterns = getActivity().openFileInput(PATTERNS_FILE);
            InputStreamReader isr = new InputStreamReader(fPatterns);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String pattern;

            while ((pattern = br.readLine()) != null) {
                Vector<Long> tempPattern = new Vector<>();
                StringTokenizer token = new StringTokenizer(pattern);
                while (token.hasMoreTokens()) {
                    tempPattern.add(Long.parseLong(token.nextToken()));
                }
                long[] pattArr = new long[tempPattern.size()];
                for (int i = 0; i < tempPattern.size(); i++) {
                    pattArr[i] = tempPattern.get(i);

                }
                patterns.add(pattArr);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Load data from notes file
        try {
            fNotes = getActivity().openFileInput(NOTES_FILE);
            InputStreamReader isr = new InputStreamReader(fNotes);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String app;


            while ((app = br.readLine()) != null) {
                apps.add(app);
            }
            appsArr = new String[apps.size()];
            for (int i = 0; i < apps.size(); i++) {
                appsArr[i] = apps.get(i);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Load data for notification patterns from internal file
        try {
            fNPatterns = getActivity().openFileInput(N_PATTERNS_FILE);
            InputStreamReader isr = new InputStreamReader(fNPatterns);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String pattern;

            while ((pattern = br.readLine()) != null) {
                Integer tempPattern = Integer.valueOf(pattern);
                nPatterns.add(tempPattern);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
