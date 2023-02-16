package com.example.i5z_a22adrianct;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
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
import java.io.IOException;
import java.io.InputStream;
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
                    readRawFiles();
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
                    listRawFiles();
                }
            }
        });
    }

    public void writeToStorage(String dirType, int context){
        boolean append;

        try{
            File dir = new File(dirType, etDir.getText().toString().trim());

            if(!dir.exists()){
                dir.mkdirs();
            }

            if(!etFile.getText().toString().isEmpty()){
                File file = new File(dir, etFile.getText().toString());

                if(context == Context.MODE_APPEND){
                    append = true;
                } else {
                    append = false;
                }

                OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(file.getAbsolutePath(), append));

                osw.write(etSentence.getText().toString() + "\n");
                osw.close();
            }
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public void readFromStorage(String dirType){
        File dir = new File(dirType, etDir.getText().toString());
        File file = new File(dir, etFile.getText().toString());

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

    public void readRawFiles(){
        tv.setText("");
        int id = -1;

        if(etFile.getText().toString().equals("ola")){
            id = R.raw.ola;
        }

        if(etFile.getText().toString().equals("adeus")){
            id = R.raw.adeus;
        }

        try{
            InputStream is = getResources().openRawResource(id);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String text;
            while ((text = br.readLine()) != null)
                tv.append(text + "\n");
            br.close();
            is.close();
        }catch(Exception e){
            Toast.makeText(this, "Could not find file: " + etFile.getText().toString() + " in RAW", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteSelectedFile(String dirType){
        File dir = new File(dirType, etDir.getText().toString());

        if(!etFile.getText().toString().isEmpty()){
            File file = new File(dir, etFile.getText().toString());

            if(file.exists()){
                file.delete();
                Toast.makeText(MainActivity.this, "File deleted: " + file.getAbsolutePath(), Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(MainActivity.this, "File: " + file.getAbsolutePath() + " does not exist.", Toast.LENGTH_LONG).show();
            }
        } else {
            if(dir.isDirectory() && dir.list().length == 0){
                dir.delete();
                Toast.makeText(MainActivity.this, "Directory: " + dir.getAbsolutePath() + " deleted succesfully.", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(MainActivity.this, "Directory: " + dir.getAbsolutePath() + " is not empty.", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void listFiles(String dirType){
        tv.setText("");

        File dir = new File(dirType, etDir.getText().toString());
        tv.append(dir.getAbsolutePath());

        String[] files = dir.list();

        if(files == null){
            Toast.makeText(MainActivity.this, "Directory: " + dir.getAbsolutePath() + " is empty.", Toast.LENGTH_LONG).show();
        } else {
            for(int i = 0; i < files.length; i++){
                File file = new File(dir, "/" + files[i]);
                if(file.isFile()){
                    tv.append("\nFile: " + files[i]);
                } else {
                    tv.append("\nDirectory: " + files[i]);
                }
            }
        }
    }

    public void listRawFiles(){
        tv.setText("");

        StringBuilder builder = new StringBuilder();
        builder.append("RAW Content:\n");
        builder.append("\tFile: ola\n");
        builder.append("\tFile: adeus");
        tv.setText(builder.toString());
    }
}