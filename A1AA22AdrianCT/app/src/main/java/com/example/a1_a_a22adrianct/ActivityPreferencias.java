package com.example.a1_a_a22adrianct;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.PreferenceActivity;

public class ActivityPreferencias extends PreferenceActivity {

    EditTextPreference etp;

    @Override
    public void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferencias);

        etp = (EditTextPreference) findPreference("etPath");
    }

    @Override
    public void onResume(){
        super.onResume();

        SharedPreferences sp = getSharedPreferences("PREFERENCIAS", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        String text = etp.getText();
        editor.putString("RUTA", text);
        editor.apply();
    }
}
