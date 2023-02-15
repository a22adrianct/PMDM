package com.example.a1_a_a22adrianct;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

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

        ArrayList<Persona> personas = getPersonas();
        ArrayList<String> nombres = new ArrayList<>();
        for(int i = 0; i < personas.size(); i++){
            String nombre = personas.get(i).getNombre();
            nombres.add(nombre);
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, nombres);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(arrayAdapter);
    }

    public ArrayList<Persona> getPersonas(){
        ArrayList<Persona> list = new ArrayList<>();
        Persona persona;

        BaseDatos bd = new BaseDatos(this);
        SQLiteDatabase db = bd.getWritableDatabase();
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
