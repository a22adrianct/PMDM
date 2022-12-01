package com.example.i3c_a22adrianct;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

public class DialogoConEntradaTexto extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getActivity().getApplicationContext().getSystemService(infService);
        View inflador = li.inflate(R.layout.dialogo_entrada_texto, null);
        final TextView etNome = (TextView) inflador.findViewById(R.id.et_nome);
        final TextView etContrasinal = (TextView) inflador.findViewById(R.id.et_contrasinal);

        AlertDialog.Builder venta = new AlertDialog.Builder(getActivity());
        venta.setTitle("Indica usuario e contrasinal");
        venta.setView(inflador);
        venta.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int boton) {
                Toast.makeText(getActivity().getApplicationContext(), "Escribiches nome: '" + etNome.getText().toString() + "'. Contrasinal: '" + etContrasinal.getText().toString() + "' e premeches 'Aceptar'",
                        Toast.LENGTH_LONG).show();
            }
        });
        venta.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int boton) {
                Toast.makeText(getActivity().getApplicationContext(), R.string.premer_cancelar, Toast.LENGTH_LONG).show();
            }
        });
        return venta.create();

    }
}
