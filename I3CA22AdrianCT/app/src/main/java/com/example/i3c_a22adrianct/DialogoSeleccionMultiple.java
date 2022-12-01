package com.example.i3c_a22adrianct;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;

public class DialogoSeleccionMultiple extends DialogFragment {

    boolean[] matrizBooleanos= new boolean[] { false, true, false, true, false, false, false };
    boolean[] matrizParaCancelar=new boolean[] { false, true, false, true, false, false, false };
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder venta = new AlertDialog.Builder(getActivity());
        venta.setIcon(android.R.drawable.ic_dialog_info);
        venta.setTitle("Selecciona modos de transporte");
        Resources res = getResources();
        final String[] matriz = res.getStringArray(R.array.elementos_dialog_seleccion2);
        venta.setMultiChoiceItems(matriz, matrizBooleanos, new DialogInterface.OnMultiChoiceClickListener() {
            public void onClick(DialogInterface dialog, int opcion, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(getActivity().getApplicationContext(), "Seleccionaches " + matriz[opcion], Toast.LENGTH_SHORT).show();
                    matrizBooleanos[opcion] = true;
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Deseleccionaches " + matriz[opcion], Toast.LENGTH_SHORT).show();
                    matrizBooleanos[opcion] = false;
                }
            }
        });

        venta.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int boton) {
                ArrayList<String> opcionesSelec=new ArrayList<>();

                for(int j = 0; j < matrizBooleanos.length ; j++) {
                    if(matrizBooleanos[j]){
                        opcionesSelec.add(matriz[j]);
                    }
                }

                StringBuilder items = new StringBuilder();
                for(String str : opcionesSelec){
                    items.append(str).append(" ");
                }

                matrizParaCancelar = matrizBooleanos.clone();
                if(opcionesSelec.size()==0)
                    Toast.makeText(getContext(),"Non seleccionaches ningún elemento",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getContext(),"Seleccionaches : "+ items,Toast.LENGTH_SHORT).show();
            }
        });

        venta.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int boton) {
                Toast.makeText(getActivity().getApplicationContext(), R.string.premer_cancelar, Toast.LENGTH_LONG).show();
                matrizBooleanos=matrizParaCancelar.clone();
            }
        });
        return venta.create();
    }
}