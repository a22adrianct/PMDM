package com.example.a1_a_a22adrianct;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ActivityDatos extends AppCompatActivity {

    Spinner spinner;
    ListView listView;
    TextView tv;
    Button btn;
    BaseDatos bd;
    SQLiteDatabase db;
    File ruta, destino;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos);

        spinner = findViewById(R.id.spinner);
        listView = findViewById(R.id.lista);
        tv = findViewById(R.id.tvDescripcion);
        btn = findViewById(R.id.btnGuardar);

        bd = new BaseDatos(this);
        db = bd.getWritableDatabase();

        ruta = getExternalFilesDir(null);
        if(!ruta.exists()){
            ruta.mkdirs();
        }

        ArrayList<Persona> personas = getPersonas();
        ArrayList<String> nombres = new ArrayList<>();
        for(int i = 0; i < personas.size(); i++){
            String nombre = personas.get(i).getNombre();
            nombres.add(nombre);
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, nombres);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(arrayAdapter);
        listView.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Persona persona = personas.get(spinner.getSelectedItemPosition());
                String descripcion = persona.getDescripcion();

                tv.setText(descripcion);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Persona persona = personas.get(position);
                spinner.setSelection(position);
                String descripcion = persona.getDescripcion();

                tv.setText(descripcion);
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getSharedPreferences("PREFERENCIAS", MODE_PRIVATE);
                destino = new File(ruta, sp.getString("RUTA", ""));
                Toast.makeText(ActivityDatos.this, destino.getAbsolutePath().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public ArrayList<Persona> getPersonas(){
        ArrayList<Persona> list = new ArrayList<>();
        Persona persona;

        Cursor cursor = db.rawQuery("SELECT * FROM persona", null);

        if(cursor.moveToFirst()){
            do{
                persona = new Persona();
                persona.setNombre(cursor.getString(0));
                persona.setDescripcion(cursor.getString(1));
                list.add(persona);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return list;
    }
}
