package com.example.a3a_a22adrianct;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btnStartThread, btnStopThread, btnStartAsync, btnStopAsync;
    TextView tvThread, tvAsync;
    Hilo hilo;
    HiloAsyncTask hiloAS;
    int random1, random2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStartThread = findViewById(R.id.btnStartThread);
        btnStartAsync = findViewById(R.id.btnStartAsync);
        btnStopThread = findViewById(R.id.btnStopThread);
        btnStopAsync = findViewById(R.id.btnStopAsync);
        tvThread = findViewById(R.id.tvThread);
        tvAsync = findViewById(R.id.tvAsync);

        btnStartThread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(hilo == null || !hilo.isAlive()){
                    random1 = (int) (Math.random()*7)+3;
                    hilo = new Hilo();
                    tvThread.setText(String.valueOf(random1));
                    hilo.start();
                    Toast.makeText(MainActivity.this, "Hilo iniciado", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Ya hay otro hilo en ejecución", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnStopThread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(hilo != null && hilo.isAlive()){
                    if(hilo.getResult() == random1){
                        Toast.makeText(MainActivity.this, "Hilo detenido en el valor correcto", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Hilo detenido en: " + hilo.getResult(), Toast.LENGTH_SHORT).show();
                    }
                    hilo.interrupt();
                    hilo = null;
                } else {
                    Toast.makeText(MainActivity.this, "No hay ningún hilo en ejecución", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnStartAsync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(hiloAS == null || hiloAS.getStatus() == AsyncTask.Status.FINISHED){
                    random2 = (int)(Math.random()*7)+2;
                    hiloAS = new HiloAsyncTask();
                    tvAsync.setText(String.valueOf(random2));
                    hiloAS.execute();
                    Toast.makeText(MainActivity.this, "Hilo iniciado", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Ya hay otro hilo en ejecución", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnStopAsync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(hiloAS != null && hiloAS.getStatus() == AsyncTask.Status.RUNNING){
                    if(hiloAS.getResult() == random2){
                        Toast.makeText(MainActivity.this, "Hilo detenido en el valor correcto", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Hilo detenido en: " + hiloAS.getResult(), Toast.LENGTH_SHORT).show();
                    }
                    hiloAS.cancel(true);
                    hiloAS = null;
                } else {
                    Toast.makeText(MainActivity.this, "No hay ningún hilo en ejecución", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}