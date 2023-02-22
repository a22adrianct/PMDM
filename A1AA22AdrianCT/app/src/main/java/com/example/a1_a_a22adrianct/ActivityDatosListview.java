package com.example.a1_a_a22adrianct;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_listview);

        listView = findViewById(R.id.listview);
        tv = findViewById(R.id.tvDescripcion);
        btn = findViewById(R.id.btnGuardar);

        posicion = -1;

        bd = new BaseDatos(this);
        db = bd.getWritableDatabase();

        ArrayList<Persona> personas = getPersonas();
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
                            guardarPersona();
                        } else {
                            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE}, 1);
                        }
                    } else {
                        guardarPersona();
                }
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

    public void guardarPersona() {
        if (posicion >= 0) {
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(ActivityDatosListview.this);
            String ruta = sp.getString("etPreferencia", "DATOS");

            File path = new File(Environment.getExternalStorageDirectory() + "/" + ruta);
            if (!path.exists()) {
                path.mkdirs();
            }

            File destino = new File(path, listView.getItemAtPosition(posicion).toString() + ".txt");

            try {
                destino.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            try {
                OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(destino));
                osw.write(listView.getItemAtPosition(posicion).toString() + ":\n" + tv.getText().toString());
                osw.close();

                Toast.makeText(ActivityDatosListview.this, "Persona guardada en: " + destino.getAbsolutePath(), Toast.LENGTH_SHORT).show();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            Toast.makeText(ActivityDatosListview.this, "No hay ninguna persona seleccionada", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            guardarPersona();
        else
            Toast.makeText(this, "Se necesitan permisos para escribir en SD", Toast.LENGTH_SHORT).show();
    }
}
