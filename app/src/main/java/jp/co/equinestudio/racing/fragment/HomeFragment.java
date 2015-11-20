package jp.co.equinestudio.racing.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import jp.co.equinestudio.racing.R;
import jp.co.equinestudio.racing.activity.RaceViewerActivity;
import jp.co.equinestudio.racing.adapter.RaceListAdapter;
import jp.co.equinestudio.racing.logic.AssetsLogic;
import jp.co.equinestudio.racing.model.Race;
import jp.co.equinestudio.racing.model.RaceList;
import jp.co.equinestudio.racing.model.Schedules;
import jp.co.equinestudio.racing.view.schedule.ScheduleView;

import java.lang.reflect.Type;

/**
 *
 */
public class HomeFragment extends Fragment {

    private LinearLayout mScheduleContainer;
    private LinearLayout mPopupView;
    private ListView mRaceListView;

    public static HomeFragment getNewInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, null);

        mScheduleContainer = (LinearLayout) view.findViewById(R.id.schedule_container);
        mPopupView = (LinearLayout) view.findViewById(R.id.popup_view);
        mRaceListView = (ListView) view.findViewById(R.id.popup_race_list);
        RaceListAdapter adapter = new RaceListAdapter(getActivity());

        mRaceListView.setAdapter(adapter);

        mRaceListView.setOnItemClickListener(mRaceClickListener);

        //開催予定をロードする
        String scheduleJson = AssetsLogic.getStringAsset(getActivity(), "schedule/schedule.static.json");
        Gson gson = new Gson();
        Type listType = new TypeToken<Schedules>() { }. getType();
        Schedules schedules = gson.fromJson(scheduleJson, listType);

        // 日付ごとに処理
        for (Schedules.ScheduleDate scheduleDate : schedules.getScheduleDates()) {
            LinearLayout scheduleRow = (LinearLayout) inflater.inflate(R.layout.parts_schedule_selecter_row, null);
            ScheduleView columnA = (ScheduleView) scheduleRow.findViewById(R.id.schedule_container_column_a);
            ScheduleView columnB = (ScheduleView) scheduleRow.findViewById(R.id.schedule_container_column_b);
            ScheduleView columnC = (ScheduleView) scheduleRow.findViewById(R.id.schedule_container_column_c);

            columnA.setListener(mScheduleListener);
            columnB.setListener(mScheduleListener);
            columnC.setListener(mScheduleListener);

            // 開催場の数で見えなくなるカラムの設定
            if (scheduleDate.getSchedules().size() < 3) {
                columnC.setVisibility(View.GONE);
            }
            if (scheduleDate.getSchedules().size() < 2) {
                columnB.setVisibility(View.GONE);
            }

            int count = 0;
            for (Schedules.Schedule schedule : scheduleDate.getSchedules()) {
                switch (count) {
                    case 0 : columnA.setSchedule(schedule); break;
                    case 1 : columnB.setSchedule(schedule); break;
                    case 2 : columnC.setSchedule(schedule); break;
                }
                count++;
            }
            mScheduleContainer.addView(scheduleRow);
        }


        return view;
    }
    /**
     * 開催予定がクリックされた際のコールバックを受け取り、レース一覧のポップアップを表示する
     */
    private ScheduleView.OnScheduleSelectedListener mScheduleListener = new ScheduleView.OnScheduleSelectedListener() {
        @Override
        public void onScheduleSelect(final String scheduleCode) {
            // レース一覧を作成する

            //レース一覧をロードする
            String raceJson = AssetsLogic.getStringAsset(getActivity(), "race_list/race_list.static." +scheduleCode+".json");
            Gson gson = new Gson();
            Type listType = new TypeToken<RaceList>() { }. getType();
            RaceList raceList = gson.fromJson(raceJson, listType);
            RaceListAdapter adapter = new RaceListAdapter(getActivity(), raceList);

            mPopupView.setVisibility(View.VISIBLE);

            mRaceListView.setAdapter(adapter);
            mRaceListView.invalidateViews();

        }
    };

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mPopupView.getVisibility() == View.VISIBLE) {
                mPopupView.setVisibility(View.INVISIBLE);
                return true;
            }
        }
        return false;
        //return super.onKeyDown(keyCode, event);
    }

    /**
     * 開催予定内のレースがクリックされた際のコールバックを受け取り、レースビューア画面に遷移する
     */
    private AdapterView.OnItemClickListener mRaceClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

            RaceListAdapter adapter = (RaceListAdapter) mRaceListView.getAdapter();
            Race race = (Race) adapter.getItem(position);

            Log.v("getScheduleCode", "getScheduleCode : " + race.getScheduleCode());
            Log.v("position", "position : " + position);

            Intent intent = new Intent(getActivity(), RaceViewerActivity.class);
            intent.putExtra(RaceViewerActivity.KEY_SCHEDULE_CODE, race.getScheduleCode());
            intent.putExtra(RaceViewerActivity.KEY_RACE_POSITION, position);

            startActivity(intent);
        }
    };


}
