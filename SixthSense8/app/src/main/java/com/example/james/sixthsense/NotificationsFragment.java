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


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */


public class NotificationsFragment extends Fragment {


    public NotificationsFragment() {
        // Required empty public constructor
    }

    //Auto-generated onCreateView that generates the associated layout
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notifications, container, false);
    }

}
