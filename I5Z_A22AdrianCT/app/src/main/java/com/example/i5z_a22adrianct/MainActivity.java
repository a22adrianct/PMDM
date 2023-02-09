package com.example.i5z_a22adrianct;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    EditText etDir, etFile, etSentence;
    RadioButton rbInternal, rbSd, rbRaw;
    CheckBox cbOW;
    Button btnWrite, btnRead, btnDelete, btnList;
    TextView tv;
    int append;
    File sdDir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etDir = (EditText) findViewById(R.id.et_directory);
        etFile = (EditText) findViewById(R.id.et_file);
        etSentence = (EditText) findViewById(R.id.et_sentences);
        rbInternal = (RadioButton) findViewById(R.id.rb_internal);
        rbSd = (RadioButton) findViewById(R.id.rb_sd);
        rbRaw = (RadioButton) findViewById(R.id.rb_raw);
        cbOW = (CheckBox) findViewById(R.id.cb_overwrite);
        btnWrite = (Button) findViewById(R.id.btn_write_add);
        btnRead = (Button) findViewById(R.id.btn_read);
        btnDelete = (Button) findViewById(R.id.btn_delete);
        btnList = (Button) findViewById(R.id.btn_list);
        tv = (TextView) findViewById(R.id.tv_text);
        sdDir = getExternalFilesDir(null);

        initRaw();

        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cbOW.isChecked()){
                    append = Context.MODE_PRIVATE;
                } else {
                    append = Context.MODE_APPEND;
                }
                
                if(rbInternal.isChecked()){
                    writeToStorage(getFilesDir().toString(), append);
                }

                if(rbSd.isChecked()){
                    writeToStorage(sdDir.toString(), append);
                }

                if(rbRaw.isChecked()){
                    Toast.makeText(MainActivity.this, "You can't write in RAW memory", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rbInternal.isChecked()){
                    readFromStorage(getFilesDir().toString());
                }

                if(rbSd.isChecked()){
                    readFromStorage(sdDir.toString());
                }

                if(rbRaw.isChecked()){
                    readRawFiles(getFilesDir().toString() + "/raw");
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rbInternal.isChecked()){
                    deleteSelectedFile(getFilesDir().toString());
                }

                if(rbSd.isChecked()){
                    deleteSelectedFile(sdDir.getAbsolutePath());
                }

                if(rbRaw.isChecked()){
                    Toast.makeText(MainActivity.this, "You can't delete in RAW memory", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rbInternal.isChecked()){
                    listFiles(getFilesDir().toString());
                }

                if(rbSd.isChecked()){
                    listFiles(sdDir.getAbsolutePath());
                }

                if(rbRaw.isChecked()){
                    listRawFiles(getFilesDir().toString() + "/raw");
                }
            }
        });
    }

    public void writeToStorage(String dirType, int context){
        boolean append;
        try{
            File dir = new File(dirType, etDir.getText().toString());

            if(!dir.exists()){
                dir.mkdir();
            }

            File file = new File(dir, etFile.getText().toString() + ".txt");

            if(context == Context.MODE_APPEND){
                append = true;
            } else {
                append = false;
            }

            OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(file.getAbsolutePath(), append));

            osw.write(etSentence.getText().toString() + "\n");
            osw.close();
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public void readFromStorage(String dirType){
        File dir = new File(dirType, etDir.getText().toString());
        File file = new File(dir, etFile.getText().toString() + ".txt");

        tv.setText("");

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file.getAbsolutePath())));
            String text;
            while((text = br.readLine()) != null){
                tv.append(text);
                tv.append("\n");
            }
            br.close();
        } catch (FileNotFoundException e) {
            Toast.makeText(this, "Could not find file: " + file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void readRawFiles(String dirType){
        File dir = new File(dirType);
        File file = new File(dir, etFile.getText().toString() + ".txt");

        tv.setText("");

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file.getAbsolutePath())));
            String text;
            while((text = br.readLine()) != null){
                tv.append(text);
                tv.append("\n");
            }
            br.close();
        } catch (FileNotFoundException e) {
            Toast.makeText(this, "Could not find file: " + file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteSelectedFile(String dirType){
        File dir = new File(dirType, etDir.getText().toString());
        File file = new File(dir, etFile.getText().toString() + ".txt");

        if(file.exists()){
            file.delete();
            Toast.makeText(MainActivity.this, "File deleted: " + file.getAbsolutePath(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(MainActivity.this, "File: " + file.getAbsolutePath() + " does not exist.", Toast.LENGTH_LONG).show();
        }
    }

    public void listFiles(String dirType){
        tv.setText("");

        File dir = new File(dirType, etDir.getText().toString());
        tv.append(dir.getAbsolutePath());

        String[] files = dir.list();

        if(files == null){
            dir.mkdir();
            files = dir.list();
        }

        for(int i = 0; i < files.length; i++){
            File file = new File(dir, "/" + files[i]);
            if(file.isFile()){
                tv.append("\nFile: " + files[i]);
            } else {
                tv.append("\nDirectory: " + files[i]);
            }
        }
    }

    public void listRawFiles(String dirType){
        tv.setText("");

        File dir = new File(dirType);
        tv.append(dir.getAbsolutePath());

        String[] files = dir.list();

        if(files == null){
            dir.mkdir();
            files = dir.list();
        }

        for(int i = 0; i < files.length; i++){
            File file = new File(dir, "/" + files[i]);
            if(file.isFile()){
                tv.append("\nFile: " + files[i]);
            } else {
                tv.append("\nDirectory: " + files[i]);
            }
        }
    }

    public void initRaw(){
        String path = getFilesDir().getAbsolutePath() + "/raw";
        File dir = new File(path);

        if(!dir.exists()){
            dir.mkdir();
        }

        File rawFile1 = new File(dir, "ola.txt");
        File rawFile2 = new File(dir, "adeus.txt");

        OutputStreamWriter osw1 = null;
        OutputStreamWriter osw2 = null;
        try {
            osw1 = new OutputStreamWriter(new FileOutputStream(rawFile1.getAbsolutePath()));

            osw1.write("Example RAW file 1");
            osw1.close();

            osw2 = new OutputStreamWriter(new FileOutputStream(rawFile2.getAbsolutePath()));

            osw2.write("Example RAW file 2");
            osw2.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}