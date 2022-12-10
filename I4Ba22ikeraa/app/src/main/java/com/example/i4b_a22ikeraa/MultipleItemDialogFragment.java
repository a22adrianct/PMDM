package com.example.i4b_a22ikeraa;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class MultipleItemDialogFragment extends DialogFragment {
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog =  ((I4B_a22ikeraa)getContext()).createDialog();
        return dialog;
    }

    @Override
    public void onDetach() {
        ((I4B_a22ikeraa)getContext()).updateTextView();
        super.onDetach();
    }

    public static final String TAG = "MultipleItemDialogFragment";

}