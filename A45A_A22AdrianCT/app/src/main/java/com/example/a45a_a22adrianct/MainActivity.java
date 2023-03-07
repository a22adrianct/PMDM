package com.example.a45a_a22adrianct;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    Button btn;
    TextView tv;
    private Hilo hilo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.btn);
        tv = findViewById(R.id.tv);

        btn.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View view) {
               if (Build.VERSION.SDK_INT >= 23) {
                   if (checkSelfPermission(WRITE_EXTERNAL_STORAGE)
                           != PackageManager.PERMISSION_GRANTED) {
                       requestPermissions(new String[] {WRITE_EXTERNAL_STORAGE}, 1);
                   } else {
                       tv.setText("");
                       hilo = new Hilo(tv);
                        hilo.start();
                   }
               }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            if(requestCode == 1){
                tv.setText("");
               hilo = new Hilo(tv);
               hilo.start();
            }
        }
        else
            Toast.makeText(this, "Es necesario conceder los permisos para realizar ésta acción", Toast.LENGTH_SHORT).show();
    }
}