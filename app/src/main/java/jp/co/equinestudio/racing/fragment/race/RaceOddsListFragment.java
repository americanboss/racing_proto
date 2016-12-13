package jp.co.equinestudio.racing.fragment.race;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import jp.co.equinestudio.racing.R;
import jp.co.equinestudio.racing.adapter.OddsListAdapter;
import jp.co.equinestudio.racing.adapter.OddsTitleListAdapter;
import jp.co.equinestudio.racing.adapter.item.OddsListItem;
import jp.co.equinestudio.racing.fragment.RaceBaseFragment;
import jp.co.equinestudio.racing.logic.AssetsLogic;
import jp.co.equinestudio.racing.model.Odds;
import jp.co.equinestudio.racing.model.RaceMember;
import jp.co.equinestudio.racing.util.OddsUtils;
import jp.co.equinestudio.racing.util.RaceMemberUtils;
import jp.co.equinestudio.racing.util.StringUtils;
import jp.co.equinestudio.racing.util.comparator.NumberStringComparator;
import jp.co.equinestudio.racing.util.comparator.OddsItemListComparator;
import jp.co.equinestudio.racing.util.comparator.WinShowOddsListComparator;

/**
 *
 */
public class RaceOddsListFragment extends RaceBaseFragment implements OddsTitleListAdapter.OnOddsListOpen, OddsListAdapter.OnCheckChanged {

    private static final String KEY_POSITION = "KEY_POSITION";
    private static final String KEY_ORDER_BY = "KEY_ORDER_BY";

    private Odds mOdds;

    private int mOddsPosition;
    private int mOrderBy;

    private LinearLayout mNagashiSelectorContainer;
    private Button mButtonShowOdds;
    private Button mButtonAddBetList;
    private Spinner mNagashiSelector;
    private ListView mListView;
    private ListView mMultipleOddsListView;
    private OddsListAdapter mAdapter;
    private OddsListAdapter mMultipleOddsAdapter;

    private String mCheckedSingle1;
    private String mCheckedSingle2;
    private List<String> mCheckedMultiple1;
    private List<String> mCheckedMultiple2;
    private List<String> mCheckedMultiple3;

    private boolean mOddsVisible;
    private boolean[] mBracketMake; // 枠連の各枠表示を実施したかどうかのフラグ


    public static RaceOddsListFragment newInstance(final int position, final int orderBy) {
        RaceOddsListFragment fragment = new RaceOddsListFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_POSITION, position);
        args.putInt(KEY_ORDER_BY, orderBy);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();

        mOddsPosition = args.getInt(KEY_POSITION);
        mOrderBy = args.getInt(KEY_ORDER_BY);

        mCheckedSingle1 = null;
        mCheckedSingle2 = null;
        mCheckedMultiple1 = new ArrayList<>();
        mCheckedMultiple2 = new ArrayList<>();
        mCheckedMultiple3 = new ArrayList<>();


        mOddsVisible = false;
        mBracketMake = new boolean[]{false, false, false, false, false, false, false, false};
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_race_odds_list, null);
        mListView = (ListView) view.findViewById(R.id.list_view);
        mMultipleOddsListView = (ListView) view.findViewById(R.id.multiple_odds_list_view);
        mNagashiSelector = (Spinner) view.findViewById(R.id.nagashi_selector);
        mNagashiSelectorContainer = (LinearLayout) view.findViewById(R.id.heading);
        mButtonShowOdds = (Button) view.findViewById(R.id.show_odds_button);
        mButtonAddBetList = (Button) view.findViewById(R.id.add_bet_list_button);

        String raceJson = AssetsLogic.getStringAsset(getActivity(), "odds/odds.static." + mRace.getCode() + ".json");
        Gson gson = new Gson();
        Type listType = new TypeToken<Odds>() { }. getType();
        mOdds = gson.fromJson(raceJson, listType);

        mAdapter = new OddsListAdapter(getContext(), mOrderBy, this);
        mAdapter.setOddsPattern(mOddsPosition);
        mMultipleOddsAdapter = new OddsListAdapter(getContext(), mOrderBy, this);
        mMultipleOddsAdapter.setOddsPattern(OddsUtils.Table.ODDS_MULTIPLE);

        List<OddsListItem> oddsItemList = new ArrayList<>();;
        if (mOddsPosition == OddsUtils.Table.WIN) {
            oddsItemList = buildWinShowOdds();
        } else {
            oddsItemList = buildOdds();
        }

        // 流しの形式を選択するSpinnerの設定
        String[] nagashiSelection = null;
        if (mOddsPosition == OddsUtils.Table.EXACTA) {
            nagashiSelection = getResources().getStringArray(R.array.nagashi_list);
        } else if (mOddsPosition == OddsUtils.Table.TRIFECTA) {
            if (mOrderBy == OddsUtils.Table.PATTERN_NAGASHI_J1) {
                nagashiSelection = getResources().getStringArray(R.array.nagashi_j1_list);
            } else if (mOrderBy == OddsUtils.Table.PATTERN_NAGASHI_J2) {
                nagashiSelection = getResources().getStringArray(R.array.nagashi_j2_list);
            }
        }
        if (nagashiSelection != null) {
            ArrayAdapter<String> nagashiAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, nagashiSelection);
            nagashiAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mNagashiSelector.setAdapter(nagashiAdapter);

        } else {
            mNagashiSelector.setVisibility(View.GONE);
        }
        mAdapter.setOddsItemList(oddsItemList);

        // オッズ表示ボタンの設定
        if (mOddsPosition == OddsUtils.Table.WIN || !OddsUtils.isMultipleSelect(mOrderBy)) {
            mButtonShowOdds.setVisibility(View.GONE);
        } else {
            mButtonShowOdds.setOnClickListener(mButtonShowOddsClickListener);
        }


        mListView.setAdapter(mAdapter);
        mMultipleOddsListView.setAdapter(mMultipleOddsAdapter);

        return view;
    }

    private List<OddsListItem> buildWinShowOdds() {
        List<OddsListItem> oddsItemList = new ArrayList<>();
        // 単勝・複勝のオッズを構築する
        if (mOdds.getWinShowOddsMap() != null) {
            mAdapter.setOddsPattern(OddsUtils.Table.WIN);
            int order = 1;

            List<Odds.WinShowOdds> winShowOddsList = new ArrayList<>();
            for (String key : mOdds.getWinShowOddsMap().keySet()) {
                winShowOddsList.add(mOdds.getWinShowOddsMap().get(key));
            }
            Collections.sort(winShowOddsList, new WinShowOddsListComparator(mOrderBy));

            for (Odds.WinShowOdds winShowOdds : winShowOddsList) {
                RaceMember raceMember = RaceMemberUtils.getHorseName(mRaceData.getRaceMembers(), Integer.parseInt(winShowOdds.getGateNumber()));

                OddsListItem item = new OddsListItem();
                item.setNumber(winShowOdds.getGateNumber());
                item.setHorseName(raceMember.getHorseName());
                item.setBracketNumber(raceMember.getBracketNumber());
                item.setHeadingAdditonal(Integer.toString(order++) + "人気");
                item.setOdds(winShowOdds.getWinOdds());
                item.setOddsAdditional(winShowOdds.getShowOddsLow() + " - " + winShowOdds.getShowOddsHigh());
                oddsItemList.add(item);
            }
        }
        return oddsItemList;
    }

    private List<OddsListItem> buildOdds() {
        List<OddsListItem> oddsItemList = new ArrayList<>();
        //オッズを構築する
        if (OddsUtils.isMultipleSelect(mOrderBy)) {
            for (RaceMember raceMember : mRaceData.getRaceMembers()) {
                int bracketPosition = Integer.parseInt(raceMember.getBracketNumber()) - 1;

                OddsListItem item = new OddsListItem();
                item.setNumber(Integer.toString(raceMember.getGateNumber()));
                item.setHorseName(raceMember.getHorseName());
                item.setBracketNumber(raceMember.getBracketNumber());
                item.setBracketShow((!mBracketMake[bracketPosition]));
                oddsItemList.add(item);

                mBracketMake[bracketPosition] = true; // 枠番表示の更新

            }
        } else {
            mAdapter.setOddsPattern(mOddsPosition);
            switch (mOddsPosition) {
                case OddsUtils.Table.BRACKET:
                    oddsItemList = getOrderedItemList(mOdds.getBracketMap(), 1);
                    break;
                case OddsUtils.Table.QUINELLA:
                    oddsItemList = getOrderedItemList(mOdds.getQuinellaMap(), 2);
                    break;
                case OddsUtils.Table.WIDE:
                    oddsItemList = getOrderedItemList(mOdds.getWideMap(), 2);
                    break;
                case OddsUtils.Table.EXACTA:
                    oddsItemList = getOrderedItemList(mOdds.getExactaMap(), 2);
                    break;
                case OddsUtils.Table.TRIO:
                    oddsItemList = getOrderedItemList(mOdds.getTrioMap(), 2);
                    break;
                case OddsUtils.Table.TRIFECTA:
                    oddsItemList = getOrderedItemList(mOdds.getTrifectaMap(), 2);
                    break;
            }
        }
        return oddsItemList;
    }

    public List<OddsListItem> getOrderedItemList(final Map<String, String> map, final int chunkLength) {
        List<OddsListItem> oddsItemList = new ArrayList<>();
        for (String key : map.keySet()) {
            // key の加工の実施
            String number = StringUtils.getChunkNumbers(key, chunkLength);

            OddsListItem item = new OddsListItem();
            item.setNumber(number);
            item.setOdds(map.get(key));
            item.setBracketShow(true);
            oddsItemList.add(item);
        }

        if (mOrderBy == OddsUtils.Table.ORDER_BY_ODDS) {
            Collections.sort(oddsItemList, new OddsItemListComparator(mOrderBy));

        }

        return oddsItemList;

    }

    @Override
    public void onOddsListOpen(final int position, final int orderBy) {
        if (getParentFragment() instanceof OddsTitleListAdapter.OnOddsListOpen) {
            ((OddsTitleListAdapter.OnOddsListOpen) getParentFragment()).onOddsListOpen(position, orderBy);
        }
    }

    /**
     * オッズ表示ボタンの動作を規定
     */
    private View.OnClickListener mButtonShowOddsClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (mOddsVisible) {
                // 付加オッズの表示を消す
                mOddsVisible = false;
                mButtonShowOdds.setText(getResources().getString(R.string.odds_show));
                mListView.setVisibility(View.VISIBLE);
                mMultipleOddsListView.setVisibility(View.GONE);
            } else {
                // オッズの構築を行う
                List<OddsListItem> oddsList = new ArrayList<>();
                Map<String, String> map = null;
                List<String> targetNumbersList = new ArrayList<>();
                if (mOrderBy == OddsUtils.Table.PATTERN_BOX) {
                    switch (mOddsPosition) {
                        case OddsUtils.Table.QUINELLA:
                        case OddsUtils.Table.WIDE:
                            targetNumbersList = OddsUtils.getBoxKeys(mCheckedMultiple1, true, false, false);
                            break;
                        case OddsUtils.Table.BRACKET:
                            targetNumbersList = OddsUtils.getBoxKeys(mCheckedMultiple1, true, false, true);
                            break;
                        case OddsUtils.Table.EXACTA:
                            targetNumbersList = OddsUtils.getBoxKeys(mCheckedMultiple1, false, false, false);
                            break;
                        case OddsUtils.Table.TRIO:
                            targetNumbersList = OddsUtils.getBoxKeys(mCheckedMultiple1, true, true, false);
                            break;
                        case OddsUtils.Table.TRIFECTA:
                            targetNumbersList = OddsUtils.getBoxKeys(mCheckedMultiple1, false, true, false);
                            break;
                    }
                } else if (mOrderBy == OddsUtils.Table.PATTERN_FORMATION) {
                    switch (mOddsPosition) {
                        case OddsUtils.Table.QUINELLA:
                        case OddsUtils.Table.WIDE:
                            targetNumbersList = OddsUtils.getFormationKeys(mCheckedMultiple1, mCheckedMultiple2, mCheckedMultiple3, true, false, false);
                            break;
                        case OddsUtils.Table.BRACKET:
                            targetNumbersList = OddsUtils.getFormationKeys(mCheckedMultiple1, mCheckedMultiple2, mCheckedMultiple3, true, false, true);
                            break;
                        case OddsUtils.Table.EXACTA:
                            targetNumbersList = OddsUtils.getFormationKeys(mCheckedMultiple1, mCheckedMultiple2, mCheckedMultiple3, false, false, false);
                            break;
                        case OddsUtils.Table.TRIO:
                            targetNumbersList = OddsUtils.getFormationKeys(mCheckedMultiple1, mCheckedMultiple2, mCheckedMultiple3, true, true, false);
                            break;
                        case OddsUtils.Table.TRIFECTA:
                            targetNumbersList = OddsUtils.getFormationKeys(mCheckedMultiple1, mCheckedMultiple2, mCheckedMultiple3, false, true, false);
                            break;
                    }
                } else if (mOrderBy == OddsUtils.Table.PATTERN_NAGASHI) {
                    switch (mOddsPosition) {
                        case OddsUtils.Table.QUINELLA:
                        case OddsUtils.Table.WIDE:
                            targetNumbersList = OddsUtils.getNagashiKeys(mCheckedSingle1, mCheckedMultiple1, mNagashiSelector.getSelectedItemPosition(), true, false);
                            break;
                        case OddsUtils.Table.BRACKET:
                            targetNumbersList = OddsUtils.getNagashiKeys(mCheckedSingle1, mCheckedMultiple1, mNagashiSelector.getSelectedItemPosition(), true, true);
                            break;
                        case OddsUtils.Table.EXACTA:
                            targetNumbersList = OddsUtils.getNagashiKeys(mCheckedSingle1, mCheckedMultiple1, mNagashiSelector.getSelectedItemPosition(), false, false);
                            break;
                    }
                } else if (mOrderBy == OddsUtils.Table.PATTERN_NAGASHI_J1) {
                    switch (mOddsPosition) {
                        case OddsUtils.Table.TRIO:
                            targetNumbersList = OddsUtils.getNagashiJ1Keys(mCheckedSingle1, mCheckedMultiple1, mCheckedMultiple2, 0, true);
                            break;
                        case OddsUtils.Table.TRIFECTA:
                            targetNumbersList = OddsUtils.getNagashiJ1Keys(mCheckedSingle1, mCheckedMultiple1, mCheckedMultiple2, mNagashiSelector.getSelectedItemPosition(), false);
                            break;
                    }
                } else if (mOrderBy == OddsUtils.Table.PATTERN_NAGASHI_J2) {
                    switch (mOddsPosition) {
                        case OddsUtils.Table.TRIO:
                            targetNumbersList = OddsUtils.getNagashiJ2Keys(mCheckedSingle1, mCheckedSingle2, mCheckedMultiple1, 0, true);
                            break;
                        case OddsUtils.Table.TRIFECTA:
                            targetNumbersList = OddsUtils.getNagashiJ2Keys(mCheckedSingle1, mCheckedSingle2, mCheckedMultiple1, mNagashiSelector.getSelectedItemPosition(), false);
                            break;
                    }
                }

                switch (mOddsPosition) {
                    case OddsUtils.Table.BRACKET:
                        map = mOdds.getBracketMap();
                        break;
                    case OddsUtils.Table.QUINELLA:
                        map = mOdds.getQuinellaMap();
                        break;
                    case OddsUtils.Table.WIDE:
                        map = mOdds.getWideMap();
                        break;
                    case OddsUtils.Table.EXACTA:
                        map = mOdds.getExactaMap();
                        break;
                    case OddsUtils.Table.TRIO:
                        map = mOdds.getTrioMap();
                        break;
                    case OddsUtils.Table.TRIFECTA:
                        map = mOdds.getTrifectaMap();
                        break;
                }
                for (String targetNumbers : targetNumbersList) {
                    if (map.containsKey(targetNumbers)) {
                        OddsListItem oddsListItem = new OddsListItem();
                        oddsListItem.setNumber(StringUtils.getChunkNumbers(targetNumbers, (mOddsPosition == OddsUtils.Table.BRACKET) ? 1 : 2));
                        oddsListItem.setOdds(map.get(targetNumbers));
                        oddsList.add(oddsListItem);
                    }
                }
                mMultipleOddsAdapter.setOddsItemList(oddsList);
                if (oddsList.size() > 0) {
                    mOddsVisible = true;
                    mButtonShowOdds.setText(getResources().getString(R.string.odds_hide));
                    mListView.setVisibility(View.GONE);
                    mMultipleOddsListView.setVisibility(View.VISIBLE);
                }
            }
        }
    };

    @Override
    public void onSingleCheckChanged(final int position, final boolean checked, final String number) {
        if (checked) {
            if (position == 0) {
                mCheckedSingle1 = number;
            } else {
                mCheckedSingle2 = number;
            }
        }
    }

    @Override
    public void onMultipleCheckChanged(final int position, final boolean checked, final String number) {
        List<String> target;
        switch (position) {
            case 0:
                target = mCheckedMultiple1;
                break;
            case 1:
                target = mCheckedMultiple2;
                break;
            case 2:
                target = mCheckedMultiple3;
                break;
            default:
                target = mCheckedMultiple1;
                break;
        }
        if (target.contains(number)) {
            if (!checked) {
                target.remove(number);
            }
        } else {
            if (checked) {
                target.add(number);
            }
        }
        Collections.sort(target, new NumberStringComparator());
    }

    @Override
    public boolean isMultipleChecked(int position, String number) {
        List<String> target;
        switch (position) {
            case 0:
                target = mCheckedMultiple1;
                break;
            case 1:
                target = mCheckedMultiple2;
                break;
            case 2:
                target = mCheckedMultiple3;
                break;
            default:
                target = mCheckedMultiple1;
                break;
        }
        return target.contains(number);
    }

    @Override
    public boolean isSingleChecked(int position, String number) {
        if (position == 0) {
            return number.equals(mCheckedSingle1);
        } else {
            return number.equals(mCheckedSingle2);
        }
    }


}
