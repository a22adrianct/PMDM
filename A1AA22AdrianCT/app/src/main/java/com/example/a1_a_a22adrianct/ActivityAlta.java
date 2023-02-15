package com.example.a1_a_a22adrianct;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityAlta extends AppCompatActivity {

    EditText etNombre, etDesc;
    Button btnGuardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alta);

        etNombre = findViewById(R.id.et_nombre);
        etDesc = findViewById(R.id.et_descripcion);
        btnGuardar = findViewById(R.id.btnGuardar);

        BaseDatos bd = new BaseDatos(ActivityAlta.this);
        SQLiteDatabase db = bd.getWritableDatabase();

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    ContentValues values = new ContentValues();
                    values.put("nombre", etNombre.getText().toString());
                    values.put("descripcion", etDesc.getText().toString());
                    db.insertOrThrow("persona", null, values);
                } catch (android.database.sqlite.SQLiteConstraintException e){
                    Toast.makeText(ActivityAlta.this, etNombre.getText().toString() + " ya existe en la base de datos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
