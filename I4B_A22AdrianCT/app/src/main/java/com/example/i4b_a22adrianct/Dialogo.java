package com.example.i4b_a22adrianct;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class Dialogo extends DialogFragment {
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog =  ((MainActivity)getContext()).createDialog();
        return dialog;
    }

    @Override
    public void onDetach() {
        ((MainActivity)getContext()).actualizarTextView();
        super.onDetach();
    }

    public static final String TAG = "MultipleItemDialogFragment";

}