package com.example.i3b_a22adrianct;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.net.InetSocketAddress;
import java.nio.BufferUnderflowException;

public class MainActivity extends FragmentActivity {

    private String texto;
    private String numTelf;
    private Button nextActivity, dataButton;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nextActivity = (Button) findViewById(R.id.telephoneButton);
        dataButton = (Button) findViewById(R.id.dataButton);

        bundle = getIntent().getExtras();

        Intent intent = new Intent(this, IntroducirDatos.class);

        nextActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });

        nextActivity.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Escolle unha opción");
                builder.setCancelable(false);
                builder.setPositiveButton("Chamar", (DialogInterface.OnClickListener) (dialog, wich) -> {
                    if (bundle != null) {
                        numTelf = bundle.getString("TELEFONO");
                        Intent callIntent = new Intent(Intent.ACTION_DIAL);
                        callIntent.setData(Uri.parse("tel:"+numTelf));
                        startActivity(callIntent);
                    }
                    else if (bundle == null || bundle.getString("TELEFONO").isEmpty()){
                        Toast.makeText(getApplicationContext(), "O campo teléfono está baleiro", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setNegativeButton("Buscar", (DialogInterface.OnClickListener) (dialog, wich) -> {
                    Intent search = new Intent(Intent.ACTION_WEB_SEARCH);
                    texto = "casa";
                    if (bundle != null){
                        texto = bundle.getString("TEXTO");
                    }
                    search.putExtra(SearchManager.QUERY, texto);
                    startActivity(search);
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                return false;
            }
        });
        dataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                texto = "casa";
                numTelf = "";

                if (bundle != null){
                    texto = bundle.getString("TEXTO");
                }

                if (bundle != null){
                    numTelf = bundle.getString("TELEFONO");
                }
                Toast.makeText(getApplicationContext(), texto + "\n" + numTelf, Toast.LENGTH_SHORT).show();
            }
        });
    }
}