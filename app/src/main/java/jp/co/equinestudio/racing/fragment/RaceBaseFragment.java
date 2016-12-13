package jp.co.equinestudio.racing.fragment;

import android.content.Context;

import jp.co.equinestudio.racing.RaceViewer;
import jp.co.equinestudio.racing.model.Race;
import jp.co.equinestudio.racing.model.RaceData;

/**
 *
 */
public class RaceBaseFragment extends BaseFragment implements RaceViewer{

    protected Race mRace;
    protected RaceData mRaceData;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (getParentFragment() != null && getParentFragment() instanceof RaceViewer) {
            mRace = ((RaceBaseFragment) getParentFragment()).getRace();
            mRaceData = ((RaceBaseFragment) getParentFragment()).getRaceData();
        }
    }


    @Override
    public Race getRace() {
        return mRace;
    }

    @Override
    public RaceData getRaceData() {
        return mRaceData;
    }
}
