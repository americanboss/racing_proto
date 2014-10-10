package net.youbuntan.racing.fragment.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;

import net.youbuntan.racing.R;

/**
 *
 */
public class RaceMemberControlDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {

        LayoutInflater inflater = LayoutInflater.from(getActivity().getApplicationContext());
        View view = inflater.inflate(R.layout.list_race_member, null);

        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(view);

        return dialog;
    }
}
