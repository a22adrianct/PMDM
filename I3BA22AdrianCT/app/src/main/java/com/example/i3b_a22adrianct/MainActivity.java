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
                    if (!bundle.getString("TELEFONO").isEmpty()) {
                        Intent call = new Intent(Intent.ACTION_CALL);
                        call.setData(Uri.parse(bundle.getString("TELEFONO")));
                        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            MainActivity.this.requestPermissions(new String[]
                                            {Manifest.permission.CALL_PHONE},
                                    1);
                        }
                        startActivity(call);
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "O campo teléfono está baleiro", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setNegativeButton("Buscar", (DialogInterface.OnClickListener) (dialog, wich) -> {
                    Intent search = new Intent(Intent.ACTION_WEB_SEARCH);
                    if (bundle.getString("TEXTO").isEmpty()){
                        search.putExtra(SearchManager.QUERY, texto);
                    } else {
                        search.putExtra(SearchManager.QUERY, bundle.getString("TEXTO"));
                    }
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

                if (!bundle.getString("TEXTO").isEmpty()){
                    texto = bundle.getString("TEXTO");
                } else {
                    texto = "casa";
                }
                if (!bundle.getString("TELEFONO").isEmpty()){
                    numTelf = bundle.getString("TELEFONO");
                } else {
                    numTelf = "";
                }
                Toast.makeText(getApplicationContext(), texto + "\n" + numTelf, Toast.LENGTH_SHORT).show();
            }
        });
    }
}