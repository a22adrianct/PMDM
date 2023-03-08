package com.example.a45_b_a22adrianct;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText et;
    Button btnDownload, btnProcess, btnShow;
    TextView tv;
    Hilo hilo;
    BaseDatos bd;
    SQLiteDatabase sqLite;
    Salarios salarios;
    ArrayList<Salarios> listaSalarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et = findViewById(R.id.etRuta);
        btnDownload = findViewById(R.id.btnDescargar);
        btnProcess = findViewById(R.id.btnProcesar);
        btnShow = findViewById(R.id.btnMostrar);
        tv = findViewById(R.id.tv);

        bd =  new BaseDatos(this);
        sqLite = bd.getWritableDatabase();

        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hilo = new Hilo(MainActivity.this, et.getText().toString(), getFilesDir().toString());
                hilo.start();
            }
        });

        btnProcess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    processXML(getFilesDir().toString());
                } catch (XmlPullParserException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(salarios != null){
                    tv.setText("Total_Salary\t" + "Month\n");

                    for(Salarios salario : listaSalarios){
                        tv.append(salario.getTotal() + "\t" + "\t" + "\t" + "\t" + salario.getMes() + "\n");
                    }
                }
            }
        });
    }

    private void processXML(String rutaFichero) throws XmlPullParserException, IOException {
        File file = new File(rutaFichero + "/fichero.xml");
        listaSalarios = new ArrayList<>();

        try {
            FileInputStream fis = new FileInputStream(file);

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(fis, null);

            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagName = parser.getName();
                if (eventType == XmlPullParser.START_TAG) {
                    if ("salary".equals(tagName)) {
                        String month = "";
                        int amount = 0;

                        while (parser.next() != XmlPullParser.END_TAG || !"salary".equals(parser.getName())) {
                            if (parser.getEventType() == XmlPullParser.START_TAG) {
                                if ("month".equals(parser.getName())) {
                                    month = parser.nextText();
                                } else if ("amount".equals(parser.getName())) {
                                    amount = Integer.parseInt(parser.nextText());
                                } else if ("complement".equals(parser.getName())) {
                                    String complementType = parser.getAttributeValue(null, "type");
                                    int complementValue = Integer.parseInt(parser.nextText());
                                    amount = amount + complementValue;
                                }
                            }
                        }
                        salarios = new Salarios(month, amount);
                        listaSalarios.add(salarios);
                    }
                }
                eventType = parser.next();
            }
            Toast.makeText(this, "Fichero procesado correctamente", Toast.LENGTH_SHORT).show();
            insertarBD();
            fis.close();
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
    }

    private void insertarBD(){
        for(Salarios salario : listaSalarios){
            try{
                ContentValues values = new ContentValues();
                values.put("month", salario.getMes());
                values.put("total_salary", salario.getTotal());
                sqLite.insertOrThrow("salary", null, values);
            } catch (android.database.sqlite.SQLiteConstraintException e){
                Log.e("DATABASE ERROR", "Duplicated month");
            }
        }
    }
}