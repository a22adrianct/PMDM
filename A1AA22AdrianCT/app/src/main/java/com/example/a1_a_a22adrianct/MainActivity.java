package com.example.a1_a_a22adrianct;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {

    Button btnAlta, btnCargar;
    BaseDatos bd;
    SQLiteDatabase sqLite;
    String dbPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAlta = findViewById(R.id.btnAlta);
        btnCargar = findViewById(R.id.btnCargar);

        dbPath = "/data/data/" + getPackageName() + "/databases/";
        File dbFile = new File(dbPath, "DATOS");

        if (!dbFile.exists()) {
            databaseDialog();
        }

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
                createDialog();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_preferencias:
                Intent intent = new Intent(MainActivity.this, ActivityPreferencias.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void databaseDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        dialog.setTitle("¿Cómo quieres crear la base de datos?");
        dialog.setPositiveButton("Código", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                bd = new BaseDatos(MainActivity.this);
                sqLite = bd.getWritableDatabase();
            }
        });

        dialog.setNegativeButton("Assets", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                File dbPathFile = new File(dbPath);
                dbPathFile.mkdirs();

                try {
                    InputStream is = getAssets().open("DATOS");
                    OutputStream os = new FileOutputStream(dbPath + "DATOS");

                    int num;
                    byte[] buffer = new byte[2048];

                    while ((num = is.read(buffer)) > 0) {
                        os.write(buffer, 0, num);
                    }

                    is.close();
                    os.flush();
                    os.close();
                } catch (IOException e) {
                    Toast.makeText(MainActivity.this, "Se ha producido un error al copiar la base de datos", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.setCancelable(false);
        dialog.show();
    }

    private void createDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        dialog.setTitle("¿Cómo quieres ver los datos?");
        dialog.setPositiveButton("Spinner", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(MainActivity.this, ActivityDatosSpinner.class);
                startActivity(intent);
            }
        });

        dialog.setNegativeButton("ListView", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(MainActivity.this, ActivityDatosListview.class);
                startActivity(intent);
            }
        });

        dialog.setCancelable(false);
        dialog.show();
    }
}