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

    public static final String KEY_TITLE = "KEY_TITLE";

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getActivity().getApplicationContext());
        View view = inflater.inflate(R.layout.dialog_race_member, null);

        Bundle args = getArguments();

        Dialog dialog  = new Dialog(getActivity());
        dialog.setTitle(args.getString(KEY_TITLE));

        dialog.setContentView(view);

        return dialog;
    }
}
