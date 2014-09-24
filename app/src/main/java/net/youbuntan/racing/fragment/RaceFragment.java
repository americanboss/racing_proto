package net.youbuntan.racing.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.youbuntan.racing.R;
import net.youbuntan.racing.adapter.RaceListAdapter;
import net.youbuntan.racing.adapter.RaceMemberListAdapter;
import net.youbuntan.racing.logic.AssetsLogic;
import net.youbuntan.racing.model.Race;
import net.youbuntan.racing.model.RaceData;
import net.youbuntan.racing.model.RaceList;

import java.lang.reflect.Type;

/**
 *
 */
public class RaceFragment extends Fragment {

    private static final String KEY_RACE = "KEY_RACE";
    private static final String KEY_RACE_CODE = "KEY_RACE_CODE";
    private static final String KEY_SCHEDULE_CODE = "KEY_SCHEDULE_CODE";

    private Race mRace;
    private ListView mMemberList;

    public static RaceFragment load(Race race) {
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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_race_viewer, null);

        String raceJson = AssetsLogic.getStringAsset(getActivity(), "race/"+mRace.getScheduleCode()+"/race.static." + mRace.getCode() + ".json");
        Gson gson = new Gson();
        Type listType = new TypeToken<RaceData>() { }. getType();
        RaceData raceData = gson.fromJson(raceJson, listType);

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

        mMemberList = (ListView) view.findViewById(R.id.list_race_member);

        RaceMemberListAdapter adapter = new RaceMemberListAdapter(getActivity(), raceData);

        mMemberList.setAdapter(adapter);



        return view;
    }
}
