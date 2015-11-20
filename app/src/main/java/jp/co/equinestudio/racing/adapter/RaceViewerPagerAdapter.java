package jp.co.equinestudio.racing.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import jp.co.equinestudio.racing.fragment.RaceFragment;
import jp.co.equinestudio.racing.model.RaceList;

/**
 *
 */
public class RaceViewerPagerAdapter extends FragmentPagerAdapter {

    private RaceList mRaceList;

    public RaceViewerPagerAdapter(FragmentManager fm, RaceList raceList) {
        super(fm);
        this.mRaceList = raceList;
    }

    @Override
    public Fragment getItem(int position) {

        RaceFragment fragment = RaceFragment.load(mRaceList.getRaces().get(position));

        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mRaceList.getRaces().get(position).getRaceNum();
    }

    @Override
    public int getCount() {
        return mRaceList.getRaces().size();
    }
}
