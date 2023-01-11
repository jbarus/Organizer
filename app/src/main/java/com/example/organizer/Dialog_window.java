package com.example.organizer;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class Dialog_window extends AppCompatDialogFragment {
    private EditText editText;
    private Dialog_windowListener listener;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder =new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog,null);
        editText=view.findViewById(R.id.editTextNumber);
        builder.setView(view).setTitle("Ustaw ilość powtórzeń").setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


            }

        }).setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String repetition=editText.getText().toString();
                listener.applyText(repetition);
            }
        });

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener= (Dialog_windowListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()+"Must implement exampleDialogListner");
        }
    }

    public interface Dialog_windowListener
    {
        void applyText(String repetition);


    }
}
