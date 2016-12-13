package jp.co.equinestudio.racing.fragment.race;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import jp.co.equinestudio.racing.R;
import jp.co.equinestudio.racing.adapter.OddsTitleListAdapter;
import jp.co.equinestudio.racing.fragment.BaseFragment;
import jp.co.equinestudio.racing.fragment.RaceBaseFragment;
import jp.co.equinestudio.racing.fragment.RaceFragment;
import jp.co.equinestudio.racing.logic.AssetsLogic;
import jp.co.equinestudio.racing.model.Odds;
import jp.co.equinestudio.racing.model.Race;
import jp.co.equinestudio.racing.model.RaceData;

/**
 *
 */
public class RaceOddsFragment extends RaceBaseFragment implements OddsTitleListAdapter.OnOddsListOpen {

    private Odds mOdds;

    private ListView mListView;
    private OddsTitleListAdapter mAdapter;

    public static RaceOddsFragment newInstance() {
        RaceOddsFragment fragment = new RaceOddsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_race_odds, null);

        mListView = (ListView) view.findViewById(R.id.list_view);
        mAdapter = new OddsTitleListAdapter(getContext(), this);

        String raceJson = AssetsLogic.getStringAsset(getActivity(), "odds/odds.static." + mRace.getCode() + ".json");
        Gson gson = new Gson();
        Type listType = new TypeToken<Odds>() { }. getType();
        mOdds = gson.fromJson(raceJson, listType);

        mAdapter.setOdds(mOdds);
        mListView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onOddsListOpen(final int position, final int orderBy) {
        if (getParentFragment() instanceof OddsTitleListAdapter.OnOddsListOpen) {
            ((OddsTitleListAdapter.OnOddsListOpen) getParentFragment()).onOddsListOpen(position, orderBy);
        }
    }
}
