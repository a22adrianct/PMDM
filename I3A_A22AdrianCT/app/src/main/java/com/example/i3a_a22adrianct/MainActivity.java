package com.example.i3a_a22adrianct;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.metrics.Event;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private final int ID_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manageEvents();
    }

    private void makeCall() {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:123456789"));

        startActivity(callIntent);
    }

    public void manageEvents() {
        Button btn = (Button) findViewById(R.id.callButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT>=23) {
                    int permiss = checkSelfPermission(Manifest.permission.CALL_PHONE);

                    if (permiss == PackageManager.PERMISSION_GRANTED) {
                        makeCall();
                    }
                    else {
                        MainActivity.this.requestPermissions(new String[]
                                {Manifest.permission.CALL_PHONE},
                                ID_CODE);
                    }
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == ID_CODE){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                makeCall();
            }
            else {
                Toast.makeText(this, "É NECESARIO O PERMISO PARA CHAMAR POR TELÉFONO", Toast.LENGTH_SHORT).show();
            }
        }
    }
}