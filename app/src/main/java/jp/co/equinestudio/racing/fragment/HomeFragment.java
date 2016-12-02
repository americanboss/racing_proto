package jp.co.equinestudio.racing.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import jp.co.equinestudio.racing.FragmentTransferListener;
import jp.co.equinestudio.racing.R;
import jp.co.equinestudio.racing.ScheduleRaceListener;
import jp.co.equinestudio.racing.adapter.HomeRecyclerAdapter;
import jp.co.equinestudio.racing.adapter.item.HomeListItem;
import jp.co.equinestudio.racing.logic.AssetsLogic;
import jp.co.equinestudio.racing.model.Race;
import jp.co.equinestudio.racing.model.RaceList;
import jp.co.equinestudio.racing.model.Schedules;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class HomeFragment extends Fragment implements ScheduleRaceListener {

    private RecyclerView mRecyclerView;
    private HomeRecyclerAdapter mHomeRecyclerAdapter;

    private FragmentTransferListener mFragmentTransferListener;

    public static HomeFragment getNewInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, null);

        if (getActivity() instanceof FragmentTransferListener) {
            mFragmentTransferListener = (FragmentTransferListener) getActivity();
        }

        mRecyclerView = (RecyclerView) view.findViewById(R.id.list_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        mHomeRecyclerAdapter = new HomeRecyclerAdapter(getActivity().getApplicationContext(), this);

        //開催予定をロードする
        String scheduleJson = AssetsLogic.getStringAsset(getActivity(), "schedule/schedule.static.json");
//        String scheduleJson = AssetsLogic.getStringAsset(getActivity(), "schedule/schedule.test.json");
        Gson gson = new Gson();
        Type listType = new TypeToken<Schedules>() { }. getType();
        Schedules schedules = gson.fromJson(scheduleJson, listType);

        // メインレースのリストをロードする
        List<HomeListItem> mainRaceItems = new ArrayList<>();
        for (Race race : schedules.getMainRaces()) {
            HomeListItem item = new HomeListItem();
            item.setTypeId(HomeListItem.TYPE_ID_MAIN_RACE);
            item.setRace(race);
            mainRaceItems.add(item);
        }
        mHomeRecyclerAdapter.setMainRaceItems(mainRaceItems);

        // 開催日付ごとに処理
        List<HomeListItem> scheduleListItems = new ArrayList<>();
        for (Schedules.ScheduleDate scheduleDate : schedules.getScheduleDates()) {

            for (Schedules.Schedule schedule : scheduleDate.getSchedules()) {
                HomeListItem item = new HomeListItem();
                item.setTypeId(HomeListItem.TYPE_ID_SCHEDULE);
                item.setSchedule(schedule);
                scheduleListItems.add(item);

                mHomeRecyclerAdapter.setScheduleVisibility(schedule.getCode(), false);

                String raceJson = AssetsLogic.getStringAsset(getActivity(), "race_list/race_list.static." +schedule.getCode()+".json");
                gson = new Gson();
                listType = new TypeToken<RaceList>() { }. getType();
                RaceList raceList = gson.fromJson(raceJson, listType);
                for (Race race : raceList.getRaces()) {
                    mHomeRecyclerAdapter.putScheduleRaceItem(schedule.getCode(), race);
                }
            }
        }
        mHomeRecyclerAdapter.setScheduleItems(scheduleListItems);

        mHomeRecyclerAdapter.buildItems();

        mRecyclerView.setAdapter(mHomeRecyclerAdapter);


        return view;
    }
    /**
     * 開催予定内のレースがクリックされた際のコールバックを受け取り、レースビューア画面に遷移する
     */
    private AdapterView.OnItemClickListener mRaceClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

//            RaceListAdapter adapter = (RaceListAdapter) mRaceListView.getAdapter();
//            Race race = (Race) adapter.getItem(position);
//
//            Log.v("getScheduleCode", "getScheduleCode : " + race.getScheduleCode());
//            Log.v("position", "position : " + position);
//
//            Intent intent = new Intent(getActivity(), RaceViewerActivity.class);
//            intent.putExtra(RaceViewerActivity.KEY_SCHEDULE_CODE, race.getScheduleCode());
//            intent.putExtra(RaceViewerActivity.KEY_RACE_POSITION, position);
//
//            startActivity(intent);
        }
    };


    @Override
    public void onScheduleSelected(final String scheduleCode) {
        mHomeRecyclerAdapter.setScheduleVisibility(scheduleCode, !mHomeRecyclerAdapter.getScheduleVisibility(scheduleCode));
        mHomeRecyclerAdapter.buildItems();
        mHomeRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRaceSelected(final String scheduleCode, final int racePosition) {
        if (mFragmentTransferListener != null) {
            mFragmentTransferListener.replaceRaceViewerFragment(scheduleCode, racePosition);
        }
    }

}
