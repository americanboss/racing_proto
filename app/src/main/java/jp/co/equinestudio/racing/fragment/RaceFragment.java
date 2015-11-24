package jp.co.equinestudio.racing.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import jp.co.equinestudio.racing.R;
import jp.co.equinestudio.racing.adapter.RaceMemberListAdapter;
import jp.co.equinestudio.racing.adapter.RaceResultListAdapter;
import jp.co.equinestudio.racing.dao.DbHelper;
import jp.co.equinestudio.racing.dao.green.DaoSession;
import jp.co.equinestudio.racing.dao.green.FavoriteHorse;
import jp.co.equinestudio.racing.dao.green.FavoriteHorseDao;
import jp.co.equinestudio.racing.fragment.dialog.RaceMemberControlDialog;
import jp.co.equinestudio.racing.logic.AssetsLogic;
import jp.co.equinestudio.racing.model.Race;
import jp.co.equinestudio.racing.model.RaceData;
import jp.co.equinestudio.racing.model.RaceMember;
import jp.co.equinestudio.racing.util.FavoriteHorseUtils;
import jp.co.equinestudio.racing.util.comparator.RaceMemberComparator;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

/**
 *
 */
public class RaceFragment extends Fragment implements RaceMemberControlDialog.OnRaceMemberControl {

    private static final String KEY_RACE = "KEY_RACE";
    private static final String KEY_RACE_CODE = "KEY_RACE_CODE";
    private static final String KEY_SCHEDULE_CODE = "KEY_SCHEDULE_CODE";

    private Race mRace;
    private ListView mMemberList;
    private ListView mResultList;

    private FavoriteHorseDao mFavoriteHorseDao;

    private RaceMemberListAdapter mMemberListAdapter;
    private RaceResultListAdapter mResultListAdapter;

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
        View view = inflater.inflate(R.layout.fragment_race, null);

        String raceJson = AssetsLogic.getStringAsset(getActivity(), "race/"+mRace.getScheduleCode()+"/race.static." + mRace.getCode() + ".json");
        Gson gson = new Gson();
        Type listType = new TypeToken<RaceData>() { }. getType();
        RaceData raceData = gson.fromJson(raceJson, listType);
        RaceData resultData = gson.fromJson(raceJson, listType);


        DaoSession session = DbHelper.getInstance(getActivity().getApplicationContext()).session();
        mFavoriteHorseDao = session.getFavoriteHorseDao();
        List<FavoriteHorse> favoriteHorseList = mFavoriteHorseDao.loadAll();

        Collections.sort(raceData.getRaceMembers(), new RaceMemberComparator(RaceMemberComparator.GATE_NUMBER));
        Collections.sort(resultData.getRaceMembers(), new RaceMemberComparator(RaceMemberComparator.RESULT));

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
        mResultList = (ListView) view.findViewById(R.id.list_race_result);

        mMemberListAdapter = new RaceMemberListAdapter(getActivity(), raceData);
        mMemberListAdapter.setFavoriteHorse(favoriteHorseList);
        mResultListAdapter = new RaceResultListAdapter(getActivity(), resultData);

        mMemberList.setAdapter(mMemberListAdapter);
        mResultList.setAdapter(mResultListAdapter);

        // 表示スイッチ
        TextView switcherMemberList = (TextView) view.findViewById(R.id.switcher_member_list);
        TextView switcherResultList = (TextView) view.findViewById(R.id.switcher_result_list);

        switcherMemberList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                mMemberList.setVisibility(View.VISIBLE);
                mResultList.setVisibility(View.GONE);
            }
        });

        switcherResultList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                mMemberList.setVisibility(View.GONE);
                mResultList.setVisibility(View.VISIBLE);
            }
        });

        mMemberList.setOnItemLongClickListener(mMemberLongClickListener);
        mMemberList.setOnItemClickListener(mMemberClickListener);

        return view;
    }

    private AdapterView.OnItemClickListener mMemberClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

            RaceMemberControlDialog dialog = RaceMemberControlDialog.newInstance(position, (RaceMember) mMemberListAdapter.getItem(position), mMemberListAdapter.isFavoriteHorse(position));
            dialog.setTargetFragment(RaceFragment.this, 0);
            dialog.show(fragmentManager, "tag");

        }
    };

    private AdapterView.OnItemLongClickListener mMemberLongClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(final AdapterView<?> parent, final View view, final int position, final long id) {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

            Bundle args = new Bundle();
            RaceMemberControlDialog dialog = new RaceMemberControlDialog();
            dialog.setArguments(args);
            dialog.show(fragmentManager, "tag");

            return false;
        }
    };

    @Override
    public void switchFavoriteHorse(final int position) {
        FavoriteHorseUtils favoriteHorseUtil = new FavoriteHorseUtils(mFavoriteHorseDao);
        RaceMember raceMember = (RaceMember) mMemberListAdapter.getItem(position);

        if (favoriteHorseUtil.isFavorite(raceMember.getHorseCode())) {
            if (favoriteHorseUtil.remove(raceMember.getHorseCode())) {
//                Toast.makeText(getActivity(), "お気に入りから削除しました", Toast.LENGTH_SHORT).show();
            }
        } else {
            favoriteHorseUtil.add(raceMember.getHorseCode(), raceMember.getHorseName());
//            Toast.makeText(getActivity(), "お気に入りに登録しました", Toast.LENGTH_SHORT).show();
        }
        List<FavoriteHorse> favoriteHorseList = mFavoriteHorseDao.loadAll();
        mMemberListAdapter.setFavoriteHorse(favoriteHorseList);
        mMemberListAdapter.notifyDataSetChanged();

    }
}
