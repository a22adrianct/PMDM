package com.example.i3c_a22adrianct;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class DialogoConMensaxe extends DialogFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView;
        if (Build.VERSION.SDK_INT >22){
            rootView = inflater.inflate(R.layout.dialogo_con_mensaxe, container, false);
        } else{
            rootView = inflater.inflate(R.layout.dialogo_con_mensaxe_api22, container, false);
        }
        getDialog().setTitle(getTag());
        getDialog().setCanceledOnTouchOutside (false);
        getDialog().setCancelable(true);
        return rootView;
    }

}
