package com.example.a1_a_a22adrianct;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class ActivityDatosListview extends AppCompatActivity {

    ListView listView;
    TextView tv;
    Button btn;
    BaseDatos bd;
    SQLiteDatabase db;
    int posicion;
    Utils utils;
    ArrayList<Persona> personas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_listview);

        listView = findViewById(R.id.listview);
        tv = findViewById(R.id.tvDescripcion);
        btn = findViewById(R.id.btnGuardar);

        utils = new Utils();
        posicion = -1;

        bd = new BaseDatos(this);
        db = bd.getWritableDatabase();

        personas = utils.getPersonas(db);
        ArrayList<String> nombres = new ArrayList<>();
        for(int i = 0; i < personas.size(); i++){
            String nombre = personas.get(i).getNombre();
            nombres.add(nombre);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, nombres);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Persona persona = personas.get(position);
                String descripcion = persona.getDescripcion();
                posicion = position;
                tv.setText(descripcion);
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(Build.VERSION.SDK_INT >= 23){
                        if(checkSelfPermission(WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                            if(posicion >= 0){
                                utils.guardarPersona(personas.get(posicion),ActivityDatosListview.this);
                                Toast.makeText(ActivityDatosListview.this, "Persona guardada correctamente " , Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ActivityDatosListview.this, "No hay ninguna persona seleccionada", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE}, 1);
                        }
                    } else {
                        if(posicion >= 0){
                            utils.guardarPersona(personas.get(posicion), ActivityDatosListview.this);
                            Toast.makeText(ActivityDatosListview.this, "Persona guardada correctamente " , Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ActivityDatosListview.this, "No hay ninguna persona seleccionada", Toast.LENGTH_SHORT).show();
                        }                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            if(posicion >= 0){
                utils.guardarPersona(personas.get(posicion), ActivityDatosListview.this);
                Toast.makeText(ActivityDatosListview.this, "Persona guardada correctamente " , Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ActivityDatosListview.this, "No hay ninguna persona seleccionada", Toast.LENGTH_SHORT).show();
            }        else
            Toast.makeText(this, "Se necesitan permisos para escribir en SD", Toast.LENGTH_SHORT).show();
    }
}
