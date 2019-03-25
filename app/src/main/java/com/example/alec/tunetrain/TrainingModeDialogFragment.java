package com.example.alec.tunetrain;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class TrainingModeDialogFragment extends DialogFragment {

    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    public interface TrainingDialogListener {
        public void onDialogSpotifyClick(DialogFragment dialog);
        public void onDialogNoteClick(DialogFragment dialog);
    }

    TrainingDialogListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the TrainingModeDialogListener so we can send events to the host
            listener = (TrainingDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " must implement TrainingDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.mode_alert)
                .setPositiveButton(R.string.spotify_mode, (dialog, id) -> listener.onDialogSpotifyClick(TrainingModeDialogFragment.this))
                .setNegativeButton(R.string.note_mode, (dialog, id) -> listener.onDialogNoteClick(TrainingModeDialogFragment.this));
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
