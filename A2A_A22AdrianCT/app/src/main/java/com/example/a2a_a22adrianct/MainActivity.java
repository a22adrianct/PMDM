package com.example.a2a_a22adrianct;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import static androidx.core.content.FileProvider.getUriForFile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Spinner spinner;
    Button btnReproducir, btnGrabar, btnFoto;
    ImageView iv;
    String musicPath, photoPath;
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

        musicPath = Environment.getExternalStorageDirectory() + "/UD-A2A/MUSICA/A22AdrianCT/";
        photoPath = Environment.getExternalStorageDirectory() + "/UD-A2A/FOTO/A22AdrianCT/";
        mediaPlayer = new MediaPlayer();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[] {WRITE_EXTERNAL_STORAGE}, 1);
            } else {
                copyAudioFromAssets();
                setSpinnerAdapter();
            }
        } else {
            copyAudioFromAssets();
            setSpinnerAdapter();
        }

        btnReproducir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                }
                try {
                    mediaPlayer.reset();
                    mediaPlayer.setDataSource(musicPath + "/" + spinner.getSelectedItem().toString());
                    Toast.makeText(MainActivity.this, musicPath + "/" + spinner.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
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
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if(checkSelfPermission(RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED){
                        requestPermissions(new String[] {RECORD_AUDIO}, 2);
                    } else {
                        recordAudio();
                    }
                } else {
                    recordAudio();
                }
            }
        });

        btnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if(checkSelfPermission(CAMERA) != PackageManager.PERMISSION_GRANTED){
                        requestPermissions(new String[] {CAMERA}, 3);
                    } else {
                        takePhoto();
                    }
                } else {
                    takePhoto();
                }
            }
        });
    }

    private void takePhoto(){
        if ( mediaPlayer != null && mediaPlayer.isPlaying() ){
            mediaPlayer.stop();
        };
        File path = new File(photoPath);
        if(!path.exists()) path.mkdirs();
        File photoFile = new File(path +"/photo.jpg");

        Uri contentUri = null;
        if (Build.VERSION.SDK_INT >= 24) {
            contentUri = getUriForFile(getApplicationContext(),getApplicationContext().getPackageName() + ".provider", photoFile);
        } else {
            contentUri= Uri.fromFile(photoFile);
        }

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
        startActivityForResult(intent, 1);
    }

    private void recordAudio() {
        if(mediaPlayer != null && mediaPlayer.isPlaying()){
            mediaPlayer.stop();
        }

        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setMaxDuration(10000);
        mediaRecorder.setAudioEncodingBitRate(32768);
        mediaRecorder.setAudioSamplingRate(8000);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        mediaRecorder.setOutputFile(musicPath + "record.3gp");

        try {
            mediaRecorder.prepare();
            mediaRecorder.start();
            Toast.makeText(MainActivity.this, "Grabación iniciada", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        dialog.setTitle("Grabación en curso...");
        dialog.setPositiveButton("Finalizar grabación", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mediaRecorder.stop();
                mediaRecorder.release();
                mediaRecorder = null;
                Toast.makeText(MainActivity.this, "Grabación finalizada y guardada en: " + musicPath + "record.3gp", Toast.LENGTH_SHORT).show();
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
        if(dir.exists()){
            files = dir.listFiles();
            for(File file : files){
                fileList.add(file.getName());
            }

            adapter.addAll(fileList);
        }
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
                setSpinnerAdapter();
            }

            if(requestCode == 2){
                recordAudio();
            }

            if(requestCode == 3){
                takePhoto();
            }
        }
        else
            Toast.makeText(this, "Es necesario conceder los permisos para realizar ésta acción", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        File photoFile = new File(photoPath + "/photo.jpg");
        if (resultCode == RESULT_OK) {
            Bitmap bitmap = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
            iv.setImageBitmap(bitmap);
        }
    }
}