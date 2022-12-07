package com.example.i4a_a22adrianct;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class SecundariaSpinner extends AppCompatActivity {

    File dirFicheiroSD;
    File rutaCompleta;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spinner_view);

        dirFicheiroSD = getExternalFilesDir(null);
        rutaCompleta = new File(dirFicheiroSD.getAbsolutePath(), MainActivity.ficheiro);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        ArrayList<String> coches = new ArrayList<String>();
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(rutaCompleta)));
                while(br.readLine() != null){
                    coches.add(br.readLine());
                }
                br.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, coches);
            adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spinner.setAdapter(adaptador);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                Toast.makeText(getBaseContext(), "Seleccionaches: " + adapterView.getItemAtPosition(pos) + " Posición :"+ pos, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
