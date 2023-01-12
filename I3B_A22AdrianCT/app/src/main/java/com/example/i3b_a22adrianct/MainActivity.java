package com.example.i3b_a22adrianct;

import androidx.annotation.NonNull;
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
import android.os.Build;
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
                    if (bundle != null && bundle.getString("TELEFONO").length() > 0) {
                        numTelf = bundle.getString("TELEFONO");

                        if (Build.VERSION.SDK_INT>=23) {
                            int permiss = checkSelfPermission(Manifest.permission.CALL_PHONE);

                            if (permiss == PackageManager.PERMISSION_GRANTED) {
                                makeCall();                            }
                            else {
                                MainActivity.this.requestPermissions(new String[]
                                                {Manifest.permission.CALL_PHONE},
                                        1);
                            }
                        }
                    }
                    else if (bundle == null || bundle.getString("TELEFONO").isEmpty()){
                        Toast.makeText(getApplicationContext(), "O campo teléfono está baleiro", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setNegativeButton("Buscar", (DialogInterface.OnClickListener) (dialog, wich) -> {
                    Intent search = new Intent(Intent.ACTION_WEB_SEARCH);
                    texto = "casa";
                    if (bundle != null && bundle.getString("TEXTO").length() > 0){
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
                texto = "";
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

    private void makeCall(){
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:"+numTelf));
        startActivity(callIntent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                makeCall();
            }
            else {
                Toast.makeText(this, "É NECESARIO O PERMISO PARA CHAMAR POR TELÉFONO", Toast.LENGTH_SHORT).show();
            }
        }
    }
}