package com.example.a1_a_a22adrianct;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class BaseDatos extends SQLiteOpenHelper {

    public BaseDatos(@Nullable Context context) {
        super(context, "basedatos", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE persona (" +
                "nombre VARCHAR(20) PRIMARY KEY, " +
                "descripcion VARCHAR(200));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS persoa");
        onCreate(db);
    }
}
