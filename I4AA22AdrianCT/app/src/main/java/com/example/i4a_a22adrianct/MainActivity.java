package com.example.i4a_a22adrianct;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    static String ficheiro = "ficheiro_SD.txt";
    boolean sdDisponible = false;
    boolean sdAccesoEscritura = false;
    File dirFicheiroSD;
    File rutaCompleta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        comprobarEstadoSD();
        establecerDirectorioFicheiro();

        Button spinnerBtn = (Button) findViewById(R.id.btnSpinner);
        Button listViewBtn = (Button) findViewById(R.id.btnListView);

        Intent intentSpinner = new Intent(this, SecundariaSpinner.class);
        Intent intentListView = new Intent(this, SecundariaListView.class);

        spinnerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(intentSpinner);
            }
        });

        listViewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intentListView);
            }
        });
    }

    public void comprobarEstadoSD(){
        String estado = Environment.getExternalStorageState();
        Log.e("SD", estado);

        if(estado.equals(Environment.MEDIA_MOUNTED)){
            sdDisponible = true;
            sdAccesoEscritura = true;
        } else if(estado.equals(Environment.MEDIA_MOUNTED_READ_ONLY)){
            sdDisponible = true;
            sdAccesoEscritura = false;
        }
    }

    public void establecerDirectorioFicheiro(){
        if (sdDisponible){
            dirFicheiroSD = getExternalFilesDir(null);
            rutaCompleta = new File(dirFicheiroSD.getAbsolutePath(), ficheiro);
        }
    }

    public void onEscribirClick(View v){
        EditText textoEscribir = (EditText) findViewById(R.id.textoEscribir);
        RadioButton opcion = (RadioButton) findViewById(R.id.sobreescribir);
        Date fechaHora = new Date();
        String contido = textoEscribir.getText() + " - " + fechaHora + "\n";

        boolean sobrescribir;

        if (opcion.isChecked()){
            sobrescribir = false;
        } else {
            sobrescribir = true;
        }

        if (sdAccesoEscritura){
            try {
                Log.i("RUTA COMPLETA", rutaCompleta.toString());
                Log.i("CONTIDO", contido);
                OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(rutaCompleta, sobrescribir));
                osw.write(contido);
                osw.close();
                textoEscribir.setText("");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}