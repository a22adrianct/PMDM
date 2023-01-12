package com.example.i3b_a22adrianct;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class IntroducirDatos extends AppCompatActivity {

    private EditText etCadea, etTelefono;
    private Button close;
    private Bundle bundle = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introducir_datos);

        etCadea = findViewById(R.id.cadea);
        etTelefono = findViewById(R.id.telefono);
        close = findViewById(R.id.pechar);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);

                bundle.putString("TEXTO", etCadea.getText().toString());
                bundle.putString("TELEFONO", etTelefono.getText().toString());

                i.putExtras(bundle);
                startActivity(i);
                finish();
            }
        });
    }

    public void finish(){
        super.finish();
    }
}