package jp.co.equinestudio.racing.fragment.race;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jp.co.equinestudio.racing.R;
import jp.co.equinestudio.racing.adapter.OddsListAdapter;
import jp.co.equinestudio.racing.adapter.OddsTitleListAdapter;
import jp.co.equinestudio.racing.fragment.BaseFragment;
import jp.co.equinestudio.racing.logic.AssetsLogic;
import jp.co.equinestudio.racing.model.Odds;
import jp.co.equinestudio.racing.model.Race;
import jp.co.equinestudio.racing.model.RaceData;

/**
 *
 */
public class RaceOddsListFragment extends BaseFragment implements OddsTitleListAdapter.OnOddsListOpen {

    private static final String KEY_RACE      = "KEY_RACE";
    private static final String KEY_POSITION = "KEY_POSITION";
    private static final String KEY_ORDER_BY = "KEY_ORDER_BY";

    private Race mRace;
    private RaceData mRaceData;
    private Odds mOdds;

    private int mOddsPosition;
    private int mOrderBy;

    private ListView mListView;
    private OddsListAdapter mAdapter;

    public static RaceOddsListFragment newInstance(final Race race, final int position, final int orderBy) {
        RaceOddsListFragment fragment = new RaceOddsListFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_POSITION, position);
        args.putInt(KEY_ORDER_BY, orderBy);
        args.putSerializable(KEY_RACE, race);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();

        mRace = (Race) args.getSerializable(KEY_RACE);
        mOddsPosition = args.getInt(KEY_POSITION);
        mOrderBy = args.getInt(KEY_ORDER_BY);

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_race_odds_list, null);

        String raceJson = AssetsLogic.getStringAsset(getActivity(), "odds/odds.static." + mRace.getCode() + ".json");
        Gson gson = new Gson();
        Type listType = new TypeToken<Odds>() { }. getType();
        mOdds = gson.fromJson(raceJson, listType);

        // この構成もちょっと見直したい
        mAdapter = new OddsListAdapter();

        List<OddsListAdapter.OddsItem> oddsItemList = new ArrayList<>();
        if (mOddsPosition == 0) {
            // 単勝・複勝のオッズを構築する
            if (mOdds.getWinShowOddsMap() != null) {
                int order = 1;
                for (String key : mOdds.getWinShowOddsMap().keySet()) {
                    //mOdds.getWinShowOddsMap().get(key).;
                    Odds.WinShowOdds winShowOdds = mOdds.getWinShowOddsMap().get(key);
                    winShowOdds.getGateNumber();
                    OddsListAdapter.OddsItem item = mAdapter.getOddsItem();
                    item.Number = winShowOdds.getGateNumber();
                    item.HorseName = "ああああ";
                    item.HeadingAdditonal = Integer.toString(order++);
                    item.Odds = winShowOdds.getWinOdds();
                    item.OddsAdditional = winShowOdds.getShowOddsLow();
                    item.CheckboxCount = 1;
                    oddsItemList.add(item);
                }
            }

        }

        mAdapter = new OddsListAdapter(getContext(), oddsItemList);
        mListView = (ListView) view.findViewById(R.id.list_view);


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
