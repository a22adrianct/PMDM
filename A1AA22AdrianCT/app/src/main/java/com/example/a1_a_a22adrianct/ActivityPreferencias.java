package com.example.a1_a_a22adrianct;

import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class ActivityPreferencias extends PreferenceActivity {

    EditTextPreference etp;

    @Override
    public void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferencias);

        etp = (EditTextPreference) findPreference("etPreferencia");
        etp.setDefaultValue("DATOS");

        PreferenceManager.setDefaultValues(this, R.xml.preferencias, false);
    }

    @Override
    public void onResume(){
        super.onResume();
    }
}
