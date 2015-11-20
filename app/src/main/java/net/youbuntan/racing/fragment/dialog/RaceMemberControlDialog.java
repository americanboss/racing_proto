package net.youbuntan.racing.fragment.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import net.youbuntan.racing.R;
import net.youbuntan.racing.util.FavoriteHorseUtil;

/**
 *
 */
public class RaceMemberControlDialog extends DialogFragment {

    public static final String KEY_TITLE = "KEY_TITLE";

    private LinearLayout mControlFavoriteHorse;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getActivity().getApplicationContext());
        View view = inflater.inflate(R.layout.dialog_race_member, null);

        mControlFavoriteHorse = (LinearLayout) view.findViewById(R.id.control_favorite_horse);
        mControlFavoriteHorse.setOnClickListener(mControlFavoriteHorseListener);


        Bundle args = getArguments();

        Dialog dialog  = new Dialog(getActivity());
        dialog.setTitle(args.getString(KEY_TITLE));

        dialog.setContentView(view);

        return dialog;
    }

    private View.OnClickListener mControlFavoriteHorseListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (FavoriteHorseUtil.isFavorite()) {
                FavoriteHorseUtil.remove();
                Toast.makeText(getActivity(), "お気に入りから削除しました", Toast.LENGTH_SHORT).show();
            } else {
                FavoriteHorseUtil.add();
                Toast.makeText(getActivity(), "お気に入りに登録しました", Toast.LENGTH_SHORT).show();
            }
        }
    };

}
