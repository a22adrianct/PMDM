package com.example.a1_a_a22adrianct;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPref = getPreferences(MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();

                editor.putString(etNombre.getText().toString(), etDesc.getText().toString());
                editor.commit();
            }
        });
    }
}
