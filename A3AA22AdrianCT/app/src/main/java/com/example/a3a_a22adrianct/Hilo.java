package com.example.a3a_a22adrianct;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.Toast;

public class Hilo extends Thread{

    int result;
    Context context;
    public Hilo(Context context){
        this.context = context;
    }

    @Override
    public void run() {
        super.run();

        for(int i = 10; i >= 0; i--){
            try {
                Log.i("TEMPORIZADOR THREAD", String.valueOf(i));
                result = i;
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                break;
            }
        }
        result = -1;
        ((Activity) context).runOnUiThread(() -> Toast.makeText(context, "(Thread) Se acabó el tiempo", Toast.LENGTH_SHORT).show());
    }

    public int getResult(){
        return this.result;
    }
}
