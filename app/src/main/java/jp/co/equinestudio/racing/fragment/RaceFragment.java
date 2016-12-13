package jp.co.equinestudio.racing.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;

import jp.co.equinestudio.racing.R;
import jp.co.equinestudio.racing.RaceViewer;
import jp.co.equinestudio.racing.adapter.OddsTitleListAdapter;
import jp.co.equinestudio.racing.fragment.race.RaceMemberFragment;
import jp.co.equinestudio.racing.fragment.race.RaceOddsFragment;
import jp.co.equinestudio.racing.fragment.race.RaceOddsListFragment;
import jp.co.equinestudio.racing.fragment.race.RaceResultFragment;
import jp.co.equinestudio.racing.logic.AssetsLogic;
import jp.co.equinestudio.racing.model.Race;
import jp.co.equinestudio.racing.model.RaceData;
import jp.co.equinestudio.racing.model.RaceMember;
import jp.co.equinestudio.racing.util.comparator.RaceMemberComparator;

/**
 *
 */
public class RaceFragment extends RaceBaseFragment implements OddsTitleListAdapter.OnOddsListOpen {

    private static final String KEY_RACE = "KEY_RACE";

    public static RaceFragment newInstance(Race race) {
        RaceFragment fragment = new RaceFragment();
        Bundle args = new Bundle();
        args.putSerializable(KEY_RACE, race);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();

        mRace = (Race) args.getSerializable(KEY_RACE);

        String raceJson = AssetsLogic.getStringAsset(getActivity(), "race/" + mRace.getScheduleCode() + "/race.static." + mRace.getCode() + ".json");
        Log.d("RaceMemberFragment", "race:" + mRace.getCode());
        Gson gson = new Gson();
        Type listType = new TypeToken<RaceData>() { }. getType();
        mRaceData = gson.fromJson(raceJson, listType);
        Collections.sort(mRaceData.getRaceMembers(), new RaceMemberComparator(RaceMemberComparator.GATE_NUMBER));


    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_race, null);

        TextView tvRaceNum = (TextView) view.findViewById(R.id.text_race_num);
        TextView tvStartTime = (TextView) view.findViewById(R.id.text_start_time);
        TextView tvRaceName = (TextView) view.findViewById(R.id.text_race_name);
        TextView tvTrack = (TextView) view.findViewById(R.id.text_track);
        TextView tvDistance = (TextView) view.findViewById(R.id.text_distance);
        TextView tvEntryNum = (TextView) view.findViewById(R.id.text_entry_num);

        tvRaceNum.setText(mRace.getRaceNum());
        tvStartTime.setText(mRace.getStartTime());
        tvRaceName.setText(mRace.getRaceName(Race.RACE_NAME_10));
        tvTrack.setText(mRace.getTrackMaster());
        tvDistance.setText(mRace.getDistance());
        tvEntryNum.setText(mRace.getEntryNum());

        // 表示スイッチ
        TextView switcherMemberList = (TextView) view.findViewById(R.id.switcher_member_list);
        TextView switcherOddsList = (TextView) view.findViewById(R.id.switcher_odds);
        TextView switcherResultList = (TextView) view.findViewById(R.id.switcher_result_list);

        replaceRaceMemberFragment();

        switcherMemberList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                replaceRaceMemberFragment();
            }
        });

        switcherOddsList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                replaceOddsFragment();
            }
        });

        switcherResultList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                replaceResultFragment();
            }
        });

        return view;
    }

    private void replaceFragment(final Fragment fragment) {

        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.race_content, fragment);
        transaction.commit();
    }

    private void replaceRaceMemberFragment() {
        RaceMemberFragment fragment = RaceMemberFragment.newInstance();
        replaceFragment(fragment);
    }

    private void replaceOddsFragment() {
        RaceOddsFragment fragment = RaceOddsFragment.newInstance();
        replaceFragment(fragment);
    }

    private void replaceResultFragment() {
        RaceResultFragment fragment = RaceResultFragment.newInstance();
        replaceFragment(fragment);
    }

    @Override
    public void onOddsListOpen(final int position, final int orderBy) {
        RaceOddsListFragment fragment = RaceOddsListFragment.newInstance(position, orderBy);
        replaceFragment(fragment);
    }
}
