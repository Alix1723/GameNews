package com.student.alix.gamenews;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.student.alix.gamenews.R;

public class AboutFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inf = getActivity().getLayoutInflater();
        builder.setView(inf.inflate(R.layout.fragment_about,null));

        builder.setTitle("About")
                .setMessage("GameNews by Alix\nLiterally the best app ever created.")
                .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //Close the dialog.
                            }
                        }
                );

        // Create the AlertDialog object and return it
        return builder.create();
    }

}
