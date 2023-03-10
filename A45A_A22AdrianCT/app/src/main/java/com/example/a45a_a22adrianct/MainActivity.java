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
import android.util.Log;
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
                       hilo = new Hilo();
                       hilo.start();
                       try {
                           processXML();
                       } catch (XmlPullParserException e) {
                           throw new RuntimeException(e);
                       } catch (IOException e) {
                           throw new RuntimeException(e);
                       }
                   }
               } else {
                   tv.setText("");
                   hilo = new Hilo();
                   hilo.start();
                   try {
                       processXML();
                   } catch (XmlPullParserException e) {
                       throw new RuntimeException(e);
                   } catch (IOException e) {
                       throw new RuntimeException(e);
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
               hilo = new Hilo();
               hilo.start();
                try {
                    processXML();
                } catch (XmlPullParserException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        else
            Toast.makeText(this, "Es necesario conceder los permisos para realizar ésta acción", Toast.LENGTH_SHORT).show();
    }

    private void processXML() throws XmlPullParserException, IOException {
        File file = new File(Environment.getExternalStorageDirectory() + "/RUTAS/ficheiro.xml");

        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        XmlPullParser parser = factory.newPullParser();

        FileInputStream fis = null;
        try{
            fis = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            Log.e("XML", "Non se encontrou o arquivo");
            return;
        }

        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
        parser.setInput(fis, "UTF-8");

        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_TAG && parser.getName().equals("ruta")) {
                String nome = null;
                String descripcion = null;
                while (eventType != XmlPullParser.END_TAG || !parser.getName().equals("ruta")) {
                    if (eventType == XmlPullParser.START_TAG && parser.getName().equals("nome")) {
                        parser.next();
                        nome = parser.getText();
                    } else if (eventType == XmlPullParser.START_TAG && parser.getName().equals("descripcion")) {
                        parser.next();
                        descripcion = parser.getText();
                    }
                    eventType = parser.next();
                }
                tv.append(nome + " " + descripcion + "\n");
            }
            eventType = parser.next();
        }
        fis.close();
    }
}