package jp.co.equinestudio.racing.fragment.race;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

import jp.co.equinestudio.racing.R;
import jp.co.equinestudio.racing.adapter.RaceMemberListAdapter;
import jp.co.equinestudio.racing.dao.DbHelper;
import jp.co.equinestudio.racing.dao.green.DaoSession;
import jp.co.equinestudio.racing.dao.green.FavoriteHorse;
import jp.co.equinestudio.racing.dao.green.FavoriteHorseDao;
import jp.co.equinestudio.racing.fragment.BaseFragment;
import jp.co.equinestudio.racing.fragment.RaceBaseFragment;
import jp.co.equinestudio.racing.fragment.dialog.RaceMemberControlDialog;
import jp.co.equinestudio.racing.logic.AssetsLogic;
import jp.co.equinestudio.racing.model.Race;
import jp.co.equinestudio.racing.model.RaceData;
import jp.co.equinestudio.racing.model.RaceMember;
import jp.co.equinestudio.racing.util.FavoriteHorseUtils;
import jp.co.equinestudio.racing.util.comparator.RaceMemberComparator;

/**
 *
 */
public class RaceMemberFragment extends RaceBaseFragment implements RaceMemberControlDialog.OnRaceMemberControl {

    private static final String KEY_RACE = "KEY_RACE";

    private ListView mMemberList;
    private RaceMemberListAdapter mMemberListAdapter;
    private FavoriteHorseDao mFavoriteHorseDao;

    public static RaceMemberFragment newInstance() {
        RaceMemberFragment fragment = new RaceMemberFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_race_member, null);
        mMemberList = (ListView) view.findViewById(R.id.list_view);

        DaoSession session = DbHelper.getInstance(getActivity().getApplicationContext()).session();
        mFavoriteHorseDao = session.getFavoriteHorseDao();
        List<FavoriteHorse> favoriteHorseList = mFavoriteHorseDao.loadAll();

        mMemberListAdapter = new RaceMemberListAdapter(getActivity());
        mMemberListAdapter.setRaceData(mRaceData);
        mMemberListAdapter.setFavoriteHorse(favoriteHorseList);

        mMemberList.setAdapter(mMemberListAdapter);

        mMemberList.setOnItemLongClickListener(mMemberLongClickListener);
        mMemberList.setOnItemClickListener(mMemberClickListener);

        return view;
    }

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

    private AdapterView.OnItemClickListener mMemberClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

            RaceMemberControlDialog dialog = RaceMemberControlDialog.newInstance(position, (RaceMember) mMemberListAdapter.getItem(position), mMemberListAdapter.isFavoriteHorse(position));
            dialog.setTargetFragment(RaceMemberFragment.this, 0);
            dialog.show(fragmentManager, "tag");

        }
    };

    private AdapterView.OnItemLongClickListener mMemberLongClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(final AdapterView<?> parent, final View view, final int position, final long id) {
//            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//
//            Bundle args = new Bundle();
//            RaceMemberControlDialog dialog = new RaceMemberControlDialog();
//            dialog.setArguments(args);
//            dialog.show(fragmentManager, "tag");
//
            return false;
        }
    };

}
