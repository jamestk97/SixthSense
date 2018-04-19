// Name: James Kennedy
// Course: CSC 415
// Semester: Fall 2017
// Instructor: Dr. Pulimood
// Project name: SixthSense
// Description: Android app that can apply different vibration patterns to notifications.
// Filename: PatternsFragment.java
// Description: This is where the patterns tab functionality is handled. The internal files are read in here so that the list can be populated for viewing and pattern playing
// Last modified on: 4/18/18

package com.example.james.sixthsense;


import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.Vector;

import static android.content.Context.VIBRATOR_SERVICE;


/**
 * A simple {@link Fragment} subclass.
 */
public class PatternsFragment extends Fragment {


    public PatternsFragment() {
        // Required empty public constructor
    }

    //This defines the two file names used to save patterns internally
    private static final String NAMES_FILE = "names3.txt";
    private static final String PATTERNS_FILE = "patterns3.txt";
    //This defines the output streams for the two files
    FileInputStream fNames = null;
    FileInputStream fPatterns = null;
    //Define vibrator object to perform vibrations
    Vibrator vibrator;
    //define global variables that are used to store data read from the internal files
    Vector<String> names = new Vector<>();
    Vector<long[]> patterns = new Vector<>();
    String[] namesArr;
    //Define ListView object that will be populated with names list
    ListView listView;



    //onCreateView is an auto-generated method that handles the creation of the fragment layout by using & returning the view variable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_patterns, container, false);
        //Call the load function
        load(view);
        //Vibrator needs system information and service to work
        vibrator = (Vibrator) getActivity().getSystemService(VIBRATOR_SERVICE);
        //This is where the ArrayAdapter is defined that handles populating the ListView with pattern names
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, namesArr);
        listView = (ListView) view.findViewById(R.id.patternsList);
        listView.setAdapter(adapter);
        //assign onClickListener to the list so that they will play their vibration when pressed
        listView.setOnItemClickListener(new OnItemClickListener()
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
                vibrator.vibrate(patterns.get(position), -1);
            }
        });




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
            for(int i = 0; i < names.size(); i++)
            {
                namesArr[i] = names.get(i);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            fPatterns = getActivity().openFileInput(PATTERNS_FILE);
            InputStreamReader isr = new InputStreamReader(fPatterns);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String pattern;

            while((pattern = br.readLine()) != null) {
                Vector<Long> tempPattern = new Vector<>();
                StringTokenizer token = new StringTokenizer(pattern);
                while(token.hasMoreTokens()){
                    tempPattern.add(Long.parseLong(token.nextToken()));
                }
                long[] pattArr = new long[tempPattern.size()];
                for(int i = 0; i < tempPattern.size(); i++)
                {
                    pattArr[i] = tempPattern.get(i);

                }
                patterns.add(pattArr);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}


