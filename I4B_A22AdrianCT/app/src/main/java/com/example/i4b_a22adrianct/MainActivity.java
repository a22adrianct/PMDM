package com.example.i4b_a22adrianct;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private File ficheiro;
    private File ficheiroEscollido;
    private TextView textView;
    private EditText editText;
    private Button btnDialog;
    private Button btnFragmentDialog;
    private Button btnEngadirElemento;
    private String[] elementos;
    private ArrayList<Boolean> elementosEscollidos = new ArrayList<>();
    private ArrayList<Boolean> cancelarEscollidos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUp();

        btnEngadirElemento.setOnClickListener(view -> {
            String txtToast = "Elemento engadido correctamente";
            String txt = editText.getText().toString();
            if(!txt.isEmpty() && txt.trim().length() != 0) {
                txtToast = "O elemento non foi engadido";
                escribirFicheiro(ficheiro, editText.getText().toString());
                actualizarElementos();
                actualizarSeleccion();
            }

            Toast.makeText(this, txtToast, Toast.LENGTH_SHORT).show();
            editText.getText().clear();
        });

        btnDialog.setOnClickListener(view -> {
            Dialog dialog = createDialog();
            dialog.show();
        });

        btnFragmentDialog.setOnClickListener(view -> {
            FragmentManager fm = getSupportFragmentManager();
            new Dialogo().show(fm, Dialogo.TAG);
        });
    }

    private void setUp() {
        textView = findViewById(R.id.tv);
        editText = findViewById(R.id.et);
        btnEngadirElemento = findViewById(R.id.btnEngadir);
        btnDialog = findViewById(R.id.btnShowDialog);
        btnFragmentDialog = findViewById(R.id.btnDialogFragment);

        ficheiro = new File(getExternalFilesDir(null).getAbsolutePath() , "/animals.txt");
        ficheiroEscollido = new File(getExternalFilesDir(null).getAbsolutePath() , "/selected.txt");

        actualizarElementos();

        if(ficheiro.length() == 0){
             for(String elemento : elementos){
                escribirFicheiro(ficheiro, elemento);
            }
        }

        if(ficheiroEscollido.length() != 0 && ficheiroEscollido.exists() ){
            elementosEscollidos = cargarRecursos(ficheiroEscollido);
            cancelarEscollidos = (ArrayList<Boolean>) elementosEscollidos.clone();
        }

        if(!elementosEscollidos.isEmpty()) actualizarTextView();

    }

    public Dialog createDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        boolean[] selected = formatSelected();

        builder.setTitle("Elementos")
                .setMultiChoiceItems(elementos, selected, (dialogInterface, i, b) -> elementosEscollidos.set(i , b))
                .setPositiveButton("Aceptar", (dialogInterface, i) -> {
                    cancelarEscollidos = (ArrayList<Boolean>) elementosEscollidos.clone();
                    gardarRecursos(ficheiroEscollido, elementosEscollidos);
                    actualizarTextView();
                })
                .setNegativeButton("Cancelar", (dialogInterface, i) -> {
                    elementosEscollidos = (ArrayList<Boolean>) cancelarEscollidos.clone();
                    gardarRecursos(ficheiroEscollido, elementosEscollidos);
                });
        return builder.create();
    }

    private void actualizarElementos(){
        ArrayList<String> lines = new ArrayList<>();
        String line;
        try{
            BufferedReader br = new BufferedReader( new FileReader(ficheiro));
            line = br.readLine();
            while(line != null){
                lines.add(line);
                line = br.readLine();
            }
            br.close();
        }catch(IOException ioe){
            ioe.printStackTrace();
        }

        elementos = new String[lines.size()];

        for(int i = 0 ; i < elementos.length ; i++){
            elementos[i] = lines.get(i);
        }
    }

    private void actualizarSeleccion(){
        if(elementosEscollidos.isEmpty()){
            for(int i = 0; i < elementos.length ; i++){
                elementosEscollidos.add(false);
            }
        } else {
            if(elementos.length > elementosEscollidos.size()){
                for(int i = elementosEscollidos.size(); i < elementos.length ; i++ ){
                    elementosEscollidos.add(false);
                }
            }
        }

        gardarRecursos(ficheiroEscollido, elementosEscollidos);
        cancelarEscollidos = (ArrayList<Boolean>) elementosEscollidos.clone();
    }

    public void actualizarTextView(){
        StringBuilder builder = new StringBuilder();

        for(int i = 0 ; i < elementos.length ; i++){
            if(elementosEscollidos.get(i))
                builder.append(elementos[i]).append(" ");
        }

        textView.setText(builder.toString().trim());
    }

    private boolean[] formatSelected(){
        boolean[] selected = new boolean[elementosEscollidos.size()];
        for(int i = 0 ; i < selected.length ; i++){
            selected[i] = elementosEscollidos.get(i);
        }
        return selected;
    }

    public void escribirFicheiro(File file , String txt){
        try{
            OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(file , true));
            osw.write(txt + "\n");
            osw.close();
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
    }

    public void gardarRecursos(File file , ArrayList<Boolean> data){
        try{
            OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(file , false));
            osw.write("");
            osw.close();

            osw = new OutputStreamWriter(new FileOutputStream(file , true));

            for(boolean item : data){
                if(item){
                    osw.write("true\n");
                } else {
                    osw.write("false\n");
                }
            }
            osw.close();
        }catch (IOException ioe){ioe.printStackTrace();}
    }

    public ArrayList<Boolean> cargarRecursos(File file){
        ArrayList<Boolean> booleanArrayList = new ArrayList<>();
        String line;
        try{
            BufferedReader br = new BufferedReader( new FileReader(file));
            line = br.readLine();
            while(line != null){
                if(line.equalsIgnoreCase("true")){
                    booleanArrayList.add(true);
                } else {
                    booleanArrayList.add(false);
                }
                line = br.readLine();
            }
            br.close();
        }catch(IOException ioe){
            ioe.printStackTrace();
        }

        if (booleanArrayList.isEmpty()) return null;

        return booleanArrayList;
    }
}