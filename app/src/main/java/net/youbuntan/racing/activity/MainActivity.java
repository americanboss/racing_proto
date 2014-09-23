package net.youbuntan.racing.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.youbuntan.racing.R;
import net.youbuntan.racing.adapter.RaceListAdapter;
import net.youbuntan.racing.logic.AssetsLogic;
import net.youbuntan.racing.model.Race;
import net.youbuntan.racing.model.RaceList;
import net.youbuntan.racing.model.Schedules;
import net.youbuntan.racing.view.schedule.ScheduleView;

import java.lang.reflect.Type;


public class MainActivity extends Activity {

    private LinearLayout mScheduleContainer;
    private LinearLayout mPopupView;
    private ListView mRaceListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mScheduleContainer = (LinearLayout) findViewById(R.id.schedule_container);
        mPopupView = (LinearLayout) findViewById(R.id.popup_view);
        mRaceListView = (ListView) findViewById(R.id.popup_race_list);
        RaceListAdapter adapter = new RaceListAdapter(this);

        mRaceListView.setAdapter(adapter);

        mRaceListView.setOnItemClickListener(mRaceClickListener);

        LayoutInflater inflater = LayoutInflater.from(this);


        //開催予定をロードする
        String scheduleJson = AssetsLogic.getStringAsset(this, "schedule/schedule.static.json");
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
    }

    /**
     * 開催予定がクリックされた際のコールバックを受け取り、レース一覧のポップアップを表示する
     */
    private ScheduleView.OnScheduleSelectedListener mScheduleListener = new ScheduleView.OnScheduleSelectedListener() {
        @Override
        public void onScheduleSelect(final String scheduleCode) {
            // レース一覧を作成する

            //レース一覧をロードする
            String raceJson = AssetsLogic.getStringAsset(MainActivity.this, "race_list/race_list.static." +scheduleCode+".json");
            Gson gson = new Gson();
            Type listType = new TypeToken<RaceList>() { }. getType();
            RaceList raceList = gson.fromJson(raceJson, listType);
            RaceListAdapter adapter = new RaceListAdapter(MainActivity.this, raceList);

            mPopupView.setVisibility(View.VISIBLE);

            mRaceListView.setAdapter(adapter);
            mRaceListView.invalidateViews();

        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mPopupView.getVisibility() == View.VISIBLE) {
                mPopupView.setVisibility(View.INVISIBLE);
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 開催予定内のレースがクリックされた際のコールバックを受け取り、レースビューア画面に遷移する
     */
    private AdapterView.OnItemClickListener mRaceClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

            RaceListAdapter adapter = (RaceListAdapter) mRaceListView.getAdapter();
            Race race = (Race) adapter.getItem(position);

            Intent intent = new Intent(MainActivity.this, RaceViewerActivity.class);
            intent.putExtra(RaceViewerActivity.KEY_SCHEDULE_CODE, race.getScheduleCode());
            intent.putExtra(RaceViewerActivity.KEY_RACE_POSITION, position);

            startActivity(intent);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
