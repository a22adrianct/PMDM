package com.example.i3b_a22adrianct;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class IntroducirDatos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introducir_datos);
    }

    EditText etCadea = findViewById(R.id.cadea);
    EditText etTelefono = findViewById(R.id.telefono);

    public void onPecharClick(View v) {
        Intent datosVolta = new Intent();
        datosVolta.putExtra("TEXTO", etCadea.getText().toString());
        datosVolta.putExtra("TELEFONO", etTelefono.getText().toString());
        setResult(RESULT_OK, datosVolta);
        finish();
    }

    public void finish(){
        super.finish();
    }
}