package com.example.i2_a22adrianct;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.Chronometer;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Chronometer.OnChronometerTickListener;


public class MainActivity extends AppCompatActivity implements OnItemSelectedListener, OnChronometerTickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        final String galicianProvince = getString(R.string.galician_province);
        final String notGalicianProvince = getString(R.string.not_galician_province);
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long l) {
                if (pos >= 0 && pos <= 3){
                    Toast.makeText(parent.getContext(),
                            galicianProvince,
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(parent.getContext(),
                            notGalicianProvince,
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        Chronometer crono = (Chronometer) findViewById(R.id.chronometer);
        int selfDestructionTime = 15;

        crono.setOnChronometerTickListener(new OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                long timeElapsed = SystemClock.elapsedRealtime() - chronometer.getBase();

                int secs = (int) timeElapsed / 1000;
                if (secs == selfDestructionTime){

                    finish();
                }
            }
        });

        Switch switchButton = (Switch) findViewById(R.id.switch_button);
        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                crono.setBase(SystemClock.elapsedRealtime());
                if (switchButton.isChecked()){
                    crono.start();
                } else {
                    crono.stop();
                }
            }
        });
    }


    public void sendText(View v){
        final EditText etTextBox = findViewById(R.id.text_box);
        final TextView tvContentText = findViewById(R.id.content_text);
        final CheckBox cbClear = findViewById(R.id.clear);
        if (cbClear.isChecked()) {
            tvContentText.setText("");
        } else {
            //tvContentText.setText(tvContentText.getText().toString() + " " + etTextBox.getText());
            tvContentText.append(" ".concat(String.valueOf(etTextBox.getText())));

        }
        etTextBox.setText("");
    }

    public void changeColor(View v){
        TextView tvContentText = findViewById(R.id.content_text);

        switch (v.getId()){
            case R.id.blue_button:
                tvContentText.setTextColor(getResources().getColor(R.color.blue));
                break;
            case R.id.red_button:
                tvContentText.setTextColor(getResources().getColor(R.color.red));
        }
    }

    public void pictureToast(View v){
        final ImageView img = (ImageView) findViewById(R.id.image);
        final String msg = img.getTag().toString();
        Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onChronometerTick(Chronometer chronometer) {

    }
}