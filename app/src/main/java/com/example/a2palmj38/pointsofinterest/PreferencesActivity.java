package com.example.a2palmj38.pointsofinterest;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by 2palmj38 on 11/05/2017.
 */
public class PreferencesActivity extends PreferenceActivity
{
    public void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}
