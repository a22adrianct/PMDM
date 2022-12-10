package com.example.i4b_a22ikeraa;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import java.io.File;
import java.util.ArrayList;

public class I4B_a22ikeraa extends AppCompatActivity {
    private File file;
    private File selectedFile;
    private Tools tools;
    private TextView textView;
    private EditText editText;
    private Button bttnDialog;
    private Button bttnFragmentDialog;
    private Button bttnAddElement;
    private String[] elements;
    private ArrayList<Boolean> selectedElements = new ArrayList<>();
    private ArrayList<Boolean> cancelSelected = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUp();

        bttnAddElement.setOnClickListener(view -> {
            String txtToast = getString(R.string.invalidElement);
            if(tools.isElementValid(editText.getText().toString())) {
                txtToast = getString(R.string.validElement);
                tools.writeIntoFile(file , editText.getText().toString());
                updateElements();
                updateSelected();
            }

            Toast.makeText(this, txtToast, Toast.LENGTH_SHORT).show();
            editText.getText().clear();
        });

        bttnDialog.setOnClickListener( view -> {
            Dialog dialog = createDialog();
            dialog.show();
        });

        bttnFragmentDialog.setOnClickListener(view -> {
            FragmentManager fm = getSupportFragmentManager();
            new MultipleItemDialogFragment().show(fm, MultipleItemDialogFragment.TAG);
        });
    }

    private void setUp() {
        textView = findViewById(R.id.tvSelected);
        editText = findViewById(R.id.etElementToAdd);
        bttnAddElement = findViewById(R.id.bttnAddElement);
        bttnDialog = findViewById(R.id.bttnDialog);
        bttnFragmentDialog = findViewById(R.id.bttnFragmentDialog);

        file = new File(getExternalFilesDir(null).getAbsolutePath() , "/animals.txt");
        selectedFile = new File(getExternalFilesDir(null).getAbsolutePath() , "/selected.txt");
        tools = new Tools();

        tools.firstUse(file , getResources().getStringArray(R.array.animals));
        updateElements();

        if(selectedFile.length() != 0 && selectedFile.exists() ){
            selectedElements = tools.loadResources(selectedFile);
            cancelSelected = (ArrayList<Boolean>) selectedElements.clone();
        }

        if(!selectedElements.isEmpty()) updateTextView();

    }

    public Dialog createDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        boolean[] selected = formatSelected();

        builder.setTitle(getString(R.string.dialogTitle))
                .setMultiChoiceItems(elements, selected, (dialogInterface, i, b) -> selectedElements.set(i , b))
                .setPositiveButton(R.string.accept, (dialogInterface, i) -> {
                    cancelSelected = (ArrayList<Boolean>) selectedElements.clone();
                    tools.saveResources(selectedFile , selectedElements);
                    updateTextView();
                })
                .setNegativeButton(R.string.cancel, (dialogInterface, i) -> {
                    selectedElements = (ArrayList<Boolean>) cancelSelected.clone();
                    tools.saveResources(selectedFile , selectedElements);
                });
        return builder.create();
    }

    //metodo que cambia el tamaño
    private void updateElements(){
        elements = tools.readFileLines(file);
    }

    //metodo que en caso de que se añadan campos amplia el tamaño de la arrayList
    private void updateSelected(){
        if(selectedElements.isEmpty()){
            for(int i = 0 ; i < elements.length ; i++){
                selectedElements.add(false);
            }
        } else {
            if(elements.length > selectedElements.size()){
                for(int i = selectedElements.size() ; i < elements.length ; i++ ){
                    selectedElements.add(false);
                }
            }
        }

        tools.saveResources(selectedFile , selectedElements);
        cancelSelected = (ArrayList<Boolean>) selectedElements.clone();
    }

    //metodo que actualiza los datos que se visualizan en el textView
    public void updateTextView(){
        textView.setText(tools.formatText(elements , formatSelected()));
    }

    private boolean[] formatSelected(){
        boolean[] selected = new boolean[selectedElements.size()];
        for(int i = 0 ; i < selected.length ; i++){
            selected[i] = selectedElements.get(i);
        }
        return selected;
    }
}