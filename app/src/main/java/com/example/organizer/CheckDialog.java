package com.example.organizer;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class CheckDialog extends AppCompatDialogFragment {

  private checkDialoglistenet checklistner;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setTitle("Wykryto konflikt między wydarzeniami")
                .setMessage("Wydarzenie które chcesz utworzyć zachodzi na inne wydarzenie. Czy chcesz kontynuować?")
                .setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        checklistner.onYesclciked();
                    }
                }).setNegativeButton("nie", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        return builder.create();
    }
    public interface checkDialoglistenet{
        void onYesclciked();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            checklistner= (checkDialoglistenet) context;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(context.toString()+"chceck dialog listener");
        }

    }
}
