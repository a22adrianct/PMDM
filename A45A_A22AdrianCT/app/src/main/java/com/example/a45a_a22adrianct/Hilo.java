package com.example.a45a_a22adrianct;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Hilo extends Thread{

    @Override
    public void run(){
        String urlStr = "https://manuais.iessanclemente.net/images/2/20/Platega_pdm_rutas.xml";
        String path = Environment.getExternalStorageDirectory() + "/RUTAS/";

        try {
            URL url = new URL(urlStr);
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

            File file = new File(path);
            file.mkdirs();
            File xml = new File(file, "ficheiro.xml");

            try (InputStream in = con.getInputStream();
                 FileOutputStream fos = new FileOutputStream(xml)) {

                byte data[] = new byte[1024];
                int count;

                while ((count = in.read(data)) != -1) {
                    fos.write(data, 0, count);
                }

                Log.i("Archivo descargado", "Descarga exitosa");
            }

            con.disconnect();
        } catch (IOException e) {
            Log.e("Error en descarga", e.getMessage(), e);
        }
    }
}
