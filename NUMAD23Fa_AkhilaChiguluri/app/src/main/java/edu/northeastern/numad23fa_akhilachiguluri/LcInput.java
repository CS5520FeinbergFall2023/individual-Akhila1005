package edu.northeastern.numad23fa_akhilachiguluri;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;



public class LcInput extends DialogFragment {
    private LcActivity activity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (LcActivity) getActivity();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_lc_input, null);
        EditText linkNameInput = view.findViewById(R.id.link_name_input);
        EditText linkUrlInput = view.findViewById(R.id.link_url_input);

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setView(view)
                .setCancelable(false)
                .setPositiveButton(getString(R.string.Add), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String name = linkNameInput.getText().toString();
                        String url = linkUrlInput.getText().toString();
                        LcItem linkCollectorUnit = new LcItem(name, url);
                        if (linkCollectorUnit.isValid()) {
                            activity.addLink(linkCollectorUnit);
                        } else {
                            activity.showSnackbar(getString(R.string.link_invalid));
                        }
                    }
                })
                .setNegativeButton(getString(R.string.Cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        return builder.create();
    }

}
