package com.example.i3c_a22adrianct;

import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends FragmentActivity {

    private DialogoConMensaxe dialogoConMensaxe;
    private DialogoCon3Botons dialogoCon3Botons;
    private DialogoConListaSeleccion dialogoConListaSeleccion;
    private DialogoConUnhaSeleccion dialogoConUnhaSeleccion;
    private DialogoSeleccionMultiple dialogoSeleccionMultiple;
    private DialogoConEntradaTexto dialogoConEntradaTexto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dialogoConMensaxe = new DialogoConMensaxe();
        dialogoCon3Botons = new DialogoCon3Botons();
        dialogoConListaSeleccion = new DialogoConListaSeleccion();
        dialogoConUnhaSeleccion = new DialogoConUnhaSeleccion();
        dialogoSeleccionMultiple = new DialogoSeleccionMultiple();
        dialogoConEntradaTexto = new DialogoConEntradaTexto();
    }

    public void onBotonClick(View view) {
        FragmentManager fm = getSupportFragmentManager();

        switch (view.getId()) {
            case R.id.btn_dialogo:
                dialogoConMensaxe.show(fm, "ATENCIÓN");
                break;
            case R.id.btn_diag_tres_botons:
                dialogoCon3Botons.show(fm, "dialogoTresBotons");
                break;
            case R.id.btn_diag_list_selecc:
                dialogoConListaSeleccion.show(fm, "dialogoListaSelec");
                break;
            case R.id.btn_diag_radio_button:
                dialogoConUnhaSeleccion.show(fm, "dialogoUnhaSeleccion");
                break;
            case R.id.btn_diag_checkbox:
                dialogoSeleccionMultiple.show(fm, "dialogoSeleccionMultiple");
                break;
            case R.id.btn_diag_entrada_texto:
                dialogoConEntradaTexto.show(fm, "dialogoConEntradaTexto");
                break;
        }
    }
}