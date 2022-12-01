package com.example.i3c_a22adrianct;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

public class DialogoCon3Botons extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder venta = new AlertDialog.Builder(getActivity());
        venta.setIcon(android.R.drawable.ic_dialog_info);
        venta.setTitle(R.string.titulo_dialogo_tres_botons);
        venta.setMessage(R.string.mensaje_dialogo_tres_botons);
        venta.setCancelable(false);
        venta.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int boton) {
                Toast.makeText(getActivity().getApplicationContext(), R.string.pulsarSi_dialogo_3Botons, Toast.LENGTH_LONG).show();
            }
        });
        venta.setNegativeButton("Non", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int boton) {
                Toast.makeText(getActivity().getApplicationContext(), R.string.pulsarNon_dialogo_3Botons, Toast.LENGTH_LONG).show();
            }
        });
        venta.setNeutralButton("Ás veces", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int boton) {
                Toast.makeText(getActivity().getApplicationContext(), R.string.pulsarAsVeces_dialogo_3Botons, Toast.LENGTH_LONG).show();
            }
        });
        return venta.create();
    }
}
