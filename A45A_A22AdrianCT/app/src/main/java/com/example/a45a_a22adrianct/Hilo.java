package com.example.a45a_a22adrianct;

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
import java.net.MalformedURLException;
import java.net.URL;

public class Hilo extends Thread{

    TextView tv;

    public Hilo(TextView tv){
        this.tv = tv;
    }

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
            processXML();
            con.disconnect();
        } catch (IOException e) {
            Log.e("Error en descarga", e.getMessage(), e);
        } catch (XmlPullParserException e) {
            throw new RuntimeException(e);
        }
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
