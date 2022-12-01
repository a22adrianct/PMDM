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

public class DialogoConListaSeleccion extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder venta = new AlertDialog.Builder(getActivity());
        venta.setIcon(android.R.drawable.ic_dialog_alert);
        venta.setTitle(R.string.titulo_dialogo_lista_seleccion);
        venta.setItems(R.array.elementos_dialog_seleccion, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int opcion) {
                String[] opcions = getResources().getStringArray(R.array.elementos_dialog_seleccion);
                Toast.makeText(getActivity().getApplicationContext(), "Seleccionaches: '" + opcions[opcion] + "'", Toast.LENGTH_LONG).show();
            }
        });
        return venta.create();
    }
}
