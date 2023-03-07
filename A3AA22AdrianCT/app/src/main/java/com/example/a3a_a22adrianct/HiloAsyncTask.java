package com.example.a3a_a22adrianct;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

public class HiloAsyncTask extends AsyncTask {

    int result;
    Context context;

    public HiloAsyncTask(Context context){
        this.context = context;
    }

    @Override
    protected Object doInBackground(Object[] objects) {

        for (int i = 10; i >= 0; i--) {
            if (isCancelled()) {
                break;
            }
            try {
                Log.i("TEMPORIZADOR ASYNCTASK", String.valueOf(i));
                result = i;
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                cancel(true);
            }
        }
        result = -1;
        return null;
    }

    public int getResult(){
        return this.result;
    }
}
