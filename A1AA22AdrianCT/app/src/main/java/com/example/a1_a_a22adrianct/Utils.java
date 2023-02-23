package com.example.a1_a_a22adrianct;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.widget.Toast;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class Utils {

    public void guardarPersona(Persona persona, Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        String ruta = sp.getString("etPreferencia", "DATOS");

        File path = new File(Environment.getExternalStorageDirectory() + "/" + ruta);
        if (!path.exists()) {
            path.mkdirs();
        }

        File destino = new File(path, persona.getNombre() + ".txt");

        try {
            destino.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(destino));
            osw.write(persona.getNombre() + ":\n" + persona.getDescripcion());
            osw.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Persona> getPersonas(SQLiteDatabase db){
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
