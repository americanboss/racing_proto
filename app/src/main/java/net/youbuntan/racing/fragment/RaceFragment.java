package net.youbuntan.racing.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.youbuntan.racing.R;
import net.youbuntan.racing.adapter.RaceListAdapter;
import net.youbuntan.racing.logic.AssetsLogic;
import net.youbuntan.racing.model.Race;
import net.youbuntan.racing.model.RaceData;
import net.youbuntan.racing.model.RaceList;

import java.lang.reflect.Type;

/**
 *
 */
public class RaceFragment extends Fragment {

    private static final String KEY_RACE_CODE = "KEY_RACE_CODE";
    private static final String KEY_SCHEDULE_CODE = "KEY_SCHEDULE_CODE";

    private String mRaceCode;
    private String mScheduleCode;

    public static RaceFragment load(Race race) {
        RaceFragment fragment = new RaceFragment();
        Bundle args = new Bundle();
        args.putString(KEY_RACE_CODE, race.getCode());
        args.putString(KEY_SCHEDULE_CODE, race.getScheduleCode());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        mRaceCode = args.getString(KEY_RACE_CODE);
        mScheduleCode = args.getString(KEY_SCHEDULE_CODE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_race_viewer, null);

        String raceJson = AssetsLogic.getStringAsset(getActivity(), "race/"+mScheduleCode+"/race.static." + mRaceCode + ".json");
        Gson gson = new Gson();
        Type listType = new TypeToken<RaceData>() { }. getType();
        RaceData raceData = gson.fromJson(raceJson, listType);

        return view;
    }
}
