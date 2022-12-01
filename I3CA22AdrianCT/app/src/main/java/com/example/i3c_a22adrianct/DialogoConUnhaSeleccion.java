package com.example.i3c_a22adrianct;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

public class DialogoConUnhaSeleccion extends DialogFragment {

    CharSequence[] smartphones = { "iPhone", "Blackberry", "Android" };
    int posicionOpcionSeleccionada = 0;
    int posicionOpcionAnterior = 0;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setIcon(android.R.drawable.ic_dialog_info)
                .setTitle("Selecciona un smartphone")
                .setSingleChoiceItems(smartphones, posicionOpcionSeleccionada, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int posicion) {
                        posicionOpcionSeleccionada = posicion;
                    }
                })
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getContext(), "Seleccionaches : " + smartphones[posicionOpcionSeleccionada], Toast.LENGTH_SHORT).show();
                        posicionOpcionAnterior = posicionOpcionSeleccionada;
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        posicionOpcionSeleccionada = posicionOpcionAnterior;
                    }
                });
        return builder.create();
    }
}
