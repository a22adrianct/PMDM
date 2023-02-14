package com.example.a1_a_a22adrianct;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btnAlta, btnCargar;
    BaseDatos bd;
    SQLiteDatabase sqLite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bd = new BaseDatos(this);
        sqLite = bd.getWritableDatabase();
        databaseDialog();

        btnAlta = findViewById(R.id.btnAlta);
        btnCargar = findViewById(R.id.btnCargar);

        btnAlta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ActivityAlta.class);
                startActivity(intent);
            }
        });

        btnCargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ActivityDatos.class);
                startActivity(intent);
            }
        });
    }

    private void databaseDialog(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        dialog.setTitle("¿Cómo quieres crear la base de datos?");
        dialog.setPositiveButton("Código", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        dialog.setNegativeButton("Assets", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        dialog.setCancelable(false);

        dialog.show();
    }
}