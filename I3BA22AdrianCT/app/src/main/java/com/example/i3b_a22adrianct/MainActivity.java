package com.example.i3b_a22adrianct;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {

    private String texto = "casa";
    private String numTelf;
    private static final int CODIGO = 8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onBtnClick(View view){
        Intent intent = new Intent(this, IntroducirDatos.class);
        activityLauncher.launch(intent, result -> {
            if (result.getResultCode() == Activity.RESULT_OK) {
                Intent data = result.getData();
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODIGO) {
            if (resultCode == RESULT_OK) {
                if (data.hasExtra("TEXTO")) {
                    texto = data.getExtras().getString("TEXTO");
                }
                 if (data.hasExtra("TELEFONO")) {
                     numTelf = data.getExtras().getString("TELEFONO");
                } else {
                     Toast.makeText(this, "Non intruduciches un número de teléfono", Toast.LENGTH_SHORT).show();
                 }
            }
        }
    }
}