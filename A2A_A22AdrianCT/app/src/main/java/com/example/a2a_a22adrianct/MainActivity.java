package com.example.a2a_a22adrianct;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Spinner spinner;
    Button btnReproducir, btnGrabar, btnFoto;
    ImageView iv;
    String musicPath;
    MediaPlayer mediaPlayer;
    MediaRecorder mediaRecorder;
    File[] files;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = findViewById(R.id.spinner);
        btnReproducir = findViewById(R.id.btnReproducir);
        btnGrabar = findViewById(R.id.btnGrabar);
        btnFoto = findViewById(R.id.btnFoto);
        iv = findViewById(R.id.iv);

        musicPath = Environment.getExternalStorageDirectory() + "/" + "UD-A2A/MUSICA/A22AdrianCT/";
        mediaPlayer = new MediaPlayer();

        if (checkSelfPermission(WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[] {WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            copyAudioFromAssets();
        }

        setSpinnerAdapter();

        btnReproducir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                }
                try {
                    mediaPlayer.reset();
                    mediaPlayer.setDataSource(musicPath + "/" + spinner.getSelectedItem().toString());
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        btnGrabar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkSelfPermission(RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED){
                    requestPermissions(new String[] {RECORD_AUDIO}, 2);
                } else {
                    recordAudio();
                }
            }
        });

        btnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void recordAudio() {
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setMaxDuration(10000);
        mediaRecorder.setAudioEncodingBitRate(32768);
        mediaRecorder.setAudioSamplingRate(8000);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        mediaRecorder.setOutputFile(musicPath + "record" + files.length + ".3gp");

        try {
            mediaRecorder.prepare();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        mediaRecorder.start();
        Toast.makeText(MainActivity.this, "Grabación iniciada", Toast.LENGTH_SHORT).show();

        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        dialog.setTitle("Grabación en curso...");
        dialog.setPositiveButton("Finalizar grabación", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mediaRecorder.stop();
                mediaRecorder.release();
                mediaRecorder = null;
                Toast.makeText(MainActivity.this, "Grabación finalizada y guardada en: " + musicPath + "record" + files.length + ".3gp", Toast.LENGTH_SHORT).show();
                setSpinnerAdapter();
            }
        });
        dialog.setCancelable(false);
        dialog.show();
    }

    private void setSpinnerAdapter(){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);

        ArrayList<String> fileList = new ArrayList<>();
        File dir = new File(musicPath);
        files = dir.listFiles();
        for(File file : files){
            fileList.add(file.getName());
        }

        adapter.addAll(fileList);
    }

    private void copyAudioFromAssets(){
        File sdPath = new File(musicPath);
        if(!sdPath.exists()){
            sdPath.mkdirs();
        }

        try{
            InputStream is1 = getAssets().open("audio1.mp3");
            OutputStream os1 = new FileOutputStream(sdPath + "/audio1.mp3");

            int num1;
            byte[] buffer1 = new byte[2048];

            while((num1 = is1.read(buffer1)) > 0){
                os1.write(buffer1, 0, num1);
            }

            is1.close();
            os1.flush();
            os1.close();

            InputStream is2 = getAssets().open("audio2.mp3");
            OutputStream os2 = new FileOutputStream(sdPath + "/audio2.mp3");

            int num2;
            byte[] buffer2 = new byte[2048];

            while((num2 = is2.read(buffer2)) > 0){
                os2.write(buffer2, 0, num2);
            }

            is2.close();
            os2.flush();
            os2.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            if(requestCode == 1){
                copyAudioFromAssets();
            }

            if(requestCode == 2){
                recordAudio();
            }
        }
        else
            Toast.makeText(this, "Es necesario conceder los permisos para realizar ésta acción", Toast.LENGTH_SHORT).show();
    }
}