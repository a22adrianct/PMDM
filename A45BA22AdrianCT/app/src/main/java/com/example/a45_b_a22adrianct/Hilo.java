package com.example.a45_b_a22adrianct;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.util.Log;
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
import java.net.HttpURLConnection;
import java.net.URL;

public class Hilo extends Thread{

    String rutaXml;
    String rutaFichero;
    Context context;

    public Hilo(Context context, String rutaXml, String rutaFichero){
        this.context = context;
        this.rutaXml = rutaXml;
        this.rutaFichero = rutaFichero;
    }

    @Override
    public void run(){
        try {
            URL url = new URL(rutaXml);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            con.setUseCaches(true);
            con.setDefaultUseCaches(true);
            con.setChunkedStreamingMode(0);
            con.setInstanceFollowRedirects(true);
            con.setConnectTimeout(30000);
            con.setReadTimeout(30000);

            if (con.getResponseCode() != HttpURLConnection.HTTP_OK) {
                Log.i("ConResponse", con.getResponseMessage() + con.getResponseCode());
                return;
            }

            File file = new File(rutaFichero);
            file.mkdirs();
            File xml = new File(file, "fichero.xml");

            try (InputStream in = con.getInputStream();
                 FileOutputStream fos = new FileOutputStream(xml)) {

                byte data[] = new byte[1024];
                int count;

                while ((count = in.read(data)) != -1) {
                    fos.write(data, 0, count);
                }

                ((Activity) context).runOnUiThread(() -> Toast.makeText(context, "Archivo descargado y guardado correctamente", Toast.LENGTH_SHORT).show());
            }
            con.disconnect();
        } catch (IOException e) {
            ((Activity) context).runOnUiThread(() -> Toast.makeText(context, "No se ha encontrado el archivo proporcionado en la URL", Toast.LENGTH_SHORT).show());
        }
    }
}
