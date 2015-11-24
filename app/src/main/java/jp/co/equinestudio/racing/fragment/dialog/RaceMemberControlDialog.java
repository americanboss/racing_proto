package jp.co.equinestudio.racing.fragment.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import jp.co.equinestudio.racing.R;
import jp.co.equinestudio.racing.model.RaceMember;

/**
 *
 */
public class RaceMemberControlDialog extends DialogFragment {

    public static final String KEY_POSITION = "KEY_POSITION";
    public static final String KEY_RACE_MEMBER = "KEY_RACE_MEMBER";
    public static final String KEY_FAVORITE_HORSE = "KEY_FAVORITE_HORSE";

    private LinearLayout mControlFavoriteHorse;


    private int mPosition;
    private RaceMember mRaceMember;

    private OnRaceMemberControl mOnRaceMemberControl;

    public static RaceMemberControlDialog newInstance(final int position, final RaceMember raceMember, final boolean isFavoriteHorse) {
        RaceMemberControlDialog dialog = new RaceMemberControlDialog();

        Bundle args = new Bundle();
        args.putInt(KEY_POSITION, position);
        args.putSerializable(RaceMemberControlDialog.KEY_RACE_MEMBER, raceMember);
        args.putBoolean(KEY_FAVORITE_HORSE, isFavoriteHorse);

        dialog.setArguments(args);

        return dialog;

    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        if (getActivity() instanceof OnRaceMemberControl) {
            mOnRaceMemberControl = (OnRaceMemberControl) getActivity();
        } else if (getParentFragment() instanceof  OnRaceMemberControl) {
            mOnRaceMemberControl = (OnRaceMemberControl) getParentFragment();
        } else if (getTargetFragment() instanceof  OnRaceMemberControl) {
            mOnRaceMemberControl = (OnRaceMemberControl) getTargetFragment();
        }

        LayoutInflater inflater = LayoutInflater.from(getActivity().getApplicationContext());
        View view = inflater.inflate(R.layout.dialog_race_member, null);

        mControlFavoriteHorse = (LinearLayout) view.findViewById(R.id.control_favorite_horse);
        mControlFavoriteHorse.setOnClickListener(mControlFavoriteHorseListener);

        Bundle args = getArguments();
        mPosition = args.getInt(KEY_POSITION);
        mRaceMember = (RaceMember) args.getSerializable(KEY_RACE_MEMBER);

        Dialog dialog  = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        ((TextView) view.findViewById(R.id.text_horse_name)).setText(mRaceMember.getHorseName());
        String controlFavoriteHorse = getResources().getString(args.getBoolean(KEY_FAVORITE_HORSE) ? R.string.favorite_hose_remove : R.string.favorite_hose_add);
        ((TextView) view.findViewById(R.id.control_favorite_horse_text)).setText(controlFavoriteHorse);

        dialog.setContentView(view);

        return dialog;
    }

    public interface OnRaceMemberControl {
        void switchFavoriteHorse(final int position);
    }

    private View.OnClickListener mControlFavoriteHorseListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (mOnRaceMemberControl != null) {
                mOnRaceMemberControl.switchFavoriteHorse(mPosition);
            }
            dismiss();
        }
    };

}
