package com.example.a1_a_a22adrianct;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityDatos extends AppCompatActivity {

    Spinner spinner;
    ListView listView;
    TextView tv;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos);

        spinner = findViewById(R.id.spinner);
        listView = findViewById(R.id.lista);
        tv = findViewById(R.id.tvDescripcion);
        btn = findViewById(R.id.btnGuardar);
    }
}
