package jp.co.equinestudio.racing.fragment.race;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import jp.co.equinestudio.racing.adapter.RaceResultListAdapter;
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
public class RaceResultFragment extends RaceBaseFragment {

    private RecyclerView mResultList;
    private RaceResultListAdapter mResultListAdapter;
    private FavoriteHorseDao mFavoriteHorseDao;

    public static RaceResultFragment newInstance() {
        RaceResultFragment fragment = new RaceResultFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_race_result, null);
        mResultList = (RecyclerView) view.findViewById(R.id.list_view);

        mResultListAdapter = new RaceResultListAdapter(getContext());
        mResultListAdapter.setRaceData(mRaceData);
        mResultList.setLayoutManager(new LinearLayoutManager(getContext()));

        mResultList.setAdapter(mResultListAdapter);


        return view;
    }

}
